package org.h_naka.twittertrendssearch;

import android.view.View;
import android.view.View.OnClickListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.Toast;
import android.view.Gravity;

public class TrendsDataProcess
    implements OnClickListener, OnTrendsJsonDataDownloadListener {

    private TrendsActivity m_activity;
    
    public TrendsDataProcess(TrendsActivity activity) {
        m_activity = activity;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.getButton:
            getJsonData();
            break;
        }
    }

    @Override
    public void onFinishTrendsJsonDataDownload(JSONArray jArray) {
        if (jArray == null) {
            createToast(m_activity.getResources().getString(R.string.getTrendsError));
            return;
        }
        
        JSONObject json;
        try {
            json = jArray.getJSONObject(0);
            JSONArray trends = json.getJSONArray("trends");
            int cnt = trends.length();
            if (cnt == 0) {
                createToast(m_activity.getResources().getString(R.string.getTrendsError));            }
            
            for (int i = 0;i < cnt;i++) {
                JSONObject e = trends.getJSONObject(i);
                add(e.getString("name"));
            }
        } catch(JSONException e) {
            e.printStackTrace();
            createToast(m_activity.getResources().getString(R.string.getTrendsError));
        }
    }
    
    private void getJsonData() {
        deleteAll();
        TrendsJsonDataDownloadAsyncTask task = new TrendsJsonDataDownloadAsyncTask(m_activity);
        task.setOnTrendsJsonDataDownloadListener(this);
        task.execute(m_activity.getResources().getString(R.string.trendsURL));
    }

    private void createToast(String message) {
        Toast toast = Toast.makeText(m_activity,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void add(String out) {
        m_activity.getTrendsList().addTrend(out);
    }

    private void deleteAll() {
        m_activity.getTrendsList().deleteAllTrends();
    }

    public void getTrends() {
        getJsonData();
    }
}
