package com.example.personal.login_webservice;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;


public class agendar_citafin extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    TextView espe, fech, hor, doc, cod;
    ProgressDialog progreso;
    Button regist, com;
    EditText ced;
    private NotificationManager notificacion;
    String estado = "Pendiente";
    int uno;
    StringRequest stringRequest;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    int notificationID = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_citafin);


        espe = (TextView) findViewById(R.id.especialidad);
        fech = (TextView) findViewById(R.id.fecha);
        hor = (TextView) findViewById(R.id.horario);
        doc = (TextView) findViewById(R.id.doctor);
        cod = (TextView) findViewById(R.id.cedulas);
        ced = (EditText) findViewById(R.id.cedula);

        com = (Button) findViewById(R.id.comprobar);

        Bundle bundle = getIntent().getExtras();
        String esp = bundle.getString("ESPECIALIDAD");
        String fecha = bundle.getString("FECHA");
        String horario = bundle.getString("HORARIO");
        String doct = bundle.getString("DOCTOR");


        espe.setText(esp);
        fech.setText(fecha);
        request = Volley.newRequestQueue(this);
        hor.setText(horario);
        doc.setText(doct);
        ced.setSingleLine(false);
        ced.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);

        com.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (espe.getText().toString().equals("Traumotología" + " ")) {

                    guardar_traumotologia();
                    gestion_citas();


                }

                if (espe.getText().toString().equals("Cardiología" + " ")) {
                    guardar_cardiologia();
                    gestion_citas();
                }
                if (espe.getText().toString().equals("Pediatría" + " ")) {
                    guardar_pediatria();
                    gestion_citas();
                }
                if (espe.getText().toString().equals("Ginecología" + " ")) {
                    guardar_ginecologia();
                    gestion_citas();
                }
                if (espe.getText().toString().equals("Obstreticia" + " ")) {
                    guardar_obstreticia();
                    gestion_citas();
                }
                if (espe.getText().toString().equals("Cirugía Gen." + " ")) {
                    guardar_cirugiageneral();
                    gestion_citas();

                }
                if (espe.getText().toString().equals("Medicina Gener." + " ")) {
                    guardar_medicinalgeneral();
                    gestion_citas();
                }

            }
        });

        if (ActivityCompat.checkSelfPermission(agendar_citafin.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(agendar_citafin.this, Manifest
                .permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(agendar_citafin.this, new String[]
                    {Manifest.permission.SEND_SMS,}, 1000);
        } else {

        }
        ;

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    private void enviarMensaje(String numero, String mensaje, String msj) {
        try {

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero, null, mensaje, null, null);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public void codigo() {
        uno = (int) Math.floor(Math.random() * 50000 + 10000);
        cod.setText(uno + "");
        String jhon = "5930960799906";

        enviarMensaje(jhon, String.valueOf(uno) + " este el  codigo de su cita, no olvide que es muy importante para gestionarla", "hola");
        displayNotification();

    }

    protected void displayNotification() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker = "Codigo de cita";
        CharSequence contentTitle = "Citas Medicas en Linea/MedicAPP";
        CharSequence contentText = "codigo de cita : " + cod.getText().toString() + " Revisa tu Bandeja de MSJ";
        Notification noti = new android.support.v7.app.NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.kk)
                .addAction(R.drawable.kk, ticker, pendingIntent)
                .setVibrate(new long[]{100, 250, 100, 500})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        nm.notify(notificationID, noti);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void guardar_traumotologia() {
        if(ced.getText().toString().equals("")) {

            cedula();

        }else {

            codigo();


            String url = "http://hospitalmanabideldia.com/datos_traumotologia.php?Codigo=" + cod.getText().toString()
                    + "&Fecha=" + fech.getText().toString() + "&Horario=" + hor.getText().toString() + "&CedulaPaciente=" + ced.getText().toString() + "&especialidad=" + espe.getText().toString() + "&doctor=" + doc.getText().toString();
            url = url.replace(" ", "%20");

            try {
                progreso = new ProgressDialog(this);
                progreso.setMessage("Cargando...");
                progreso.show();

                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                request.add(jsonObjectRequest);

            } catch (Exception exe) {
                Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void guardar_cardiologia() {



        if(ced.getText().toString().equals("")) {

          cedula();

        }else {
            try {
                codigo();
                progreso = new ProgressDialog(this);
                progreso.setMessage("Cargando...");
                progreso.show();
                String url = "http://hospitalmanabideldia.com/datos_cardiologia.php?Codigo=" + cod.getText().toString()
                        + "&Fecha=" + fech.getText().toString() + "&Horario=" + hor.getText().toString() + "&CedulaPaciente=" + ced.getText().toString() + "&especialidad=" + espe.getText().toString() + "&doctor=" + doc.getText().toString();
                url = url.replace(" ", "%20");
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                request.add(jsonObjectRequest);

            } catch (Exception exe) {
                Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void guardar_pediatria() {
        if(ced.getText().toString().equals("")) {

            cedula();

        }else {
            codigo();

            try {
                progreso = new ProgressDialog(this);
                progreso.setMessage("Cargando...");
                progreso.show();
                String url = "http://hospitalmanabideldia.com/datos_pediatria.php?Codigo=" + cod.getText().toString()
                        + "&Fecha=" + fech.getText().toString() + "&Horario=" + hor.getText().toString() + "&CedulaPaciente=" + ced.getText().toString() + "&especialidad=" + espe.getText().toString() + "&doctor=" + doc.getText().toString();
                url = url.replace(" ", "%20");
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                request.add(jsonObjectRequest);

            } catch (Exception exe) {
                Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void guardar_ginecologia() {
        if(ced.getText().toString().equals("")) {

            cedula();

        }else {
            codigo();

            try {
                progreso = new ProgressDialog(this);
                progreso.setMessage("Cargando...");
                progreso.show();
                String url = "http://hospitalmanabideldia.com/datos_ginecologia.php?Codigo=" + cod.getText().toString()
                        + "&Fecha=" + fech.getText().toString() + "&Horario=" + hor.getText().toString() + "&CedulaPaciente=" + ced.getText().toString() + "&especialidad=" + espe.getText().toString() + "&doctor=" + doc.getText().toString();
                url = url.replace(" ", "%20");
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                request.add(jsonObjectRequest);

            } catch (Exception exe) {
                Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void guardar_obstreticia() {
        if(ced.getText().toString().equals("")) {

            cedula();

        }else {
            codigo();

            try {
                progreso = new ProgressDialog(this);
                progreso.setMessage("Cargando...");
                progreso.show();
                String url = "http://hospitalmanabideldia.com/datos_obstreticia.php?Codigo=" + cod.getText().toString()
                        + "&Fecha=" + fech.getText().toString() + "&Horario=" + hor.getText().toString() + "&CedulaPaciente=" + ced.getText().toString() + "&especialidad=" + espe.getText().toString() + "&doctor=" + doc.getText().toString();
                url = url.replace(" ", "%20");
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                request.add(jsonObjectRequest);

            } catch (Exception exe) {
                Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void guardar_cirugiageneral() {
        if(ced.getText().toString().equals("")) {

            cedula();

        }else {
            codigo();

            try {
                progreso = new ProgressDialog(this);
                progreso.setMessage("Cargando...");
                progreso.show();
                String url = "http://hospitalmanabideldia.com/datos_cirugiageneral.php?Codigo=" + cod.getText().toString()
                        + "&Fecha=" + fech.getText().toString() + "&Horario=" + hor.getText().toString() + "&CedulaPaciente=" + ced.getText().toString() + "&especialidad=" + espe.getText().toString() + "&doctor=" + doc.getText().toString();
                url = url.replace(" ", "%20");
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                request.add(jsonObjectRequest);

            } catch (Exception exe) {
                Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void guardar_medicinalgeneral() {
        if(ced.getText().toString().equals("")) {

            cedula();

        }else {
            codigo();

            try {
                progreso = new ProgressDialog(this);
                progreso.setMessage("Cargando...");
                progreso.show();
                String url = "http://hospitalmanabideldia.com/datos_medicinageneral.php?Codigo=" + cod.getText().toString()
                        + "&Fecha=" + fech.getText().toString() + "&Horario=" + hor.getText().toString() + "&CedulaPaciente=" + ced.getText().toString() + "&especialidad=" + espe.getText().toString() + "&doctor=" + doc.getText().toString();
                url = url.replace(" ", "%20");
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                request.add(jsonObjectRequest);

            } catch (Exception exe) {
                Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void gestion_citas() {

        if(ced.getText().toString().equals("")) {

            cedula();

        }else {
            try {
                progreso = new ProgressDialog(this);
                progreso.setMessage("Cargando...");
                progreso.show();
                String url = "http://hospitalmanabideldia.com/gestion_citas.php?codigo=" + cod.getText().toString()
                        + "&fecha=" + fech.getText().toString() + "&horario=" + hor.getText().toString() + "&especialidad=" + espe.getText().toString() + "&doctor=" + doc.getText().toString() + "&cedula=" + ced.getText().toString() + "&estado=" + estado;
                url = url.replace(" ", "%20");
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                request.add(jsonObjectRequest);

            } catch (Exception exe) {
                Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onResponse(JSONObject response) {
        citaagendada();
        progreso.hide();
        Intent i = new Intent(this, inicio.class);
        startActivity(i);


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();


        Log.i("ERROR", error.toString());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            Intent intent = new Intent(getApplication(), inicio.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /*

 }
});


}


public void guardar_traumotologia() {
// codigo();



     String url="https://clinicaprotoipo.000webhostapp.com/datos.php?Codigo=" + cod.getText().toString()
             + "&Fecha=" + fech.getText().toString() + "&Horario=" + hor.getText().toString() + "&CedulaPaciente=" + ced.getText().toString() + "&especialidad=" + espe.getText().toString() + "&doctor=" + doc.getText().toString();
     url = url.replace(" ", "%20");

try {
 progreso = new ProgressDialog(this);
 progreso.setMessage("Cargando...");
 progreso.show();

 jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, this, this);
 request.add(jsonObjectRequest);

} catch (Exception exe) {
 Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
}
}





}*/
    public void citaagendada() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customToast = inflater.inflate(R.layout.toas_personalizado, null);
        TextView txt = (TextView) customToast.findViewById(R.id.txttoas);
        txt.setText("Cita agendada, correctamente ");
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(customToast);
        toast.show();
    }

    public void citanoagendada() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customToast = inflater.inflate(R.layout.toas_personalizado, null);
        TextView txt = (TextView) customToast.findViewById(R.id.txttoas);
        txt.setText("Error al agendar, Por Fabor Contacte con el administrador ");
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(customToast);
        toast.show();
    }


    public void mensajeenviado() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customToast = inflater.inflate(R.layout.toas_personalizado, null);
        TextView txt = (TextView) customToast.findViewById(R.id.txttoas);
        txt.setText("Mensaje enviado");
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(customToast);
        toast.show();
    }

    public void mensajenoenviado() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customToast = inflater.inflate(R.layout.toas_personalizado, null);
        TextView txt = (TextView) customToast.findViewById(R.id.txttoas);
        txt.setText("Mensaje no enviado");
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(customToast);
        toast.show();
    }
    public void cedula() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customToast = inflater.inflate(R.layout.toas_personalizado, null);
        TextView txt = (TextView) customToast.findViewById(R.id.txttoas);
        txt.setText("Cedula es un campo obligatorio, Completelo!");
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(customToast);
        toast.show();
    }

}



