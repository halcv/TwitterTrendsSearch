package org.h_naka.twittertrendssearch;

import android.view.View;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SearchTwit implements OnClickListener {
    private TrendsActivity m_activity;

    public SearchTwit(TrendsActivity activity) {
        m_activity = activity;
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
        case R.id.searchButton:
            searchTwitProcess();
            break;
        }
	}

    private void searchTwitProcess() {
        String search = m_activity.getSearchEdit().getText().toString();
        if (search.equals("")) {
            createToast();
            return;
        }
        m_activity.getSearchEdit().setText("");
        m_activity.goTwitActivity(search);
    }

    private void createToast() {
        Toast toast = Toast.makeText(m_activity,
                                     m_activity.getResources().getString(R.string.noSearch),
                                     Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
