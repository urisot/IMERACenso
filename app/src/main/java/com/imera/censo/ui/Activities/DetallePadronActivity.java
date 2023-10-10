package com.imera.censo.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.imera.censo.BosinessCore.BDManager;
import com.imera.censo.Contracts.Models.OPR_USUARIOS;
import com.imera.censo.R;

public class DetallePadronActivity extends AppCompatActivity {
    private OPR_USUARIOS Registro = new OPR_USUARIOS();

    private final int REQUEST_CODE_CLANDESTINO = 991;
    private final int REQUEST_CODE_FACTIBLE = 102;
    private final int REQUEST_CODE_NORMAL = 110;

    //Controles
    private Toolbar toolbar;
    private TextView Direccion;
    private TextView Usuario;
    private TextView Cuenta;
    private TextView Estado;
    private TextView Contador;
    private TextView Clave_LOC;
    private TextView Medidor;
    private ImageButton Atras;
    private ImageButton Siguiente;
    private ImageView img_Foto;
    private FloatingActionButton btn_Capturar;
    private FloatingActionButton btn_Convertir_Clandestino;
    private LinearLayout ll_RFatible;
    private LinearLayout ll_RClandestino;
    private CardView Carta;
    private TextView isC;
    private TextView isF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_padron);
        //Obteniendo data
        String CurrentId = getIntent().getStringExtra("Id_Inicial");

        //Anado la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Instanciando Controles
        Carta = (CardView)findViewById(R.id.cv_DetallesRutas);
        Direccion = (TextView)findViewById(R.id.txt_Direccion);
        Usuario = (TextView)findViewById(R.id.txt_Usuario);
        Cuenta = (TextView)findViewById(R.id.txt_Cuenta);
        Estado = (TextView)findViewById(R.id.txt_Estado);
        img_Foto = (ImageView)findViewById(R.id.img_casa);
        Clave_LOC = (TextView)findViewById(R.id.txt_CVELOC);
        Medidor = (TextView)findViewById(R.id.txt_medidor);
        isC= (TextView)findViewById(R.id.isClandestino);
        isF= (TextView)findViewById(R.id.isFactible);
        Contador = (TextView)findViewById(R.id.txt_contador_rutas);
        Atras = (ImageButton)findViewById(R.id.btn_rutaatras);
        Siguiente = (ImageButton)findViewById(R.id.btn_rutasiguiente);
        btn_Capturar = (FloatingActionButton)findViewById(R.id.btnf_registrar);
        btn_Convertir_Clandestino = (FloatingActionButton)findViewById(R.id.btnf_convertir_clandestino);
        ll_RFatible = (LinearLayout)findViewById(R.id.ll_rfactible);
        ll_RClandestino = (LinearLayout)findViewById(R.id.ll_rclandestino);

        //Cargando la informacion la primera vez
        Cargar(CurrentId);

        //Asignando Listeners
        asignarEventoMovimientoCarta();

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnterior(Registro.getId());

            }
        });

        Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSiguiente(Registro.getId());
            }
        });

        //basicamente es un boton de edicion decide que pantalla llamar
        btn_Capturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Registro.getId_identificacion()!=null){//Caso fueron previamente insertados modificados
                    if(Registro.getId_identificacion().equals("1")) {
                        Intent intento = new Intent(DetallePadronActivity.this,EdicionNormalActivity.class);
                        intento.putExtra("data",Registro);
                        startActivityForResult(intento,REQUEST_CODE_NORMAL);
                    }
                    if(Registro.getId_identificacion().equals("2")) {
                        Registro.setId_estatus("Actualizar");
                        Intent intento = new Intent(DetallePadronActivity.this,EdicionClandestinoActivity.class);
                        intento.putExtra("data",Registro);
                        startActivityForResult(intento,REQUEST_CODE_CLANDESTINO);
                    }
                    else if(Registro.getId_identificacion().equals("3")){
                        Registro.setId_estatus("Actualizar");
                        Intent intento = new Intent(DetallePadronActivity.this,EdicionFactibleActivity.class);
                        intento.putExtra("data",Registro);
                        startActivityForResult(intento,REQUEST_CODE_FACTIBLE);
                    }
                /*
                Modificado 05/02/2016
                ya no se marcan como clandestinos sino como factibles por el nuevo boton convertir
                a clandestino
                * */
                }else if(Registro.getId_cuenta()!=null){
                    if(Registro.getId_cuenta().equals("0")){
                        Registro.setId_estatus("Actualizar");
                        Registro.setId_identificacion("3");// 2 anteriormente
                        Intent intento = new Intent(DetallePadronActivity.this,EdicionFactibleActivity.class);
                        intento.putExtra("data",Registro);
                        startActivityForResult(intento,REQUEST_CODE_FACTIBLE);
                    }else{
                        Registro.setId_identificacion("1");
                        Intent intento = new Intent(DetallePadronActivity.this,EdicionNormalActivity.class);
                        intento.putExtra("data",Registro);
                        startActivityForResult(intento,REQUEST_CODE_NORMAL);
                    }
                }
            }
        });

        btn_Convertir_Clandestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetallePadronActivity.this, R.style.AppCompatAlertDialogStyle);
                alertDialog.setMessage("Si convierte este factible tendra que recapturar toda la informacion correspondiente.");
                alertDialog.setTitle("Convertir a Clandestino?");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //c�digo java si se ha pulsado no
                    }
                });
                alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        BDManager usdbh = new BDManager(DetallePadronActivity.this, "DBCenso", null, 1);
                        SQLiteDatabase db = usdbh.getWritableDatabase();
                        if (db != null) {
                            db.execSQL("UPDATE OPR_USUARIOS SET id_identificacion = 2,id_estatus = 101 WHERE id="+Registro.getId()+";");
                            db.close();
                            Cargar(Registro.getId());
                        }

                    }
                });
                alertDialog.show();

            }
        });

        ll_RFatible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OPR_USUARIOS data = new OPR_USUARIOS();
                data.setId_carga(Registro.getId_carga());
                data.setId_padron("0");
                data.setId(Registro.getId());
                data.setNombre("A QUIEN CORRESPONDA");
                data.setId_identificacion("3");
                data.setId_estatus("Insertar");

                Intent intento = new Intent(DetallePadronActivity.this,EdicionFactibleActivity.class);
                intento.putExtra("data",data);
                startActivityForResult(intento,REQUEST_CODE_FACTIBLE);
            }
        });

        ll_RClandestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OPR_USUARIOS data = new OPR_USUARIOS();
                data.setId_carga(Registro.getId_carga());
                data.setId_padron("0");
                data.setId(Registro.getId());
                data.setNombre("A QUIEN CORRESPONDA");
                data.setId_identificacion("2");
                data.setId_estatus("Insertar");

                Intent intento = new Intent(DetallePadronActivity.this,EdicionClandestinoActivity.class);
                intento.putExtra("data",data);
                startActivityForResult(intento,REQUEST_CODE_CLANDESTINO);
            }
        });
    }

    /***********************************************************************************************/
    @Override
    public void onActivityResult(int requestc,int resultc, Intent data){
        super.onActivityResult(requestc,resultc,data);
        if(requestc==REQUEST_CODE_NORMAL){
            if(resultc ==1 ){
                getSiguiente(Registro.getId());
            }
        }else if(requestc == REQUEST_CODE_CLANDESTINO){
            if(resultc ==1 ){
                String accion = data.getStringExtra("accion");

                if(accion.equals("Insertado")){
                    //se inserto ver info actual
                    Cargar(Registro.getId());
                }else if(accion.equals("Actualizado")){
                    //se "censo" pasar siguiente registro
                    getSiguiente(Registro.getId());
                }
            }
        }else if(requestc == REQUEST_CODE_FACTIBLE){
            if(resultc ==1 ){
                String accion = data.getStringExtra("accion");

                if(accion.equals("Insertado")){
                    //se inserto ver info actual
                    Cargar(Registro.getId());
                }else if(accion.equals("Actualizado")){
                    //se "censo" pasar siguiente registro
                    getSiguiente(Registro.getId());
                }

            }
        }
    }


    /***********************************************************************************************/
    public void getSiguiente(String Id){
        Id = ""+(Integer.parseInt(Id)+1);

        //Abrimos la base de datos 'DBUsuarios' en modo Lectura
        BDManager usdbh = new BDManager(this, "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT id FROM OPR_USUARIOS WHERE id="+Id, null);

            //Nos aseguramos de que existe al menos un registro
            if (!c.moveToFirst()) {
                Id="1";
            }
            //Cerramos la base de datos
            db.close();
            if(!c.isClosed())c.close();
        }

        Cargar(Id);
    }

    public void getAnterior(String Id){
        Id = ""+(Integer.parseInt(Id)-1);
        String Ultimo = getUltimo();

        //Abrimos la base de datos 'DBUsuarios' en modo Lectura
        BDManager usdbh = new BDManager(this, "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT id FROM OPR_USUARIOS WHERE id="+Id, null);

            //Nos aseguramos de que existe al menos un registro
            if (!c.moveToFirst()) {
                Id = Ultimo;
            }
            //Cerramos la base de datos
            db.close();
            if(!c.isClosed())c.close();
        }

        Cargar(Id);
    }

    public String getUltimo(){
        //Abrimos la base de datos 'DBUsuarios' en modo Lectura
        BDManager usdbh = new BDManager(this, "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT MAX(id) FROM OPR_USUARIOS", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                return c.getString(0);
            }
            //Cerramos la base de datos
            db.close();
            if(!c.isClosed())c.close();
        }

        return "";
    }

    public String getCantidad(){
        //Abrimos la base de datos 'DBUsuarios' en modo Lectura
        BDManager usdbh = new BDManager(this, "DBCenso", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT count(id) FROM OPR_USUARIOS", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                return c.getString(0);
            }
            //Cerramos la base de datos
            if(!c.isClosed())c.close();
            db.close();
        }
        return "";
    }

    public void Cargar(String Id){
        Registro = OPR_USUARIOS.cargarRegistro(Id);
        getSupportActionBar().setTitle("Ruta: "+Registro.getId_carga());

        isF.setVisibility(View.INVISIBLE);
        isC.setVisibility(View.INVISIBLE);

        String Ultimo = getCantidad();

        Direccion.setText(Registro.getDireccion());
        Usuario.setText(Registro.getNombre());
        Cuenta.setText(Registro.getId_cuenta());

        switch(Registro.getId_estatus()){
            case "101":
                Estado.setText("Pendiente");
                Estado.setTextColor(ContextCompat.getColor(this,android.R.color.holo_red_dark));
                break;
            case "102":
                Estado.setText("Capturado");
                Estado.setTextColor(ContextCompat.getColor(this,android.R.color.holo_green_dark));
                break;
        }

        if(Registro.getFoto_predio()!=null){
            if(!Registro.getFoto_predio().equals("")){
                Bitmap Foto = BitmapFactory.decodeFile(Registro.getFoto_predio());
                img_Foto.setImageBitmap(Foto);
            }
            else
                img_Foto.setImageResource(R.drawable.tarjeta);
        }else
            img_Foto.setImageResource(R.drawable.tarjeta);

        if(Registro.getClaveloc()!=null){
            if(!Registro.getClaveloc().equals(""))
                Clave_LOC.setText(Registro.getClaveloc());
            else
                Clave_LOC.setText("No Tiene");
        }else
            Clave_LOC.setText("No Tiene");

        if(Registro.getMedidor_ins()!=null){
            if(!Registro.getMedidor_ins().equals(""))
                Medidor.setText(Registro.getMedidor_ins());
            else
                Medidor.setText("No Capturado");
        }else
            Medidor.setText("No Capturado");

        if(Registro.getId_identificacion()!=null){
            if(Registro.getId_identificacion().equals("3"))
                isF.setVisibility(View.VISIBLE);
            else if(Registro.getId_identificacion().equals("2"))
                isC.setVisibility(View.VISIBLE);


            if(Registro.getId_identificacion().equals("3") && Registro.getId_estatus().equals("102"))
                btn_Convertir_Clandestino.setVisibility(View.VISIBLE);
            else
                btn_Convertir_Clandestino.setVisibility(View.GONE);

        }else{
            btn_Convertir_Clandestino.setVisibility(View.GONE);

            if(Registro.getId_cuenta().equals("0"))
                isF.setVisibility(View.VISIBLE);
        }

        Contador.setText(Id + " de " + Ultimo);
    }

    /**
     * Funcion creada para facilida de lectura, es la encargada de gestionar el gesture, valga la
     * redundancia, que se encarga de hacer la animacion de movimiento de la carta y cargar el nuevo
     * registro.
     */
    public void asignarEventoMovimientoCarta(){
        View v = this.findViewById(android.R.id.content);

        final GestureDetector gesture = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        Log.i("Debugg", "onFling has been called!");
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Carta.animate().translationX(-10).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        Carta.animate().translationX(0);

                                    }
                                });

                                getSiguiente(Registro.getId());
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                                Carta.animate().translationX(10).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        Carta.animate().translationX(0);

                                    }
                                });

                                getAnterior(Registro.getId());
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

    }

}
