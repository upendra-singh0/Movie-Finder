package github.upendrasinghktp.moviefinder.Util;

import org.json.JSONObject;

import github.upendrasinghktp.moviefinder.Model.Movie;

/**
 * Created by Upendra on 4/18/2017.
 */

public class Parser {

    public static Movie ParseMovie(JSONObject object) {

        Movie movie = new Movie();

        try {

            movie.setTitle(object.getString("Title"));
            movie.setYear(object.getString("Year"));
            movie.setImdbID(object.getString("imdbID"));
            movie.setType(object.getString("Type"));
            movie.setPoster(object.getString("Poster"));
            movie.setRated(object.getString("Rated"));
            movie.setReleased(object.getString("Released"));
            movie.setRuntime(object.getString("Runtime"));
            movie.setGenre(object.getString("Genre"));
            movie.setPlot(object.getString("Plot"));
            movie.setImdbRating(object.getString("imdbRating"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    public static Movie ParseListItemMovie(JSONObject object) {

        Movie movie = new Movie();

        try {

            movie.setTitle(object.getString("Title"));
            movie.setYear(object.getString("Year"));
            movie.setImdbID(object.getString("imdbID"));
            movie.setType(object.getString("Type"));
            movie.setPoster(object.getString("Poster"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }
}
