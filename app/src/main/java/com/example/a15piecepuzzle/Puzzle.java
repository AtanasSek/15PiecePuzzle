package com.example.a15piecepuzzle;

import android.graphics.Bitmap;

import java.util.ArrayList;

import static com.example.a15piecepuzzle.GameActivity.PUZZLE_COUNT;

public class Puzzle
{
    public int GRID_SIZE;
    public int PIECE_COUNT;
    public ArrayList<PuzzlePiece> puzzlePieces;
    public ArrayList<Integer> initialState;
    public int puzzleHeight, puzzleWidth;
    public int pieceHeight, pieceWidth;
    public Bitmap puzzleBitmap;
    
    public Puzzle(Bitmap puzzleBitmap, int GRID_SIZE, ArrayList<PuzzlePiece> puzzlePieces, ArrayList<Integer> initialState)
    {
        this.GRID_SIZE = GRID_SIZE;
        this.PIECE_COUNT = GRID_SIZE * GRID_SIZE;
        this.puzzlePieces = puzzlePieces;
        this.initialState = initialState;
        this.puzzleBitmap = puzzleBitmap;
        puzzleHeight = puzzleBitmap.getHeight();
        pieceHeight =  puzzleHeight / GRID_SIZE;
        puzzleWidth = puzzleBitmap.getWidth();
        pieceWidth = puzzleWidth / GRID_SIZE;
    }
    
    public PuzzlePiece getPiece(int position)
    {
        for(int i = 0; i < PUZZLE_COUNT; i++)
        {
            if (puzzlePieces.get(i).getCurrentPos() == position)
                return puzzlePieces.get(i);
        }
        return null;
    }
    
    public PuzzlePiece getHole()
    {
        for(int i = 0; i < PUZZLE_COUNT; i++)
        {
            if (puzzlePieces.get(i).isHole)
                return puzzlePieces.get(i);
        }
        return null;
    }
    
    public int getHolePosition(){
        return getHole().getCurrentPos();
    }
    
    private void moveHole(int direction)
    {
        PuzzlePiece hole = getHole();
        int holePosition = hole.getCurrentPos();
        PuzzlePiece clicked = getPiece(holePosition + direction);
        hole.setCurrentPos(holePosition + direction);
        clicked.setCurrentPos(holePosition);
    }
    
    public void moveHole(String direction)
    {
        switch (direction)
        {
            case "up":
                moveHole(-GRID_SIZE);
                break;
            case "down":
                moveHole(GRID_SIZE);
                break;
            case "left":
               moveHole(-1);
                break;
            case "right":
                moveHole(1);
                break;
        }
    }
    
    public boolean checkSolved()
    {
        boolean solved = true;
        for(int i = 0; i < PUZZLE_COUNT; i++)
        {
            if(this.puzzlePieces.get(i).getCurrentPos() != this.puzzlePieces.get(i).getSolvedPos()) solved = false;
        }
        return solved;
    }
}
