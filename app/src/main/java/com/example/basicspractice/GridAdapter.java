package com.example.basicspractice;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.view.LayoutInflater;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private int tiles[];
    private LayoutInflater inflater;

    // Constructor
    public GridAdapter(Context applicationContext, int[] tiles) {
        this.context = applicationContext;
        this.tiles = tiles;
        inflater = (LayoutInflater.from(applicationContext));
    }

    // Adapter Methods
    @Override
    public int getCount() {
        return tiles.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_boardtile, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
        icon.setImageResource(tiles[i]); // set tile images
        return view;
    }
}
