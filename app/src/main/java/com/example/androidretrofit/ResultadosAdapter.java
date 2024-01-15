package com.example.androidretrofit;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.androidretrofit.model.VistaResultados;

import java.util.ArrayList;

public class ResultadosAdapter extends RecyclerView.Adapter<ResultadosAdapter.ViewHolder>{

    private ArrayList<VistaResultados> resultadosList;
    private Context context;

    public ResultadosAdapter(ArrayList<VistaResultados> resultadosList, Context context) {
        this.resultadosList = resultadosList;
        this.context = context;
    }

    // Constructor adicional sin parámetros
    public ResultadosAdapter() {
        this.resultadosList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ResultadosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_resultados_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultadosAdapter.ViewHolder holder, int position) {
        VistaResultados resultado = resultadosList.get(position);

        holder.nombrePacienteTextView.setText("Paciente: " + resultado.getNombrePaciente());
        holder.fechaRealizacionTextView.setText("Fecha: " + resultado.getFechaRealizacion());
        holder.tipoEstudioTextView.setText("Tipo de Estudio: " + resultado.getTipoEstudio());
        holder.resultadoAnalisisTextView.setText("Resultado: " + resultado.getResultadoAnalisis());
        holder.valorMinimoTextView.setText("Valor Mínimo: " + String.valueOf(resultado.getValorMinimo()));
        holder.valorMaximoTextView.setText("Valor Máximo: " + String.valueOf(resultado.getValorMaximo()));
        holder.rangoNormalTextView.setText("Rango Normal: " + resultado.getRangoNormal());
    }

    @Override
    public int getItemCount() {
        return resultadosList.size();
    }

    public void actualizarLista(ArrayList<VistaResultados> nuevaLista) {
        resultadosList.clear();
        resultadosList.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombrePacienteTextView;
        TextView fechaRealizacionTextView;
        TextView tipoEstudioTextView;
        TextView resultadoAnalisisTextView;
        TextView valorMinimoTextView;
        TextView valorMaximoTextView;
        TextView rangoNormalTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombrePacienteTextView = itemView.findViewById(R.id.viewNombrePaciente);
            fechaRealizacionTextView = itemView.findViewById(R.id.viewFechaRealizacion);
            tipoEstudioTextView = itemView.findViewById(R.id.viewTipoEstudio);
            resultadoAnalisisTextView = itemView.findViewById(R.id.viewResultadoAnalisis);
            valorMinimoTextView = itemView.findViewById(R.id.viewValorMinimo);
            valorMaximoTextView = itemView.findViewById(R.id.viewValorMaximo);
            rangoNormalTextView = itemView.findViewById(R.id.viewRangoNormal);
        }
    }
}
