package com.example.a15piecepuzzle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {
    
    //https://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews
    //https://github.com/ArthurHub/Android-Image-Cropper

    private ImageView imageView;
    private Button btnBack;
    private Uri imgUri; //image-ot dobien od prethodnoto activity
    private Bitmap bitmap; //istiot image, konvertiran vo bitmap


    public static int GRID_SIZE = 3;
    public static int PUZZLE_COUNT = GRID_SIZE * GRID_SIZE;
    Puzzle puzzle;
    GridView puzzleGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        imageView = findViewById(R.id.imageView);
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

        //Image to bitmap and fill puzzle
        try {
            bitmap = getImageFromUri(imgUri);
            imageView.setImageBitmap(bitmap);

            fillPuzzle(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        puzzleGridView = findViewById(R.id.puzzleGridView);
        puzzleGridView.setNumColumns(GRID_SIZE);
        puzzleGridView.setVerticalScrollBarEnabled(false); //dont work
        puzzleGridView.setHorizontalScrollBarEnabled(false); //dont work
        PuzzleAdapter adapter = new PuzzleAdapter(GameActivity.this, puzzle, new OnMoveCallback() {
            @Override
            public void OnMove(PuzzleAdapter oldAdapter)
            {
                puzzleGridView.invalidateViews();
                puzzleGridView.setAdapter(oldAdapter);
            }
        });
        puzzleGridView.setAdapter(adapter);

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

    private void fillPuzzle(Bitmap puzzleBitmap){
        
        ArrayList<Integer> shuffled = new ArrayList<Integer>();
        for(int i = 1; i <= PUZZLE_COUNT; i++)
            shuffled.add(i);
        
        //todo: check if shuffle is possible
        
        Collections.shuffle(shuffled);
        
        puzzle = new Puzzle(puzzleBitmap, GRID_SIZE, new ArrayList<>(PUZZLE_COUNT), shuffled);
        
        for(int i = 0; i < PUZZLE_COUNT; i++)
        {
            Bitmap pieceImg = Bitmap.createBitmap(puzzleBitmap, (i / GRID_SIZE) * puzzle.pieceWidth, (i % GRID_SIZE) * puzzle.pieceHeight, puzzle.pieceWidth, puzzle.pieceHeight);
            PuzzlePiece puzzlePiece = new PuzzlePiece(pieceImg, shuffled.get(i), i + 1);
            if(puzzlePiece.getSolvedPos() == PUZZLE_COUNT){
                puzzlePiece.isHole = true;
            }
            puzzle.puzzlePieces.add(puzzlePiece);
        }
    }

}
