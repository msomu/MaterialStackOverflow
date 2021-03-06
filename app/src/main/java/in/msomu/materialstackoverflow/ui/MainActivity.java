package in.msomu.materialstackoverflow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import in.msomu.materialstackoverflow.utils.Const;
import in.msomu.materialstackoverflow.adapter.FeedAdapter;
import in.msomu.materialstackoverflow.R;
import in.msomu.materialstackoverflow.utils.PreferencesHelper;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager.setAdapter(feedAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        feedAdapter = new FeedAdapter(getSupportFragmentManager());
        feedAdapter.addFragment(FeedFragment.newInstance(Const.SORT_BY_ACTIVITY), getString(R.string.activity));
        feedAdapter.addFragment(FeedFragment.newInstance(Const.SORT_BY_HOT), getString(R.string.hot));
        feedAdapter.addFragment(FeedFragment.newInstance(Const.SORT_BY_VOTES), getString(R.string.votes));
        feedAdapter.addFragment(FeedFragment.newInstance(Const.SORT_BY_CREATION_DATE), getString(R.string.create));
        feedAdapter.addFragment(FeedFragment.newInstance(Const.MY_ACTIVITIES), getString(R.string.activities));
        viewPager.setAdapter(feedAdapter);
    }

    // Initiating Menu XML file (menu_main.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (PreferencesHelper.getLoginCheck()) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Toast.makeText(this, R.string.loggingout, Toast.LENGTH_SHORT).show();
            PreferencesHelper.setLoginCheck(false);
            startActivity(new Intent(this,SplashActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
