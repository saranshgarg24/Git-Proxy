package com.saransh.app.gitproxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saransh.app.gitproxy.R;
import com.saransh.app.gitproxy.helper.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    Context r_context;

    List<List<String>>  bookmarks = new ArrayList<>();
    public BookmarkAdapter(Context context){

        r_context = context;
        DatabaseHandler db = new DatabaseHandler(r_context);
        bookmarks = db.getBookmarks();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item, parent, false);
        ViewHolder vItem = new ViewHolder(v, viewType, parent.getContext());

        return vItem;
    }//onCreateViewHolder()

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final List<String> repo = bookmarks.get(position);

        holder.title.setText(repo.get(0));
        holder.languages.setText(repo.get(1));
        holder.starGuage.setText(repo.get(2));
        holder.fork.setText(repo.get(3));
        holder.owner.setText(repo.get(4));
        holder.lastUpdated.setText(repo.get(5));
        holder.btnCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(r_context);
                db.deleteBookmark(repo.get(0));
                notifyItemRemoved(position);
                notifyDataSetChanged();
                notifyItemRangeChanged(position, bookmarks.size());
                bookmarks.remove(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }



    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{

        int HolderId = 1;
        TextView title;
        TextView languages;
        TextView starGuage;
        TextView fork;
        TextView owner;
        TextView lastUpdated;
        ImageView btnCross;
        LinearLayout layout;

        public ViewHolder(View itemView, int item_type, final Context context) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            title = (TextView) itemView.findViewById(R.id.title);
            starGuage = (TextView) itemView.findViewById(R.id.star);
            fork = (TextView) itemView.findViewById(R.id.fork);
            owner = (TextView) itemView.findViewById(R.id.owner);
            lastUpdated = (TextView) itemView.findViewById(R.id.updated);
            languages = (TextView) itemView.findViewById(R.id.lang);
            btnCross = (ImageView) itemView.findViewById(R.id.btn_cross);

            HolderId = 1;
        }//if


    }//Constructor






}//MyRecyclerAdaterClass