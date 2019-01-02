package navneet.com.samplejsonparser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Navneet Krishna on 16/12/18.
 */
interface RequestInterface {
    @GET("sample_array.json")
    Call<List<CarModel>> getJson();
}
