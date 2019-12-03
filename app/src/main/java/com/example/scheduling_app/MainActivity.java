package com.example.scheduling_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scheduling_app.Atividades.Domain.Atividades;
import com.example.scheduling_app.Atividades.GUI.AtividadesActivity;
import com.example.scheduling_app.Atividades.GUI.Calendario2Activity;
import com.example.scheduling_app.Atividades.GUI.CalendarioActivity;
import com.example.scheduling_app.Infra.Sessao;
import com.example.scheduling_app.User.Domain.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button buttonListaDeAtividades, buttonCalendarioAtividades;
    TextView textViewEmail;
    ImageButton imageButtonConfiguracao;
    //ArrayList<Atividades> atividades = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User usuarioIn = Sessao.getUsuario();
        //atividades = Sessao.getListaDeAtividades();

        String email =  usuarioIn.getUserEmail();
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewEmail.setText(email);
        imageButtonConfiguracao = findViewById(R.id.imageButtonConfiguracao);


        imageButtonConfiguracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,ConfiguracaoActivity.class);
                startActivity(intent);
                finish();

            }
        });

        buttonListaDeAtividades = findViewById(R.id.buttonTodasAsAtividades);

        buttonListaDeAtividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AtividadesActivity.class);
                String datinha = "Todas as Atividades";
                intent.putExtra("descricao",datinha);
                intent.putExtra("listaDeAtividades", Sessao.getListaDeAtividades());


                startActivity(intent);


            }
        });


        buttonCalendarioAtividades = findViewById(R.id.buttonCalendario);
        buttonCalendarioAtividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Calendario2Activity.class);
                startActivity(intent);

            }
        });



    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Por Favor Confirme");
        builder.setMessage("Quer Sair Do App?");
        builder.setCancelable(true);

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when user want to exit the app
                // Let allow the system to handle the event, such as exit the app
                Sessao.setUsuario(null);
                Sessao.setListaDeAtividades(null);

                MainActivity.super.onBackPressed();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when want to stay in the app
                Toast.makeText(MainActivity.this,"Okay",Toast.LENGTH_LONG).show();
            }
        });

        // Create the alert dialog using alert dialog builder
        AlertDialog dialog = builder.create();

        // Finally, display the dialog when user press back button
        dialog.show();
    }


}
