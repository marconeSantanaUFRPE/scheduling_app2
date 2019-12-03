package com.example.scheduling_app.Atividades.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.scheduling_app.Atividades.Domain.Atividades;
import com.example.scheduling_app.Infra.INodeJs;
import com.example.scheduling_app.Infra.RetrofitClient;
import com.example.scheduling_app.Infra.Sessao;
import com.example.scheduling_app.MainActivity;
import com.example.scheduling_app.R;
import com.example.scheduling_app.User.GUI.CriarContaActivity;
import com.example.scheduling_app.User.GUI.LoginActivity;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class cadastroAtividadeActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;

    EditText editTextNomeAtividade, editTextDescricaoAtividade, editTextDataInicio, editTextDataTermino;
    Button buttonCadastrarAtividade;
    INodeJs myAPI;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atividade);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create((INodeJs.class));
        mProgressBar = findViewById(R.id.progressBarAtividades);

        buttonCadastrarAtividade = findViewById(R.id.buttonCadastrarAtividades);
        editTextNomeAtividade = findViewById(R.id.editTextNomeAtividade);
        editTextDescricaoAtividade = findViewById(R.id.editTextDescricaoAtividade);
        editTextDataInicio = findViewById(R.id.editTextDataInicioAtividade);
        editTextDataTermino = findViewById(R.id.editTextDataTerminoAtividade);

        pegarData(editTextDataInicio);


        pegarData(editTextDataTermino);



        exibirProgress(false);
        
        buttonCadastrarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editTextNomeAtividade.getText().toString().equals("") &&
                !editTextDescricaoAtividade.getText().toString().equals("") &&
                        !editTextDataInicio.getText().toString().equals("")&&
                        !editTextDataTermino.getText().toString().equals("")){


                buttonCadastrarAtividade.setVisibility(View.INVISIBLE);
                exibirProgress(true);
                mandarInformacaoAtividadeCadastro(editTextNomeAtividade.getText().toString()
                        , editTextDescricaoAtividade.getText().toString(), editTextDataInicio.getText().toString(), editTextDataTermino.getText().toString()
                );
            }
                else {Toast.makeText(cadastroAtividadeActivity.this,
                        "PRENCHA TODOS OS CAMPOS!",
                        Toast.LENGTH_SHORT).show();}

                
            }



        });
        
        
        

    }

    private void mandarInformacaoAtividadeCadastro(String nome, String descricao, String dataIncio, String dataTermino ) {
        int id = Sessao.getUsuario().getUserId();
        String status = "Recém Cadastrada";

        final Atividades atividade = new Atividades();
        atividade.setNome(nome);
        atividade.setDescricao(descricao);
        atividade.setDataInicio(dataIncio);
        atividade.setDataTermino(dataTermino);
        atividade.setStatus(status);
        atividade.setUsuarioId(Sessao.getUsuario().getUserId());

        Call<JsonObject> call = myAPI.cadastrarAtividade(nome,descricao,dataIncio,dataTermino,status,id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String resposta = response.body().get("resposta").getAsString();

                int idDaAtividadeNova = Integer.parseInt( response.body().get("idnova").getAsString());
                atividade.setIdAtividade(idDaAtividadeNova);

                Toast.makeText(cadastroAtividadeActivity.this, resposta, Toast.LENGTH_LONG).show();

                Sessao.getListaDeAtividades().add(atividade);
                Intent intent = new Intent(cadastroAtividadeActivity.this, AtividadesActivity.class);
                intent.putExtra("listaDeAtividades",Sessao.getListaDeAtividades());
                intent.putExtra("descricao","Todas as Atividades");
                buttonCadastrarAtividade.setVisibility(View.VISIBLE);

                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(cadastroAtividadeActivity.this, "Deu Errado", Toast.LENGTH_LONG).show();
                exibirProgress(false);
                buttonCadastrarAtividade.setVisibility(View.VISIBLE);

            }
        });


    }


    private void exibirProgress(boolean exibir) {
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }




    private  void pegarData(final EditText editText) {


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText);
            }

        };

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(cadastroAtividadeActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
        private void updateLabel(EditText editText) {

            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

            editText.setText(sdf.format(myCalendar.getTime()));
        }






}



