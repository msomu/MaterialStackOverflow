package in.msomu.materialstackoverflow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msomu on 27/06/16.
 * Implementation of PagerAdapter that uses a Fragment to manage each page.
 * This class also handles saving and restoring of fragment's state.
 */
public class FeedAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    /**
     * @param fragmentManager This is the Fragment Manager used to manage the fragments in your activity, you need to use FragmentManager.
     */
    public FeedAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    /**
     *
     * @param position gives the postion of the fragment
     * @return the positions Fragment
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Gives the total number of fragments
     * @return the size of the number of fragments
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * This method is used to add the fragment to the list, this is called from MainActivity.
     * @param fragment Fragment to be added
     * @param title Fragments Tab Title
     */
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    /**
     * Used to set the title for the tab
     * @param position position of the tab
     * @return the charsequesnce of the tab name
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
