package jp.goka.favos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import jp.goka.favos.R;


public class MediaListActivity extends BaseActivity
        implements MediaListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        if (findViewById(R.id.photo_detail_container) != null) {
            mTwoPane = true;

            ((MediaListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.photo_list))
                    .setActivateOnItemClick(true);
        }
	}

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(MediaDetailFragment.ARG_ITEM_ID, id);
            MediaDetailFragment fragment = new MediaDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.photo_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, MediaDetailActivity.class);
            detailIntent.putExtra(MediaDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
