package org.h_naka.twittertrendssearch;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URL;
import java.net.MalformedURLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

public class IconDownloadTask implements Runnable {

    private ArrayList<String> m_iconUrlList;
    private Handler handler;
    private OnIconDownloadListener m_listener;
    private TwitDataProcess m_process;
    private ArrayList<IconDataCache> m_cacheList;
    private Bitmap m_bitmap;
    
    public IconDownloadTask(TwitDataProcess process,ArrayList<String> urlList,ArrayList<IconDataCache> cacheList) {
        m_iconUrlList = urlList;
        handler = new Handler();
        m_process = process;
        m_cacheList = cacheList;
    }
    
	@Override
	public void run() {
        for (int i = 0;i < m_iconUrlList.size();i++) {
            if (m_process.isIconDownloadCancel() == true) {
                break;
            }
            m_bitmap = null;
            InputStream is = null;
            boolean isCache = false;

            isCache = isIconDataCacheExist(m_iconUrlList.get(i));

            if (isCache == false) {
                try {
                    URL url = new URL(m_iconUrlList.get(i));
                    is = url.openStream();
                    m_bitmap = BitmapFactory.decodeStream(is);
                } catch(MalformedURLException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            final int index = i;
            final Bitmap icon = m_bitmap;
            if (m_bitmap != null) {
                if (isCache == false) {
                    saveIconDataCache(m_iconUrlList.get(i));
                }
                handler.post(new Runnable() {
                        @Override
                        public void run() {
                            m_listener.onAddIcon(index,icon);
                        }
                    });
            }
        }

        m_listener.onFinishIconDownload();
	}

    private boolean isIconDataCacheExist(String url) {
        boolean ret = false;
        for (int i = 0;i < m_cacheList.size();i++) {
            if (m_cacheList.get(i).getUrl().equals(url) == true) {
                m_bitmap = m_cacheList.get(i).getIcon();
                ret = true;
            }
        }

        return ret;
    }

    private void saveIconDataCache(String url) {
        m_cacheList.add(new IconDataCache(url,m_bitmap));
    }
    
    public void setOnIconDownloadListener(OnIconDownloadListener listener) {
        m_listener = listener;
    }
}

interface OnIconDownloadListener {
    public void onFinishIconDownload();
    public void onAddIcon(int index,Bitmap icon);
}
