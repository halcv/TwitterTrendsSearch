package org.h_naka.twittertrendssearch;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import org.json.JSONArray;
import org.json.JSONException;

public class TrendsJsonDataDownloadAsyncTask extends
		AsyncTask<String, Void, JSONArray> {

    private TrendsActivity m_activity;
    private OnTrendsJsonDataDownloadListener m_listener = null;
    private ProgressDialog m_dialog;
    
    public TrendsJsonDataDownloadAsyncTask(TrendsActivity activity) {
        m_activity = activity;
    }
    
    @Override
    protected void onPreExecute() {
        m_dialog = new ProgressDialog(m_activity);
        m_dialog.setIndeterminate(true);
        m_dialog.setCancelable(false);
        m_dialog.setMessage(m_activity.getResources().getString(R.string.getTrends));
        m_dialog.show();
    }
    
    @Override
    protected JSONArray doInBackground(String... param) {
        return getJsonDataFromUrl(param[0]);
    }

    @Override
    protected void onPostExecute(JSONArray jArray) {
        if (m_listener != null) {
            m_listener.onFinishTrendsJsonDataDownload(jArray);
        }
        m_dialog.dismiss();
    }

    public void setOnTrendsJsonDataDownloadListener(OnTrendsJsonDataDownloadListener listener) {
        m_listener = listener;
    }

    private JSONArray getJsonDataFromUrl(String url) {
        JSONArray m_jArray;
        String data = new DataDownload(url).getData();
        if (data == null) {
        	return null;
        }
        
        // try parse the string to a JSON object
        try {
            m_jArray = new JSONArray(data);
        } catch(JSONException e) {
            e.printStackTrace();
            m_jArray = null;;
        } 

        return m_jArray;
    }
}

interface OnTrendsJsonDataDownloadListener {
    public void onFinishTrendsJsonDataDownload(JSONArray jArray);
}
