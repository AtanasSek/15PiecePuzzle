package com.example.a15piecepuzzle;

import android.graphics.Bitmap;

class PuzzlePiece
{
    private Bitmap img;
    private int currentPos;
    private int solvedPos;
    
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
    
    public void setCurrentPos(int currentPos)
    {
        this.currentPos = currentPos;
    }
    
    public int getSolvedPos()
    {
        return solvedPos;
    }
    
    public void setSolvedPos(int solvedPos)
    {
        this.solvedPos = solvedPos;
    }
}
