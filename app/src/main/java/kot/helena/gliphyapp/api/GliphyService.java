package kot.helena.gliphyapp.api;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import kot.helena.gliphyapp.api.ApiKeyInterceptor;
import kot.helena.gliphyapp.api.dto.DataDto;
import kot.helena.gliphyapp.api.dto.ResponseDto;
import kot.helena.gliphyapp.dashboard.ImageData;
import kot.helena.gliphyapp.dashboard.event.LoadDataEvent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class GliphyService {
    public static final String API_URL = "http://api.giphy.com";
    public static final String API_KEY = "dc6zaTOxFJmzC";
    GifService myService;

    public GliphyService() {
        //Logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //Api key
        httpClient.addInterceptor(new ApiKeyInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myService = retrofit.create(GifService.class);
    }

    public interface GifService {
        @GET("/v1/gifs/search?")
        Call<ResponseDto> query(@Query("limit") int limit,
                                @Query("q") String tag);

        @GET("/v1/gifs/trending?")
        Call<ResponseDto> trending(@Query("limit") int limit);
    }

    public void getTrending(int limit) {
        Call<ResponseDto> call = myService.trending(limit);

        call.enqueue(new Callback<ResponseDto>() {
            @Override
            public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {
                ResponseDto record = response.body();
                try {
                    List<ImageData> images = serialize(record.data);
                    EventBus.getDefault().post(new LoadDataEvent(images));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseDto> call, Throwable t) {
                System.out.println("shame");
            }
        });
    }

    List<ImageData> serialize(DataDto[] dto) throws Exception {
        List<ImageData> output = new ArrayList<ImageData>();
        for (DataDto dataDto : dto) {
            output.add(new ImageData(dataDto.images.previewImage.url));
        }
        return output;
    }

}
