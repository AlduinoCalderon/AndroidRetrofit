package com.example.androidretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androidretrofit.dataaccess.ConexionRetrofit;
import com.example.androidretrofit.dataaccess.MateriaAPI;
import com.example.androidretrofit.databinding.ActivityMainBinding;
import com.example.androidretrofit.model.Materia;

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

    private void botones(){
        vista.btnInsertar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        insertarMateria();
                    }
                }
        );
        vista.btnBuscar1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buscarMateria();
                    }
                }
        );
        vista.btnActualizar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        actualizaMateria();
                    }
                }
        );
    }

    private void eliminaMateria(){
        MateriaAPI resultados = ConexionRetrofit.getInstanciaRetrofit()
                .create(MateriaAPI.class);
        Call<Materia> resultado = resultados.deleteMateria(
                vista.etId.getText().toString()
        );

        resultado.enqueue(
                new Callback<Materia>() {
                    @Override
                    public void onResponse(Call<Materia> call, Response<Materia> response) {
                        ////////
                        //NOTA: se debe pedir confirmar antes de eliminar, para pruebas se elimina directo
                        ////////////
                        Materia materia = response.body();
                        Toast.makeText(getApplicationContext(),
                                "Materia eliminada",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Materia> call, Throwable t) {

                    }
                }
        );
    }


    private void actualizaMateria(){
        Materia materia = new Materia(
                vista.etNombre.getText().toString(),
                (vista.rbTrue.isChecked())?true:false,
                Integer.parseInt(vista.etSemestre.getText().toString())
        );
        MateriaAPI conecta = ConexionRetrofit.getInstanciaRetrofit()
                .create(MateriaAPI.class);

        Call<Materia> resultado = conecta.putMateria(
                vista.etId.getText().toString(),
                materia
        );
        resultado.enqueue(
                new Callback<Materia>() {
                    @Override
                    public void onResponse(Call<Materia> call, Response<Materia> response) {
                        Materia materia = response.body();
                        Toast.makeText(
                                MainActivity.this,
                                "Materia actualizada",
                                Toast.LENGTH_LONG
                        ).show();
                    }

                    @Override
                    public void onFailure(Call<Materia> call, Throwable t) {

                    }
                }
        );


    }

    private void buscarMateria(){
        MateriaAPI conecta = ConexionRetrofit.getInstanciaRetrofit()
                .create(MateriaAPI.class);

        Call<Materia> resultado = conecta.getMateria(
                vista.etId.getText().toString()
        );
        resultado.enqueue(
                new Callback<Materia>() {
                    @Override
                    public void onResponse(Call<Materia> call, Response<Materia> response) {
                        Materia materiaResultado = response.body();
                        vista.etNombre.setText(materiaResultado.nombreMateria);
                        vista.etSemestre.setText(
                            Integer.toString(materiaResultado.semestreMateria)
                        );
                        if (materiaResultado.estadoMateria){
                            vista.rbTrue.setChecked(true);
                            vista.rbFalse.setChecked(false);
                        }
                        else {
                            vista.rbTrue.setChecked(false);
                            vista.rbFalse.setChecked(true);
                        }

                    }

                    @Override
                    public void onFailure(Call<Materia> call, Throwable t) {

                    }
                }
        );
    }

    private void insertarMateria(){
        Materia materia = new Materia(
                vista.etNombre.getText().toString(),
                (vista.rbTrue.isChecked())?true:false,
                Integer.parseInt(vista.etSemestre.getText().toString())
        );
        MateriaAPI conecta = ConexionRetrofit.getInstanciaRetrofit()
                .create(MateriaAPI.class);

        Call<Materia> resultado = conecta.postMateria(materia);
        //se recuperan los datos
        resultado.enqueue(new Callback<Materia>() {
                              @Override
                              public void onResponse(Call<Materia> call, Response<Materia> response) {
                                  Materia materiaResponse = response.body();
                                  Toast.makeText(MainActivity.this,
                                          "Materia insertada:" +materiaResponse.id,
                                          Toast.LENGTH_LONG).show();
                              }

                              @Override
                              public void onFailure(Call<Materia> call, Throwable t) {
                                  Log.e("Error", "insertar");
                              }
                          }
        );

    }
}