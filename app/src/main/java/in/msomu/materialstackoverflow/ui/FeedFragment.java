package in.msomu.materialstackoverflow.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.msomu.materialstackoverflow.AppController;
import in.msomu.materialstackoverflow.Const;
import in.msomu.materialstackoverflow.Question;
import in.msomu.materialstackoverflow.R;
import in.msomu.materialstackoverflow.adapter.QuestionsAdapter;


public class FeedFragment extends Fragment {
    private static final String TAG = "FeedFragment";
    private String sortOrder;
    private String urlJsonObj = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&site=stackoverflow";
    private ArrayList<Question> questions = new ArrayList<>();
    private QuestionsAdapter adapter;

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
    public void onStart() {
        super.onStart();
        makeJsonObjectRequest();
    }

    /**
     * Method to make json object request where json response starts wtih {
     */
    private void makeJsonObjectRequest() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, urlJsonObj, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                parseJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error on Network", Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void hidepDialog() {

    }

    private void showpDialog() {

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
        if (getArguments() != null) {
            sortOrder = getArguments().getString(Const.SORT_ORDER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.feedRecylerView);
        setRecyclerAdapter(recyclerView);
        return rootView;
    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuestionsAdapter(getContext(),questions);
        recyclerView.setAdapter(adapter);
    }
}
