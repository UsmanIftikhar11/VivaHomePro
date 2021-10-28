package com.viva.vivahomepro;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {

    Context context;
    ArrayList<GalleryModel> arrayList;
    public GalleryAdapter(Context context, ArrayList<GalleryModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gallery_list, parent, false);
        }
        ImageView imageView;
        imageView = (ImageView) convertView.findViewById(R.id.image);
        String image = arrayList.get(position).getImage();
        String newImage = image.replace("\\" , "");
        String newImage1 = newImage.replace(" " , "%");
        Log.d("galleryImage" , "image = " + image);
        Log.d("galleryImage" , "newImage = " + newImage);
        Log.d("galleryImage" , "newImage1 = " + newImage1);
        Log.d("galleryImage" , "size = " + arrayList.size());

//        Log.d("galleryImage" , "newImage1" + newImage1);
//        imageView.setImageResource(arrayList.get(position).getImage());
//        Picasso.get().load(newImage1).placeholder(R.drawable.profilemip).into(imageView);
        Glide
                .with(context)
                .load(newImage)
                .apply(new RequestOptions().override(600, 600))
                .placeholder(R.drawable.profile1mip)
                .into(imageView);
        return convertView;
    }
}
