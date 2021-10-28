package com.viva.vivahomepro;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;


public class ServiceCancelAdapter extends RecyclerView.Adapter<ServiceCancelAdapter.ServiceViewHolder> {

    private Context mCtx;
    private List<ServiceCancel> serviceList;

    public ServiceCancelAdapter(Context mCtx, List<ServiceCancel> serviceList) {
        this.mCtx = mCtx;
        this.serviceList = serviceList;
        Log.d("dlaaa", "Cancel : "+ serviceList);
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.service_cancel_list_row, null);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        ServiceCancel service = serviceList.get(position);

//        Toast.makeText(mCtx, service.getLead_id()+service.getU_pic()+service.getDate()+service.getTname()+service.getUl_name()+service.getUl_uid(), Toast.LENGTH_SHORT).show();

        if(service.getU_pic()!= null) {
//            Picasso.get().load(service.getU_pic()).into(holder.imgUser1);
            //with(mCtx).load(service.getImage()).into(holder.imgUser)
            Glide
                    .with(mCtx)
                    .load(service.getU_pic())
                    .apply(new RequestOptions().override(600, 600))
                    .placeholder(R.drawable.profile1mip)
                    .into(holder.imgUser1);
        }else {
            holder.imgUser1.setImageResource(R.drawable.profilemip);
        }
        holder.txtDate1.setText(service.getDate());
        holder.txtTaskName1.setText(service.getTname());
        holder.txtUserName1.setText(service.getUl_name());

        /*holder.rlLeads1.setOnClickListener(new View.OnClickListener() {
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

        TextView txtUserName1, txtTaskName1, txtDate1;
        ImageView imgUser1;
        RelativeLayout rlLeads1;

        public ServiceViewHolder(View itemView) {
            super(itemView);

            txtUserName1 = itemView.findViewById(R.id.txtUserName1);
            txtTaskName1 = itemView.findViewById(R.id.txtTaskName1);
            txtDate1 = itemView.findViewById(R.id.txtDate1);
            imgUser1 = itemView.findViewById(R.id.imgUser1);
            rlLeads1 = itemView.findViewById(R.id.rlLeads1);
        }
    }

}
