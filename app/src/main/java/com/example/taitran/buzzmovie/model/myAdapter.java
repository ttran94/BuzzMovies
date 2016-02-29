package com.example.taitran.buzzmovie.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.taitran.buzzmovie.controller.R;

import java.util.ArrayList;

/**
 * Created by taitr on 2/28/2016.
 */
public class myAdapter extends RecyclerView.Adapter<myAdapter.MyView> {

    private ImageLoader image;
    private VolleySingleton volleySingleton;
    private LayoutInflater layout;
    private ArrayList<Movie> movieList = new ArrayList<>();

    public myAdapter(Context context) {
        layout = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance(context);
        image = volleySingleton.getImage();
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layout.inflate(R.layout.customview, parent, false);
        MyView viewHolder = new MyView(view);
        return viewHolder;
    }

    public void setMovie(ArrayList<Movie> movie) {
        this.movieList = movie;
        notifyItemRangeChanged(0, movie.size());
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {
        Movie currentVIew = movieList.get(position);
        holder.movieTitle.setText(currentVIew.getTitle());
        holder.movieDate.setText("Year: " + currentVIew.getYear());
        holder.movieType.setText("Type: " + currentVIew.getType());
        String thumbNail = currentVIew.getPoster();
        final MyView temp = holder;
        if(thumbNail != null) {
            image.get(thumbNail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    temp.moviePoster.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
        });

        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MyView extends RecyclerView.ViewHolder {
        private ImageView moviePoster;
        private TextView movieTitle;
        private TextView movieDate;
        private  TextView movieType;
        public MyView(View itemView) {
            super(itemView);

            moviePoster = (ImageView) itemView.findViewById(R.id.imageURL);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieDate = (TextView) itemView.findViewById(R.id.movieYear);
            movieType = (TextView) itemView.findViewById(R.id.movieType);
        }
    }
}
