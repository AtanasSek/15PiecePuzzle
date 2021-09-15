package com.example.a15piecepuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class gameActivity extends AppCompatActivity {

    private ImageView test;
    private Button btnBack;
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        test = findViewById(R.id.imageView);

        Intent intent = getIntent();
        imgUri = Uri.parse(intent.getStringExtra("imgUri"));
        test.setImageURI(imgUri);


        //End Game or return
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}