package org.h_naka.twittertrendssearch;

import java.util.ArrayList;

import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.view.View;

public class TrendsList implements OnItemClickListener {

    private TrendsActivity m_activity;
    private ArrayAdapter<String> m_adapter;
    private ArrayList<String> m_arrayList;
    
    public TrendsList(TrendsActivity activity) {
        m_activity = activity;
        m_arrayList = new ArrayList<String>();
        m_adapter = new ArrayAdapter<String>(m_activity,R.layout.trends,m_arrayList);
        m_activity.getTrendsListView().setAdapter(m_adapter);
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent,View v,int pos,long id) {
        switch(v.getId()) {
        case R.id.trendsTextView:
            selectItem(pos);
            break;
        }
    }

    public void addTrend(String out) {
        m_adapter.add(out);
    }

    public void deleteAllTrends() {
        m_adapter.clear();
    }

    private void selectItem(int pos) {
        m_activity.goTwitActivity(m_adapter.getItem(pos));
    }
}
