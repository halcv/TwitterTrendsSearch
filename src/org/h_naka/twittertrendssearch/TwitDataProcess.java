package org.h_naka.twittertrendssearch;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import android.view.View;
import android.view.View.OnClickListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.Toast;
import android.view.Gravity;
import android.graphics.Bitmap;

public class TwitDataProcess
    implements OnClickListener,
               OnTwitJsonDataDownloadListener,
               OnIconDownloadListener {

    private TwitActivity m_activity;
    private String m_trend;
    private ArrayList<String> m_iconUrlList;
    private boolean m_isIconDownloadExecute;
    private boolean m_isIconDownloadCancel;
    private ArrayList<IconDataCache> m_iconDataCacheList;
    
    public TwitDataProcess(TwitActivity activity,String trend) {
        m_activity = activity;
        m_trend = trend;
        m_iconUrlList = new ArrayList<String>();
        m_isIconDownloadExecute = false;
        m_isIconDownloadCancel = false;
        m_iconDataCacheList = new ArrayList<IconDataCache>();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.updateButton:
            checkIconDownloadExecute();
            getJsonData();
            break;
        }
    }

    @Override
    public void onFinishTwitJsonDataDownload(JSONObject json) {
        if (json == null) {
            createToast(m_activity.getResources().getString(R.string.getTwitError));
            return;
        }
        
        try {
            JSONArray twit = json.getJSONArray("results");
            int cnt = twit.length();
            if (cnt == 0) {
                createToast(m_activity.getResources().getString(R.string.getTwitError));            }
            
            for (int i = 0;i < cnt;i++) {
                JSONObject e = twit.getJSONObject(i);
                add(e.getString("from_user") + "\n" + e.getString("text"));
                m_iconUrlList.add(e.getString("profile_image_url"));
            }
            m_activity.getUpdateButton().setEnabled(true);
            m_isIconDownloadExecute = true;
            IconDownloadTask task = new IconDownloadTask(this,m_iconUrlList,m_iconDataCacheList);
            task.setOnIconDownloadListener(this);
            new Thread(task).start();
        } catch(JSONException e) {
            e.printStackTrace();
            createToast(m_activity.getResources().getString(R.string.getTwitError));
        }
    }

    private void getJsonData() {
        m_iconUrlList.clear();
        deleteAll();
        TwitJsonDataDownloadAsyncTask task = new TwitJsonDataDownloadAsyncTask(m_activity);
        task.setOnTwitJsonDataDownloadListener(this);
        StringBuilder builder = new StringBuilder();
        builder.append(m_activity.getResources().getString(R.string.twitURL1));
        try {
            builder.append(URLEncoder.encode(m_trend,"utf-8"));
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        builder.append(m_activity.getResources().getString(R.string.twitURL2));
        task.execute(builder.toString());
    }

    private void createToast(String message) {
        Toast toast = Toast.makeText(m_activity,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void add(String out) {
        m_activity.getTwitList().addTwit(out);
    }
    
    private void deleteAll() {
        m_activity.getTwitList().deleteAllTwit();
    }

    public void getTwit() {
        getJsonData();
    }

    public boolean isIconDownloadExecute() {
        return m_isIconDownloadExecute;
    }

    public void onFinishIconDownload() {
        m_isIconDownloadExecute = false;
        m_isIconDownloadCancel = false;
    }

    public void onAddIcon(int index,Bitmap icon) {
        m_activity.getTwitList().addIcon(index,icon);
    }

    public void checkIconDownloadExecute() {
        if (m_isIconDownloadExecute == true) {
            m_activity.getUpdateButton().setEnabled(false);
            waitIconDownloadFinish();
        }
    }
    
    public void waitIconDownloadFinish() {
        m_isIconDownloadCancel = true;
        while(m_isIconDownloadExecute == true) {
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isIconDownloadCancel() {
        return m_isIconDownloadCancel;
    }
}
