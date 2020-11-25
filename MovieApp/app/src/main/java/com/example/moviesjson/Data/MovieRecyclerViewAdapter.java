package com.example.moviesjson.Data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesjson.Model.Movie;
import com.example.moviesjson.MovieDetailsActivity;
import com.example.moviesjson.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.VievHolder> {

    Context context;

    List<Movie> movieList;

    public MovieRecyclerViewAdapter(Context ctx, List<Movie> movieList) {
       context = ctx;
        this.movieList = movieList;
    }

    @Override
    public VievHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);


        return new VievHolder(view,context);
    }

    @Override
    public void onBindViewHolder(VievHolder holder, int position) {

        Movie movie=movieList.get(position);

        String posterLink=movie.getPoster();
Picasso.with(context).load(posterLink).into(holder.moviePoster);


holder.movieTitle.setText(movie.getTitle());
holder.movieRelease.setText(movie.getYear());
holder.movieCategory.setText(movie.getMovieType());



    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class VievHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle;
        TextView movieCategory;
        TextView movieRelease;

        public VievHolder(View itemView, final Context ctx) {

            super(itemView);
           context=ctx;

                    moviePoster=itemView.findViewById(R.id.movieImageID);

                    movieCategory=itemView.findViewById(R.id.movieCat);

                    movieTitle=itemView.findViewById(R.id.movieTitleID);

                    movieRelease=itemView.findViewById(R.id.movieReleaseID);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Row tapped",Toast.LENGTH_LONG).show();

                Movie movie=movieList.get(getAdapterPosition());
                Intent intent=new Intent(context, MovieDetailsActivity.class);

                intent.putExtra("movie",movie);
                ctx.startActivity(intent);
            }
        });


        }
    }








}
