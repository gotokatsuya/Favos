package jp.goka.favos.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import com.android.volley.Response;
import jp.goka.favos.adapter.MediaAdapter;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Media;
import jp.goka.favos.request.MediaRequest;

import java.util.List;


public class MediaListFragment extends BaseListFragment {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;

    private int mActivatedPosition = ListView.INVALID_POSITION;

    public interface Callbacks {
        public void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {}
    };

    public MediaListFragment() {
    }

	MediaAdapter mediaArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		mediaArrayAdapter = new MediaAdapter(getActivity());

		MediaRequest.popular(new Response.Listener<List<Media>>() {
			@Override
			public void onResponse(List<Media> medias) {
				if (!medias.isEmpty()) {
					mediaArrayAdapter.addAll(medias);
				}
			}
		}, new Response.Listener<InstagramError>() {
			@Override
			public void onResponse(InstagramError response) {

			}
		});
        setListAdapter(mediaArrayAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
		mCallbacks.onItemSelected(mediaArrayAdapter.getItem(position).getIdentity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }
        mActivatedPosition = position;
    }
}
