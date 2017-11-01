package com.tgr.wservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    //Variaveis para a atribuição de dados para o servidor
    String estadoPut;
    String cidadePut;
    String locPut;
    String descPut;

    //Variaveis para as views do android
    EditText etestado;
    EditText etcidade;
    EditText etloc;
    EditText etdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //informando as views do android para as variaveis
        etestado = (EditText) findViewById(R.id.estadoPutV);
        etcidade = (EditText) findViewById(R.id.cidadePutV);
        etloc = (EditText) findViewById(R.id.locPutV);
        etdesc = (EditText) findViewById(R.id.descPutV);
    }

    public void retrieve(View view) {

        //colocando em variaveis as informações enviadas pelos usuarios
        estadoPut = etestado.getText().toString();
        cidadePut = etcidade.getText().toString();
        locPut = etloc.getText().toString();
        descPut = etdesc.getText().toString();

        //esse "if" testa se todos os campos foram preenchidos
        if (estadoPut != null && cidadePut != null && locPut != null && descPut != null && !estadoPut.equals("") && !cidadePut.equals("") && !locPut.equals("") && !descPut.equals("")) {

            //se todos foram preenchidos a função de conexão é iniciada
            new CallAPI().execute();

        } else {

            //uma mensagem é mostrada ao usuario se este não preencher todos os campos
            Toast.makeText(getApplicationContext(), "Todos os campos são obrigatorios",
                    Toast.LENGTH_SHORT).show();
        }


    }

    /*
    ---------POST---------
    estamos aqui chamando a função POST em AsyncTask, para que a conexão com o servidor seja feita
    no background do android, para que não ocorra nenhum travamento no aplicativo.
     */

    private class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {

                //link do servidor
                URL url = new URL("https://warm-ridge-33561.herokuapp.com/users");

                //é feito um mapa de Chaves (keys) e parâmetros, que serão atribuidos a suas
                //respectivas variaveis dentro do servidor
                Map<String, Object> mapParams = new LinkedHashMap<>();
                mapParams.put("estado", estadoPut);
                mapParams.put("cidade", cidadePut);
                mapParams.put("loc", locPut);
                mapParams.put("desc", descPut);

                //aqui são feitas modificações na string para que o servidor entenda o input
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : mapParams.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                //conexão com o servidor
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                //envio de metodos e propriedades, para que o servidor saiba o que queremos fazer
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);

                //envio das informações do usuario para o servidor
                conn.getOutputStream().write(postDataBytes);
                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                for (int c; (c = in.read()) >= 0; )
                    System.out.print((char) c);
            } catch (java.io.IOException e) {

            }
            return null;
        }
    }
}
