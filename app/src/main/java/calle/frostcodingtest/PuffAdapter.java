package calle.frostcodingtest;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class PuffAdapter extends ArrayAdapter {

     private List<Puff> puffList;
     private int resource;
     private LayoutInflater inflater;

     public PuffAdapter(Context context, int resource, List<Puff> puffList) {
         super(context, resource, puffList);
         this.puffList = puffList;
         this.resource = resource;
         inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
     }

     @Override
     public View getView(final int position, View convertView, ViewGroup parent) {

         if(convertView == null) {
            convertView = inflater.inflate(resource, null);
         }

         ImageView img = (ImageView)convertView.findViewById(R.id.img);
         Button btn = (Button)convertView.findViewById(R.id.btn);
         ImageLoader.getInstance().displayImage(puffList.get(position).getImageUrl(), img);

         btn.setText(puffList.get(position).getButtonText() + " »   ");
         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent webScreen = new Intent(v.getContext(), WebActivity.class);
                 webScreen.putExtra("url", puffList.get(position).getUrl());
                 v.getContext().startActivity(webScreen);
             }
         });

         return convertView;
     }
 }