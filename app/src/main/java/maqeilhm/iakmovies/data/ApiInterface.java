package maqeilhm.iakmovies.data;

import maqeilhm.iakmovies.data.dao.MovieResponseDao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MAqielHilmanM on 12/10/2017.
 */

public interface ApiInterface {
    @GET("movie/popular")
    Call<MovieResponseDao> getMovieList(@Query("api_key") String api_key);
}
