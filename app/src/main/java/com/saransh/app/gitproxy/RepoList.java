package com.saransh.app.gitproxy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;


public class RepoList extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String AccessToken;
    RecyclerView r_list;

    View v;

 //   private OnFragmentInteractionListener mListener;

    public RepoList() {
        // Required empty public constructor
    }

    public static RepoList newInstance(String param1, String param2) {
        RepoList fragment = new RepoList();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_repo_list, container, false);
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();

        if(ni == null)
        {
            Toast.makeText(getContext(), "Can't Connect to the Internet", Toast.LENGTH_LONG).show();
        }
        else {


            new getRepositries().execute();
        }

        initList();

        return v;
    }
    private void initList() {
        r_list = (RecyclerView) v.findViewById(R.id.list);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        r_list.setLayoutManager(llm);
    }

    class getRepositries extends AsyncTask<Object, Object, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //     loader.smoothToShow(); //showing the loader


        }


        @Override
        protected JSONArray doInBackground(Object... args) {

            JSONParser jsonParser = new JSONParser();

            String url = "https://api.github.com/user/repos";
            url = url + "?access_token=" + AccessToken;

            JSONArray response = jsonParser.getJSONFromUrl(url);

            if (response != null) {
                return response;
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray k) {

            //  loader.hide();
            if (k != null) {

                RepoAdapter recyclerAdapter = new RepoAdapter(getContext(), k);
                r_list.setAdapter(recyclerAdapter);

            } else {
                Toast.makeText(getContext(), "no internet"
                        , Toast.LENGTH_SHORT).show();
            }


        }
    }

        public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
       super.onDetach();
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
