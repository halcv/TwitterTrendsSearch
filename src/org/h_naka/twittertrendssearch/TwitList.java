package org.h_naka.twittertrendssearch;

import java.util.List;
import java.util.ArrayList;
import java.lang.IndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TwitList {
    private TwitActivity m_activity;
    private TwitAdapter m_adapter;
    private List<TwitData> m_arrayList;
    private Bitmap m_icon;
    
    public TwitList(TwitActivity activity) {
        m_activity = activity;
        m_arrayList = new ArrayList<TwitData>();
        m_adapter = new TwitAdapter(m_activity,-1,m_arrayList);
        m_activity.getTwitListView().setAdapter(m_adapter);
        m_icon = BitmapFactory.decodeResource(m_activity.getResources(), R.drawable.image);
    }

    public void addTwit(String out) {
        m_adapter.add(new TwitData(out,m_icon));
    }

    public void deleteAllTwit() {
        m_adapter.clear();
    }

    public void addIcon(int index,Bitmap icon) {
        try {
            TwitData item = (TwitData)m_adapter.getItem(index);
            item.setIcon(icon);
            m_adapter.notifyDataSetChanged();
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
