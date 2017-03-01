package com.saransh.app.gitproxy.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saransh.app.gitproxy.R;
import com.saransh.app.gitproxy.fragment.RepoList;
import com.saransh.app.gitproxy.helper.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    Context r_context;
    int count;
    List<String> title = new ArrayList<>();

    public SearchAdapter(Context context){

        r_context = context;

        DatabaseHandler db = new DatabaseHandler(r_context);
        title = db.getSearchQueries();
        title = new ArrayList<String>(new HashSet<String>(title));
        count = title.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        ViewHolder vItem = new ViewHolder(v, viewType, parent.getContext());

        return vItem;
    }//onCreateViewHolder()

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.title.setText(title.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportActionBar().setTitle(title.get(position));
                Fragment fragment  = RepoList.newInstance(title.get(position));
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack(null).commit();
            }
        });
        //Set Images And Position Here

    }//onBindViewHolder()

    @Override
    public int getItemCount() {
        return count;
    }



    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{

        int HolderId = 1;

        TextView title;

        public ViewHolder(View itemView, int item_type, final Context context) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.search_item);


            HolderId = 1;
        }//if


    }//Constructor






}//MyRecyclerAdaterClass