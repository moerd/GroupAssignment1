package edu.cs.birzeit.app1group;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinl;
    private EditText txtf;
    private EditText txtl;
    private EditText txte;
    private EditText txtp;
    private EditText txtpa;
    private EditText txtd;
    RadioButton ragf;
    RadioButton ragm;
    RadioGroup rgroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        populateSpinner();
        ragm.setChecked(true);
    }
    private void setup() {
        txtf =findViewById(R.id.txtf);
        txtl =findViewById(R.id.txtl);
        txte =findViewById(R.id.txte);
        txtp =findViewById(R.id.txtp);
        txtpa =findViewById(R.id.txtpa);
        spinl =(Spinner) findViewById(R.id.spinl);
        txtd=findViewById(R.id.txtd);
        rgroup =findViewById(R.id.rgroup);
        ragf =findViewById(R.id.ragf);
        ragm =findViewById(R.id.ragm);
    }
    private void populateSpinner() {
        ArrayList<String> data=new ArrayList<>();
        data.add("Married");
        data.add("Single");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,data);
        spinl.setAdapter(adapter);
    }


    public boolean validate() {
        String first = txtf.getText().toString();
        String last = txtl.getText().toString();
        String email = txte.getText().toString();
        String phone = txtp.getText().toString();
        String address = txtpa.getText().toString();
        String date = txtd.getText().toString();
        if (first.isEmpty()) {
            txtf.setError("Field can not be empty");
            return false;
        }
        if (last.isEmpty()) {
            txtl.setError("Field can not be empty");
            return false;
        }
        if (email.isEmpty()) {
            txte.setError("Field can not be empty");
            return false;
        }
        if (phone.isEmpty()) {
            txtp.setError("Field can not be empty");
            return false;
        }
        if (address.isEmpty()) {
            txtpa.setError("Field can not be empty");
            return false;
        }
        if (date.isEmpty()) {
            txtd.setError("Field can not be empty");
            return false;
        }
        return true;
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String first = txtf.getText().toString();
        String last = txtl.getText().toString();
        String email = txte.getText().toString();
        String phone = txtp.getText().toString();
        String date = txtd.getText().toString();
        String address = txtpa.getText().toString();
        int status = spinl.getSelectedItemPosition();

        String data = URLEncoder.encode("first", "UTF-8")
                + "=" + URLEncoder.encode(first, "UTF-8");

        data += "&" + URLEncoder.encode("last", "UTF-8") + "="
                + URLEncoder.encode(last, "UTF-8");

        data += "&" + URLEncoder.encode("email", "UTF-8")
                + "=" + URLEncoder.encode(email, "UTF-8");
        data += "&" + URLEncoder.encode("phone", "UTF-8") + "="
                + URLEncoder.encode(phone, "UTF-8");

        data += "&" + URLEncoder.encode("date", "UTF-8")
                + "=" + URLEncoder.encode(date, "UTF-8");
        data += "&" + URLEncoder.encode("address", "UTF-8") + "="
                + URLEncoder.encode(address, "UTF-8");

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

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddOnClick(View view) {

        String url = "http://192.168.110.1/app/addStudent.php";
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
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }
    }
    public void btnSearchOnClick(View view) {
        Intent intent = new Intent(this, MainActivity5.class);
        startActivity(intent);
    }
}
