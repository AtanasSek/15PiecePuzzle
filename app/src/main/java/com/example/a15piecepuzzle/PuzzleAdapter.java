package com.example.a15piecepuzzle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class PuzzleAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<ArrayList<PuzzlePiece>> puzzlePieceIDs;

    public PuzzleAdapter(Context context, ArrayList<ArrayList<PuzzlePiece>> puzzlePieceIDs) {
        this.context = context;
        this.puzzlePieceIDs = puzzlePieceIDs;
    }

    @Override
    public int getCount() {
        return puzzlePieceIDs.size();
    }

    @Override
    public Object getItem(int position) {
        return puzzlePieceIDs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
        //puzzlePiece.setImageResource(puzzlePieceIDs.get(position)); //oh oh, what now
        //puzzlePiece.setImageBitmap();
        // TODO: brain stopped, treba integracija za array, so .get(position) samo redot go dobivam, a ne individualnata pozicija na puzzlepiece
        return puzzlePiece;
    }
}
