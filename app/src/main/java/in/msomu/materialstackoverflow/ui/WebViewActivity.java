package in.msomu.materialstackoverflow.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.msomu.materialstackoverflow.AppController;
import in.msomu.materialstackoverflow.R;
import in.msomu.materialstackoverflow.utils.Const;
import in.msomu.materialstackoverflow.utils.PreferencesHelper;

/**
 * Created by msomu on 29/06/2016.
 */
public class WebViewActivity extends AppCompatActivity {

    private final String clientId = "6136";
    private final String apiKey = "gQJsL7krOvbXkJ0NEI*mWA((";
    private final String redirectUri = "https://stackexchange.com/oauth/login_success";
    public static String EXTRA_ACTION_TOKEN_URL = "TokenUrl";
    public static final String API_BASE_URL = "https://stackexchange.com/oauth/dialog";
    private WebView browser;
    private ProgressBar progressBar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress_spinner);
        clearCookies();
        browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        final String url= API_BASE_URL +"?client_id=" + clientId +"&scope=write_access"+"&redirect_uri=" + redirectUri+"";

        browser.setWebChromeClient(new WebChromeClient() {
            // Show loading progress in activity's title bar.
            @Override
            public void onProgressChanged(WebView view, int progress) {
                setProgress(progress * 100);

                if (progress >= 100) {
                    //setProgressBarIndeterminateVisibility(false);
                    progressBar.setVisibility(View.GONE);
                } else {
                    //setProgressBarIndeterminateVisibility(true);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        browser.setWebViewClient(new WebViewClient() {
            // When start to load page, show url in activity's title bar

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("WebActivity","Auth URL: " + url);
                if (url.contains("#access_token")) {
                    return true;
                }
                return false;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("WebActivity","Loading URL: " + url);
                progressBar.setVisibility(View.VISIBLE);
                //setProgressBarIndeterminateVisibility(true);
                //setTitle(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebActivity","Loaded URL: " + url);
                if (url.contains("#access_token")) {

                    mProgressDialog = new ProgressDialog(WebViewActivity.this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    String token_str = url;
                    Log.d("WebActivity","Token URL: " + token_str);
                    String[] str = token_str.split("access_token=");
                    Log.d("WebActivity","srt[0]: " + str[0]);
                    Log.d("WebActivity","srt[1]: " + str[1]);
                    String token = str[1].substring(0, str[1].length() - 14);
                    Log.d("WebActivity","token: " + token);

                    makeJsonObjectRequest(token);

//                    //Calling method to get UserId
//                    RetrofitClient.userIdServices().getUserId("stackoverflow", apiKey, token, new Callback<UserShortInfo>() {
//                        @Override
//                        public void success(UserShortInfo info, Response response) {
//                            if (mProgressDialog.isShowing() && mProgressDialog!=null)
//                                mProgressDialog.dismiss();
//
//                            Log.d("WebActivity","UserId: " + info.getItems().get(0).getUserId());
//                            finishAct(info.getItems().get(0).getUserId());
//                        }
//
//                        @Override
//                        public void failure(RetrofitError error) {
//                            if (mProgressDialog.isShowing() && mProgressDialog!=null)
//                                mProgressDialog.dismiss();
//
//                            String merror = error.getMessage();
//                            Log.d("WebActivity","merror :" + merror);
//                            Snackbar.make(browser, merror, Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
//
//                        }
//                    });




                    //Intent intent = new Intent();
                    //intent.putExtra(EXTRA_ACTION_TOKEN_URL, url);
                    //setResult(Activity.RESULT_OK, intent);
                    //WebViewActivity.this.finish();
                    //overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
                }
            }

        });

        browser.loadUrl(url);

        /*Button loginButton = (Button) findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///String scope  = " ?scope \"\" ";
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(ServiceGenerator.API_BASE_URL +"?client_id=" + clientId +"?redirect_uri=" + redirectUri+""));
                startActivity(intent);
            }
        });*/
    }

    private void finishAct(String UserId){

        Const.UserId = UserId;
        PreferencesHelper.setLoginCheck(true);
        PreferencesHelper.setUserID(UserId);

        Intent i = new Intent(WebViewActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        WebViewActivity.this.finish();
    }

    public void clearCookies(){
        CookieSyncManager.createInstance(WebViewActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    private void makeJsonObjectRequest(String token) {
        Log.d("UserID","Trying to get userId");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, "https://api.stackexchange.com/2.2/me?site=stackoverflow&key="+apiKey+"&access_token="+token, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Result", response.toString());
                if (mProgressDialog.isShowing() && mProgressDialog!=null)
                    mProgressDialog.dismiss();

                try {
                    JSONArray items = response.getJSONArray("items");
                    JSONObject item = items.getJSONObject(0);
                    String userId = item.getString("user_id");
                    Log.d("user_id",userId);
                    finishAct(userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Result","Parse Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WebViewActivity.this, "Error on Network", Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}
