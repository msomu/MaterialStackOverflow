package in.msomu.materialstackoverflow.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import in.msomu.materialstackoverflow.utils.Const;
import in.msomu.materialstackoverflow.adapter.FeedAdapter;
import in.msomu.materialstackoverflow.R;

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

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        setupViewPager(mViewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        feedAdapter = new FeedAdapter(getSupportFragmentManager());
        feedAdapter.addFragment(FeedFragment.newInstance(Const.SORT_BY_ACTIVITY), "Activity");
        feedAdapter.addFragment(FeedFragment.newInstance(Const.SORT_BY_HOT), "Hot");
        feedAdapter.addFragment(FeedFragment.newInstance(Const.SORT_BY_VOTES), "Votes");
        feedAdapter.addFragment(FeedFragment.newInstance(Const.SORT_BY_CREATION_DATE), "Create");
        feedAdapter.addFragment(FeedFragment.newInstance(Const.MY_ACTIVITIES), "My Activities");
        viewPager.setAdapter(feedAdapter);
    }

}
