package com.gdr.ldr.ws;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.gdr.ldr.model.ResponseDTO;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;


/**
 * Created by promero on 31/07/13.
 */
public class UsersJson extends AsyncTask<Void, Void, Boolean> {
    private String user;
    private String pass;

    /*Contructor*/
    public UsersJson(String user, String pass){
        this.user = user;
        this.pass = pass;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            return true;


				 	 	 	 	 /*
				  * Creamos el objeto Gson al que le pasamos una URL
				  */
           /* Gson miGson;
            miGson = new Gson();
            URL url = new URL("http://localhost/prueba.json");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream(),
                            Charset.forName("UTF-8")));

            //Pasamos la info del json a un objeto para consultarlo
            ResponseDTO data = miGson.fromJson(reader, ResponseDTO.class);


            /*for (int i = 0; i < users.size(); i++) {

                if (userEdit.getText().toString()
                        .equals(users.get(i).getName().toString())
                        && passEdit.getText().toString()
                        .equals(users.get(i).getPass().toString())) {
                    return true;
                } else
                    return false;
            }
*/
            //return Boolean.FALSE;
        } catch (Exception e) {
            Log.i("Login", "Error al leer el json de login:" + ConstantURLWS.WS_LOGIN );
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }




}
