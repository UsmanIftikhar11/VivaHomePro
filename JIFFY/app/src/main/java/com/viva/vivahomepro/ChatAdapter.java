package com.viva.vivahomepro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ServiceViewHolder> {

    private Context mCtx;
    private ArrayList<ChatModel> serviceList;
    private ArrayList<String> proDetails;
    private SessionManager sessionManager;
    private String proId;

    public ChatAdapter(Context mCtx, ArrayList<ChatModel> serviceList) {
        this.mCtx = mCtx;
        this.serviceList = serviceList;
        proDetails = new ArrayList<>();
        sessionManager= new SessionManager(mCtx);
        proDetails = sessionManager.getUserDetails();
        proId = proDetails.get(0);
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row, parent, false);

       // View view = inflater.inflate(R.layout.chat_row, null);


        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        ChatModel service = serviceList.get(position);

//        Toast.makeText(mCtx, service.getLead_id()+service.getU_pic()+service.getDate()+service.getTname()+service.getUl_name()+service.getUl_uid(), Toast.LENGTH_SHORT).show();

        if(service.getWhosend().equals("user")) {
            holder.txtReceived.setVisibility(View.VISIBLE);
            holder.txtSent.setVisibility(View.GONE);
            holder.imgSent.setVisibility(View.GONE);
            holder.txtReceived.setText(service.getMessage());
            if (service.getAttachment().equals("https://www.vivahomepros.com/uploads/"))
                holder.imgReceived.setVisibility(View.GONE);
            else {
                holder.imgReceived.setVisibility(View.VISIBLE);
                Glide.with(mCtx).load(service.getAttachment())/*.placeholder(R.drawable.profile1mip)*/.into(holder.imgReceived);
            }
        } else /*if(service.getWhosend().equals("pro")) */{
            holder.txtReceived.setVisibility(View.GONE);
            holder.txtSent.setVisibility(View.VISIBLE);
            holder.imgReceived.setVisibility(View.GONE);
            holder.txtSent.setText(service.getMessage());
            if (service.getAttachment().equals("https://www.vivahomepros.com/uploads/"))
                holder.imgSent.setVisibility(View.GONE);
            else {
                holder.imgSent.setVisibility(View.VISIBLE);
                Glide.with(mCtx).load(service.getAttachment())./*placeholder(R.drawable.profile1mip).*/into(holder.imgSent);
            }
        }
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView txtReceived, txtSent;
        ImageView imgReceived , imgSent;
//        RelativeLayout rlReceived , rlSent;

        public ServiceViewHolder(View itemView) {
            super(itemView);

            txtReceived = itemView.findViewById(R.id.txtReceived);
            txtSent = itemView.findViewById(R.id.txtSent);
            imgReceived = itemView.findViewById(R.id.imgReceived);
            imgSent = itemView.findViewById(R.id.imgSent);
//            rlReceived = itemView.findViewById(R.id.rlReceived);
//            rlSent = itemView.findViewById(R.id.rlSent);
        }
    }

}
