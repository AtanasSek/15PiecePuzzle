package com.example.a15piecepuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.a15piecepuzzle.GameActivity.GRID_SIZE;
import static com.example.a15piecepuzzle.GameActivity.PUZZLE_COUNT;

public class PuzzleAdapter extends BaseAdapter {

    private Context context;
    public Puzzle puzzle;
    private Bitmap blackSquare;
    public OnMoveCallback onMoveCallback;
    
    private void makeBlackSquare()
    {
        blackSquare = Bitmap.createBitmap(puzzle.pieceWidth, puzzle.pieceHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(blackSquare);
        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        canvas.drawRect(0F, 0F, puzzle.pieceWidth, puzzle.pieceHeight, paint);
    }
    
    public PuzzleAdapter(Context context, Puzzle puzzle, OnMoveCallback onMoveCallback) {
        this.context = context;
        this.puzzle = puzzle;
        this.onMoveCallback = onMoveCallback;
        makeBlackSquare();
    }
    
    @Override
    public int getCount() {
        return PUZZLE_COUNT;
    }

    @Override
    public PuzzlePiece getItem(int position)
    {
        return this.puzzle.getPiece(position + 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        PuzzlePiece piece = this.getItem(position);
        PuzzlePieceView puzzlePieceView;
        if(convertView == null)
        {
            puzzlePieceView = new PuzzlePieceView(context, piece);
            puzzlePieceView.setLayoutParams(new ViewGroup.LayoutParams(250,250));
            puzzlePieceView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else
        {
            puzzlePieceView = (PuzzlePieceView) convertView;
        }
       
        if (piece.isHole)
            puzzlePieceView.setImageBitmap(blackSquare);
        else
            puzzlePieceView.setImageBitmap(piece.getImg());
        
        puzzlePieceView.setOnClickListener(view -> {
            PuzzlePiece clickedPiece = ((PuzzlePieceView) view).piece;
            if(clickedPiece.getCurrentPos() == puzzle.getHolePosition() - 1)
                    puzzle.moveHole("left");
            else if(clickedPiece.getCurrentPos() == puzzle.getHolePosition() + 1)
                puzzle.moveHole("right");
            else if(clickedPiece.getCurrentPos() == puzzle.getHolePosition() + GRID_SIZE)
                    puzzle.moveHole("down");
            else if(clickedPiece.getCurrentPos() == puzzle.getHolePosition() - GRID_SIZE)
                    puzzle.moveHole("up");
            else
                Toast.makeText(this.context, "Not a valid move", Toast.LENGTH_SHORT).show();
            if(puzzle.checkSolved())
                Toast.makeText(this.context, "Congratulations! You win!", Toast.LENGTH_LONG).show();
            this.onMoveCallback.OnMove(this);
        });
        
        return puzzlePieceView;
    }
   
}
