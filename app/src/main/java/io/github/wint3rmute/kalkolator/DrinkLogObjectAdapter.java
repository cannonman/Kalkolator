package io.github.wint3rmute.kalkolator;

/**
 * Created by mateu on 16/01/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DrinkLogObjectAdapter extends RecyclerView.Adapter<DrinkLogObjectAdapter.MyViewHolder> {

    private List<DrinkLogObject> drinkLog;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, liters, percentage;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            liters = (TextView) view.findViewById(R.id.liters);
            percentage = (TextView) view.findViewById(R.id.percentage);
        }
    }


    public DrinkLogObjectAdapter(List<DrinkLogObject> moviesList) {
        this.drinkLog = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drink_log_object, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrinkLogObject drinkLogObject = drinkLog.get(position);
        holder.date.setText(drinkLogObject.getDate().toString());
        holder.liters.setText(Float.toString(drinkLogObject.getLiters()));
        holder.percentage.setText(Float.toString(drinkLogObject.getPercentage()));
    }

    @Override
    public int getItemCount() {
        return drinkLog.size();
    }
}
