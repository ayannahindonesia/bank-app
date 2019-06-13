package com.ayannah.bantenbank.screen.otpphone;

import android.content.Intent;
import android.os.Bundle;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.custom.PinEntryEditText;
import com.ayannah.bantenbank.dialog.DialogSuccess;
import com.ayannah.bantenbank.screen.homemenu.MainMenuActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationOTPActivity extends AppCompatActivity {

    final static String REGIST = "regist";
    final static String PINJAMAN = "pinjaman";
    
    String confirmPin = "181818";

    @BindView(R.id.secretDummyCode)
    TextView secretDummyCode;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    
    @BindView(R.id.etPin)
    PinEntryEditText etPin;
    
    @BindView(R.id.loadingProgress)
    LinearLayout pgLoading;

    @BindView(R.id.successIndicator)
    LinearLayout successIndicator;

    @BindView(R.id.errorIndicator)
    LinearLayout errorIndicator;

    String purpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_otp);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        purpose = getIntent().getStringExtra("purpose");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Verifikasi");
        
        etPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() == 6){

                    pinAccepted(etPin.getText().toString());
                    
                }
            }
        });
    }

    private void pinAccepted(String pin) {

        errorIndicator.setVisibility(View.GONE);
        pgLoading.setVisibility(View.VISIBLE);

        CountDownTimer countDownTimer = new CountDownTimer(1000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                
            }

            @Override
            public void onFinish() {

                pgLoading.setVisibility(View.GONE);

                if(pin.equals(confirmPin)){

                    successIndicator.setVisibility(View.VISIBLE);

                    switch (purpose){

                        case REGIST:

                            DialogSuccess dialogSuccess = new DialogSuccess();
                            dialogSuccess.setTitleDialog("Pendaftaran Berhasil");
                            dialogSuccess.showNow(getSupportFragmentManager(), "TAG");
                            dialogSuccess.setOnClick(() -> {
                                dialogSuccess.dismiss();


                                Intent home = new Intent(VerificationOTPActivity.this, MainMenuActivity.class);
                                startActivity(home);
                                finish();
                                Toast.makeText(VerificationOTPActivity.this, "Selamat datang di Kaya Credit", Toast.LENGTH_SHORT).show();

                            });
                            break;

                        case PINJAMAN:

                            DialogSuccess  dialogSuccess2 = new DialogSuccess();
                            dialogSuccess2.setTitleDialog("Aplikasi anda sedang diproses. Silakan cek menu Pinjaman Saya untuk melihat status pinjaman.");
                            dialogSuccess2.showNow(getSupportFragmentManager(), "Success");
                            dialogSuccess2.setOnClick(() -> {
                                dialogSuccess2.dismiss();

                                Intent pinjaman = new Intent(VerificationOTPActivity.this, MainMenuActivity.class);
                                pinjaman.putExtra("purpose", "continuePinjam");
                                startActivity(pinjaman);
                                finish();
                            });

                            break;
                    }

                }else {
                    errorIndicator.setVisibility(View.VISIBLE);
                    etPin.setText("");
                }


                
            }
        };
        countDownTimer.start();
        
    }

    @OnClick(R.id.titleVerification)
    void onClickTitle(){
        secretDummyCode.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
