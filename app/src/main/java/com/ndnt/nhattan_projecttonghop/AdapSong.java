package com.ndnt.nhattan_projecttonghop;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapSong extends ArrayAdapter<Song>
{
    Activity context;
    int id;
    ArrayList<Song> arrayList;

    public AdapSong(@NonNull Activity context, int id, ArrayList<Song> arrayList) {
        super(context, id,arrayList);
        this.context = context;
        this.id = id;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        convertView=layoutInflater.inflate(id,null);

        Song song=arrayList.get(position);

        TextView edtms,edtname;
        edtms=convertView.findViewById(R.id.txtMS);
        edtname=convertView.findViewById(R.id.txtName);

        edtms.setText(song.getMs());
        edtname.setText(song.getTenbh());

        ImageView img=convertView.findViewById(R.id.btnlike);

        if(song.getThich()==0)
            img.setImageResource(R.drawable.unlike);
        else
            img.setImageResource(R.drawable.love);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(song.getThich()==0)
                {
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("YEUTHICH",1);
                    song.setThich(1);
                    img.setImageResource(R.drawable.love);
                    MainActivity.database.update("ArirangSongList",contentValues,"MABH=?",new String[]{song.getMs()});

                }
                else
                {
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("YEUTHICH",0);
                    song.setThich(0);
                    img.setImageResource(R.drawable.unlike);
                    MainActivity.database.update("ArirangSongList",contentValues,"MABH=?",new String[]{song.getMs()});
                }

                notifyDataSetChanged();

            }
        });


        return convertView;
    }


}
