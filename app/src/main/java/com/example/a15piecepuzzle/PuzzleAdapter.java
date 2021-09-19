package com.example.a15piecepuzzle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.example.a15piecepuzzle.GameActivity.PUZZLE_SIZE;

public class PuzzleAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<ArrayList<PuzzlePiece>> puzzlePieces;
    private PuzzlePiece selectedPiece;


    public PuzzleAdapter(Context context, ArrayList<ArrayList<PuzzlePiece>> puzzlePieces) {
        this.context = context;
        this.puzzlePieces = puzzlePieces;
    }
    
    @Override
    public int getCount() {
        return PUZZLE_SIZE * PUZZLE_SIZE;
    }

    @Override
    public PuzzlePiece getItem(int position)
    {
        for(int i = 0; i < PUZZLE_SIZE; i++)
        {
            ArrayList<PuzzlePiece> row = puzzlePieces.get(i);
            for( int j = 0; j < PUZZLE_SIZE; j++)
            {
                if (row.get(j).getCurrentPos() == position + 1)
                    return row.get(j);
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView puzzlePieceView;
        if(convertView == null){
            puzzlePieceView = new ImageView(context);
            puzzlePieceView.setLayoutParams(new ViewGroup.LayoutParams(250,250));
            puzzlePieceView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else{
            puzzlePieceView = (ImageView) convertView;
        }
        puzzlePieceView.setImageBitmap(this.getItem(position).getImg());

        //ke mora so callback
        puzzlePieceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPiece = getItem(position);

                if(selectedPiece.getSolvedPos() == PUZZLE_SIZE * PUZZLE_SIZE){


                }
                else{

                }


            }
        });
        
        //puzzlePieceView.setOnClickListener();
        
        return puzzlePieceView;
    }

}

