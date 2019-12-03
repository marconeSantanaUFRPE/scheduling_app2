package com.example.scheduling_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scheduling_app.Infra.INodeJs;
import com.example.scheduling_app.Infra.RetrofitClient;
import com.example.scheduling_app.Infra.Sessao;
import com.example.scheduling_app.User.GUI.LoginActivity;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfiguracaoActivity extends AppCompatActivity {

    Button  buttonApgarConta;
    TextView textViewEmail, textViewSenha, textViewDataCriacao;
    INodeJs myAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
    buttonApgarConta = findViewById(R.id.buttonApagarConta);


        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create((INodeJs.class));

        textViewEmail = findViewById(R.id.textViewEmailConfig);
        textViewSenha = findViewById(R.id.textViewSenhaConfig);
        textViewDataCriacao = findViewById(R.id.textViewDataCriacao);
        textViewEmail.setText(Sessao.getUsuario().getUserEmail());
        textViewSenha.setText(Sessao.getUsuario().getUserPassword());
        textViewDataCriacao.setText(Sessao.getUsuario().getDataCriacao());


    buttonApgarConta.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Call<JsonObject> call = myAPI.apagarUsuario(Sessao.getUsuario().getUserId());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    Toast.makeText(ConfiguracaoActivity.this,response.body().get("resposta").getAsString(),Toast.LENGTH_LONG).show();
                    Sessao.setUsuario(null);
                    Intent intent = new Intent(ConfiguracaoActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    Toast.makeText(ConfiguracaoActivity.this,"Erro ao Excluir Conta",Toast.LENGTH_LONG).show();


                }
            });

        }
    });


    }


    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(ConfiguracaoActivity.this,MainActivity.class);
        startActivity(intent);

    }

}



