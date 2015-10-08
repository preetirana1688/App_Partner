package com.apppartner.androidprogrammertest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.models.CustomHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class LoginActivity extends ActionBarActivity {

    private EditText loginEditText;
    private EditText passEditText;

    private TextView responseTextView;
    String response = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passEditText = (EditText) findViewById(R.id.passEditText);
        responseTextView = (TextView) findViewById(R.id.resultTextView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    public void onLoginClick(View view) {

        String username = loginEditText.getText().toString();
        String password = passEditText.getText().toString();
        AuthUserPass authUserPass = new AuthUserPass();
        authUserPass.execute(username, password);
    }


    public class AuthUserPass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            long startTime = System.nanoTime();

            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("username", params[0]));
            postParameters.add(new BasicNameValuePair("password", params[1]));
            String res = null;

            try {

                String url = "http://dev.apppartner.com/AppPartnerProgrammerTest/scripts/login.php";

                response = CustomHttpClient.executeHttpPost(url, postParameters);

                res = response;

            } catch (Exception e) {
                e.printStackTrace();
            }

            long stopTime = System.nanoTime();
            long timePeriod = (stopTime - startTime);

            String apiTime = "API took : " + Long.toString(timePeriod) + " miliseconds" + "\n";


            return apiTime + "\n" + res;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result != null) {


                if (result.contains("Success")) {

                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Login Successful!")
                            .setMessage(result)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else {

                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Login Failed!")
                            .setMessage(result)
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }


            }
        }


    }


    public void onClickUsernameEditText(View view) {
        loginEditText.setText("");
    }

    public void onClickPassEditText(View view) {
        passEditText.setText("");
        passEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
}
