package org.h_naka.twittertrendssearch;

import android.graphics.Bitmap;

public class TwitData {
    private Bitmap m_icon;
    private String m_twit;

    public TwitData(String twit,Bitmap icon) {
        m_twit = twit;
    	m_icon = icon;
    }

    public String getTwit() {
        return m_twit;
    }

    public void setTwit(String twit) {
        m_twit = twit;
    }

    public Bitmap getIcon() {
        return m_icon;
    }

    public void setIcon(Bitmap icon) {
        m_icon = icon;
    }
}
