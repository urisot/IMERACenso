package com.imera.censo.ui.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.imera.censo.App;
import com.imera.censo.BosinessCore.DomainObject.ParametroDomainObject;
import com.imera.censo.Contracts.Enums.tipo_snackbar;
import com.imera.censo.Contracts.Services.IParametro;
import com.imera.censo.R;
import com.imera.censo.Rutinas;

public class ModificaWcfDialog extends Dialog {
    private Activity activityMain;


    public ModificaWcfDialog(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activityMain = activity;
    }

    private EditText et_DireccionWcf;
    private ImageButton btn_guarda_wcf;
    private ImageView ivCerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion_wcf);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);

        et_DireccionWcf = (EditText) findViewById(R.id.et_DireccionWcf);
        btn_guarda_wcf = (ImageButton) findViewById(R.id.btn_guarda_wcf);
        ivCerrar = (ImageView)findViewById(R.id.ivCerrar);

        btn_guarda_wcf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardaWCF();
            }
        });

        ivCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_DireccionWcf.getText().toString().trim().length()>0){
                    dismiss();
                }
            }
        });

        LoadDireccionWCF();
    }
    private void GuardaWCF(){
        String strDirWCF = et_DireccionWcf.getText().toString();
        String lastCharacter = strDirWCF.substring(strDirWCF.length() - 1);

        if(!(strDirWCF.length()>0)){
            et_DireccionWcf.setError(App.getContext().getString(R.string.error_campo_no_puede_ir_vacio));
            return;
        }

        if(!lastCharacter.equals("/")){
            et_DireccionWcf.setError(App.getContext().getString(R.string.error_debe_terminar_en));
            return;
        }


            IParametro paraService = new ParametroDomainObject();
            paraService.ParametroUpgrade(App.getContext().getString(R.string.parametro_direccion_wcf),et_DireccionWcf.getText().toString());

            Rutinas.SnackBarCustom(activityMain,et_DireccionWcf,"",
                    App.getContext().getString(R.string.msj_actualizo_wcf_con_exito),
                    tipo_snackbar.POSITIVE_SNACKBAR);

            dismiss();
    }

    private void LoadDireccionWCF(){
        IParametro parService = new ParametroDomainObject();

        String strDireccion = parService.GetValorParametro(App.getContext().getString(R.string.parametro_direccion_wcf));
        et_DireccionWcf.setText(strDireccion);

    }
}
