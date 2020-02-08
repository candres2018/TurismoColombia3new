package com.example.turismocolombia3.ui.quipama;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismocolombia3.Interfaces.QuipamaApi;
import com.example.turismocolombia3.Modelos.Quipama;
import com.example.turismocolombia3.QuipamaAdapter;
import com.example.turismocolombia3.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuipamaFragment extends Fragment {


    private Retrofit retrofit;
    private final  String logs = "---| ";

    private View root;
    private Bundle bundle;

    private RecyclerView recycler;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_quipama, container, false);
        bundle = savedInstanceState;

        recycler = root.findViewById(R.id.recycler_quipama);
        recycler.setHasFixedSize(true);

        LinearLayoutManager linear = new LinearLayoutManager(root.getContext());
        recycler.setLayoutManager(linear);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.datos.gov.co/resource/")
                .addConverterFactory(GsonConverterFactory
                        .create()).build();
        getData();


        return root;
    }

    public void onResume(){
        super.onResume();
    }

    public void onPause(){
        super.onPause();
    }

    private void getData() {
        try{

            QuipamaApi service = retrofit.create(QuipamaApi.class);
            Call<List<Quipama>> QuipamaResponseCall = service.getQuipamaList("aq9g-b755.json");

            QuipamaResponseCall.enqueue(new Callback<List<Quipama>>() {
                @Override
                public void onResponse(Call<List<Quipama>> call, Response<List<Quipama>> response) {
                    if(response.isSuccessful()){
                        List<Quipama> quipamas = response.body();
                        QuipamaAdapter adapter = new QuipamaAdapter(quipamas);
                        recycler.setAdapter(adapter);
                    } else {
                        Log.e(logs, "onResponse: "+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<Quipama>> call, Throwable t) {
                    Log.e(logs," onFailure: "+t.getStackTrace());
                }
            });

        }catch (Exception e){
            Log.e(logs, "onFailure: " + e);
        }

    }
}