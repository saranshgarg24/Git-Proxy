package com.saransh.app.gitproxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saransh.app.gitproxy.R;
import com.saransh.app.gitproxy.adapter.SearchAdapter;


public class SearchList extends Fragment {

    Context context;

    public SearchList(Context c) {
        // Required empty public constructor
        context = c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_list, container, false);
         RecyclerView r_list = (RecyclerView) v.findViewById(R.id.searchList);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        r_list.setLayoutManager(llm);
        SearchAdapter recyclerAdapter = new SearchAdapter(context);
        r_list.setAdapter(recyclerAdapter);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
