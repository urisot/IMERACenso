package com.imera.censo.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.imera.censo.R;

import java.util.ArrayList;

public class PickerActivity extends AppCompatActivity {
    ListView lvopciones;
    EditText Buscador;
    Button btnCancelar;
    ArrayList<String> Data = new ArrayList<String>();
    ArrayAdapter<String> Adaptador;
    TextView Titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        lvopciones = (ListView)findViewById(R.id.lvopciones);
        btnCancelar = (Button)findViewById(R.id.btn_Cancel);
        Buscador = (EditText)findViewById(R.id.et_Buscar);
        Titulo = (TextView)findViewById(R.id.cabecera);

        Bundle manejadord = getIntent().getExtras();
        Data = manejadord.getStringArrayList("data");
        Titulo.setText(manejadord.getString("Titulo","Seleccione una opcion"));

        Adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,Data);
        lvopciones.setAdapter(Adaptador);
        lvopciones.setTextFilterEnabled(true);

        lvopciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(Buscador.getWindowToken(), 0);
                Intent intent = new Intent();
                intent.putExtra("Opcion", Adaptador.getItem(position));
                setResult(1, intent);
                finish();//finishing activity
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(Buscador.getWindowToken(), 0);
                setResult(2);
                finish();//finishing activity
            }
        });

        Buscador.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                PickerActivity.this.Adaptador.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

}
