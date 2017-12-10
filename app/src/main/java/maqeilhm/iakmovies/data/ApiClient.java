package maqeilhm.iakmovies.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MAqielHilmanM on 12/10/2017.
 */

public class ApiClient {
    public static ApiInterface service(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build();
        return retrofit.create(ApiInterface.class);
    }
}
