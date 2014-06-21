package jp.goka.favos.ui;

import android.os.Bundle;
import android.view.MenuItem;
import jp.goka.favos.R;


public class MediaDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(MediaDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(MediaDetailFragment.ARG_ITEM_ID));
            MediaDetailFragment fragment = new MediaDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.photo_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
    }
}
