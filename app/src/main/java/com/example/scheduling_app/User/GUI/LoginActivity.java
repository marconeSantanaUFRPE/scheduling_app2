package com.example.scheduling_app.User.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scheduling_app.Atividades.Domain.Atividades;
import com.example.scheduling_app.Infra.INodeJs;
import com.example.scheduling_app.Infra.RetrofitClient;
import com.example.scheduling_app.Infra.Sessao;
import com.example.scheduling_app.MainActivity;
import com.example.scheduling_app.R;
import com.example.scheduling_app.User.Domain.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    INodeJs myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ProgressBar mProgressBar;
    private User usuario;
    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private TextView textViewCriarConta;
    private ArrayList<Atividades> listaDeAtividades = new ArrayList<>();

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        buttonLogin = findViewById(R.id.button_login);
        editTextEmail = findViewById(R.id.editText2_email_login);
        editTextSenha = findViewById(R.id.editText3_senha_login);
        textViewCriarConta = findViewById(R.id.textView_criarConta);
        //init api
        exibirProgress(false);
        Retrofit  retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create((INodeJs.class));

        //
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(!editTextEmail.getText().toString().equals("") && !editTextSenha.getText().toString().equals("")){
                exibirProgress(true);
                buttonLogin.setVisibility(View.INVISIBLE);
                textViewCriarConta.setVisibility(View.INVISIBLE);
                usuario = new User();
                fazerLogin(editTextEmail.getText().toString(),(editTextSenha.getText().toString()));


            }
            else{
                Toast.makeText(LoginActivity.this,
                    "Preencha os campos de e-mail e senha!",
                    Toast.LENGTH_SHORT).show(); }

            }

        });


        textViewCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this ,CriarContaActivity.class);
                startActivity(intent);

            }
        });


    }

    private void fazerLogin(String email, final String senha) {

        Call<JsonObject> call = myAPI.loginUsuario(email,senha);
        call.enqueue(new Callback<JsonObject>() {


            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Boolean erro = response.body().get("Erro").getAsBoolean();


                if ( erro.equals(false) ) {

                    User usuario = new User();
                    int id = Integer.parseInt(response.body().get("id").getAsString());
                    String email = response.body().get("email").getAsString();
                    String senha = response.body().get("senha").getAsString();
                    String dataCriacao = response.body().get("dataCriacao").getAsString();
                    usuario.setUserId(id);
                    usuario.setUserEmail(email);
                    usuario.setUserPassword(senha);
                    usuario.setDataCriacao(dataCriacao);
                    carregarUsuarioNaSessao(usuario);
                    carregarAtividadesNaSessao();
                }else {
                    Toast.makeText(LoginActivity.this, response.body().get("mensagem").getAsString(), Toast.LENGTH_SHORT).show();}
                    exibirProgress(false);
                    buttonLogin.setVisibility(View.VISIBLE);
                    textViewCriarConta.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Alguma coisa deu errado '-'",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void abrirtTelaPrincipal() {

        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
        startActivity(intent);
        finish();


    }

    private void carregarUsuarioNaSessao(User usuario) {

        Sessao.setUsuario(usuario);

    }


    private void carregarAtividadesNaSessao(){



        Call<JsonObject> call = myAPI.gettodasAtividade(Sessao.getUsuario().getUserId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                int quantidade = Integer.parseInt(response.body().get("quantidade").getAsString());

                JsonArray atv = (JsonArray) response.body().get("atividades");


                for (int x = 0; x < quantidade; x++) {
                    JsonObject attt = (JsonObject) atv.get(x);

                    int id = Integer.parseInt(attt.get("id").getAsString());
                    String nome = attt.get("nome").getAsString();
                    String descricao = attt.get("descricao").getAsString();
                    String data_inicio = attt.get("data_inicio").getAsString();
                    String data_termino = attt.get("data_termino").getAsString();
                    String status_atividade = attt.get("status_atividade").getAsString();

                    Atividades atividades = new Atividades(id, nome, descricao, data_inicio, data_termino, status_atividade);
                    listaDeAtividades.add(atividades);

                }

                Sessao.setListaDeAtividades(listaDeAtividades);
                exibirProgress(false);
                abrirtTelaPrincipal();


            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }




    private void exibirProgress(boolean exibir) {
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }


}



