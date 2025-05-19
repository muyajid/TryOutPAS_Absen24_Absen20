package com.example.tryoutpas_absen24_absen20;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentDashboard extends Fragment {
    RecyclerView rvEnglishLeague;
    AdapterRecylerViews adapter;
    private static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/3/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rvEnglishLeague = view.findViewById(R.id.rvEnglishLeague);
        rvEnglishLeague.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<DataModel> call = apiService.getEnglishLeague();

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.isSuccessful()) {
                    List<DataModel.Teams> teams = response.body().getTeams();
                    adapter = new AdapterRecylerViews(teams);
                    rvEnglishLeague.setAdapter(adapter);
                } else {
                    Log.e("Dashboard", response.message());
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Log.e("Dashboard",t.getMessage());
            }
        });
        return view;
    }
}
