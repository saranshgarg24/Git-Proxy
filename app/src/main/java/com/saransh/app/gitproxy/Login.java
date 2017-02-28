package com.saransh.app.gitproxy;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class Login extends Fragment {

    private OnFragmentInteractionListener mListener;
    public static String OAUTH_URL = "http://github.com/login/oauth/authorize";
    public static String OAUTH_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    public static String CLIENT_ID = "27df83cfb3490de690de";
    public static String CLIENT_SECRET = "5937492d477bba7b64e7f57cb626e56277213878";
    public static String CALLBACK_URL = "https://gitproxy-aaf55.firebaseapp.com/__/auth/handler";

    String accessCode;
    public  static String accessToken;

    public static  Context context;


    public Login() {
        // Required empty public constructor
    }


    public static Login newInstance(Context c) {
        Login fragment = new Login();
        context = c;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_login, container, false);
        String url = OAUTH_URL + "?client_id=" + CLIENT_ID;

        WebView webview = (WebView)v.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String accessTokenFragment = "access_token=";
                String accessCodeFragment = "code=";

                // We hijack the GET request to extract the OAuth parameters

                Log.d("url1",url);

                if (url.contains(accessTokenFragment)) {
                    // the GET request contains directly the token
                    String accessToken = url.substring(url.indexOf(accessTokenFragment));
                    //  TokenStorer.setAccessToken(accessToken);
                    Log.d("accessToken",accessToken);

                } else if(url.contains(accessCodeFragment)) {
                    // the GET request contains an authorization code
                    accessCode = url.substring(url.indexOf(accessCodeFragment));
                    //TokenStorer.setAccessCode(accessCode);
                    accessCode = accessCode.replaceAll("code=","").replaceAll("&state=","");
                    Log.d("accessCode",accessCode);

                    String query = "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&" + accessCode;

                    Log.d("query",query);
                    view.postUrl(OAUTH_ACCESS_TOKEN_URL, query.getBytes());

                    ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo ni = cm.getActiveNetworkInfo();

                    if(ni == null)
                    {
                        Toast.makeText(getContext(), "Can't Connect to the Internet", Toast.LENGTH_LONG).show();
                    }
                    else {

                        new SendPostRequest().execute();
                    }
                }
            }
        });
        Log.d("url2",url);
        webview.loadUrl(url);

        return v;
    }

    class SendPostRequest extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("client_id", CLIENT_ID));
            params.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
            params.add(new BasicNameValuePair("code", accessCode));

            String response = jsonParser.getJSONFromUrl(OAUTH_ACCESS_TOKEN_URL, params);

            if (response != null)
            {
                Log.d("response", String.valueOf(response));
                String s[] = response.split("&");
                accessToken = s[0];
                accessToken = accessToken.replaceAll("access_token=","");
                Log.d("accesstoken",accessToken);
                return "ok";
            }
            return null;
        }
        @Override
        protected void onPostExecute(String k) {
            if (k != null){
                Toast.makeText(getContext(), "Logged In"
                    , Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "no internet"
                        , Toast.LENGTH_SHORT).show();
            }


        }

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
