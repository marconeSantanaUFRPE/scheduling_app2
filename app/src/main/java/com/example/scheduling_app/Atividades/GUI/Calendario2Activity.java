package com.example.scheduling_app.Atividades.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.scheduling_app.Atividades.Domain.Atividades;
import com.example.scheduling_app.Infra.Sessao;
import com.example.scheduling_app.R;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Calendario2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario2);
        final ArrayList<Atividades> ativ = Sessao.getListaDeAtividades();
        Date data = new Date();
        final Calendar nextYear = Calendar.getInstance();
        final  Calendar antYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,40);
        antYear.add(Calendar.YEAR,-10);
        CalendarPickerView calendarPickerView = findViewById(R.id.calendarioBacana);
        calendarPickerView.init(antYear.getTime(),nextYear.getTime()).withSelectedDate(data);

        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                String datinha = DateFormat.getDateInstance(DateFormat.FULL).format(date);
                String soData = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
                ArrayList<Atividades> atividadesDoDia =new ArrayList<>();
                for(Atividades atividades: ativ){

                    if((atividades.getDataInicio().equals(soData)) || (atividades.getDataTermino().equals(soData))){
                        atividadesDoDia.add(atividades);


                    }





                }


                Toast.makeText(Calendario2Activity.this, datinha,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Calendario2Activity.this,AtividadesActivity.class);
                intent.putExtra("descricao",datinha);
                intent.putExtra("listaDeAtividades", atividadesDoDia);
                startActivity(intent);
                finish();

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });




    }




}
