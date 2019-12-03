package com.example.scheduling_app.User.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.scheduling_app.Infra.INodeJs;
import com.example.scheduling_app.Infra.RetrofitClient;
import com.example.scheduling_app.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CriarContaActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextSenha;
    Button buttonCriarConta;

    private ProgressBar mProgressBar;

    INodeJs myAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        editTextEmail = findViewById(R.id.editTextEmailCadastro);
        editTextSenha = findViewById(R.id.editTextSenhaCadastro);

        buttonCriarConta = findViewById(R.id.buttonCriarConta);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create((INodeJs.class));

        mProgressBar = findViewById(R.id.progressBarCadastroConta);
        exibirProgress(false);

        buttonCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonCriarConta.setVisibility(View.INVISIBLE);
                exibirProgress(true);

                mandarInformacaoCadastro(editTextEmail.getText().toString(),editTextSenha.getText().toString());


            }
        });


    }

    private void mandarInformacaoCadastro(String email, String senha) {

        Call<JsonObject> call = myAPI.cadastroUsuario(email,senha);

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //
                String resposta = response.body().get("resposta").getAsString();

                if(resposta.equals("EMAIL JÁ CADASTRADO")){

                    Toast.makeText(CriarContaActivity.this, resposta, Toast.LENGTH_LONG).show();
                    buttonCriarConta.setVisibility(View.INVISIBLE);
                    exibirProgress(false);


                }else{

                Toast.makeText(CriarContaActivity.this, resposta, Toast.LENGTH_LONG).show();
                buttonCriarConta.setVisibility(View.INVISIBLE);
                exibirProgress(false);

                finish();}
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(CriarContaActivity.this, "Erro !", Toast.LENGTH_LONG).show();


                buttonCriarConta.setVisibility(View.VISIBLE);
                exibirProgress(false);
            }

        });


}


    private void exibirProgress(boolean exibir) {
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }


}