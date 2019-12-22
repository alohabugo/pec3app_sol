package edu.uoc.android.rest;

import edu.uoc.android.models.Museums;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MuseumAPI {
     @GET( "/api/dataset/museus/format/json/pag-ini/{pagIni}/pag-fi/{pagFi}"  )
     Call<Museums> museums(@Path("pagIni") String pagIni, @Path("pagFi") String pagFi);
}


