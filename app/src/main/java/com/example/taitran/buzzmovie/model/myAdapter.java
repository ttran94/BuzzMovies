package com.example.taitran.buzzmovie.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.taitran.buzzmovie.controller.R;
import com.example.taitran.buzzmovie.controller.RatingActivity;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MovieView>{
    /**
     * The image loader
     */
    private final ImageLoader image;
    /**
     * The layout inflater
     */
    private final LayoutInflater layout;
    /**
     * The list of movies
     */
    private static ArrayList<Movie> movieList = new ArrayList<>();

    /**
     * set up VolleySingleton, image and layout for the activity
     * @param context get the activity class
     */
    public myAdapter(Context context) {
        layout = LayoutInflater.from(context);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(context);
        image = volleySingleton.getImage();
    }

    @Override
    public MovieView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layout.inflate(R.layout.customview, parent, false);
        return new MovieView(view);
    }

    /**
     * passed the movie list to the adapter and set up the view.
     * @param movie take in a list that contains movies
     */
    public void setMovieList(ArrayList<Movie> movie) {
        //noinspection AccessStaticViaInstance
        this.movieList = movie;
        notifyItemRangeChanged(0, movie.size());
    }

    /**
     * The array list of movies
     * @return the movie list
     */
    public static ArrayList<Movie> getMovieList() {
        return movieList;
    }

// --Commented out by Inspection START (4/10/2016 9:56 PM):
//    /**
//     * Translation animation.
//     * @param holder The view for the translation.
//     */
//    private static void translation(RecyclerView.ViewHolder holder) {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(holder.itemView, "translationY", -100, 0);
//        animator.setDuration(1000);
//        animator.start();
//    }
// --Commented out by Inspection STOP (4/10/2016 9:56 PM)

    /**
     * Fade in animation
     * @param view the view for the animation
     */
    private void fadeInAnimation(View view) {
        AlphaAnimation animator = new AlphaAnimation(0.0f, 1.0f);
        animator.setDuration(1000);
        view.startAnimation(animator);
    }

    @Override
    public void onBindViewHolder(MovieView holder, int position) {
        Movie currentView = movieList.get(position);
        holder.movieTitle.setText(currentView.getTitle());
        holder.movieDate.setText("Year: " + currentView.getYear());
        holder.movieType.setText("Type: " + currentView.getType());
        String thumbNail = currentView.getPoster();
        holder.position = position;
        final MovieView temp = holder;
        if(thumbNail != null) {image.get(thumbNail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {temp.moviePoster.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        }
        fadeInAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieView extends RecyclerView.ViewHolder{
        /**
         * The movie poster
         */
        private final ImageView moviePoster;
        /**
         * The movie title
         */
        private final TextView movieTitle;
        /**
         * The date of the movie
         */
        private final TextView movieDate;
        /**
         * The type of movie
         */
        private final TextView movieType;
        /**
         * The position of the movie
         */
        private int position;

        /**
         * The movie view
         * @param itemView the movie view.
         */
        public MovieView(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent ratingPage = new Intent(context, RatingActivity.class);
                    //send position of the movie in movieList for access in the activity
                    ratingPage.putExtra("position", position);

                    context.startActivity(ratingPage);
                }
            });

            moviePoster = (ImageView) itemView.findViewById(R.id.imageURL);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieDate = (TextView) itemView.findViewById(R.id.movieYear);
            movieType = (TextView) itemView.findViewById(R.id.movieType);
        }


    }
}
