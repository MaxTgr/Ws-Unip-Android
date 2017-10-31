package com.tgr.wservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostActivity extends AppCompatActivity {

    String estadop;
    String estadoPut;
    String populationPut;
    String industriasPut;
    EditText ete;
    EditText etp;
    EditText eti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ete = (EditText) findViewById(R.id.estadoPutV);
        etp = (EditText) findViewById(R.id.populationPutV);
        eti = (EditText) findViewById(R.id.industriasPutV);
    }

    public void retrieve(View view) {
        estadoPut = ete.getText().toString();
        populationPut = etp.getText().toString();
        industriasPut = eti.getText().toString();
    }

    public void toApi(View view) {

        EditText et2 = (EditText) findViewById(R.id.estadoViewP);
        String finalEs = et2.getText().toString().toUpperCase();

        if (finalEs.equals("AC")) {
            estadop = "59f72d777919a200123da55a";
        } else if (finalEs.equals("AL")) {
            estadop = "59f72d9a7919a200123da55b";
        } else if (finalEs.equals("AP")) {
            estadop = "59f72d9d7919a200123da55c";
        } else if (finalEs.equals("AM")) {
            estadop = "59f72da67919a200123da55d";
        } else if (finalEs.equals("BA")) {
            estadop = "59f72dae7919a200123da55e";
        } else if (finalEs.equals("CE")) {
            estadop = "59f72db57919a200123da55f";
        } else if (finalEs.equals("DF")) {
            estadop = "59f72db97919a200123da560";
        } else if (finalEs.equals("ES")) {
            estadop = "59f72dbf7919a200123da561";
        } else if (finalEs.equals("GO")) {
            estadop = "59f72dc77919a200123da562";
        } else if (finalEs.equals("MA")) {
            estadop = "59f72dcd7919a200123da563";
        } else if (finalEs.equals("MT")) {
            estadop = "59f72dd57919a200123da564";
        } else if (finalEs.equals("MS")) {
            estadop = "59f72de57919a200123da565";
        } else if (finalEs.equals("MG")) {
            estadop = "59f72de97919a200123da566";
        } else if (finalEs.equals("PA")) {
            estadop = "59f72def7919a200123da567";
        } else if (finalEs.equals("PB")) {
            estadop = "59f72dfd7919a200123da568";
        } else if (finalEs.equals("PR")) {
            estadop = "59f72e047919a200123da569";
        } else if (finalEs.equals("PE")) {
            estadop = "59f72e0c7919a200123da56a";
        } else if (finalEs.equals("PI")) {
            estadop = "59f72e137919a200123da56b";
        } else if (finalEs.equals("RJ")) {
            estadop = "59f72e1b7919a200123da56c";
        } else if (finalEs.equals("RN")) {
            estadop = "59f72e207919a200123da56d";
        } else if (finalEs.equals("RS")) {
            estadop = "59f72e257919a200123da56e";
        } else if (finalEs.equals("RO")) {
            estadop = "59f72e2c7919a200123da56f";
        } else if (finalEs.equals("RR")) {
            estadop = "59f72e3c7919a200123da570";
        } else if (finalEs.equals("SC")) {
            estadop = "59f72e427919a200123da571";
        } else if (finalEs.equals("SP")) {
            estadop = "59f72e4b7919a200123da572";
        } else if (finalEs.equals("SE")) {
            estadop = "59f72e557919a200123da573";
        } else if (finalEs.equals("TO")) {
            estadop = "59f72e5b7919a200123da574";
        } else {
            estadop = "59f741517919a200123da575";
        }

        new CallAPI().execute();
    }

    /*
    ---------POST---------
     */

    private class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params){


            String input = "{\"name\":" + estadoPut + ",\"population\":\"" + populationPut + "\",\"industrias\":\"" + industriasPut + "\"}";

            InputStream inputStream = null;

            BufferedWriter bufferedWriter = null;
            String result = null;

            try {
                try {
                    try {
                        //Create data to update
                        JSONObject dataToSend = new JSONObject();
                        dataToSend.put("name", estadoPut);
                        //dataToSend.put("population", populationPut);
                        //dataToSend.put("industrias", industriasPut);

                        //Initialize and config request, then connect to server
                        URL url = new URL("https://warm-ridge-33561.herokuapp.com/users/" + estadop);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setReadTimeout(10000 /* milliseconds */);
                        urlConnection.setConnectTimeout(10000 /* milliseconds */);
                        urlConnection.setRequestMethod("PUT");
                        urlConnection.setDoOutput(true);  //enable output (body data)
                        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// set header
                        urlConnection.connect();

                        //Write data into server
                        OutputStream outputStream = urlConnection.getOutputStream();
                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                        bufferedWriter.write("name,Acri");
                        //bufferedWriter.write(dataToSend.toString());
                        bufferedWriter.flush();

                        //Check update successful or not
                        if (urlConnection.getResponseCode() == 200) {
                            return "Update successfully !";
                        } else {
                            return "Update failed !";
                        }
                    } finally {
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                    }
                } catch (JSONException e) {

                }
            }catch(java.io.IOException e){

            }

            return null;

        }
    }}


//            try {
//                URL url = new URL("https://warm-ridge-33561.herokuapp.com/users/" + estadop);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setDoOutput(true);
//                conn.setRequestMethod("PUT");
//                conn.setRequestProperty("Content-Type", "application/json");
//
//                //String input = "{\"name\":" + estadoPut + ",\"population\":\"" + populationPut + "\",\"industrias\":\"" + industriasPut + "\"}";
//
//                OutputStream os = conn.getOutputStream();
//                os.write(input.getBytes("UTF-8"));
//                os.flush();
//
//                if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//                    //throw new RuntimeException("Failed : HTTP error code : "
//                    //        + conn.getResponseCode());
//                }
//
//                BufferedReader br = new BufferedReader(new InputStreamReader(
//                        (conn.getInputStream())));
//
//                String output;
//                System.out.println("Output from Server .... \n");
//                while ((output = br.readLine()) != null) {
//                    System.out.println(output);
//                }
//
//                conn.disconnect();
//
//            } catch (MalformedURLException e) {
//
//                e.printStackTrace();
//
//            } catch (IOException e) {
//
//                e.printStackTrace();
//
//            }
//
//            return null;
//
//      }
