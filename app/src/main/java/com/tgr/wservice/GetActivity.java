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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

    /*
    ---------MAIN---------
     */

public class GetActivity extends AppCompatActivity {

    public static String toScreen = "";
    public String estado;

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        et = (EditText)findViewById(R.id.estadoView);

    }

    public void button(View view) {
        TextView tx = (TextView) findViewById(R.id.main);
        tx.setText(toScreen);
        toScreen = "";
    }

    public void get(View view){

        toScreen = "";

        String finalEs = et.getText().toString().toUpperCase();

        if(finalEs.equals("AC")){estado = "59f72d777919a200123da55a";}
        else if (finalEs.equals("AL")){estado = "59f72d9a7919a200123da55b";}
        else if (finalEs.equals("AP")){estado = "59f72d9d7919a200123da55c";}
        else if (finalEs.equals("AM")){estado = "59f72da67919a200123da55d";}
        else if (finalEs.equals("BA")){estado = "59f72dae7919a200123da55e";}
        else if (finalEs.equals("CE")){estado = "59f72db57919a200123da55f";}
        else if (finalEs.equals("DF")){estado = "59f72db97919a200123da560";}
        else if (finalEs.equals("ES")){estado = "59f72dbf7919a200123da561";}
        else if (finalEs.equals("GO")){estado = "59f72dc77919a200123da562";}
        else if (finalEs.equals("MA")){estado = "59f72dcd7919a200123da563";}
        else if (finalEs.equals("MT")){estado = "59f72dd57919a200123da564";}
        else if (finalEs.equals("MS")){estado = "59f72de57919a200123da565";}
        else if (finalEs.equals("MG")){estado = "59f72de97919a200123da566";}
        else if (finalEs.equals("PA")){estado = "59f72def7919a200123da567";}
        else if (finalEs.equals("PB")){estado = "59f72dfd7919a200123da568";}
        else if (finalEs.equals("PR")){estado = "59f72e047919a200123da569";}
        else if (finalEs.equals("PE")){estado = "59f72e0c7919a200123da56a";}
        else if (finalEs.equals("PI")){estado = "59f72e137919a200123da56b";}
        else if (finalEs.equals("RJ")){estado = "59f72e1b7919a200123da56c";}
        else if (finalEs.equals("RN")){estado = "59f72e207919a200123da56d";}
        else if (finalEs.equals("RS")){estado = "59f72e257919a200123da56e";}
        else if (finalEs.equals("RO")){estado = "59f72e2c7919a200123da56f";}
        else if (finalEs.equals("RR")){estado = "59f72e3c7919a200123da570";}
        else if (finalEs.equals("SC")){estado = "59f72e427919a200123da571";}
        else if (finalEs.equals("SP")){estado = "59f72e4b7919a200123da572";}
        else if (finalEs.equals("SE")){estado = "59f72e557919a200123da573";}
        else if (finalEs.equals("TO")){estado = "59f72e5b7919a200123da574";}
        else{
            estado = "59f741517919a200123da575";
        }

        new CallAPI().execute();
    }

    /*
    ---------GET---------
     */

    private class CallAPI extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {


            try

            {

                URL url = new URL("https://warm-ridge-33561.herokuapp.com/users/" + estado);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    toScreen += output;
                    //System.out.println(output);
                }

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

            return toScreen;

        }

    }

}