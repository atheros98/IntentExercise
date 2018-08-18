package com.example.atheros.intentexercise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final int numOfImg = 18;
    final int REQUEST_CODE_IMAGE = 1730;
    public static ArrayList<String> pokemonNames;
    ImageView imgOrigin, imgSelected;
    TextView txtScore;
    int imgResourceOrigin;
    int score = 100;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        imgSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
    }

    void init() {
        imgOrigin = (ImageView) findViewById(R.id.imageViewOrigin);
        imgSelected = (ImageView) findViewById(R.id.imageViewSelected);
        txtScore = (TextView) findViewById(R.id.textViewScore);
        sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
        score = sharedPreferences.getInt("score", 100);
        txtScore.setText(score + "");
        pokemonNames = new ArrayList<>();
        Collections.addAll(pokemonNames, getResources().getStringArray(R.array.pokemon_names));
        changeOriginImage();
    }

    void changeOriginImage() {
        Random random = new Random();
        int pos = random.nextInt(numOfImg);
        String imgName = pokemonNames.get(pos);
        imgResourceOrigin = getResources().getIdentifier(imgName, "drawable", getPackageName());
        imgOrigin.setImageResource(imgResourceOrigin);
    }

    void saveScore(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("score", score);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            int imageResource = data.getIntExtra("imgResourceSelected", R.drawable.question);
            imgSelected.setImageResource(imageResource);

            if (imageResource == imgResourceOrigin) {
                Toast.makeText(this, "Well done! Plus 10 pts", Toast.LENGTH_SHORT).show();
                score += 10;
                new CountDownTimer(2000, 1000) {

                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        changeOriginImage();
                    }
                }.start();
            } else {
                Toast.makeText(this, "Aww! Try again! Subtract 5pts", Toast.LENGTH_SHORT).show();
                score -= 5;
            }
        }

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "You canceled! Subtract 15pts", Toast.LENGTH_SHORT).show();
            score -= 15;
        }
        saveScore();
        txtScore.setText(score + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reload, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuReload) {
            changeOriginImage();
        }
        return super.onOptionsItemSelected(item);
    }
}
