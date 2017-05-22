package kot.helena.gliphyapp;

import kot.helena.gliphyapp.api.ApiKeyInterceptor;
import kot.helena.gliphyapp.api.ImageDto;
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
        Call<ImageDto> query(@Query("limit") int limit,
                             @Query("q") String tag);
    }

    public void guerryGifs(int limit) {
        Call<ImageDto> call = myService.query(limit, "cat");

        call.enqueue(new Callback<ImageDto>() {
            @Override
            public void onResponse(Call<ImageDto> call, Response<ImageDto> response) {
                ImageDto record = response.body();
                try {
                    serialize(record);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ImageDto> call, Throwable t) {
                System.out.println("shame");
            }
        });
    }

    ImageData serialize(ImageDto dto) throws Exception {
        ImageData output = new ImageData();
        //todo mapper
        return output;
    }

}
