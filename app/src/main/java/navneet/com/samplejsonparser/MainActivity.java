package navneet.com.samplejsonparser;

import android.app.ProgressDialog;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 10;
    private DataAdapter dataAdapter;
    private ArrayList<CarModel> carModels=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private ProgressDialog mProgressDialog;
    private TextView myTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
        mRecyclerView=(RecyclerView)findViewById(R.id.cars_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        parseJson();
    }

    private void parseJson() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://navneet7k.github.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<CarModel>> call1=request.getJson();
        call1.enqueue(new Callback<List<CarModel>>() {
            @Override
            public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body()!=null) {
                    carModels = new ArrayList<>(response.body());
                    dataAdapter=new DataAdapter(carModels,MainActivity.this);
                    mRecyclerView.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CarModel>> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"Oops! Something went wrong!",Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (dataAdapter!=null)
                    dataAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}
