package com.viva.vivahomepro;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceCompleteAdapter extends RecyclerView.Adapter<ServiceCompleteAdapter.ServiceViewHolder> {

    private Context mCtx;
    private List<ServiceComplete> serviceList;

    public ServiceCompleteAdapter(Context mCtx, List<ServiceComplete> serviceList) {
        this.mCtx = mCtx;
        this.serviceList = serviceList;
        Log.d("dlaaa", "Completed : "+ serviceList);
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.service_complete_list_row, null);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        ServiceComplete service = serviceList.get(position);

//        Toast.makeText(mCtx, service.getLead_id()+service.getU_pic()+service.getDate()+service.getTname()+service.getUl_name()+service.getUl_uid(), Toast.LENGTH_SHORT).show();

        if(service.getU_pic()!= null) {
//            Picasso.get().load(service.getU_pic()).into(holder.imgUser);
            //with(mCtx).load(service.getImage()).into(holder.imgUser)
            Glide
                    .with(mCtx)
                    .load(service.getU_pic())
                    .apply(new RequestOptions().override(600, 600))
                    .placeholder(R.drawable.profile1mip)
                    .into(holder.imgUser);
        }else {
            holder.imgUser.setImageResource(R.drawable.profilemip);
        }

        holder.txtDate.setText(service.getDate());
        holder.txtTaskName.setText(service.getTname());
        holder.txtUserName.setText(service.getUl_name());

        /*holder.rlLeads2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                mCtx.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserName, txtTaskName, txtDate;
        ImageView imgUser;
        RelativeLayout rlLeads2;

        public ServiceViewHolder(View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtTaskName = itemView.findViewById(R.id.txtTaskName);
            txtDate = itemView.findViewById(R.id.txtDate);
            imgUser = itemView.findViewById(R.id.imgUser);
            rlLeads2 = itemView.findViewById(R.id.rlLeads2);
        }
    }

}
