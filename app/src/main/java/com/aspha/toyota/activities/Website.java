package com.aspha.toyota.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aspha.toyota.R;

public class Website extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_website );
        getSupportActionBar().setTitle ( "Website" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = findViewById ( R.id.webview );
        final String url = "http://www.toyota.co.zw";
        webView.setWebViewClient ( new WebViewClient () {
            ProgressDialog progressDialog;
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url) {
                view.loadUrl ( url );
                return true;
            }

            @Override
            public void onLoadResource (WebView view, String url) {
                if ( progressDialog == null ) {
                    progressDialog = new ProgressDialog ( Website.this );
                    progressDialog.setMessage ( "Fetching Data..." );
                    progressDialog.show ();
                }
            }
            @Override
            public void onPageFinished (WebView view, String url) {
                try {
                    if ( progressDialog.isShowing () ) {
                        progressDialog.dismiss ();
                        progressDialog = null;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace ();
                }
            }

        } );

// Javascript inabled on webview
        webView.getSettings ().setJavaScriptEnabled ( true );

        // Other webview options

        webView.getSettings ().setLoadWithOverviewMode ( true );
        webView.getSettings ().setUseWideViewPort ( true );
        webView.setScrollBarStyle ( WebView.SCROLLBARS_OUTSIDE_OVERLAY );
        webView.setScrollbarFadingEnabled ( false );
        webView.getSettings ().setBuiltInZoomControls ( true );
        webView.loadUrl ( url );


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
