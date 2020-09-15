package com.ayannah.asira.screen.borrower.termscondition;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ayannah.asira.R;
import com.ayannah.asira.dialog.BottomInputPassword;

public class TermsAndConditionActivity extends AppCompatActivity {

    CheckBox tncCB;
    Button btnTnc;
//    WebView tncWV;
//    ================
    TextView btnDeleteAcc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition);

        btnDeleteAcc = (TextView) findViewById(R.id.btnDeleteAcc);
        btnDeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomInputPassword dialog = new BottomInputPassword();
                dialog.showNow(getSupportFragmentManager(), "BottomDialogShow");
            }
        });

//        ===================================================================================

        tncCB = (CheckBox) findViewById(R.id.tncCB);
        btnTnc = (Button) findViewById(R.id.btnTNC);
//        tncWV = (WebView) findViewById(R.id.tncWV);

//        WebSettings webSettings = tncWV.getSettings();
//        tncWV.getSettings().setLoadWithOverviewMode(true);
//        tncWV.getSettings().setUseWideViewPort(true);
//        tncWV.getSettings().setBuiltInZoomControls(true);
//        tncWV.getSettings().setPluginState(WebSettings.PluginState.ON);
//
//
//        tncWV.setWebViewClient(new myWebClient());
//
//        tncWV.loadUrl("https://ayannah.co.id/");

        tncCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tncCB.isChecked()) {
                    btnTnc.setEnabled(true);
                } else {
                    btnTnc.setEnabled(false);
                }
            }
        });

//        ========================================================



    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            Log.d("page", "started");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }
}
