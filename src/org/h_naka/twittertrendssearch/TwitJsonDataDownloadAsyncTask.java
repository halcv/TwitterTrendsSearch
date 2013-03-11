package org.h_naka.twittertrendssearch;

import android.os.AsyncTask;
import android.app.ProgressDialog;

import org.json.JSONObject;
import org.json.JSONException;

public class TwitJsonDataDownloadAsyncTask extends
		AsyncTask<String, Void, JSONObject> {

    private OnTwitJsonDataDownloadListener m_listener = null;
    private TwitActivity m_activity;
    private ProgressDialog m_dialog;
    
    public TwitJsonDataDownloadAsyncTask(TwitActivity activity) {
        m_activity = activity;
    }

    @Override
    protected void onPreExecute() {
        m_dialog = new ProgressDialog(m_activity);
        m_dialog.setIndeterminate(true);
        m_dialog.setCancelable(false);
        m_dialog.setMessage(m_activity.getResources().getString(R.string.getTwit));
        m_dialog.show();
    }
    
    @Override
    protected JSONObject doInBackground(String... param) {
        return getJsonDataFromUrl(param[0]);
    }


    @Override
    protected void onPostExecute(JSONObject jArray) {
        m_listener.onFinishTwitJsonDataDownload(jArray);
        m_dialog.dismiss();
    }
    
    public void setOnTwitJsonDataDownloadListener(OnTwitJsonDataDownloadListener listener) {
        m_listener = listener;
    }

    private JSONObject getJsonDataFromUrl(String url) {
        JSONObject m_jArray;
        String data = new DataDownload(url).getData();
        if (data == null) {
            return null;
        }
        
        // try parse the string to a JSON object
        try {
            m_jArray = new JSONObject(data);
        } catch(JSONException e) {
            e.printStackTrace();
            m_jArray = null;
        }

        return m_jArray;
    }
}

interface OnTwitJsonDataDownloadListener {
    public void onFinishTwitJsonDataDownload(JSONObject jArray);
}
