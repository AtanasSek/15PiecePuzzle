package com.example.a15piecepuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
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


    public static int PUZZLE_SIZE = 4; //puzzle_size = 3 ne raboti, pretpostavuvam i so drugi vrednosti nema
    //ArrayList<ArrayList<Bitmap>> puzzle = new ArrayList<ArrayList<Bitmap>>();
    ArrayList<ArrayList<PuzzlePiece>> puzzle = new ArrayList<ArrayList<PuzzlePiece>>();
    int h, w;

    GridView puzzleGridView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        imgUri = Uri.parse(intent.getStringExtra("imgUri"));

        puzzleGridView = findViewById(R.id.puzzleGridView);
        puzzleGridView.setNumColumns(PUZZLE_SIZE);
        puzzleGridView.setVerticalScrollBarEnabled(false); //dont work
        puzzleGridView.setHorizontalScrollBarEnabled(false); //dont work
        PuzzleAdapter puzzleAdapter = new PuzzleAdapter(GameActivity.this,puzzle);
        puzzleGridView.setAdapter(puzzleAdapter);


        //End Game or return
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // ova postoi deka ne sakam callbacks da se obiduvam da pravam | ne raboti od x pricini
        puzzleGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(puzzleAdapter.getItem(position));
            }
        });


        //Image to bitmap and fill puzzle
        try {
            bitmap = getImageFromUri(imgUri);
            imageView.setImageBitmap(bitmap);
    
            h = bitmap.getHeight()/PUZZLE_SIZE;
            w = bitmap.getWidth()/PUZZLE_SIZE;

            fillPuzzle();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Bitmap getImageFromUri(Uri uri) throws IOException {
        return MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
    }


    private void fillPuzzle(){
        ArrayList<Integer> shuffled = new ArrayList(PUZZLE_SIZE*PUZZLE_SIZE);
        for(int i = 1; i <= PUZZLE_SIZE*PUZZLE_SIZE; i++)
            shuffled.add(i);
        
        Collections.shuffle(shuffled);
        
        for(int i = 0; i < PUZZLE_SIZE ; i++)
        {
            puzzle.add(new ArrayList<PuzzlePiece>()); // Dodadi empty row
            for(int j = 0; j < PUZZLE_SIZE; j++)
            {
    
                if(!(i == PUZZLE_SIZE - 1 && j == PUZZLE_SIZE - 1 ))
                {
                    Bitmap bitmap = Bitmap.createBitmap(this.bitmap, j * w, i * h, w, h);
                    puzzle.get(i).add(new PuzzlePiece(bitmap, shuffled.get(i * PUZZLE_SIZE + j), i * PUZZLE_SIZE + j + 1));
                }
                else
                {
                    Bitmap blackSquare = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
                    Canvas canvas = new Canvas(blackSquare);
                    Paint paint = new Paint();
                    paint.setColor(Color.DKGRAY);
                    canvas.drawRect(0F, 0F, w, h, paint);
                    puzzle.get(i).add(new PuzzlePiece(blackSquare,shuffled.get(i * PUZZLE_SIZE + j),i * PUZZLE_SIZE + j + 1));
                }
                
            }
        }
    }

}
