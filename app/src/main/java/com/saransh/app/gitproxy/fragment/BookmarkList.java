package com.saransh.app.gitproxy.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saransh.app.gitproxy.R;
import com.saransh.app.gitproxy.adapter.BookmarkAdapter;


public class BookmarkList extends Fragment {

    RecyclerView r_list;
    Context context;

    View v;


    public BookmarkList(Context c) {
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
        v = inflater.inflate(R.layout.fragment_bookmark_list, container, false);

        initList();

        return v;
    }
    private void initList() {
        r_list = (RecyclerView) v.findViewById(R.id.list);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        r_list.setLayoutManager(llm);
        BookmarkAdapter recyclerAdapter = new BookmarkAdapter(context);
        r_list.setAdapter(recyclerAdapter);
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
