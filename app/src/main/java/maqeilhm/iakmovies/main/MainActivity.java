package maqeilhm.iakmovies.main;


import android.annotation.SuppressLint;
import android.support.v4.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import maqeilhm.iakmovies.R;
import maqeilhm.iakmovies.data.ApiClient;
import maqeilhm.iakmovies.data.dao.MovieResponseDao;
import maqeilhm.iakmovies.data.offline.MovieContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private List<MainDao> data = new ArrayList<>();
    private Context context = this;
    private MainAdapter mAdapter;
    private TextView tvError;
    public static final String TAG = "MAINACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0,null,this);

        recyclerView = findViewById(R.id.rv);
        swipeRefreshLayout = findViewById(R.id.swipeMain);
        tvError = findViewById(R.id.tv_error);

        toolbar = findViewById(R.id.toolbar);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new MainAdapter(data);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movies");

        getData();

    }


    private void getData() {
        swipeRefreshLayout.setRefreshing(true);
        tvError.setVisibility(View.GONE);
        ApiClient.service().getMovieList("a9c89e148d7e5ef7bf5c54c18745c62c").enqueue(new Callback<MovieResponseDao>() {
            @Override
            public void onResponse(Call<MovieResponseDao> call, Response<MovieResponseDao> response) {
                if (response.isSuccessful()) {
                    swipeRefreshLayout.setRefreshing(false);

                    Uri deleUri = MovieContract.MovieEntry.CONTENT_URI;
                    getContentResolver().delete(deleUri,null,null);

                    for (MovieResponseDao.Result data:
                         response.body().getResults()) {
                        ContentValues contentValues = new ContentValues();

                        contentValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE_IDS, data.getId());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, data.getTitle());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_ORI_TITLE, data.getOriginal_title());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, data.getVote_count());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, data.isVideo() ? 1 : 0);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVG, data.getVote_average());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, data.getPopularity());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, data.getPoster_path());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANG, data.getOriginal_language());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_GENRE, "");
                        contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, data.getBackdrop_path());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_ADULT, data.isAdult() ? 1 : 0);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, data.getOverview());
                        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, data.getRelease_date());

                        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,contentValues);

                        if(uri != null){
                            Log.d("onResponse : ","INSERT DATA SUCCESS");
                        }
                        getSupportLoaderManager().restartLoader(0,null,MainActivity.this);
                    }
                    tvError.setVisibility(View.GONE);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(context, "Internal Server Error", Toast.LENGTH_SHORT).show();
                    tvError.setVisibility(View.VISIBLE);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponseDao> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                tvError.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mMovieData = null;

            @Override
            protected void onStartLoading() {
                if(mMovieData != null){
                    deliverResult(mMovieData);
                }else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try{
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.MovieEntry._ID);
                }catch (Exception e){
                    Log.e(TAG, "Failed to asyncronusly load data "+e.getMessage() );
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data){
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished: "+String.valueOf(cursor.getCount()));
        data.clear();

        for (int i = 0; i < cursor.getCount() ; i++) {
            cursor.moveToPosition(i);

            data.add(new MainDao(
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)),
                    "https://image.tmdb.org/t/p/w185/"+cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)),
                    "https://image.tmdb.org/t/p/w185/"+cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)),
                    cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVG))
            ));
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
