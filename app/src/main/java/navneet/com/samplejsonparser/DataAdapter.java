package navneet.com.samplejsonparser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Navneet Krishna on 16/12/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private ArrayList<CarModel> articles=new ArrayList<>();
    private ArrayList<CarModel> mArrayList=new ArrayList<>();
    private Context context;

    public DataAdapter(ArrayList<CarModel> articles, Context context) {
        this.context=context;
        this.articles=articles;
        this.mArrayList=articles;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    articles = mArrayList;
                } else {

                    ArrayList<CarModel> filteredList = new ArrayList<>();

                    for (CarModel carModel : mArrayList) {

                        if (carModel.getName().toLowerCase().contains(charString)) {

                            filteredList.add(carModel);
                        }
                    }

                    articles = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = articles;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                articles = (ArrayList<CarModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final int i) {

        final String car_name=articles.get(i).getName();
        final String car_desc=articles.get(i).getDesc();

        viewHolder.car_name.setText(car_name);
        viewHolder.car_desc.setText(car_desc);

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView car_name,car_desc;


        public ViewHolder(View view) {
            super(view);
            car_name = (TextView)view.findViewById(R.id.car_name);
            car_desc = (TextView)view.findViewById(R.id.car_desc);
        }
    }

}
