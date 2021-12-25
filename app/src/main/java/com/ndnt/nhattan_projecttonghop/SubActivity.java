package com.ndnt.nhattan_projecttonghop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    Intent intent;
    TextView txtsubms,txtsubtenbh,txtsubloi,txttheloai;
    ImageView imgsubyeuthich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        thamchieu();
        intent=getIntent();
        control();
    }

    private void control()
    {
        String ms=intent.getStringExtra("ms");
        Cursor cursor=MainActivity.database.rawQuery("SELECT * FROM ArirangSongList WHERE MABH="+ms,null);

        while (cursor.moveToNext())
        {
            txtsubms.setText("#"+cursor.getString(1));
            txtsubtenbh.setText(cursor.getString(2));
            txtsubloi.setText(cursor.getString(3)+"    ");
            txttheloai.setText(cursor.getString(5));

            int like=cursor.getInt(6);

            if(like==0)
            {
                imgsubyeuthich.setImageResource(R.drawable.unlike);
            }
            else
            {
                imgsubyeuthich.setImageResource(R.drawable.love);
            }
        }
        cursor.close();

    }

    private void thamchieu() {
        txtsubms=findViewById(R.id.txtsubms);
        txtsubtenbh=findViewById(R.id.txtsubtenbh);
        txtsubloi=findViewById(R.id.txtsubloi);
        txttheloai=findViewById(R.id.txttheloai);

        imgsubyeuthich=findViewById(R.id.imgsubyeuthich);

    }
}