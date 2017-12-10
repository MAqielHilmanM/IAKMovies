package maqeilhm.iakmovies.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import maqeilhm.iakmovies.R;
import maqeilhm.iakmovies.detail.DetailActivity;

/**
 * Created by MAqielHilmanM on 12/10/2017.
 */

public class MainAdapter  extends RecyclerView.Adapter<MainAdapter.MainHolder>{
    private List<MainDao> data =  new ArrayList<>();

    public MainAdapter(List<MainDao> data) {
        this.data = data;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main,parent,false));
    }

    @Override
    public void onBindViewHolder(MainHolder holder, final int position) {
        holder.txt.setSelected(true);
        holder.txt.setText(data.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), DetailActivity.class).putExtra("dataMovie",data.get(position)));
            }
        });
        Picasso.with(holder.img.getContext())
                .load(data.get(position).getImageUrl())
                .fit()
                .placeholder(R.drawable.coca_cola)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MainHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txt;
        public MainHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.row_image);
            txt = itemView.findViewById(R.id.row_title);
        }
    }
}
