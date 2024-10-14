package com.example.androidretrofit;

import android.app.NotificationChannel;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import com.example.androidretrofit.ResultadosAdapter;
import com.example.androidretrofit.dataaccess.ConexionRetrofit;
import com.example.androidretrofit.dataaccess.VistaResultadosAPI;
import com.example.androidretrofit.databinding.ActivityVistaResultadosBinding;
import com.example.androidretrofit.model.VistaResultados;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
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
    private void showNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Verifica si el dispositivo está ejecutando Android 8.0 (Oreo) o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Nombre del Canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        // Intent para abrir la actividad al hacer clic en la notificación
        Intent resultIntent = new Intent(this, VistaResultadosActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Crea la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_launcher_background) // Reemplaza con tu propio icono de notificación
                .setContentTitle("Título de la notificación")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);

        // Muestra la notificación
        notificationManager.notify(1, builder.build());
    }
    private void makeApiCall(String nombre) {

        Call<ArrayList<VistaResultados>> call = vistaResultadosAPI.getVistaResultadosById(nombre);

        call.enqueue(new Callback<ArrayList<VistaResultados>>() {
            @Override
            public void onResponse(Call<ArrayList<VistaResultados>> call, Response<ArrayList<VistaResultados>> response) {
                if (response.isSuccessful()) {
                    ArrayList<VistaResultados> vistaResultadosList = response.body();
                    updateUI(vistaResultadosList);
                    showNotification("Consulta exitosa");
                } else {
                    Toast.makeText(VistaResultadosActivity.this, "Error en la respuesta de la API. El registro no existe.", Toast.LENGTH_SHORT).show();
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
