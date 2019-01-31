package com.example.benura.snakegame2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayScore extends AppCompatActivity{

    public TextView score,name;
    public Button exit;
    public static int max;
    public String names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_score);
        score=findViewById(R.id.scoreView);
        name=findViewById(R.id.name);
        exit=findViewById(R.id.back);
        String selectQuery = "SELECT * FROM scores;";
        SQLiteDatabase db = openOrCreateDatabase( "Highscore.db", SQLiteDatabase.OPEN_READWRITE , null);
        Cursor cursor = db.rawQuery(selectQuery, null);
        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            max=cursor.getInt(1);
            do {
                if(cursor.getInt(1)>=max) {
                    max = cursor.getInt(1);
                    names=cursor.getString(0);
                }
               // score.setText(cursor.getString(1));
               // Toast.makeText(DisplayScore.this,cursor.getString(1),Toast.LENGTH_SHORT).show();

            } while (cursor.moveToNext());

            name.setText(names);
            score.setText(String.valueOf(max));

        }
        db.close();


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DisplayScore.this,Menu.class);
                finish();
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        //   boolean fromNewActivity=true;
        Intent mainIntent = new Intent(DisplayScore.this, Menu.class);
        startActivityForResult(mainIntent, 0);

    }

    }

