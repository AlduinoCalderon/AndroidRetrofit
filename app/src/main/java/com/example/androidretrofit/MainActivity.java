package com.example.androidretrofit;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.androidretrofit.dataaccess.ConexionRetrofit;
import com.example.androidretrofit.dataaccess.ValoresNormalesAPI;
import com.example.androidretrofit.databinding.ActivityMainBinding;
import com.example.androidretrofit.model.ValoresNormales;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vista = ActivityMainBinding.inflate(getLayoutInflater());
        View view = vista.getRoot();
        setContentView(view);

        botones();

    }
 private void botones() {
        vista.btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    insertarValoresNormales();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al actualizar valores normales: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        vista.btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(MainActivity.this, VistaResultadosActivity.class);
               startActivity(intent);

            }
        });
        vista.btnBuscar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    buscarValoresNormales();

            }
        });

        vista.btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    actualizaValoresNormales();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al actualizar valores normales: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        vista.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    eliminaValoresNormales();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al actualizar valores normales: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void clean(){
        vista.etId.setText("");
        vista.etNombre.setText("");
        vista.etUnidades.setText("");
        vista.etValorMin.setText("");
        vista.etValorMax.setText("");
        vista.etRangoNormal.setText("");
    }
    private void eliminaValoresNormales() {
        ValoresNormalesAPI api = ConexionRetrofit.getInstanciaRetrofit().create(ValoresNormalesAPI.class);
        Call<ValoresNormales> resultado = api.deleteValoresNormalesById(
                vista.etId.getText().toString()
        );

        resultado.enqueue(new Callback<ValoresNormales>() {
            @Override
            public void onResponse(Call<ValoresNormales> call, Response<ValoresNormales> response) {
                if (response.isSuccessful()) {
                    ValoresNormales valoresNormales = response.body();
                    Toast.makeText(getApplicationContext(),
                            "Registro "+valoresNormales.Nombre+" eliminado.",
                            Toast.LENGTH_LONG).show();

                    clean();
                } else {
                    // El servidor respondió, pero la solicitud no fue exitosa
                    if (response.code() == 404) {
                        Toast.makeText(getApplicationContext(), "No se encontró la fila solicitada", Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("API Error", "Fallo en la llamada API: " + response.message());
                        Toast.makeText(getApplicationContext(), "Error en la respuesta: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ValoresNormales> call, Throwable t) {
                // Manejar el fallo
                Toast.makeText(getApplicationContext(),"Falló.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizaValoresNormales() {
        ValoresNormales valoresNormales = new ValoresNormales(
                vista.etNombre.getText().toString(),
                Double.parseDouble(vista.etValorMin.getText().toString()),
                Double.parseDouble(vista.etValorMax.getText().toString()),
                vista.etUnidades.getText().toString(),
                vista.etRangoNormal.getText().toString(),
                (vista.rbTrue.isChecked()) ? true : false
        );

        ValoresNormalesAPI api = ConexionRetrofit.getInstanciaRetrofit().create(ValoresNormalesAPI.class);
        Call<ValoresNormales> result = api.updateValoresNormalesById(
                vista.etId.getText().toString(),
                valoresNormales
        );

        result.enqueue(new Callback<ValoresNormales>() {
            @Override
            public void onResponse(Call<ValoresNormales> call, Response<ValoresNormales> response) {
                if (response.isSuccessful()) {
                    ValoresNormales valorResponse = response.body();
                    Toast.makeText(
                            MainActivity.this,
                            "Valor de " + valoresNormales.Nombre + " agregado correctamente.",
                            Toast.LENGTH_LONG
                    ).show();
                    clean();
                } else {
                    // El servidor respondió, pero la solicitud no fue exitosa
                    if (response.code() == 404) {
                        Toast.makeText(getApplicationContext(), "No se encontró la fila solicitada", Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("API Error", "Fallo en la llamada API: " + response.message());
                        Toast.makeText(getApplicationContext(), "Error en la respuesta: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }
            }



            @Override
            public void onFailure(Call<ValoresNormales> call, Throwable t) {
                // Manejar el fallo
            }
        });
    }

    private void buscarValoresNormales() {
        ValoresNormalesAPI api = ConexionRetrofit.getInstanciaRetrofit().create(ValoresNormalesAPI.class);
        Call<ValoresNormales> resultado = api.getValoresNormalesById(
                vista.etId.getText().toString()
        );
        resultado.enqueue(new Callback<ValoresNormales>() {
            @Override
            public void onResponse(Call<ValoresNormales> call, Response<ValoresNormales> response) {

                ValoresNormales valorResponse = response.body();
                if (valorResponse == null) {
                    Toast.makeText(getApplicationContext(), "No se encontró la fila solicitada", Toast.LENGTH_LONG).show();

                } else {
                    vista.etNombre.setText(valorResponse.Nombre);
                    vista.etValorMin.setText(String.valueOf(valorResponse.valor_min));
                    vista.etValorMax.setText(String.valueOf(valorResponse.valor_max));
                    vista.etUnidades.setText(valorResponse.Unidades);
                    vista.etRangoNormal.setText(valorResponse.RangoNormal);
                    // El servidor respondió, pero la solicitud no fue exitosa
                        }
            }

            @Override
            public void onFailure(Call<ValoresNormales> call, Throwable t) {
                Log.e("API Error", "Fallo en la llamada API: " + t.getMessage());
                Toast.makeText(getApplicationContext(),"Error en la respuesta: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void insertarValoresNormales() {
        ValoresNormales valoresNormales = new ValoresNormales(
                vista.etNombre.getText().toString(),
                Double.parseDouble(vista.etValorMin.getText().toString()),
                Double.parseDouble(vista.etValorMax.getText().toString()),
                vista.etUnidades.getText().toString(),
                vista.etRangoNormal.getText().toString(),
                (vista.rbTrue.isChecked()) ? true : false
        );

        ValoresNormalesAPI api = ConexionRetrofit.getInstanciaRetrofit().create(ValoresNormalesAPI.class);
        Call<ValoresNormales> call = api.postValoresNormales(valoresNormales);

        call.enqueue(new Callback<ValoresNormales>() {
            @Override
            public void onResponse(Call<ValoresNormales> call, Response<ValoresNormales> response) {
                ValoresNormales valoresNormalesResponse = response.body();
                Toast.makeText(MainActivity.this,
                        "Valores Normales insertados:" + valoresNormalesResponse.id,
                        Toast.LENGTH_LONG).show();

                clean();
            }

            @Override
            public void onFailure(Call<ValoresNormales> call, Throwable t) {
                // Manejar el fallo
            }
        });
    }

}