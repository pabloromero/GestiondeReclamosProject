package com.gdr.ldr.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdr.ldr.R;
import com.gdr.ldr.Utils;
import com.gdr.ldr.activities.modules.HomeActivity;
import com.gdr.ldr.activities.modules.webview.WebViewActivity;
import com.gdr.ldr.service.GPSTracker;


public class MainLoginActivity extends Activity {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;


    // Values for email and password at the time of the login attempt.
    private String mEmail;
    private String mPassword;


    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);


        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

        mEmailView = (EditText) findViewById(R.id.form_user);
        mPasswordView = (EditText) findViewById(R.id.form_password);

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                attemptLogin();
            }
        });


    }


    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (mPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //GPSTracker gps = new GPSTracker(MainLoginActivity.this);
            // check if GPS enabled
            //if(gps.canGetLocation()){
                //mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
                showProgress(true);
                mAuthTask = new UserLoginTask();
                mAuthTask.execute();
            //}else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
              //  gps.showSettingsAlert();
            //}



        }

        //Toast.makeText(MainLoginActivity.this, "MSG TESTTT...!", Toast.LENGTH_SHORT).show();
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private Intent homeActivity;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // TODO: La llamada al WS de login Simulate network access
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO temporal para simular el login
            return Utils.md5(mPassword).equals(Utils.md5(mEmail));
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                /*homeActivity = new Intent(getApplicationContext(), HomeActivity.class);*/
                homeActivity = new Intent(getApplicationContext(), WebViewActivity.class);
                startActivity(homeActivity);
                finish(); // termina la activity actual
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
           getMenuInflater().inflate(R.menu.main_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit_app:
                System.exit(0);
                finish();
                return true;
            default:
                return false;
        }

    }



    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        pDialog = new ProgressDialog(MainLoginActivity.this);
        pDialog.setTitle(getString(R.string.app_dialog_title));
        pDialog.setMessage(getString(R.string.app_dialog_mesagge_wait));
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);

        if (show) {
            pDialog.show();
        } else {
            pDialog.hide();
        }
    }


}
