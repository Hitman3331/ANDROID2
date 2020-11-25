package com.example.moviesjson;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesjson.Data.MovieRecyclerViewAdapter;
import com.example.moviesjson.Model.Movie;
import com.example.moviesjson.Util.Constants;
import com.example.moviesjson.Util.Prefs;
import com.squareup.picasso.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    MovieRecyclerViewAdapter movieRecyclerViewAdapte;
    RequestQueue queue;
    RecyclerView recyclerView;
    RecyclerView.Adapter rAdapter;
    List<Movie> movieList;
    String url;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();


        if (id == R.id.new_search) {
            showInputDialog();
            // return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        queue = Volley.newRequestQueue(this);

         url = "http://www.omdbapi.com/?s=Batman&page=2&apikey=21e0dfef";
       // http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&apikey=21e0dfef

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieList=new ArrayList<>();
        Prefs prefs=new Prefs(MainActivity.this);
        String search=prefs.getSearch();



        movieList.clear();

        movieList=getMovies(search);

        MovieRecyclerViewAdapter movieRecyclerViewAdapter=new MovieRecyclerViewAdapter(this,movieList);



        recyclerView.setAdapter(movieRecyclerViewAdapter);
        movieRecyclerViewAdapter.notifyDataSetChanged();



/*
        //String ur_episode="http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&Episode=2&apikey=21e0dfef";
        JSONObject jo=new JSONObject();
            String searchTerm="Batman";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,Constants.URL_LEFT + searchTerm +Constants.URL_RIGHT +Constants.API_KEY,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String tx=response.get("Title").toString();
                    Log.d("TAB","ovo je result "+tx);
                    JSONArray jsonArray=response.getJSONArray("Search");

                    for(int i=0;i<jsonArray.length();i++){JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Log.d("TAB","OVO SU OBJEKTI IZ NIZA "+jsonObject);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("TAB","ALEX");
                Log.d("TAB","ovo je result "+response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



queue.add(jsonObjectRequest);
*/




    }

        public List<Movie> getMovies(String searchTerm){

        movieList.clear();

            Log.d("TAB","ovo je poslije clear duzina liste "+movieList.size());

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,Constants.URL_LEFT + searchTerm +Constants.URL_RIGHT +Constants.API_KEY, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray=response.getJSONArray("Search");

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        Movie movie=new Movie();

                        movie.setTitle(jsonObject.getString("Title"));
                        movie.setYear(jsonObject.getString("Year"));
                        movie.setMovieType(jsonObject.getString("Type"));
                        movie.setImdbId(jsonObject.getString("imdbID"));
                        movie.setPoster(jsonObject.getString("Poster"));


                        movieList.add(movie);

                        //Log.d("Movies",movie.getTitle());

                    }


                }catch (JSONException e){e.printStackTrace();}

            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        queue.add(jsonObjectRequest);



return movieList;

        }



    public void showInputDialog() {

        alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        final EditText newSearchEdt = (EditText) view.findViewById(R.id.searchEdt);
        Button submitButton = (Button) view.findViewById(R.id.submitButton);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs prefs = new Prefs(MainActivity.this);

                if (!newSearchEdt.getText().toString().isEmpty()) {

                    String search = newSearchEdt.getText().toString();
                    prefs.setSearch(search);
                    movieList.clear();

                    getMovies(search);

                    // movieRecyclerViewAdapter.notifyDataSetChanged();//Very important!!
                }
                dialog.dismiss();


            }
        });

}}