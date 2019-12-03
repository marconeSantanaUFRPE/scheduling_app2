package com.example.scheduling_app.Atividades.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scheduling_app.Atividades.Domain.Atividades;
import com.example.scheduling_app.Infra.INodeJs;
import com.example.scheduling_app.Infra.RetrofitClient;
import com.example.scheduling_app.Infra.Sessao;
import com.example.scheduling_app.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AtividadeDetalhesActivity extends AppCompatActivity {

    private TextView editTextNome,editTextDescricao,editTextDataInicio,editTextDataTermino,editTextStatus;
    private ImageButton buttonApagar, imageButtonAtualizarStatus;
    private INodeJs myAPI;
    private Atividades atividades;
    private ArrayList<Atividades> novaLista = new ArrayList<>();
    private Spinner spinner;

    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_detalhes);


        editTextStatus = findViewById(R.id.textViewStatus);
        atividades = (Atividades) getIntent().getSerializableExtra("atividade");
        index = (int) getIntent().getSerializableExtra("index");
                Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create((INodeJs.class));

        spinner = findViewById(R.id.spinnerStatus);


        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.StatusArray , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        imageButtonAtualizarStatus = findViewById(R.id.imageButtonAtualizarStatus);

        imageButtonAtualizarStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Call<JsonObject> call = myAPI.updateAtividadeStatus(atividades.getIdAtividade(), spinner.getSelectedItem().toString());
                call.enqueue(new Callback<JsonObject>() {
                                 @Override
                                 public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                                     Sessao.getListaDeAtividades().get(index).setStatus(spinner.getSelectedItem().toString());

                                     editTextStatus.setText(spinner.getSelectedItem().toString());

                                     Toast.makeText(AtividadeDetalhesActivity.this, response.body().get("mensagem").getAsString()  ,Toast.LENGTH_LONG).show();



                                 }

                                 @Override
                                 public void onFailure(Call<JsonObject> call, Throwable t) {

                                 }
                             });




            }
        });


        buttonApagar = findViewById(R.id.imageButtonApagar);

        editTextNome = findViewById(R.id.textViewNomeDetalhes);
        editTextDescricao = findViewById(R.id.textViewDescricaoDetalhes);
        editTextDataInicio = findViewById(R.id.textViewDataInicioDetalhes);
        editTextDataTermino = findViewById(R.id.textViewDataTerminoDetalhes);

        editTextNome.setText(atividades.getNome());
        editTextDescricao.setText(atividades.getDescricao());
        editTextDataInicio.setText(atividades.getDataInicio());
        editTextDataTermino.setText(atividades.getDataTermino());
        editTextStatus.setText(atividades.getStatus());


        buttonApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apagarAtividade(atividades);


            }
        });

    }

    private void apagarAtividade(final Atividades ativi) {



        Call<JsonObject> call = myAPI.apagarAtividade(ativi.getIdAtividade());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String resposta = response.body().get("resposta").getAsString();
                Sessao.getListaDeAtividades().remove(index);
                Toast.makeText(AtividadeDetalhesActivity.this, resposta, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AtividadeDetalhesActivity.this, AtividadesActivity.class);
                intent.putExtra("listaDeAtividades",Sessao.getListaDeAtividades());
                intent.putExtra("descricao","Todas as Atividades");

                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }


    @Override
    public void onBackPressed(){


        Intent intent = new Intent(AtividadeDetalhesActivity.this, AtividadesActivity.class);
        intent.putExtra("listaDeAtividades",Sessao.getListaDeAtividades());
        intent.putExtra("descricao","Todas as Atividades");

        startActivity(intent);
        finish();



    }


}

