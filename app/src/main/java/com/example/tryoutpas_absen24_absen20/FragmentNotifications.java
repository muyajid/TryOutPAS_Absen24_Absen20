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

public class FragmentNotifications extends Fragment {
    private static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/3/";
    RecyclerView rvSpainLeague;
    AdapterRecylerViews2 adapterRecylerViews2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        rvSpainLeague = view.findViewById(R.id.rvSpainLeague);
        rvSpainLeague.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<DataModel> call = apiService.getSpainLeague();

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.isSuccessful()) {
                    List<DataModel.Teams> teamsList = response.body().getTeams();
                    adapterRecylerViews2 = new AdapterRecylerViews2(teamsList);
                    rvSpainLeague.setAdapter(adapterRecylerViews2);
                } else {
                    Log.e("Home", response.message());
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Log.e("Home", t.getMessage());
            }
        });

        return view;
    }
}
