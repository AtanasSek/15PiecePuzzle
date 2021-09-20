package com.example.a15piecepuzzle;

import android.database.DataSetObserver;
import android.graphics.Bitmap;

class PuzzlePiece extends DataSetObserver
{
    private Bitmap img;
    private int currentPos;
    private int solvedPos;
    public boolean isHole;
    
    public PuzzlePiece(Bitmap img, int currentPos, int solvedPos)
    {
        this.img = img;
        this.currentPos = currentPos;
        this.solvedPos = solvedPos;
    }
    
    public Bitmap getImg()
    {
        return img;
    }
    
    public void setImg(Bitmap img)
    {
        this.img = img;
    }
    
    public int getCurrentPos()
    {
        return currentPos;
    }
    
    public void setCurrentPos(int newPos)
    {
        this.currentPos = newPos;
    }
    
    public int getSolvedPos()
    {
        return solvedPos;
    }
    
    @Override
    public void onChanged()
    {
        super.onChanged();
    }
}
