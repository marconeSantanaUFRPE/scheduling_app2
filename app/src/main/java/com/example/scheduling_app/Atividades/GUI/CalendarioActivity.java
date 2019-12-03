package com.example.scheduling_app.Atividades.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scheduling_app.R;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.Calendar;

public class CalendarioActivity extends AppCompatActivity  {

    TextView textViewData;
    Calendar calendar;
    ImageGenerator imageGenerator;
    ImageView imageView;
    Bitmap bitmapGene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        imageGenerator = new ImageGenerator(this);
        textViewData = findViewById(R.id.textViewData);

        imageGenerator.setIconSize(50,50);
        imageGenerator.setDateSize(30);
        imageGenerator.setMonthSize(10);
        imageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        imageGenerator.setMonthColor(Color.WHITE);
        imageGenerator.setStorageToSDCard(true);

        textViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                 int ano = calendar.get(Calendar.YEAR);
                 int mes = calendar.get(Calendar.MONTH);
                 int dia = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarioActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int diaS, int mesS, int anoS) {
                        textViewData.setText(anoS+"/"+mesS+"/"+diaS);
                        calendar.set(diaS,mesS,anoS);
                        Toast.makeText(CalendarioActivity.this,"sadsada",Toast.LENGTH_LONG).show();

                    }
                }, ano, mes, dia);
                datePickerDialog.show();


            }
        });

    }


}
