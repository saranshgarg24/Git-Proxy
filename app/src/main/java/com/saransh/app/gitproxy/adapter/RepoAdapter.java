package com.saransh.app.gitproxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saransh.app.gitproxy.R;
import com.saransh.app.gitproxy.helper.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    Context r_context;
    int count;
    int Toggle = 0;
    List<String> title = new ArrayList<>();
    List<String> lang = new ArrayList<>();
    List<String> owners = new ArrayList<>();
    List<String> stars = new ArrayList<>();
    List<String> forks = new ArrayList<>();
    List<String> lastUpdates = new ArrayList<>();
    public static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public RepoAdapter(Context context, JSONObject repos){

        r_context = context;
        String dateStr;


        try{
            JSONArray items = repos.getJSONArray("items");

            count = items.length();

            for (int i = 0; i<count;i++) {
                JSONObject obj = items.getJSONObject(i);
                // Log.d("OBJ", String.valueOf(obj));
                title.add(obj.getString("full_name"));
                lang.add(obj.getString("language"));
                owners.add(obj.getJSONObject("owner").getString("login"));
                stars.add(obj.getString("stargazers_count"));
                forks.add(obj.getString("forks_count"));

                dateStr = obj.getString("updated_at");
                Date date = inputFormat.parse(dateStr);
                lastUpdates.add(String.valueOf(DateUtils.getRelativeTimeSpanString(date.getTime() ,
                        Calendar.getInstance().getTimeInMillis(),
                        DateUtils.MINUTE_IN_MILLIS)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vItem = new ViewHolder(v, viewType, parent.getContext());

        return vItem;
    }//onCreateViewHolder()

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final DatabaseHandler db = new DatabaseHandler(r_context);
        holder.title.setText(title.get(position));
        holder.languages.setText(lang.get(position));
        holder.starGuage.setText(stars.get(position));
        holder.fork.setText(forks.get(position));
        holder.owner.setText(owners.get(position));
        holder.lastUpdated.setText(lastUpdates.get(position));

        if (db.IsBookmarked(title.get(position))){
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
        }else {
            holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }

        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (db.IsBookmarked(title.get(position))){

                 holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    db.deleteBookmark(title.get(position));
                }else {
                    holder.btnBookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    db.addBookmark(title.get(position), lang.get(position), stars.get(position),
                            forks.get(position), owners.get(position), lastUpdates.get(position));
                }
            }
        });

        db.close();
    }

    @Override
    public int getItemCount() {
        return count;
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
        ImageView btnBookmark;
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
            btnBookmark = (ImageView) itemView.findViewById(R.id.btn_bookmark);

            HolderId = 1;
        }//if


    }//Constructor






}//MyRecyclerAdaterClass