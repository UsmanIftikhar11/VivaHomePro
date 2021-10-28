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

public class ServiceRequestAdapter extends RecyclerView.Adapter<ServiceRequestAdapter.ServiceViewHolder> {

    private Context mCtx;
    private ArrayList<ServiceRequestModel> serviceList;

    public ServiceRequestAdapter(Context mCtx, ArrayList<ServiceRequestModel> serviceList) {
        this.mCtx = mCtx;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.service_request_row, null);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        ServiceRequestModel service = serviceList.get(position);

        int index = service.getQues().indexOf("nextline");
        String ques = service.getQues().substring(0, index);
        String ans = service.getQues().substring(service.getQues().indexOf("nextline")+8);


        holder.txtQuestions.setText(ques);
        holder.txtAnswer.setText(ans);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestions , txtAnswer;

        public ServiceViewHolder(View itemView) {
            super(itemView);

            txtQuestions = itemView.findViewById(R.id.txtQuestions);
            txtAnswer = itemView.findViewById(R.id.txtAnswer);
        }
    }

}
