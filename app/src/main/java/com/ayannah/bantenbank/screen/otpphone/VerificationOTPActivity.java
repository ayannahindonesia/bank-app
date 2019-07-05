package com.ayannah.bantenbank.screen.otpphone;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.BuildConfig;
import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.custom.PinEntryEditText;
import com.ayannah.bantenbank.data.local.PreferenceDataSource;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.ayannah.bantenbank.dialog.DialogSuccess;
import com.ayannah.bantenbank.screen.homemenu.MainMenuActivity;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherContract;
import com.ayannah.bantenbank.util.ActivityUtils;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

public class VerificationOTPActivity extends DaggerAppCompatActivity {

    private Unbinder mUnbinder;
    public String phoneRegister = "";

    @Inject
    VerificationOTPFragment mFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Verifikasi");

        VerificationOTPFragment verificationOTPFragment = (VerificationOTPFragment) getSupportFragmentManager()
        .findFragmentById(R.id.fragment_container);
        if(verificationOTPFragment == null){
            verificationOTPFragment = mFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), verificationOTPFragment, R.id.fragment_container);
        }


    }

//    private void pinAccepted(String pin) {
//
//        errorIndicator.setVisibility(View.GONE);
//        pgLoading.setVisibility(View.VISIBLE);
//
//        CountDownTimer countDownTimer = new CountDownTimer(1000, 3000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//                pgLoading.setVisibility(View.GONE);
//
//                if(pin.equals(confirmPin)){
//
//                    successIndicator.setVisibility(View.VISIBLE);
//
//                    switch (purpose){
//
//                        case REGIST:
//
//                            DialogSuccess dialogSuccess = new DialogSuccess();
//                            dialogSuccess.setTitleDialog("Pendaftaran Berhasil");
//                            dialogSuccess.showNow(getSupportFragmentManager(), "TAG");
//                            dialogSuccess.setOnClick(() -> {
//                                dialogSuccess.dismiss();
//
//
//                                Intent home = new Intent(VerificationOTPActivity.this, MainMenuActivity.class);
//                                startActivity(home);
//                                finish();
//                                Toast.makeText(VerificationOTPActivity.this, "Selamat datang di Kaya Credit", Toast.LENGTH_SHORT).show();
//
//                            });
//                            break;
//
//                        case PINJAMAN:
//
//                            DialogSuccess  dialogSuccess2 = new DialogSuccess();
//                            dialogSuccess2.setTitleDialog("Aplikasi anda sedang diproses. Silakan cek menu Pinjaman Saya untuk melihat status pinjaman.");
//                            dialogSuccess2.showNow(getSupportFragmentManager(), "Success");
//                            dialogSuccess2.setOnClick(() -> {
//                                dialogSuccess2.dismiss();
//
//                                Intent pinjaman = new Intent(VerificationOTPActivity.this, MainMenuActivity.class);
//                                pinjaman.putExtra("purpose", "continuePinjam");
//                                startActivity(pinjaman);
//                                finish();
//                            });
//
//                            break;
//                    }
//
//                }else {
//                    errorIndicator.setVisibility(View.VISIBLE);
//                    etPin.setText("");
//                }
//
//
//
//            }
//        };
//        countDownTimer.start();
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
