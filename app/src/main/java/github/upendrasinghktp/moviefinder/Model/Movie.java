package github.upendrasinghktp.moviefinder.Model;

import java.io.Serializable;

/**
 * Created by Upendra on 4/18/2017.
 */

public class Movie implements Serializable {

    private String title;
    private String year;
    private String imdbID;
    private String type;
    private String poster;
    // first five common in search list also

    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String plot;
    private String imdbRating;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getPoster() {
        return poster;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlot() {
        return plot;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
}
