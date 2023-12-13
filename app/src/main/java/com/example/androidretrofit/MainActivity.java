package com.example.androidretrofit;

import androidx.appcompat.app.AppCompatActivity;

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
        //llenarFilas();
        botones();

    }

    /*
    private void llenarFilas(){
        List<TelefonoCelular> lItems = new ArrayList<TelefonoCelular>();
        lItems.add(new TelefonoCelular(R.drawable.celphone1,"TechNova", "QuantumX1", 699));
        lItems.add(new TelefonoCelular(R.drawable.celphone2,"Horizon Mobile", "Nova Plus", 549));
        lItems.add(new TelefonoCelular(R.drawable.celphone3,"SlickGear", "ThunderPro9", 799));
        lItems.add(new TelefonoCelular(R.drawable.celphone4,"SkyLink", "Infinity12Max", 899));
        lItems.add(new TelefonoCelular(R.drawable.celphone5,"NeoTech", "UltraLite5G", 599));

        vista.rvLista.setLayoutManager(new LinearLayoutManager(this));
        vista.rvLista.setAdapter(
                new AdapterFila(lItems, new AdapterFila.OnItemClickListener() {
                    @Override
                    public void onItemClick(TelefonoCelular telefonoCelular) {

                        Toast.makeText(
                                getApplicationContext(),
                                "Celular seleccionado: "+ telefonoCelular.getMarca()+"\n Precio a pagar: "+telefonoCelular.getPrecio(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                )
        );
    }
    */
    private void botones() {
        vista.btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarValoresNormales();
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
                actualizaValoresNormales();
            }
        });

        vista.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminaValoresNormales();
            }
        });

    }

    private void eliminaValoresNormales() {
        ValoresNormalesAPI api = ConexionRetrofit.getInstanciaRetrofit().create(ValoresNormalesAPI.class);
        Call<ValoresNormales> resultado = api.deleteValoresNormalesById(
                vista.etId.getText().toString()
        );

        resultado.enqueue(new Callback<ValoresNormales>() {
            @Override
            public void onResponse(Call<ValoresNormales> call, Response<ValoresNormales> response) {
                ValoresNormales valoresNormales = response.body();
                Toast.makeText(getApplicationContext(),
                        "Registro "+valoresNormales.Nombre+" eliminado.",
                        Toast.LENGTH_LONG).show();
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
                ValoresNormales valoresNormales = response.body();
                Toast.makeText(
                        MainActivity.this,
                        "Valor de "+valoresNormales.Nombre+" agregado correctamente.",
                        Toast.LENGTH_LONG
                ).show();
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
                vista.etNombre.setText(valorResponse.Nombre);
                vista.etValorMin.setText(String.valueOf(valorResponse.valor_min));
                vista.etValorMax.setText(String.valueOf(valorResponse.valor_max));
                vista.etUnidades.setText(valorResponse.Unidades);
                vista.etRangoNormal.setText(valorResponse.RangoNormal);
                if (valorResponse.estado == true) {
                    vista.rbTrue.setChecked(true);
                } else {
                    vista.rbTrue.setChecked(false);
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
            }

            @Override
            public void onFailure(Call<ValoresNormales> call, Throwable t) {
                // Manejar el fallo
            }
        });
    }

/*
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

*/

}