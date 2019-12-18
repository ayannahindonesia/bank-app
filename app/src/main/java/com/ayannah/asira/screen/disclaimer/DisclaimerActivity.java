package com.ayannah.asira.screen.disclaimer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.otpphone.VerificationOTPActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisclaimerActivity extends AppCompatActivity {
    
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    
    @BindView(R.id.cbAgree)
    CheckBox cbAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Disclaimer");
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){
        
        if(cbAgree.isChecked()){
            Intent intent = new Intent(this, VerificationOTPActivity.class);
            intent.putExtra("purpose", "pinjaman");
            startActivity(intent);
        }else {
            Toast.makeText(this, "Kamu belum menyetujui disclaimer ini.", Toast.LENGTH_SHORT).show();
        }
        
    }
}
