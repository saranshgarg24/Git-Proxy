package com.saransh.app.gitproxy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    Context r_context;
    int count;
    List<String> title = new ArrayList<>();

    public SearchAdapter(Context context){

        r_context = context;

        DatabaseHandler db = new DatabaseHandler(r_context);
        title = db.getSearchQueries();
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