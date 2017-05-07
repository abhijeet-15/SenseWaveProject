package com.example.abhijeet.sensewaveproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by abhijeet on 7/5/17.
 */

public class GatewayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<GateWayViewHolder> data= Collections.emptyList();
    GateWayViewHolder current;
    int currentPos=0;

    public GatewayAdapter(Context context, List<GateWayViewHolder> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_gateway, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder= (MyHolder) holder;
        GateWayViewHolder current=data.get(position);

        myHolder.text.setText(current.gatewayId);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView text;


        public MyHolder(final View itemView) {
            super(itemView);
            text =(TextView) itemView.findViewById(R.id.gatewayId_textview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Clicked  id",text.getText().toString());

                    Intent  i = new Intent(v.getContext(),GatewayDetailsActivity.class);
                    i.putExtra("key",text.getText().toString());
                    v.getContext().startActivity(i);




                }
            });
        }
    }}
