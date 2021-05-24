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

public class MainActivity3 extends AppCompatActivity {
    private EditText txtco;
    private EditText txtma;
    private EditText txtpy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setup();
    }

    private void setup() {
        txtco =findViewById(R.id.txtco);
        txtma =findViewById(R.id.txtma);
        txtpy =findViewById(R.id.txtpy);
    }



    public boolean validate() {
        String whyCollege = txtco.getText().toString();
        String whyMajor = txtma.getText().toString();
        String payment = txtpy.getText().toString();
        if (whyCollege.isEmpty()) {
            txtco.setError("Field can not be empty");
            return false;
        }
        if (whyMajor.isEmpty()) {
            txtma.setError("Field can not be empty");
            return false;
        }
        if (payment.isEmpty()) {
            txtpy.setError("Field can not be empty");
            return false;
        }
        return true;
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String q1=txtco. getText(). toString();
        String q2=txtma. getText(). toString();
        String q3=txtpy. getText(). toString();

        String data = URLEncoder.encode("q1", "UTF-8")
                + "=" + URLEncoder.encode(q1, "UTF-8");

        data += "&" + URLEncoder.encode("q2", "UTF-8") + "="
                + URLEncoder.encode(q2, "UTF-8");

        data += "&" + URLEncoder.encode("q3", "UTF-8")
                + "=" + URLEncoder.encode(q3, "UTF-8");


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

            Toast.makeText(MainActivity3.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddOnClick(View view) {

        String url = "http://192.168.110.1/app/questions.php";
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
            Intent intent = new Intent(this, MainActivity5.class);
            startActivity(intent);
        }
    }
}