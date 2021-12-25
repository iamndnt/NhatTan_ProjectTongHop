package com.ndnt.nhattan_projecttonghop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TabHost tabHost;
    EditText edttimkiem;
    ImageButton imgxoa;
    ListView lv1,lv2,lv3;
    ArrayList<Song> ar1,ar2,ar3;
    AdapSong ad1,ad2,ad3;

    String DB_PATH_SUFFIX = "/databases/";
    static SQLiteDatabase database=null;
    String DATABASE_NAME="arirang.sqlite";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thamchieu();
        processCopy();
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song=ar1.get(position);Intent intent=new Intent(MainActivity.this,SubActivity.class);
                intent.putExtra("ms",song.getMs());
                startActivity(intent);
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song=ar2.get(position);
                Intent intent=new Intent(MainActivity.this,SubActivity.class);
                intent.putExtra("ms",song.getMs());
                startActivity(intent);
            }
        });

        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song=ar3.get(position);
                Intent intent=new Intent(MainActivity.this,SubActivity.class);
                intent.putExtra("ms",song.getMs());
                startActivity(intent);
            }
        });


        tab1();
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.compareTo("t1")==0)
                {
                    tab1();
                }

                if(tabId.compareTo("t2")==0)
                {
                    tab2();
                }

                if(tabId.compareTo("t3")==0)
                {
                    tab3();
                }
            }
        });

        imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edttimkiem.setText("");
                ad1.notifyDataSetChanged();
            }
        });



    }

    private void tab3() {
        ar3.clear();
        Cursor cursor=database.rawQuery("SELECT * FROM ArirangSongList WHERE YEUTHICH=1",null);
        while (cursor.moveToNext())
        {
            Song song=new Song(cursor.getString(1),cursor.getString(2),cursor.getInt(6));
            ar3.add(song);
        }
        cursor.close();
        ad3.notifyDataSetChanged();
    }

    private void tab2()
    {
        ar2.clear();
        Cursor cursor=database.query("ArirangSongList",null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            Song song=new Song(cursor.getString(1),cursor.getString(2),cursor.getInt(6));
            ar2.add(song);
        }
        cursor.close();
        ad2.notifyDataSetChanged();
    }

    private void tab1()
    {
        edttimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getData();
            }

            private void getData() {
                ar1.clear();
                String timkiem=edttimkiem.getText().toString();
                if(timkiem.compareTo("")!=0) {
                    Cursor cursor = database.rawQuery("SELECT * FROM ArirangSongList WHERE TENBH1 LIKE '%" + timkiem + "%' OR MABH LIKE '%" + timkiem + "%'  OR TENBH LIKE '%" + timkiem + "' ", null);
                    while (cursor.moveToNext()) {
                        Song song = new Song(cursor.getString(1), cursor.getString(2), cursor.getInt(6));
                        ar1.add(song);
                    }
                    cursor.close();
                    ad1.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void thamchieu() {
        tabHost=findViewById(R.id.tabhost);
        tabHost.setup();


        TabHost.TabSpec tab1,tab2,tab3;

        tab1=tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("",getResources().getDrawable(R.drawable.search));
        tabHost.addTab(tab1);

        tab2=tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("",getResources().getDrawable(R.drawable.list));
        tabHost.addTab(tab2);

        tab3=tabHost.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("",getResources().getDrawable(R.drawable.favourite));
        tabHost.addTab(tab3);

        lv1=findViewById(R.id.lvtimkiem);
        ar1=new ArrayList<>();
        ad1=new AdapSong(this,R.layout.song_layout,ar1);
        lv1.setAdapter(ad1);

        lv2=findViewById(R.id.lvtatca);
        ar2=new ArrayList<>();
        ad2=new AdapSong(this,R.layout.song_layout,ar2);
        lv2.setAdapter(ad2);

        lv3=findViewById(R.id.lvyeuthich);
        ar3=new ArrayList<>();
        ad3=new AdapSong(this,R.layout.song_layout,ar3);
        lv3.setAdapter(ad3);

        lv1.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        lv2.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        lv3.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        edttimkiem=findViewById(R.id.edttimkiem);
        imgxoa=findViewById(R.id.imgxoa);
    }

    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try{CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset() {
        // TODO Auto-generated method stub
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            // Path to the just created empty db
            String outFileName = getDatabasePath();
            // if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            // Truyền bytes dữ liệu từ input đến output
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}