package com.imera.censo.ui.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


import com.imera.censo.BosinessCore.DomainObject.ParametroDomainObject;
import com.imera.censo.BosinessCore.DomainObject.UsuarioDomainObject;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.tipo_mensajes_error;
import com.imera.censo.Contracts.Enums.tipo_snackbar;
import com.imera.censo.Contracts.Models.clsUsuario;
import com.imera.censo.Contracts.Services.IParametro;
import com.imera.censo.Contracts.Services.IUsuario;
import com.imera.censo.R;
import com.imera.censo.Rutinas;
import com.imera.censo.Services.ApiManager;
import com.imera.censo.Services.PostUsuario;
import com.imera.censo.StringMD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private EditText txtUsuario,txtContrasena;
    private Button btnCancel,btnAceptar;
    private ProgressBar pbEstado;
    private LinearLayout llBotones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        bindUI();

        prefs = getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE);
        setCredentialsIfExist();

    }
    private void bindUI(){

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        pbEstado = (ProgressBar) findViewById(R.id.pbEstado);
        llBotones = (LinearLayout) findViewById(R.id.llBotones);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidaUsuarioContraseña();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Constants.RESULTADO_CANCEL);
                finish();//finishing activity
            }
        });

    }
    private void setCredentialsIfExist() {
        String usuario =  Rutinas.getUsuarioPrefs(prefs);
        if (!TextUtils.isEmpty(usuario)){
            txtUsuario.setText(usuario);
        }

    }
    private void saveOnPreferences(String strUsuario) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.pref_usuario), strUsuario);
        editor.apply();
    }

    private void MuestraOculataProcesando(boolean boolMostrar){
        if(boolMostrar){
            pbEstado.setVisibility(View.VISIBLE);
            llBotones.setVisibility(View.GONE);
        }else{
            pbEstado.setVisibility(View.GONE);
            llBotones.setVisibility(View.VISIBLE);
        }

    }

    private void ValidaUsuarioContraseña(){

        String strUsuario = txtUsuario.getText().toString();
        String strPasNoEncriptado = txtContrasena.getText().toString();

        if(TextUtils.isEmpty(strUsuario)){
            txtUsuario.setError(getString(R.string.error_escriba_su_usuario));
            return;
        }

        if(TextUtils.isEmpty(strPasNoEncriptado)){
            txtContrasena.setError(getString(R.string.error_escriba_su_contrasena));
            return;
        }

        MuestraOculataProcesando(true);

        ApiManager apiManager = ApiManager.getInstance();

        String strPassword = StringMD.getStringMessageDigest(strUsuario + strPasNoEncriptado
                , StringMD.SHA1).toUpperCase();

        PostUsuario usuario = new PostUsuario(strUsuario,strPassword);
        apiManager.ValidaUsuarioContrasena(usuario, new Callback<clsUsuario>() {
            @Override
            public void onResponse(Call<clsUsuario> call, Response<clsUsuario> response) {
                clsUsuario usuario = response.body();

                if (response.isSuccessful() && usuario != null)
                {
                    if(!TextUtils.isEmpty(usuario.getId_usuario())){
                        //Registro o actualizo el usuario actual
                        IUsuario usrService = new UsuarioDomainObject();
                        usrService.UsuarioUpgrade(usuario);

                        //Actualizo el valor del key de acceso para los metodos del servicio
                        IParametro paraService = new ParametroDomainObject();
                        paraService.ParametroUpgrade(getString(R.string.parametro_key_wcf),usuario.getKey());

                        //Guardo el usuario de la ultima persona que se logueó
                        saveOnPreferences(usuario.getUsuario());

                        //Espesifico el capturista que tendran las ordenes de trabajo
                    /*    clsCapturista capturista = new clsCapturista(usuario.getId_usuario(),usuario.getNombre(),1);
                        ICapturista capService = new CapturistaDomainObject();
                        capService.CapturistaSetDefault(capturista);*/

                        setResult(Constants.RESULTADO_OK);
                        finish();//finishing activity

                    }else{
                        Rutinas.SnackBarCustom(LogInActivity.this,
                                btnCancel,"",
                                usuario.getNombre(),
                                tipo_snackbar.NEGATIVE_SNACKBAR);
                    }

                }else {
                    Rutinas.MuestraMensajeResponseNotSuccessful(response.code()
                            ,LogInActivity.this);
                }

                MuestraOculataProcesando(false);
            }

            @Override
            public void onFailure(Call<clsUsuario> call, Throwable t) {
                MuestraOculataProcesando(false);
                Rutinas.MuestraErrorOnRetrofitFailure(t
                        ,LogInActivity.this, tipo_mensajes_error.TOAST, btnCancel);
            }
        });
    }
}