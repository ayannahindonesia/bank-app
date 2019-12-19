package com.ayannah.asira.screen.register.termcondition;

import android.content.Intent;
import android.os.Bundle;

import com.ayannah.asira.screen.register.choosebank.ChooseBankActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ayannah.asira.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TermConditionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.checkBox)
    CheckBox checkBox;

    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.btn_confirm)
    Button btn_confirm;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.terms_condition);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }

            webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    btn_confirm.setBackgroundResource(R.drawable.button_register);
                }else {

                    btn_confirm.setBackgroundResource(R.drawable.button_register_disabled);
                }
            }
        });

    }

    @OnClick(R.id.btn_confirm)
    void onClickButton(){

        if(checkBox.isChecked()) {

            Intent regist = new Intent(this, ChooseBankActivity.class);
            startActivity(regist);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
