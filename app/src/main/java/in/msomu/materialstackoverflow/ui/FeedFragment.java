package in.msomu.materialstackoverflow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.msomu.materialstackoverflow.AppController;
import in.msomu.materialstackoverflow.utils.Const;
import in.msomu.materialstackoverflow.utils.PreferencesHelper;
import in.msomu.materialstackoverflow.models.Question;
import in.msomu.materialstackoverflow.R;
import in.msomu.materialstackoverflow.adapter.QuestionsAdapter;
import in.msomu.materialstackoverflow.widgets.DividerItemDecoration;


public class FeedFragment extends Fragment {
    private static final String TAG = "FeedFragment";
    private String sortOrder = "";
    private String baseUrl = "https://api.stackexchange.com/2.2/";
    private String baseUrl1 = "questions";
    private String baseUrl2 = "?order=desc&sort=";
    private String baseUrl3 = "&site=stackoverflow";
    private ArrayList<Question> questions = new ArrayList<>();
    private QuestionsAdapter adapter;
    private boolean isUserLoggedIn = false;
    private TextView login;

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

    /**
     * Method to make json object request where json response starts wtih {
     */
    private void makeJsonObjectRequest() {

        showpDialog();

        String url = "";

        if (sortOrder.equals(Const.MY_ACTIVITIES)) {
            if (PreferencesHelper.getLoginCheck() && !TextUtils.isEmpty(PreferencesHelper.getUserID())) {
                url = baseUrl + "users/" + PreferencesHelper.getUserID() + "/" + baseUrl1 + baseUrl2 + baseUrl3;
                isUserLoggedIn = true;
            } else {
                Log.e(TAG, "User not logged in");
                isUserLoggedIn = false;
            }
        } else {
            url = baseUrl + baseUrl1 + baseUrl2 + sortOrder + baseUrl3;
        }
        if (!TextUtils.isEmpty(url)) {
            Log.d(TAG,"Pinging url : "+url);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());
                    parseJson(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), getResources().getString(R.string.error_on_network), Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);
        }
    }

    private void hidepDialog() {
        //TODO hide the dialog
    }

    private void showpDialog() {
        //TODO show the dialog
    }

    private void parseJson(JSONObject response) {
        questions.clear();
        try {
            // Parsing json object response
            // response will be a json object
            JSONArray items = response.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONArray tags = item.getJSONArray("tags");
                String[] tagsArray = new String[tags.length()];
                for (int j = 0; j < tags.length(); j++) {
                    tagsArray[j] = tags.getString(j);
                }
                String quest = item.getString("title");
                JSONObject owner = item.getJSONObject("owner");
                String userName = owner.getString("display_name");
                String timeStamp = item.getString("creation_date");
                int upVote = item.getInt("score");
                Question question = new Question(quest, userName, timeStamp, upVote, tagsArray);
                questions.add(question);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();

        }
        hidepDialog();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (getArguments() != null) {
                sortOrder = getArguments().getString(Const.SORT_ORDER);
                makeJsonObjectRequest();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.feedRecylerView);
        login = (TextView) rootView.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        setRecyclerAdapter(recyclerView);
        return rootView;
    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        adapter = new QuestionsAdapter(getContext(), questions);
        recyclerView.setAdapter(adapter);
        if (sortOrder.equals(Const.MY_ACTIVITIES)) {
            if (!isUserLoggedIn) {
                recyclerView.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
            }
        }
    }
}
