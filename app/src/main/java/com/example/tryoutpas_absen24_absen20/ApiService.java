package com.example.tryoutpas_absen24_absen20;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("search_all_teams.php?l=English%20Premier%20League")
    Call<DataModel> getEnglishLeague();

    @GET("search_all_teams.php?s=Soccer&c=Spain")
    Call<DataModel> getSpainLeague();
}
