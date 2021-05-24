package edu.cs.birzeit.app1group;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity2 extends AppCompatActivity {

    private EditText txtm1;
    private EditText txtm2;
    private EditText txtm3;
    private EditText txtt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setup();
    }

    private void setup() {

        txtm1 =findViewById(R.id.txtm1);
        txtm2 =findViewById(R.id.txtm2);
        txtm3 =findViewById(R.id.txtm3);
        txtt =findViewById(R.id.txtt);
    }


    public boolean validate() {
        String first = txtm1.getText().toString();
        String second = txtm2.getText().toString();
        String third = txtm3.getText().toString();
        String average = txtt.getText().toString();
        if (first.isEmpty()) {
            txtm1.setError("Field can not be empty");
            return false;
        }
        if (second.isEmpty()) {
            txtm2.setError("Field can not be empty");
            return false;
        }
        if (third.isEmpty()) {
            txtm3.setError("Field can not be empty");
            return false;
        }
        if (average.isEmpty()) {
            txtt.setError("Field can not be empty");
            return false;
        }
        return true;
    }
    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String major1=txtm1. getText(). toString();
        String major2=txtm2. getText(). toString();
        String major3=txtm3. getText(). toString();
        String average=txtt. getText(). toString();

        String data = URLEncoder.encode("major1", "UTF-8")
                + "=" + URLEncoder.encode(major1, "UTF-8");

        data += "&" + URLEncoder.encode("major2", "UTF-8") + "="
                + URLEncoder.encode(major2, "UTF-8");

        data += "&" + URLEncoder.encode("major3", "UTF-8")
                + "=" + URLEncoder.encode(major3, "UTF-8");
        data += "&" + URLEncoder.encode("average", "UTF-8") + "="
                + URLEncoder.encode(average, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        // Show response on activity
        return text;
    }

    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(MainActivity2.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddOnClick(View view) {

        String url = "http://192.168.110.1/app/addmajors.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            SendPostRequest runner = new SendPostRequest();
            runner.execute(url);
        }
        if(validate()==true) {
            Toast.makeText(this, "Your data has been saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
        }
    }


}