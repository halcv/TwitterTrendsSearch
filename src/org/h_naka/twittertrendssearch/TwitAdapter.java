package org.h_naka.twittertrendssearch;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

public class TwitAdapter extends ArrayAdapter<TwitData> {
    private LayoutInflater m_layoutInflater;
    
	public TwitAdapter(Context context, int textViewResourceId,List<TwitData> objects) {
		super(context, textViewResourceId, objects);
        m_layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
        if (null == convertView) {
            convertView = m_layoutInflater.inflate(R.layout.twit, null);
        }

        TwitData item = (TwitData)getItem(position);
        // CustomDataのデータをViewの各Widgetにセットする
        ImageView imageView;
        imageView = (ImageView)convertView.findViewById(R.id.iconImageView);
        imageView.setImageBitmap(item.getIcon());
 
        TextView textView;
        textView = (TextView)convertView.findViewById(R.id.twitTextView);
        textView.setText(item.getTwit());
        
        return convertView;
    }
}
