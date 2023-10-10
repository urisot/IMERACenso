package com.imera.censo.ui.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.tipo_snackbar;
import com.imera.censo.Contracts.Models.Catalogo;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Services.DataUpdate;
import com.imera.censo.Contracts.Services.InterfaceTiposWizard;
import com.imera.censo.R;
import com.imera.censo.Rutinas;
import com.imera.censo.ui.Activities.PickerActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CapturaClandestino3Fragment extends Fragment implements DataUpdate {

    InterfaceTiposWizard Listener;

    TextView Picker_Giro;
    ImageView Imagen_Predio;
    ImageView Imagen_Toma;

    TextInputLayout tilNombre_Comercial;
    EditText Nombre_Comercial;
    EditText Observaciones;

    Button Guardar;

    //Respaldo Data
    Bitmap Img_Predio = null;
    Bitmap Img_Toma = null;

    File FImagen_Predio = null;
    File FImagen_Toma = null;

    String Id_Giro= "";

    OPR_USUARIOS Data;
    private File mImageFile;
    private String mabsolutePhotoPath="";

    public CapturaClandestino3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        Data = bundle.getParcelable("Data");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_captura_clandestino3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            Listener = (InterfaceTiposWizard) getActivity();
        } catch (ClassCastException e) {

        }

        Picker_Giro =(TextView)getView().findViewById(R.id.et_Giro);

        Imagen_Predio = (ImageView)getView().findViewById(R.id.img_fotopredio);
        Imagen_Toma = (ImageView)getView().findViewById(R.id.img_fototoma);

        tilNombre_Comercial  = (TextInputLayout)getView().findViewById(R.id.tilNombreComercial);
        Nombre_Comercial = (EditText)getView().findViewById(R.id.etNombreComercial);
        Observaciones = (EditText)getView().findViewById(R.id.et_Observaciones);

        Guardar = (Button)getView().findViewById(R.id.btn_Guardar);

        Cargar();
        picker_vacios();

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //booleano para control de errores
                boolean HayErrores = false;
                String Error = "";//Mensaje de Error

                Data.setId_giro(Id_Giro);

                Data.setNombre_comercial(Nombre_Comercial.getText().toString());
                Data.setObserva_a(Observaciones.getText().toString());

                Data.setClaveloc(Data.getClaveloc().replaceAll("(\r\n|\n)", " "));
                Data.setNombre_comercial(Data.getNombre_comercial().replaceAll("(\r\n|\n)", " "));
                Data.setObserva_a(Data.getObserva_a().replaceAll("(\r\n|\n)", " "));

                /****************************************************** Validaciones ****************************************************************/
                //Pickers no nulos
                if(Data.getId_colonia().equals("")){
                    HayErrores = true;
                    Error = "La Colonia no ha sido Especificada.";
                }

                if (Data.getId_calle_ppal().equals("")){
                    HayErrores = true;
                    Error = "La Calle Principal no ha sido Especificada.";
                }

                if (Data.getId_calle_lat1().equals("")){
                    HayErrores = true;
                    Error = "La Calle Lateral 1 no ha sido Especificada.";
                }

                /*if (Data.getId_calle_lat2().equals("")){
                    HayErrores = true;
                    Error = "La Calle Lateral 2 no ha sido Especificada.";
                }*/

                if(Data.getId_servicio().equals("")){
                    HayErrores = true;
                    Error = "El tipo de Servicio no ha sido Especificado.";
                }

                if(Data.getId_tipousuario().equals("")){
                    HayErrores = true;
                    Error = "El tipo de Usuario no ha sido Especificado.";
                }

                if(Data.getId_diametro().equals("")){
                    HayErrores = true;
                    Error = "EL diametro de la toma no ha sido Especificado.";
                }

                if(Data.getId_materialtoma().equals("")){
                    HayErrores = true;
                    Error = "EL Material de la toma no ha sido Especificado.";
                }

                if(Data.getId_tipotoma().equals("")){
                    HayErrores = true;
                    Error = "EL tipo de toma no ha sido Especificado.";
                }

     /*           if (Data.getId_tipoglobo().equals("")){
                    HayErrores = true;
                    Error = "EL tipo de globo no ha sido Especificado.";
                }*/

                if(Data.getId_mat_banqueta().equals("")){
                    HayErrores = true;
                    Error = "EL material de la banqueta no ha sido Especificado.";
                }

                if (Data.getId_mat_calle().equals("")){
                    HayErrores = true;
                    Error = "EL material de la calle no ha sido Especificado.";
                }

                if(Data.getId_anomalia().equals("")){
                    HayErrores = true;
                    Error = "El campo 'Anomalia' debe ser especificado.";
                }

                if(Data.getId_giro().equals("")){
                    HayErrores = true;
                    Error = "EL giro no ha sido Especificado.";
                }


                //Data Valida
                if(Data.getNombre().equals("")){
                    HayErrores = true;
                    Error = "El campo 'Nombre' debe ser especificado.";
                }

                if(Data.getClaveloc().equals("")){
                    HayErrores = true;
                    Error = "El campo 'Clave Localizacion' debe ser especificado.";
                }

                /*if(Data.getNum_ext().equals("")){
                    HayErrores = true;
                    Error = "El numero Exterior no ha sido Especificado.";
                }else if(Data.getNum_ext().equals("0")){
                    HayErrores = true;
                    Error = "El Numero Exterior no puede ser '0'.";
                }*/

                if(Data.getNum_int().equals("")){
                    Data.setNum_int("0");
                }

                if(Nombre_Comercial.getVisibility() == View.VISIBLE && Data.getNombre_comercial().equals("")){
                    HayErrores = true;
                    Error = "El campo 'Nombre Comercial' debe ser especificado.";
                }

                if(Img_Predio == null){
                    if(Data.getFoto_predio()!=null){
                        if(Data.getFoto_predio().equals("")){
                            HayErrores = true;
                            Error = "No hay una foto asociada con el predio.";
                        }
                    }else{
                        HayErrores = true;
                        Error = "No hay una foto asociada con el predio.";
                    }
                }

                if (Img_Toma == null) {
                    if(Data.getFoto_toma()!=null){
                        if(Data.getFoto_toma().equals("")){
                            HayErrores = true;
                            Error = "No hay una foto asociada con la toma.";
                        }
                    }else{
                        HayErrores = true;
                        Error = "No hay una foto asociada con la toma.";
                    }
                }

                /******************************************************* Fin Validaciones *******************************************************************/
                if(!HayErrores){
                    /*if(Img_Predio!=null) {
                        if (Environment.MEDIA_MOUNTED.equals(state)) {
                            Data.setFoto_predio(guardarImagenMemoriaExterna("Imagenes", "FotoPredio_"+Data.getId_carga()+"_"+Data.getId_cuenta()+"_"+getCode(), Img_Predio));
                        }else
                            Data.setFoto_predio(guardarImagenMemoriaInterna(getActivity(),"Imagenes","FotoPredio_"+Data.getId_carga()+"_"+Data.getId_cuenta()+"_"+getCode(),Img_Predio));
                    }

                    if (Img_Toma != null) {
                        if (Environment.MEDIA_MOUNTED.equals(state)) {
                            Data.setFoto_toma(guardarImagenMemoriaExterna("Imagenes", "FotoToma_" + Data.getId_carga() + "_" + Data.getId_cuenta() + "_" + getCode(), Img_Toma));
                        }else
                            Data.setFoto_toma(guardarImagenMemoriaInterna(getActivity(), "Imagenes", "FotoToma_" + Data.getId_carga() + "_" + Data.getId_cuenta() + "_" + getCode(), Img_Toma));

                    }*/

                    Listener.Guardar(Data);
                    Listener.onClick("Guardar");

                }else{
                    Listener.callSnackBar("¡ERROR!: " + Error);
                }
            }
        });

        Imagen_Predio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFotografia(Constants.SOLICITUD_FOTO_PREDIO);
            }
        });

        Imagen_Toma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFotografia(Constants.SOLICITUD_FOTO_TOMA);
            }
        });

        Picker_Giro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> Al_DP_Data = new ArrayList<String>();
                Intent intento = new Intent(getActivity(), PickerActivity.class);
                Bundle manejadordp = new Bundle();

                if (!Data.getId_tipousuario().equals("")) {
                    boolean Domestico = (!Data.getId_tipousuario().equals("5") && !Data.getId_tipousuario().equals("6") && !Data.getId_tipousuario().equals("7"));
                    //if (GetText("Cat_TiposUsuario", Data.getId_tipousuario(),"id_tipousuario").equals("DOMESTICO")) {
                    if (Domestico) {
                        /*Al_DP_Data.add("CASA HABITACION");
                        Al_DP_Data.add("DEPARTAMENTO VIVIENDAS (RENTA)");*/
                        Al_DP_Data.add(Catalogo.GetText("Cat_Giros", "2", "id_giro"));
                        Al_DP_Data.add(Catalogo.GetText("Cat_Giros", "3", "id_giro"));

                    } else {
                        //Abrimos la base de datos 'DBCenso' en modo Lectura
                        BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                        SQLiteDatabase db = usdbh.getReadableDatabase();


                        //Si hemos abierto correctamente la base de datos
                        if (db != null) {
                            //Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Giros WHERE descripcion NOT LIKE 'CASA HABITACION'", null);
                            Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Giros WHERE id_giro != 2;", null);

                            //Nos aseguramos de que existe al menos un registro
                            if (c.moveToFirst()) {
                                //Recorremos el cursor hasta que no haya mas registros
                                do {
                                    Al_DP_Data.add(c.getString(0));
                                } while (c.moveToNext());
                            }

                            //Cerramos la base de datos
                            db.close();
                        }
                    }
                } else {
                    //Abrimos la base de datos 'DBCenso' en modo Lectura
                    BDManager usdbh = new BDManager(getActivity(), "DBCenso", null, 1);
                    SQLiteDatabase db = usdbh.getReadableDatabase();


                    //Si hemos abierto correctamente la base de datos
                    if (db != null) {
                        Cursor c = db.rawQuery("SELECT descripcion FROM Cat_Giros", null);

                        //Nos aseguramos de que existe al menos un registro
                        if (c.moveToFirst()) {
                            //Recorremos el cursor hasta que no haya mas registros
                            do {
                                Al_DP_Data.add(c.getString(0));
                            } while (c.moveToNext());
                        }

                        //Cerramos la base de datos
                        db.close();
                    }
                }

                manejadordp.putStringArrayList("data", Al_DP_Data);
                manejadordp.putString("Titulo", "Seleccione un Giro");
                intento.putExtras(manejadordp);
                startActivityForResult(intento, 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestc, int resultc, @Nullable Intent data) {
        super.onActivityResult(requestc, resultc, data);

        if(requestc==1){
            if(resultc ==1 ){
                String Giro = data.getStringExtra("Opcion");
                Picker_Giro.setText(Giro);
                Id_Giro = Catalogo.GetId("Cat_Giros",Giro,"id_giro");
            }
        }
        if (requestc == Constants.SOLICITUD_FOTO_TOMA && resultc == -1) {
            Data.setFoto_toma(mabsolutePhotoPath);

            /*OTService.ActualizaCampoDirecto(
                    OprOrdenTrabajoEnum.foto1_antes.toString()
                    ,Registro.getFoto1_antes()
                    ,Registro.getId());*/

            MuestraImagen(Imagen_Toma
                    ,Data.getFoto_toma());

            mabsolutePhotoPath = "";
        }

        if (requestc == Constants.SOLICITUD_FOTO_PREDIO && resultc == -1) {
            Data.setFoto_predio(mabsolutePhotoPath);

            /*OTService.ActualizaCampoDirecto(
                    OprOrdenTrabajoEnum.foto1_antes.toString()
                    ,Registro.getFoto1_antes()
                    ,Registro.getId());*/

            MuestraImagen(Imagen_Predio
                    ,Data.getFoto_predio());

            mabsolutePhotoPath = "";
        }
        if (requestc == 2 && resultc == -1) {

            Data.setFoto_predio(FImagen_Predio.getAbsolutePath());
            Bitmap Foto = BitmapFactory.decodeFile(Data.getFoto_predio());
            Imagen_Predio.setImageBitmap(Foto);
            Imagen_Predio.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        if (requestc == 3 && resultc == -1) {

            Data.setFoto_toma(FImagen_Toma.getAbsolutePath());
            Bitmap Foto = BitmapFactory.decodeFile(Data.getFoto_toma());
            Imagen_Toma.setImageBitmap(Foto);
            Imagen_Toma.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        picker_vacios();
    }
    private void MuestraImagen(ImageView imageView, String strAbsolutePhotoPath){
        if(imageView != null && !TextUtils.isEmpty(strAbsolutePhotoPath)){
            Picasso.with(getContext())
                    .load(new File(strAbsolutePhotoPath))
                    .resize(550, 450)
                    .centerCrop()
                    .into(imageView);
        }
    }
    private void TomarFotografia(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Asegúrese de que haya una actividad de cámara para manejar el intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            //Crea el archivo donde debe ir la foto
            try {
                mImageFile = Rutinas.createImageFile(getActivity());
            } catch (IOException ex) {
                //Se produjo un error al crear el archivo
                Rutinas.SnackBarCustom(getActivity(),Picker_Giro,"",ex.getMessage(), tipo_snackbar.NEGATIVE_SNACKBAR);
            }
            // Continuar solo si el archivo se creó correctamente
            if (mImageFile != null) {

                mabsolutePhotoPath = mImageFile.getAbsolutePath();

                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        getString(R.string.numbre_paquete),
                        mImageFile);
                //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImageFile));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }
    public void Cargar(){

        if(Data.getId_giro()!=null)
            if(!Data.getId_giro().equals("")  && !Data.getId_tipousuario().equals("")){

                /*boolean Domestico = GetText("Cat_TiposUsuario",Data.getId_tipousuario(),"id_tipousuario").equals("DOMESTICO");
                boolean CasaH = GetText("Cat_Giros",Data.getId_giro(),"id_giro").equals("CASA HABITACION");
                boolean Renta = GetText("Cat_Giros",Data.getId_giro(),"id_giro").equals("DEPARTAMENTO VIVIENDAS (RENTA)");*/

               /* boolean Domestico = (!Data.getId_tipousuario().equals("5") && !Data.getId_tipousuario().equals("6") && !Data.getId_tipousuario().equals("7") );
                boolean CasaH = Data.getId_giro().equals("2");
                boolean Renta = Data.getId_giro().equals("3");

                if((Domestico && (CasaH || Renta)) ||  (!Domestico && !CasaH)){*/
                    Picker_Giro.setText(Catalogo.GetText("Cat_Giros",Data.getId_giro(),"id_giro"));
                    Id_Giro = Data.getId_giro();
                    Nombre_Comercial.setText(Data.getNombre_comercial());
//                }
            }

        Nombre_Comercial.setText(Data.getNombre_comercial());



        /*if(!Data.getId_tipousuario().equals("")){
            if(GetText("Cat_TiposUsuario",Data.getId_tipousuario(),"id_tipousuario").equals("DOMESTICO"))
                Nombre_Comercial.setVisibility(View.GONE);
            else
                Nombre_Comercial.setVisibility(View.VISIBLE);
        }*/

        if(Data.getId_tipousuario()!=null)
            if(!Data.getId_tipousuario().equals("")){
                if(Data.getId_tipousuario().equals("5") || Data.getId_tipousuario().equals("6") || Data.getId_tipousuario().equals("7"))
                    Nombre_Comercial.setVisibility(View.VISIBLE);
                else
                    Nombre_Comercial.setVisibility(View.GONE);
            }

        Observaciones.setText(Data.getObserva_a());

        if(Data.getFoto_predio()!=null)
            if(!Data.getFoto_predio().equals("")){
                Bitmap Foto = BitmapFactory.decodeFile(Data.getFoto_predio());
                Imagen_Predio.setImageBitmap(Foto);
                Imagen_Predio.setScaleType(ImageView.ScaleType.FIT_XY);
            }

        if(Data.getFoto_toma() !=null)
            if(!Data.getFoto_toma().equals("")){
                Bitmap Foto = BitmapFactory.decodeFile(Data.getFoto_toma());
                Imagen_Toma.setImageBitmap(Foto);
                Imagen_Toma.setScaleType(ImageView.ScaleType.FIT_XY);
            }


    }


    public void picker_vacios(){
        if(Id_Giro.equals(""))
            Picker_Giro.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
        else
            Picker_Giro.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));

    }

    private String getCode()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoCode = "_" + date;
        return photoCode;
    }


    @Override
    public void setData(OPR_USUARIOS data) {
        this.Data = data;
    }

    @Override
    public OPR_USUARIOS getData() {
        Data.setId_giro(Id_Giro);

        if(Nombre_Comercial.getVisibility() == View.VISIBLE)
            Data.setNombre_comercial(Nombre_Comercial.getText().toString());
        Data.setObserva_a(Observaciones.getText().toString());

        return this.Data;
    }
}