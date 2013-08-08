package com.gdr.ldr.activities.modules.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gdr.ldr.R;
import com.gdr.ldr.activities.modules.Claim.ListClaimActivity;

import org.apache.http.util.EncodingUtils;

public class WebViewActivity extends Activity {
    private WebView browser;

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        reloadMpaClaim();

    }

    protected void reloadMpaClaim(){

        final ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {

            browser = (WebView)findViewById(R.id.webkit);
            //habilitamos javascript y el zoom
            browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            browser.getSettings().setJavaScriptEnabled(Boolean.TRUE);
            browser.getSettings().setBuiltInZoomControls(Boolean.TRUE);
            //TODO cambiar encriptando con el usuario logeado armando el passport conrrespondiente
            browser.loadUrl("http://local-reclamos.com/ldr?passport=PASSPORTGDRTEMPORALCONELUSUARIO");

            browser.setWebViewClient(new WebViewClient()
            {
                // evita que los enlaces se abran fuera nuestra app en el navegador de android
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url)
                {
                    return false;
                }

            });
            progressBar = (ProgressBar) findViewById(R.id.progressbar);
            browser.setWebChromeClient(new WebChromeClient()
            {
                @Override
                public void onProgressChanged(WebView view, int progress)
                {
                    progressBar.setProgress(0);
                    progressBar.setVisibility(View.VISIBLE);
                    WebViewActivity.this.setProgress(progress * 1000);
                    progressBar.incrementProgressBy(progress);
                    if(progress == 100)
                    {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }else{
            Toast.makeText(this, "No tiene internet", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_webview_return:
                finish();
                return true;
            case R.id.menu_webview_reload:
                reloadMpaClaim();
                return true;
            case R.id.menu_webview_listclaim:
                Intent i =  new Intent(this, ListClaimActivity.class);
                startActivity(i);
                return true;
            default:
                return false;
        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web_view, menu);
        return true;
    }
    
}
