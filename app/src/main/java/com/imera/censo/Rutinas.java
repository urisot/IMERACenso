package com.imera.censo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.BosinessCore.DomainObject.ParametroDomainObject;
import com.imera.censo.Contracts.Enums.tipo_mensajes_error;
import com.imera.censo.Contracts.Enums.tipo_snackbar;
import com.imera.censo.Contracts.Services.IParametro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.zelory.compressor.Compressor;


public class Rutinas {


    public static File createImageFile(Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageFile = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageFile     /* directory */
        );

        //Guardar un archivo: ruta para usar con ACTION_VIEW intents
        //mabsolutePhotoPath = image.getAbsolutePath();
        return image;
    }

    public static String setmedidor_tarifa(String medidor,String tarifa){
        String medidor_tarifa="";
        if(medidor!=null)
        {
            if(!medidor.equals("") && !medidor.equals("null"))
                medidor_tarifa ="Medidor: "+medidor;
            else
                medidor_tarifa="";
        }else
            medidor_tarifa="";


        if(tarifa!=null)
        {
            if(!tarifa.equals("") && !tarifa.equals("null"))
                medidor_tarifa = medidor_tarifa+" ["+tarifa+"]";
            else
                medidor_tarifa="";
        }else
            medidor_tarifa="";

        return medidor_tarifa;

    }

    public static String ObtenDIR_WCF(){
        String strDirWCF="";
        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();
        if(db!=null){
            Cursor c = db.rawQuery("SELECT valor FROM Cfg_Parametros where parametro ='DIRECION_WCF';",null);
            if(c.moveToFirst()){
                strDirWCF=c.getString(0).trim();

            }
            if(!c.isClosed())c.close();
            db.close();
        }

        return strDirWCF;
    }

    public static boolean CampoVacioONulo(String strCampo){
        boolean boolReturn = true;
        if(!(strCampo == null)){
            if(!TextUtils.isEmpty(strCampo)){
                boolReturn = false;
            }
        }
        return boolReturn;
    }

    public static String getUsuarioPrefs(SharedPreferences preferences) {
        return preferences.getString("usuario", "");
    }

    public static ArrayList<String> RetornaListaParaCombo(String tabla){
        ArrayList<String> ListReturn = new ArrayList<>();

        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();
        Cursor cursor = null;

        if (db != null) {
            try {
                cursor = db.rawQuery("SELECT * FROM "+ tabla +";", null);
                if (cursor.moveToFirst()) {
                    // iteramos sobre el cursor de resultados,
                    while (cursor.isAfterLast() == false) {

                        String strID = cursor.getString(0);
                        String strDescripcion = cursor.getString(1);
                        //ListReturn.add("["+strID+"] - "+strDescripcion);
                        ListReturn.add(strDescripcion);
                        cursor.moveToNext();
                    }
                }
            } catch (Exception e){
                ListReturn.add(e.getMessage());
            }

            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6

        return ListReturn;
    }

/*
    public static ArrayList<clsSpinnerItem> RetornaListaParaSpinner(String tabla){
        ArrayList<clsSpinnerItem> ListReturn = new ArrayList<>();

        BDManager manejador = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = manejador.getReadableDatabase();
        Cursor cursor = null;

        if (db != null) {
            try {
                cursor = db.rawQuery("SELECT * FROM "+ tabla +";", null);
                if (cursor.moveToFirst()) {
                    // iteramos sobre el cursor de resultados,
                    while (cursor.isAfterLast() == false) {

                        String strID = cursor.getString(0);
                        String strDescripcion = cursor.getString(1);

                        ListReturn.add(new clsSpinnerItem(
                                Integer.valueOf(strID)
                                ,strDescripcion));
                        cursor.moveToNext();
                    }
                }
            } catch (Exception e){
                ListReturn.add(new clsSpinnerItem(0,e.getMessage()));
            }

            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6

        return ListReturn;
    }
*/

   public static Bitmap CompressImage(Context context, String path, int width, int height) {
        final File file_thumb_path = new File(path);
        Bitmap thumb_bitmap = null;

        try {
            thumb_bitmap = new Compressor(context)
                    .setMaxWidth(width)
                    .setMaxHeight(height)
                    .setQuality(75)
                    .compressToBitmap(file_thumb_path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,75,baos);
        //byte[] thumb_byte = baos.toByteArray();
        return  thumb_bitmap;
    }

    public static String FormatearFecha(String fecha, String format){
        String Fecha = fecha;
        String strDia="";
        String strMes="";
        String strAno="";
        if(format.equals("dtos")){
            Fecha = Fecha.replace("-", "");
        }else if(format.equals("dtos1")){

            Fecha = Fecha.replace("/", "");

        }
        return Fecha;
    }

    public static String GetFechaActual()    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String date = dateFormat.format(new Date());
        String photoCode =  date;
        return photoCode;
    }

    public static String ObtnFechaDB(){

        BDManager usdbh = new BDManager(App.getContext(), Constants.NOMBRE_BD, null, Constants.VERSION_BD);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        String Date = "";

        if(db!=null){
            Cursor drRows = db.rawQuery("SELECT datetime('now','localtime') as fecha FROM OPR_ORDENES",null);
            if(drRows.moveToFirst()){
                Date = drRows.getString(0);
            }
            db.close();
        }
        return Date;
    }

    public static String getPhotoCode(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoCode = "_" + date;
        return photoCode;
    }

   /* public static String GetCapturistaActual(){
        String strIdCapturo ="";
        BDManager Manejador = new BDManager(App.getContext(), "DBOrdenesTrabajo", null, 1);
        SQLiteDatabase db = Manejador.getReadableDatabase();

        if(db!=null){
            Cursor c = db.rawQuery("SELECT id_usuario FROM Cat_Capturistas WHERE estado_activo=1;",null);
            if(c.moveToFirst()){
                strIdCapturo = c.getString(0);
            }else  {
                strIdCapturo = "C0001";
            }
            if(!c.isClosed())c.close();
            db.close();
        }
        return  strIdCapturo;
    }*/

    public static String CortaCadena(String strCadena){
        String strCadenaRetun="";
        String[] splits = strCadena.split("\\/");
         strCadenaRetun = splits[splits.length-1];
        return strCadenaRetun;
    }

    public static boolean ObtenerValorBooleanoParametro(String strParametro){
        boolean boolMostrar = true;
        IParametro paraService = new ParametroDomainObject();
        String strValor = paraService.GetValorParametro(strParametro);

        if(!TextUtils.isEmpty(strValor)){
            boolMostrar = strValor.equals("1");
        }

        return boolMostrar;
    }

    public static int ObtenerValorEnteroParametro(String strParametro){
        int intValor = 0;
        IParametro paraService = new ParametroDomainObject();
        String strValor = paraService.GetValorParametro(strParametro);

        if(!TextUtils.isEmpty(strValor)){
            intValor = Integer.parseInt(strValor);
        }else{
            intValor = 3;
        }

        return intValor;
    }

  /*  public static void ActualizarCampoDirecto(String strUpdate, String strWhere){
        try {
            BDManager Manejador = new BDManager(App.getContext(), "DBOrdenesTrabajo", null, 1);
            SQLiteDatabase db = Manejador.getReadableDatabase();

            if(db!=null){
                String StrUpdate="UPDATE OPR_ORDENES SET "+strUpdate+" WHERE "+strWhere;
                db.execSQL(StrUpdate);
                db.close();
            }

         *//*   Data = OPR_ORDENES.cargarRegistro(Data.getId());*//*
        } catch (ClassCastException e) {
            Toast.makeText(App.getContext(),"Error " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }*/
/*
    public static void SnackBarCustom(Context context, String texto, tipo_snackbar tipo){//2

        Toast.makeText(context,
                texto,
                Toast.LENGTH_LONG).show();
    }*/

    @SuppressLint("RestrictedApi")
    public static void SnackBarCustom(Activity activity
            , View view
            , String titulo
            , String mensaje
            , tipo_snackbar tipo
    ){//2

        // create an instance of the snackbar
        final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        // inflate the custom_snackbar_view created previously
        View customSnackView =  activity.getLayoutInflater().inflate(R.layout.custom_snackbar_view, null);

        // set the background of the default snackbar as transparent
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);

        // now change the layout of the snackbar
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        // set padding of the all corners as 0
        snackbarLayout.setPadding(0, 0, 0, 0);

//        snackbarLayout.setElevation(20);

      /*  CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                snackbarLayout.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 150);
        snackbarLayout.setLayoutParams(layoutParams);*/

/*
        Snackbar.SnackbarLayout.LayoutParams params = new Snackbar.SnackbarLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 55);

        snackbarLayout.setLayoutParams(params);
*/

        CardView cvSnackBar = customSnackView.findViewById(R.id.cvSnackBar);
        ImageView ivImagen = customSnackView.findViewById(R.id.ivImagen);
        TextView tvTitulo = customSnackView.findViewById(R.id.tvTitulo);
        TextView tvMensaje = customSnackView.findViewById(R.id.tvMensaje);

        if (!TextUtils.isEmpty(titulo))
        {
            tvTitulo.setText(titulo);
        }else {
            tvTitulo.setVisibility(View.GONE);
        }

        tvMensaje.setText(mensaje);
        Drawable myDrawable;
        switch (tipo){
            case POSITIVE_SNACKBAR:
                cvSnackBar.setBackgroundColor(Color.parseColor("#3EB959"));
                myDrawable = activity.getDrawable(R.drawable.ic_ok_green_dialog);
                ivImagen.setImageDrawable(myDrawable);
                break;
            case NEGATIVE_SNACKBAR:
                cvSnackBar.setBackgroundColor(Color.parseColor("#E2231A"));
                myDrawable = activity.getDrawable(R.drawable.ic_error_red_dialog);
                ivImagen.setImageDrawable(myDrawable);
                break;
            case INFORMATIVE_SNACKBAR:
                cvSnackBar.setBackgroundColor(Color.parseColor("#0F93BA"));
                myDrawable = activity.getDrawable(R.drawable.ic_info_24);
                ivImagen.setImageDrawable(myDrawable);
                break;
        }


        // add the custom snack bar layout to snackbar layout
        snackbarLayout.addView(customSnackView, 0);

        snackbar.show();

    }
    public static void MuestraErrorOnRetrofitFailure(Throwable throwable,
                                                     Activity activity,
                                                     tipo_mensajes_error tipo_mensaje,
                                                     View view)
    {

/*        if (throwable instanceof SocketTimeoutException)
        {
            // "Connection Timeout";
        }
        else if (throwable instanceof IOException)
        {
            // "Timeout";
        }
        else
        {
            //Call was cancelled by user
            if(call.isCanceled())
            {
                System.out.println("Call was cancelled forcefully");
            }
            else
            {
                //Generic error handling
                System.out.println("Network Error :: " + throwable.getLocalizedMessage());
            }
        }*/

        String strMensajeError = "Error: " + throwable.getMessage();
        Log.d("TAG1", strMensajeError);
        switch (tipo_mensaje) {
            case CUADRO_DIALOGO:
                //Util.MuestraMsjError(strMensajeError,context);

                /*CustomDialog alert = new CustomDialog();
                alert.showDialog(activity
                        , App.getContext().getString(R.string.inicio_de_sesion)
                        , strMensajeError
                        , tipo_dialog.NEGATIVE_POPUP
                );*/
                break;
            case TOAST:
                Toast.makeText(activity,strMensajeError, Toast.LENGTH_LONG).show();
                break;
            case SNACKBAR:
                //Util.customSnackBar(strMensajeError,viewProgresBar);
//                View view = new View(App.getContext());
                SnackBarCustom(activity
                        , view
                        , "Error. "
                        ,strMensajeError
                        ,tipo_snackbar.NEGATIVE_SNACKBAR);
                break;
        }
    /*    try {
            ProgressBar progressBarEsperar = (ProgressBar) viewProgresBar;
            progressBarEsperar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();

        }*/

    }

/*    public static void MuestraErrorOnRetrofitFailure(Throwable throwable, Context context, tipo_mensajes_error tipo_mensaje, View viewProgresBar){

        String strMensajeError = "Error: " + throwable.getMessage();
        Log.d("TAG1", strMensajeError);

        switch (tipo_mensaje) {
            case CUADRO_DIALOGO:
                //Util.MuestraMsjError(strMensajeError,context);

                break;
            case TOAST:
                Toast.makeText(context,
                        strMensajeError,
                        Toast.LENGTH_LONG).show();
                break;
            case SNACKBAR:
                SnackBarCustom(context
                        ,strMensajeError
                        ,tipo_snackbar.NEGATIVE_SNACKBAR);
                break;
        }



        try {
            if (viewProgresBar != null){
                ProgressBar progressBarEsperar = (ProgressBar) viewProgresBar;
                progressBarEsperar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void MuestraErrorOnRetrofitFailure(Throwable throwable, Context context){
        MuestraErrorOnRetrofitFailure(throwable,context,tipo_mensajes_error.SNACKBAR, null);
    }*/

    public static String ObtenerMensajeDeCodigo (int responseCode){
        String strMensaje = "";

        switch (responseCode) {
            case 200:
                strMensaje="200 OK, La solicitud ha tenido éxito.";
                break;
            //Errores del Cliente mas comunes
            case 400:
                strMensaje="400 Bad Request, El servidor no pudo interpretar la solicitud dada una sintaxis inválida.";
                break;
            case 401:
                strMensaje = "401 Unauthorized, Es necesario autenticar para obtener la respuesta solicitada.";
                break;
            case 403:
                strMensaje = "403 Forbidden, El cliente no posee los permisos necesarios para cierto contenido.";
                break;
            case 404:
                strMensaje = "404 Not Found, El servidor no pudo encontrar el contenido solicitado.";
                break;
            case 408:
                strMensaje = "408 Request Timeout, Se agoto el tiempo de respuesta y/o la respuesta es enviada en una conexión inactiva";
                break;
            case 409:
                strMensaje = "409 Conflict, Esta respuesta puede ser enviada cuando una petición tiene conflicto con el estado actual del servidor.";
                break;
            case 410:
                strMensaje = "410 Gone, Indica que el recurso solicitado ya no está disponible y no lo estará de nuevo. Debería ser utilizado cuando un recurso ha sido quitado de forma permanente.\n" +
                        "Si un cliente recibe este código no debería volver a solicitar el recurso en el futuro. Por ejemplo un buscador lo eliminará de sus índices y lo hará más rápidamente que utilizando un código 404.";
                break;
            case 411:
                strMensaje = "411 Length Required, El servidor rechaza la petición del navegador porque no incluye la cabecera Content-Length adecuada.";
                break;
            case 412:
                strMensaje = "412 Precondition Failed, El servidor no es capaz de cumplir con algunas de las condiciones impuestas por el navegador en su petición.";
                break;
            case 413:
                strMensaje = "413 Request Entity Too Large, La petición del navegador es demasiado grande y por ese motivo el servidor no la procesa.";
                break;
            case 414:
                strMensaje = "414 Request-URI Too Long, La URI de la petición del navegador es demasiado grande y por ese motivo el servidor no la procesa (esta condición se produce en muy raras ocasiones y casi siempre porque el navegador envía como GET una petición que debería ser POST.";
                break;
            case 415:
                strMensaje = "415 Unsupported Media Type, La petición del navegador tiene un formato que no entiende el servidor y por eso no se procesa.";
                break;
            case 416:
                strMensaje = "416 Requested Range Not Satisfiable, El cliente ha preguntado por una parte de un archivo, pero el servidor no puede proporcionar esa parte, por ejemplo, si el cliente preguntó por una parte de un archivo que está más allá de los límites del fin del archivo.";
                break;
            case 417:
                strMensaje = "417 Expectation Failed, La petición del navegador no se procesa porque el servidor no es capaz de cumplir con los requerimientos de la cabecera Expect de la petición.";
                break;

            //Errores del Servidor
            case 500:
                strMensaje = "500 Internal Server Error, El servidor ha encontrado una situación que no sabe como manejarla.";
                break;
            case 501:
                strMensaje = "501 Not Implemented, El método solicitado no esta soportado por el servidor y no puede ser manejada.";
                break;
            case 511:
                strMensaje = "511 Network Authentication Required, El cliente necesita auntenticar para ganar acceso a la red.";
                break;
            default:
                //strTitulo = "";
                strMensaje="Error de conexion. codigo: "+responseCode;
                break;
        }

        return strMensaje;
    }

    public static void MuestraMensajeResponseNotSuccessful(int responseCode, Activity activity,tipo_mensajes_error tipo_mensaje){
        final String PLANTILLA_CODIGO_RESPUESTA="";
        String strMensaje = ObtenerMensajeDeCodigo(responseCode);

        Log.e("TAG1", strMensaje);

        Log.d("TAG1", strMensaje);
        switch (tipo_mensaje) {
            case CUADRO_DIALOGO:
                //Util.MuestraMsjError(PLANTILLA_CODIGO_RESPUESTA+strMensaje,context);
                break;
            case TOAST:
                Toast.makeText(activity,PLANTILLA_CODIGO_RESPUESTA+strMensaje, Toast.LENGTH_LONG).show();
                break;
            case SNACKBAR:
                View view = new View(App.getContext());

                SnackBarCustom(activity
                        , view
                        ,"Errpr. "
                        ,PLANTILLA_CODIGO_RESPUESTA+strMensaje
                        , tipo_snackbar.NEGATIVE_SNACKBAR);
                break;
        }
    }
    public static void MuestraMensajeResponseNotSuccessful(int responseCode, Activity activity){
        MuestraMensajeResponseNotSuccessful(responseCode,activity ,tipo_mensajes_error.SNACKBAR);
    }

}

