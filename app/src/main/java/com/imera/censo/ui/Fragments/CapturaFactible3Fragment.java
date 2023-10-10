package com.imera.censo.ui.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
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

import com.imera.censo.Constants;
import com.imera.censo.Contracts.Enums.tipo_snackbar;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.Contracts.Services.DataUpdate;
import com.imera.censo.Contracts.Services.InterfaceTiposWizard;
import com.imera.censo.R;
import com.imera.censo.Rutinas;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CapturaFactible3Fragment extends Fragment implements DataUpdate {
    InterfaceTiposWizard Listener;
    ImageView Imagen_Predio;
    EditText Observaciones;
    Button Guardar;

    //Respaldo Data
    Bitmap Img_Predio = null;
    File FImagen_Predio = null;
    OPR_USUARIOS Data;
    View view;
    private File mImageFile;
    private String mabsolutePhotoPath="";

    public CapturaFactible3Fragment() {
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
        view = inflater.inflate(R.layout.fragment_captura_factible3, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            Listener = (InterfaceTiposWizard) getActivity();
        } catch (ClassCastException e) {
        }

        Imagen_Predio = (ImageView)getView().findViewById(R.id.img_fotopredio);
        Observaciones = (EditText)getView().findViewById(R.id.et_Observaciones);

        Guardar = (Button)getView().findViewById(R.id.btn_Guardar);

        Cargar();

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //booleano para control de errores
                boolean HayErrores = false;
                String Error = "";//Mensaje de Error

                Data.setObserva_a(Observaciones.getText().toString());

                Data.setClaveloc(Data.getClaveloc().replaceAll("(\r\n|\n)", " "));
                Data.setObserva_a(Data.getObserva_a().replaceAll("(\r\n|\n)", " "));

                /****************************************************** Validaciones ****************************************************************/
                //Pickers no nulos
                if(Data.getId_colonia().equals("")) {
                    HayErrores = true;
                    Error = "La Colonia no ha sido Especificada.";
                }

                if(Data.getId_calle_ppal().equals("")){
                    HayErrores = true;
                    Error = "La Calle Principal no ha sido Especificada.";
                }

                if(Data.getId_calle_lat1().equals("")){
                    HayErrores = true;
                    Error = "La Calle Lateral 1 no ha sido Especificada.";
                }

                /*if(Data.getId_calle_lat2().equals("")){
                    HayErrores = true;
                    Error = "La Calle Lateral 2 no ha sido Especificada.";
                }*/

                if(Data.getId_mat_banqueta().equals("")){
                    HayErrores = true;
                    Error = "EL material de la banqueta no ha sido Especificado.";
                }

                if(Data.getId_mat_calle().equals("")){
                    HayErrores = true;
                    Error = "EL material de la calle no ha sido Especificado.";
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

                /*if(Data.getNum_ext().equals("")){ No quieren nexterior
                    HayErrores = true;
                    Error = "El numero Exterior no ha sido Especificado.";
                }else if(Data.getNum_ext().equals("0")){
                    HayErrores = true;
                    Error = "El Numero Exterior no puede ser '0'.";
                }*/

                if(Data.getNum_int().equals("")){
                    Data.setNum_int("0");
                }


                    if(Data.getFoto_predio()!=null){
                        if(Data.getFoto_predio().equals("")){
                            HayErrores = true;
                            Error = "No hay una foto asociada con el predio.";
                        }
                    }else{
                        HayErrores = true;
                        Error = "No hay una foto asociada con el predio.";
                    }


                /******************************************************* Fin Validaciones *******************************************************************/
                if(!HayErrores){
                    Listener.Guardar(Data);
                    Listener.onClick("Guardar");
                }
                else{
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

    }

    @Override
    public void onActivityResult(int requestc, int resultc, @Nullable Intent data) {
        super.onActivityResult(requestc, resultc, data);
        if (requestc == 2 && resultc == -1) {
            Data.setFoto_predio(FImagen_Predio.getAbsolutePath());
            Bitmap Foto = BitmapFactory.decodeFile(Data.getFoto_predio());
            Imagen_Predio.setImageBitmap(Foto);
            Imagen_Predio.setScaleType(ImageView.ScaleType.FIT_XY);

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
                Rutinas.SnackBarCustom(getActivity(),Guardar,"",ex.getMessage(), tipo_snackbar.NEGATIVE_SNACKBAR);
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
        Observaciones.setText(Data.getObserva_a());

        if(Data.getFoto_predio()!=null)
            if(!Data.getFoto_predio().equals("")){
                Bitmap Foto = BitmapFactory.decodeFile(Data.getFoto_predio());
                Imagen_Predio.setImageBitmap(Foto);
                Imagen_Predio.setScaleType(ImageView.ScaleType.FIT_XY);
            }
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
        Data.setObserva_a(Observaciones.getText().toString());

        return this.Data;
    }
}