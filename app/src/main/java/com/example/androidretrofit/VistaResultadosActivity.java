package com.example.androidretrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidretrofit.ResultadosAdapter;
import com.example.androidretrofit.dataaccess.ConexionRetrofit;
import com.example.androidretrofit.dataaccess.VistaResultadosAPI;
import com.example.androidretrofit.databinding.ActivityVistaResultadosBinding;
import com.example.androidretrofit.model.VistaResultados;

import java.util.List;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VistaResultadosActivity extends AppCompatActivity {
    VistaResultadosAPI vistaResultadosAPI;
    private ActivityVistaResultadosBinding binding;
    private ResultadosAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVistaResultadosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Retrofit retrofit = ConexionRetrofit.getInstanciaRetrofit();
        vistaResultadosAPI = retrofit.create(VistaResultadosAPI.class);
        buttons();


    }
    private String nombre;
    private void buttons() {
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = binding.editTextText2.getText().toString();
                setupRecyclerView();
                makeApiCall(nombre);

            }
        });
    }
    private void setupRecyclerView() {
        adapter = new ResultadosAdapter();
        binding.recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView1.setAdapter(adapter);
    }

    private void makeApiCall(String nombre) {

        Call<ArrayList<VistaResultados>> call = vistaResultadosAPI.getVistaResultadosById(nombre);

        call.enqueue(new Callback<ArrayList<VistaResultados>>() {
            @Override
            public void onResponse(Call<ArrayList<VistaResultados>> call, Response<ArrayList<VistaResultados>> response) {
                if (response.isSuccessful()) {
                    ArrayList<VistaResultados> vistaResultadosList = response.body();
                    updateUI(vistaResultadosList);
                } else {
                    Toast.makeText(VistaResultadosActivity.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VistaResultados>> call, Throwable t) {
                Toast.makeText(VistaResultadosActivity.this, "Error en la llamada a la API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(ArrayList<VistaResultados> vistaResultadosList) {
        adapter.actualizarLista(vistaResultadosList);
        adapter.notifyDataSetChanged();
    }
}
