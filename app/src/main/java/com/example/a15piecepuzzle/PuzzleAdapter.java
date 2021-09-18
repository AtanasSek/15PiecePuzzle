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

    public PuzzleAdapter(Context context, ArrayList<ArrayList<PuzzlePiece>> puzzlePieces) {
        this.context = context;
        this.puzzlePieces = puzzlePieces;
    }

    @Override
    public int getCount() {
        return PUZZLE_SIZE * PUZZLE_SIZE;
    }

    @Override
    public PuzzlePiece getItem(int position) {
        return puzzlePieces.get(position / PUZZLE_SIZE).get(position % PUZZLE_SIZE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView puzzlePiece;
        if(convertView == null){
            puzzlePiece = new ImageView(context);
            puzzlePiece.setLayoutParams(new ViewGroup.LayoutParams(250,250));
            puzzlePiece.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else{
            puzzlePiece = (ImageView) convertView;
        }
        puzzlePiece.setImageBitmap(this.getItem(position).getImg());
        return puzzlePiece;
    }
}
