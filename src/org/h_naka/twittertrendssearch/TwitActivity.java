package org.h_naka.twittertrendssearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class TwitActivity
    extends Activity
    implements OnClickListener {

    private String m_trend;
    
    private Button m_backButton;
    private Button m_updateButton;
    private ListView m_twitListView;
    private TwitList m_twitList;
    private TwitDataProcess m_twitDataProcess;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twit);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        initInstance();
        setInterface();

        setTitle(m_trend);
        m_twitDataProcess.getTwit();
    }

    @Override
    protected void onDestroy() {
        m_twitDataProcess.checkIconDownloadExecute();
        super.onDestroy();
    }
    
    private void initInstance() {
        Intent intent = getIntent();
        m_trend = intent.getStringExtra("TREND");
        m_backButton = (Button)findViewById(R.id.backButton);
        m_updateButton = (Button)findViewById(R.id.updateButton);
        m_twitListView = (ListView)findViewById(R.id.twitListView);
        m_twitList = new TwitList(this);
        m_twitDataProcess = new TwitDataProcess(this,m_trend);
    }

    private void setInterface() {
        m_backButton.setOnClickListener(this);
        m_updateButton.setOnClickListener(m_twitDataProcess);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.backButton:
            finish();
            break;
        }
    }

    public ListView getTwitListView() {
        return m_twitListView;
    }

    public TwitList getTwitList() {
        return m_twitList;
    }

    public Button getUpdateButton() {
        return m_updateButton;
    }
}