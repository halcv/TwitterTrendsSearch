package org.h_naka.twittertrendssearch;

import android.graphics.Bitmap;

public class IconDataCache {
    private String m_url;
    private Bitmap m_icon;

    public IconDataCache(String url,Bitmap icon) {
        m_url = url;
        m_icon = icon;
    }
    
    public void setUrl(String url) {
        m_url = url;
    }

    public String getUrl() {
        return m_url;
    }

    public void setIcon(Bitmap icon) {
        m_icon = icon;
    }

    public Bitmap getIcon() {
        return m_icon;
    }
}
