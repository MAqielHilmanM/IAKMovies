package maqeilhm.iakmovies.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import maqeilhm.iakmovies.R;
import maqeilhm.iakmovies.main.MainDao;

public class DetailActivity extends AppCompatActivity {


    private MainDao dao;
    private TextView txtTitle,txtDesc,txtDate,txtRate;
    private ImageView imgHeader,imgPoster;
    private Toolbar toolbar;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        dao = getIntent().getParcelableExtra("dataMovie");

        txtTitle = findViewById(R.id.txtDetailTitle);
        txtDate = findViewById(R.id.txtDetailDate);
        txtDesc = findViewById(R.id.txtDetailDesc);
        imgHeader = findViewById(R.id.ivHeader);
        imgPoster = findViewById(R.id.ivDetail);
        toolbar = findViewById(R.id.toolbar);
        ratingBar = findViewById(R.id.rating);
        txtRate = findViewById(R.id.txtDetailRate);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(dao.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        ratingBar.setRating((float) dao.getRating()/2);
        txtRate.setText(String.valueOf(dao.getRating()));
        txtDate.setText(dao.getReleaseDate());
        txtTitle.setText(dao.getTitle());
        txtDesc.setText(dao.getDescription());

        Picasso.with(this)
                .load(dao.getImageBgUrl())
                .fit()
                .placeholder(R.drawable.coca_cola)
                .into(imgHeader);

        Picasso.with(this)
                .load(dao.getImageUrl())
                .fit()
                .placeholder(R.drawable.coca_cola)
                .into(imgPoster);

    }
}
