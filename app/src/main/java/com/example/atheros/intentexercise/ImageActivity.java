package com.example.atheros.intentexercise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Collections;

public class ImageActivity extends Activity {

    TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        init();
    }

    void init(){
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        int cols = 3;
        int rows = 6;

        Collections.shuffle(MainActivity.pokemonNames);
        for (int i = 0; i < rows; i++){
            //Create rows
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(10, 20, 10, 20);

            for (int j = 0; j < cols; j++){
                ImageView imageView = new ImageView(this);
                TableRow.LayoutParams params = new TableRow.LayoutParams(250, 250);
                imageView.setLayoutParams(params);
                int pos = cols * i + j;
                Log.d("AAA",pos + " - " + i + " - " + j);
                final int imgResource = getResources().getIdentifier(MainActivity.pokemonNames.get(pos),
                        "drawable", getPackageName());
                imageView.setImageResource(imgResource);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("imgResourceSelected", imgResource);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                tableRow.addView(imageView);
            }

            tableLayout.addView(tableRow);
        }
    }
}
