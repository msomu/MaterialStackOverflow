package in.msomu.materialstackoverflow.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.msomu.materialstackoverflow.Const;
import in.msomu.materialstackoverflow.R;


public class FeedFragment extends Fragment {

    private String sortOrder;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sortOrder Sort Order.
     * @return A new instance of fragment FeedFragment.
     */
    public static FeedFragment newInstance(String sortOrder) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(Const.SORT_ORDER, sortOrder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sortOrder = getArguments().getString(Const.SORT_ORDER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }
}
