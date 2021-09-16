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
import java.util.ArrayList;

class PuzzlePiece{
    private Bitmap img;
    private int currentPos;
    private int solvedPos;

    public PuzzlePiece(Bitmap img, int currentPos, int solvedPos) {
        this.img = img;
        this.currentPos = currentPos;
        this.solvedPos = solvedPos;
    }

    //https://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews
    //https://github.com/ArthurHub/Android-Image-Cropper
}

public class GameActivity extends AppCompatActivity {

    private ImageView test;
    private Button btnBack;
    private Uri imgUri;
    private Bitmap bitmap;

    ArrayList<ArrayList<Bitmap>> puzzle = new ArrayList<ArrayList<Bitmap>>();

    /*public const int PUZZLE_SIZE = 3; // 3*3 = 9 pieces, 4*4 = 16 pieces (classic 15 puzzle)

    Bitmap bm; // go imash od image pickerot

    int h = bm.Height/PUZZLE_SIZE; // Valjda ne e samo .Height ali sigurno postoi neshto takvo
    int w = bm.Width/PUZZLE_SIZE;

    Array<Array<Bitmap>> puzzle = new Array<Array<Bitmap>> puzzle;

for(int i = 0; i < PUZZLE_SIZE ; i++)
    {
        puzzle.push(new Array<Bitmap>); // Dodadi empty row
        for(int j = 0; j < PUZZLE_SIZE; j++)
        {
            puzzle[i].push(Bitmap.createBitmap(bm, i*w, j*h, w, h))
        }
    }
// na kraj imash matrica puzzle kajshto puzzle[0][0] ti e prviot piece vo kjosh gore levo, puzzle[0][1] e vtoriot and so on...
*/

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
