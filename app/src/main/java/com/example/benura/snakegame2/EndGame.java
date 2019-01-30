package com.example.benura.snakegame2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class EndGame extends AppCompatActivity {
    public EditText name;
    public TextView scores,names,scoreText,gameover;
    public Button button1,button2,submit;
   public int sc,j=0;
   public String stringname;
   public ProgressBar progressBar;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Handler handler=new Handler();
        Bundle bundle=getIntent().getExtras();
        progressBar=findViewById(R.id.progressBar);
        gameover=findViewById(R.id.gameOver);
        sc=bundle.getInt("score");
        scores=findViewById(R.id.score);
        scores.setText(String.valueOf(sc));
        scoreText=findViewById(R.id.scoreText);
        submit=findViewById(R.id.submit);
        names=findViewById(R.id.nameView);
        name=findViewById(R.id.nameText);
        button2=findViewById(R.id.back);
        button1=findViewById(R.id.exit);
        progressBar.setMax(100);


        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                progressBar.setVisibility(View.VISIBLE);

                EndGame.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progressBar.setProgress(j);
                        j=j+40;
                    }
                });


            }

            public void onFinish() {

               progressBar.setVisibility(View.INVISIBLE);
               gameover.setVisibility(View.INVISIBLE);
               scores.setVisibility(View.VISIBLE);
               scoreText.setVisibility(View.VISIBLE);
               submit.setVisibility(View.VISIBLE);
               names.setVisibility(View.VISIBLE);
               name.setVisibility(View.VISIBLE);

            }

        }.start();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(EndGame.this,Menu.class);
                Bundle bundle=new Bundle();
                bundle.putInt("score",sc);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringname=name.getText().toString();
                String user_name="user_name";
                String user_score="user_score";
                SQLiteDatabase db;
                if(!stringname.isEmpty()) {
                    db = openOrCreateDatabase("Highscore.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                    try {
                        // final String CREATE_TABLE = "CREATE TABLE  scores(user_name PRIMARY KEY,user_score);";

                        //   db.execSQL(CREATE_TABLE);

                        ContentValues movie_details = new ContentValues();
                        movie_details.put(user_name, stringname);
                        movie_details.put(user_score, sc);

                        db.insert("scores", null, movie_details);
                        //String sql ="INSERT INTO scores(user_name,user_score) "+"VALUES"+"('"+stringname+"',"+sc+");";
                        //  db.execSQL(sql);
                        Toast.makeText(EndGame.this, "Values added ", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(EndGame.this, "ERROR " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                name.setVisibility(View.INVISIBLE);
                names.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);

            }
        });



    }

}
