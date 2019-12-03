package com.example.scheduling_app.Atividades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduling_app.Atividades.Domain.Atividades;
import com.example.scheduling_app.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.minhaViewHolder> {

    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;
    private LayoutInflater layoutInflater;
    private ArrayList<Atividades> atividades;

    @Override
    public int getItemCount() {
        return atividades.size();
    }

    public  void addListItem(Atividades l, int position){

        atividades.add(l);
        notifyItemInserted(position);

    }

    public void add(Atividades item, int position) {
        atividades.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Atividades item) {
        int position = atividades.indexOf(item);
        atividades.remove(position);
        notifyItemRemoved(position);
    }



    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){

        recyclerViewOnClickListenerHack = r;
    }


    public Atividades imprimirItem(int position){

        return atividades.get(position);

    }

    public MyAdapter(Context c , ArrayList<Atividades> atv){

        atividades = atv;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public minhaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = layoutInflater.inflate(R.layout.atividades_adapter, viewGroup, false);
        minhaViewHolder minhaViewHolder = new minhaViewHolder(v);
        return minhaViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull minhaViewHolder minhaViewHolder, final int i) {

        minhaViewHolder.atividadeNome.setText(atividades.get(i).getNome());
        minhaViewHolder.atividadeDescricao.setText(atividades.get(i).getDescricao());

    }

    public class minhaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView atividadeNome;
        public TextView atividadeDescricao;


        public minhaViewHolder(View itemView) {
            super(itemView);

            atividadeNome = (TextView) itemView.findViewById(R.id.editTextNomeAtividade);
            atividadeDescricao = (TextView) itemView.findViewById(R.id.editTextDescricaoAtividade);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            if (recyclerViewOnClickListenerHack != null) {

                recyclerViewOnClickListenerHack.onClickListener(view, getPosition());


            }

        }

    }

}
