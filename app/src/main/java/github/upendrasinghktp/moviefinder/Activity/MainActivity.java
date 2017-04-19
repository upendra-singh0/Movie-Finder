package github.upendrasinghktp.moviefinder.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Fade;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionMenu;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import github.upendrasinghktp.moviefinder.Model.Movie;
import github.upendrasinghktp.moviefinder.R;
import github.upendrasinghktp.moviefinder.Util.Config;
import github.upendrasinghktp.moviefinder.Util.Parser;

import static android.support.design.R.id.visible;

public class MainActivity extends AppCompatActivity {

    Context context;
    RequestQueue queue;
    LayoutInflater inflater;


    private Movie movie;
    private ArrayList<Movie> movieList;
    public static final String TAG = "MainActivity";

    private com.github.clans.fab.FloatingActionButton fab1,fab2,fab3;
    private FloatingActionMenu floatingActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab);
        context = getApplicationContext();
        queue = Volley.newRequestQueue(context);
        inflater = getLayoutInflater();
        movieList = new ArrayList<>();
        movie= new Movie();
        setButtonClick();
    }

    private void setButtonClick()
    {
        fab1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(networkIsAvailable(context))
                {
                    floatingActionMenu.close(true);
                    showInputDialog(0);
                }
                else
                    Snackbar.make(view, "Please connect to Internet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(networkIsAvailable(context))
                {
                    floatingActionMenu.close(true);
                    showInputDialog(1);
                }
                else
                    Snackbar.make(view, "Please connect to Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });

        fab3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(networkIsAvailable(context))
                {
                    floatingActionMenu.close(true);
                    showInputDialog(2);
                }
                else
                    Snackbar.make(view, "Please connect to Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }

    protected void showInputDialog(final int type) {
        boolean wrapInScrollView = true; // for list automatically adjust
        MaterialDialog.Builder builder;
                builder= new MaterialDialog.Builder(this)
                .title("Enter Details")
                .customView(R.layout.input_dialog, wrapInScrollView)
                .positiveText("Search")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String name = ((EditText) dialog.getCustomView().findViewById(R.id.name)).getText().toString().trim();
                        name = name.replace(" ","+");
                        String str="";
                        if(name.length()==0 )
                            Toast.makeText(context,"Please enter details",Toast.LENGTH_SHORT).show();
                        else
                        {
                            dialog.dismiss();
                            switch (type) {
                                case 0:
                                    str=str+"s="+name;
                                    break;
                                case 1:
                                    str=str+"t="+name;
                                    break;
                                case 2:
                                    str=str+"i="+name;
                                    break;
                            }

                            String year=((EditText) dialog.getCustomView().findViewById(R.id.date)).getText().toString().trim();
                            if(year.length()!=0) str=str+"&y="+year;

                            if(((RadioGroup) dialog.getCustomView().findViewById(R.id.radio_group)).getCheckedRadioButtonId() != -1)
                            {
                                int selectedId = ((RadioGroup) dialog.getCustomView().findViewById(R.id.radio_group)).getCheckedRadioButtonId();
                                if(selectedId==R.id.movie) str=str+"&type=movie";
                                else if(selectedId==R.id.tvseries) str=str+"&type=series";
                                else if(selectedId==R.id.episode) str=str+"&type=episode";

                            }
                            apiRequest(type,str);
                        }

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .autoDismiss(false)
                .cancelable(false);

        MaterialDialog dialog = builder.build();
        View view = dialog.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        EditText editText = (EditText) view.findViewById(R.id.name);

        switch (type) {
            case 0:
                textView.setText("Name ");
                editText.setHint("Road");
                break;
            case 1:
                textView.setText("Title");
                editText.setHint("Batman");
                break;
            case 2:
                textView.setText("IMDB ID");
                editText.setHint("tt0096895");
                break;

        }
        dialog.show();
    }


    private void apiRequest(final int type,final String str)
    {
        String url = Config.URL+str;
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null && response.length() > 0) {
                            try {
                                if(response.getString("Response").equals("True"))
                                {
                                    if(type==0)
                                    {   if(movieList.size()!=0) movieList.clear();
                                        JSONArray jsonArray = response.getJSONArray("Search");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject o = jsonArray.getJSONObject(i);
                                            Movie m = Parser.ParseListItemMovie(o);
                                            movieList.add(m);
                                            showMovieList();
                                        }
                                    }
                                    else
                                    {
                                         movie = Parser.ParseMovie(response);
                                         showMovie();
                                    }
                                }
                                else
                                {
                                    String error=response.getString("Error");
                                    Toast.makeText(getApplicationContext(), "Error: "+error, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // errors
                            Toast.makeText(getApplicationContext(), "Retry Later ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                        Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

//        disabling retry policy so that it won't make
//        multiple http calls
//        int socketTimeout = 0;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

//      jsonObjectRequest.setRetryPolicy(policy);
        jsonObjectRequest.setTag(TAG);
        queue.add(jsonObjectRequest);
    }

    private void showMovieList(){

        findViewById(R.id.relative_layout3).setVisibility(View.GONE);
        findViewById(R.id.relative_layout2).setVisibility(View.GONE);
        findViewById(R.id.relative_layout1).setVisibility(View.VISIBLE);

        ListView listView = (ListView) findViewById(R.id.list_view);
        MovieListAdapter adapter = new MovieListAdapter(context, R.layout.list_row, movieList);
        listView.setAdapter(adapter);
    }

    private class MovieListAdapter extends ArrayAdapter<Movie> {

        public MovieListAdapter(Context context, int textViewResourceId, ArrayList<Movie> movieItemsList) {
            super(context, textViewResourceId, movieItemsList);
        }

        @Override
        public int getCount() {
            return movieList.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v;

            if (convertView != null) {
                v = convertView;
            } else {
                v = inflater.inflate(R.layout.list_row, null);
            }

            ImageView thumbNail = (ImageView) v.findViewById(R.id.thumbnail);
            TextView title = (TextView) v.findViewById(R.id.title);
            TextView rating = (TextView) v.findViewById(R.id.rating);
            TextView genre = (TextView) v.findViewById(R.id.genre);
            TextView year = (TextView) v.findViewById(R.id.releaseYear);

            // getting movie data for the row
            Movie m = movieList.get(position);
            Glide.with(context).load(m.getPoster()).into(thumbNail);
            title.setText(m.getTitle());
            rating.setText("IMDB ID: " + m.getImdbID());
            genre.setText(m.getType());
            year.setText(m.getYear());
            return v;
        }
    }

    private void showMovie()
    {
        View view = findViewById(R.id.relative_layout2);

        ImageView thumbNail = (ImageView) view.findViewById(R.id.thumbnail);
        Glide.with(context).load(movie.getPoster()).into(thumbNail);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(movie.getTitle());
        TextView type = (TextView) view.findViewById(R.id.type);
        type.setText(movie.getType());
        TextView released = (TextView) view.findViewById(R.id.released);
        released.setText(movie.getReleased());
        TextView genre = (TextView) view.findViewById(R.id.genre);
        genre.setText(movie.getGenre());
        TextView plot = (TextView) view.findViewById(R.id.plot);
        plot.setText(movie.getPlot());
        TextView runtime = (TextView) view.findViewById(R.id.runtime);
        runtime.setText(movie.getRuntime());
        TextView rated = (TextView) view.findViewById(R.id.rated);
        rated.setText(movie.getRated());
        TextView imdbRating = (TextView) view.findViewById(R.id.imdbRating);
        imdbRating.setText(movie.getImdbRating());

        findViewById(R.id.relative_layout1).setVisibility(View.GONE);
        findViewById(R.id.relative_layout3).setVisibility(View.GONE);
        findViewById(R.id.relative_layout2).setVisibility(View.VISIBLE);

    }


    private boolean networkIsAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}
