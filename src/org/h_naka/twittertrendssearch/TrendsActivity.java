package org.h_naka.twittertrendssearch;

import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.app.Activity;
import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;
import android.view.WindowManager.LayoutParams;

public class TrendsActivity extends Activity {

    private ListView m_trendsListView;
    private Button m_getButton;
    private Button m_searchButton;
    private TrendsList m_trendsList;
    private TrendsDataProcess m_trendsDataProcess;
    private SearchTwit m_searchTwit;
    private EditText m_searchEdit;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // ソフトキーボードを表示させない
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_trends);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initInstance();
        setInterface();
        m_trendsDataProcess.getTrends();
    }

    private void initInstance() {
        m_trendsListView = (ListView)findViewById(R.id.trendsListView);
        m_getButton = (Button)findViewById(R.id.getButton);
        m_searchButton = (Button)findViewById(R.id.searchButton);
        m_trendsList = new TrendsList(this);
        m_trendsDataProcess = new TrendsDataProcess(this);
        m_searchTwit = new SearchTwit(this);
        m_searchEdit = (EditText)findViewById(R.id.searchEdit);
    }

    private void setInterface() {
        m_trendsListView.setOnItemClickListener(m_trendsList);
        m_getButton.setOnClickListener(m_trendsDataProcess);
        m_searchButton.setOnClickListener(m_searchTwit);
    }

    public ListView getTrendsListView() {
        return m_trendsListView;
    }

    public TrendsList getTrendsList() {
        return m_trendsList;
    }

    public void goTwitActivity(String trend) {
        Intent intent = new Intent(this,TwitActivity.class);
        intent.putExtra("TREND",trend);
        startActivity(intent);
    }

    public EditText getSearchEdit() {
        return m_searchEdit;
    }
}
