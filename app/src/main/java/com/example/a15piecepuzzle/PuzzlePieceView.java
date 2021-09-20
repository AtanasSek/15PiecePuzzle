package com.example.a15piecepuzzle;

import android.content.Context;
import android.widget.ImageView;

public class PuzzlePieceView extends androidx.appcompat.widget.AppCompatImageView
{
    public PuzzlePiece piece;
    public PuzzlePieceView(Context context, PuzzlePiece piece)
    {
        super(context);
        this.piece = piece;
    }
    
}
