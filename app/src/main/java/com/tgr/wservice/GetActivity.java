package com.tgr.wservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.Result;

    /*
    ---------MAIN---------
    Classe .java para o funcionamento da atividade de recebimento de informações do servidor
    */

public class GetActivity extends AppCompatActivity {

    //Variavel para o texto a ser mostrado na tela
    public static String toScreen = "";
    //Variavel para a escolha específica de uma informação usando um id gerado no servidor
    public String id;

    //variaveis para pegar informações das views do android
    EditText et;
    TextView main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        //informando as views do android para as variaveis
        main = (TextView)findViewById(R.id.main);
        et = (EditText)findViewById(R.id.denId);

    }

    //função usada no onClick do botão, será iniciada ao clicar no botão de pesquiza espeçífica
    public void especifico(View view) {

        //reiniciando essas duas variaveis para que o aplicativo não empilhe mensagens,
        //mostrando apenas o necessario
        toScreen = "";
        main.setText("");

        //colocando o id que o usuario escolheu
        id = et.getText().toString();

        //testando se o usuario escolheu um id
        if(id.isEmpty()){
            Toast.makeText(getApplicationContext(), "Específique um id",
                    Toast.LENGTH_SHORT).show();
        }else {

            //chamando a função para conectar no servidor
            new CallAPI().execute();
        }
    }

    public void todos(View view){

        //reiniciando essas duas variaveis para que o aplicativo não estaque mensagens,
        //mostrando apenas o necessario
        toScreen = "";
        main.setText("");

        //colocando o id vazio, para que o servidor retorne tudo
        id = "";

        //chamando a função para conectar no servidor
        new CallAPI().execute();
    }

    /*
    ---------GET---------
    estamos aqui chamando a função GET em AsyncTask, para que a conexão com o servidor seja feita no
    background do android, para que não ocorra nenhum travamento no aplicativo.
    */

    private class CallAPI extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {


            try

            {

                //conexão com o servidor
                URL url = new URL("https://warm-ridge-33561.herokuapp.com/users/" + id);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                //envio de metodos e propriedades, para que o servidor saiba o que queremos fazer
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                //como a resposta hhtp de "OK" é 200, enviamos um erro se qualquer outro codigo for
                //retornado
                if (conn.getResponseCode() != 200) {
                    return null;
                }

                //parte de Output do servidor, que será atribuida a variavel toScreen
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    toScreen += output;
                }

                //saida do servidor
                conn.disconnect();

            } catch (
                    MalformedURLException e)

            {

                e.printStackTrace();

            } catch (
                    IOException e)

            {

                e.printStackTrace();

            }

            //retorno necessário da função
            return toScreen;

        }

        //esta função será chamada ao final da anterior, colocando na tela do usuario o resultado
        @Override
        protected void onPostExecute(String result) {

            if (result == null){
                Toast.makeText(getApplicationContext(), "Ocorreu um erro, por favor verifique o seu id",
                        Toast.LENGTH_SHORT).show();
            }

            String[] arr = result.split("\"");

            String finalS;

            finalS = "";

            for(int x = 0; x < arr.length/20; x++) {
                finalS += "Id: " + arr[2+20*x] + "\n";
                finalS += "Estado: " + arr[17+20*x] + "\n";
                finalS += "Cidade: " + arr[5+20*x] + "\n";
                finalS += "Endereço: " + arr[9+20*x] + "\n";
                finalS += "Descrição: " + arr[13+20*x] + "\n";
                finalS += "-------------------------------" + "\n";
            }

            main.setText(finalS);
        }
    }

}