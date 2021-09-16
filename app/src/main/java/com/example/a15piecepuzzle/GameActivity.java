package com.example.a15piecepuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class GameActivity extends AppCompatActivity {

    private ImageView test;
    private Button btnBack;
    private Uri imgUri;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        test = findViewById(R.id.imageView);
        Intent intent = getIntent();
        imgUri = Uri.parse(intent.getStringExtra("imgUri"));


        //End Game or return
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Image to bitmap
        try {
            bitmap = getImageFromUri(imgUri);
            test.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private Bitmap cropImage(Bitmap bitmap){
        return Bitmap.createBitmap(bitmap,0,0, 500,1000); //samo test, sepak ke se koristi 3rd party library
    }

    private Bitmap getImageFromUri(Uri uri) throws IOException {
        return MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
    }

}
