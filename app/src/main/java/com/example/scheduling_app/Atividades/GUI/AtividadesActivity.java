package com.example.scheduling_app.Atividades.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scheduling_app.Atividades.Domain.Atividades;
import com.example.scheduling_app.Atividades.MyAdapter;
import com.example.scheduling_app.Atividades.RecyclerViewOnClickListenerHack;
import com.example.scheduling_app.Infra.INodeJs;
import com.example.scheduling_app.Infra.RetrofitClient;
import com.example.scheduling_app.Infra.Sessao;
import com.example.scheduling_app.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AtividadesActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {
    int index;
    private RecyclerView recyclerView;
    private ArrayList<Atividades> atividades =  new ArrayList<>();

    private ImageButton imageButtonAdicionarAtividades;
    private TextView textViewDescricao, Titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);

        atividades = (ArrayList<Atividades>) getIntent().getSerializableExtra("listaDeAtividades");
        String descricao = (String) getIntent().getSerializableExtra("descricao");



        textViewDescricao = findViewById(R.id.textViewDescricao);



        if(atividades.size()== 0){

            textViewDescricao.setText("VOCÊ NÂO TEM ATIVIDADES CADASTRADAS");
        }else {textViewDescricao.setText(descricao);}

        imageButtonAdicionarAtividades = findViewById(R.id.imageButtonAdicionarAtividades);

        imageButtonAdicionarAtividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AtividadesActivity.this,cadastroAtividadeActivity.class);
                startActivity(intent);
                finish();
            }
        });



        recyclerView = findViewById(R.id.RecycleView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AtividadesActivity.this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        MyAdapter myAdapter = new MyAdapter(AtividadesActivity.this,atividades);
        myAdapter.setRecyclerViewOnClickListenerHack(AtividadesActivity.this);
        myAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public void onClickListener(View view, int position) {

        MyAdapter myAdapter = (MyAdapter) recyclerView.getAdapter();
        final Atividades atvDetalhes;
        atvDetalhes =  myAdapter.imprimirItem(position);
        int idAtividade = atvDetalhes.getIdAtividade();
        for (Atividades atividades: Sessao.getListaDeAtividades()){

            if(atividades.getIdAtividade()==idAtividade){
                 index = Sessao.getListaDeAtividades().indexOf(atividades);

            }
        }


        Intent intent = new Intent(AtividadesActivity.this,AtividadeDetalhesActivity.class);
        intent.putExtra("atividade",atvDetalhes);
        intent.putExtra("index",index);
        startActivity(intent);
        finish();

    }


 }







