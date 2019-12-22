package edu.uoc.android.rest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class   RetrofitFactory {
        public static MuseumAPI getMuseumAPI() {
            Retrofit retrofit =  new Retrofit.Builder() .baseUrl(MuseumService.API_URL)
                    .addConverterFactory(GsonConverterFactory. create()).build();
            MuseumAPI museumAPI = retrofit.create(MuseumAPI. class  );
            return museumAPI;
        }
}
