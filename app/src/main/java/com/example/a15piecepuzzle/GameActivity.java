package com.example.a15piecepuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
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
    private Uri imgUri; //image-ot dobien od prethodnoto activity
    private Bitmap bitmap; //istiot image, konvertiran vo bitmap


    public int PUZZLE_SIZE = 4;
    //ArrayList<ArrayList<Bitmap>> puzzle = new ArrayList<ArrayList<Bitmap>>();
    ArrayList<ArrayList<PuzzlePiece>> puzzle = new ArrayList<ArrayList<PuzzlePiece>>();
    int h = bitmap.getHeight()/PUZZLE_SIZE;
    int w = bitmap.getWidth()/PUZZLE_SIZE;

    GridView puzzleGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        test = findViewById(R.id.imageView);
        Intent intent = getIntent();
        imgUri = Uri.parse(intent.getStringExtra("imgUri"));

        puzzleGridView = findViewById(R.id.puzzleGridView);
        puzzleGridView.setAdapter(new PuzzleAdapter(GameActivity.this,puzzle));

        //End Game or return
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Image to bitmap and fill puzzle
        try {
            bitmap = getImageFromUri(imgUri);
            test.setImageBitmap(bitmap);

            fillPuzzle();
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


    // na kraj imash matrica puzzle kajshto puzzle[0][0] ti e prviot piece vo kjosh gore levo, puzzle[0][1] e vtoriot and so on...
    /*  Orginalniot kod
     for(int i = 0; i < PUZZLE_SIZE ; i++)
        {
            puzzle.add(new ArrayList<Bitmap>()); // Dodadi empty row
            for(int j = 0; j < PUZZLE_SIZE; j++)
            {
                puzzle.get(i).add(Bitmap.createBitmap(bitmap, i*w, j*h, w, h));
            }
        }
    */

    private void fillPuzzle(){
        for(int i = 0; i < PUZZLE_SIZE ; i++)
        {
            puzzle.add(new ArrayList<PuzzlePiece>()); // Dodadi empty row
            for(int j = 0; j < PUZZLE_SIZE; j++)
            {
                Bitmap bm = Bitmap.createBitmap(bitmap, i*w, j*h, w, h);
                puzzle.get(i).add(new PuzzlePiece(bm,i,i));
                //Ideata e da go napolni puzzle-ot vo orginalna sostojba pa posle da go shufflene, zatoa bm,i,i
            }
        }


    }

}
