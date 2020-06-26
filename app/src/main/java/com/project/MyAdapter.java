package com.project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.project.util.Util;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<String> title,body,ids;
    ArrayList<LatLng> cords;
    ArrayList<Long> firstAppears;
    int image;
    Context context;

    public MyAdapter(Context context, ArrayList<String> title, ArrayList<String> body,ArrayList<String> ids, int image,ArrayList<LatLng> cords, ArrayList<Long> firstAppears )
    {
        this.context = context;
        this.title = title;
        this.body = body;
        this.image = image;
        this.ids = ids;
        this.cords = cords;
        this.firstAppears = firstAppears;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title_row.setText("Suspet rencontré ! ");
        holder.body_row.setText(body.get(position));
        holder.image_row.setImageResource(image);

        holder.image_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("failaa","Inside Adapter");
                Util.openMap(v.getContext(),cords.get(position),firstAppears.get(position),0);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title_row,body_row;
        ImageView image_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_row = itemView.findViewById(R.id.title_row);
            body_row = itemView.findViewById(R.id.body_row);
            image_row = itemView.findViewById(R.id.image_row);
        }
    }
}
