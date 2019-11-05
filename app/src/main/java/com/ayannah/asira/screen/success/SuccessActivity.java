package com.ayannah.asira.screen.success;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.borrower.homemenu.MainMenuActivity;
import com.ayannah.asira.screen.borrower.login.LoginActivity;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class SuccessActivity extends DaggerAppCompatActivity {

    public static final String SUCCESS_TITLE = "title";
    public static final String SUCCESS_DESC = "desc";
    public static final String SUCCESS_COND = "cond";


    @Inject
    @Named("title")
    String title;

    @Inject
    @Named("desc")
    String description;

    @Inject
    @Named("cond")
    int cond;

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.desc)
    TextView tvDesc;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        mUnbinder = ButterKnife.bind(this);

        tvTitle.setText(title);

        tvDesc.setText(description);


    }

    @OnClick(R.id.btnSelesai)
    void onClickButton(){

        if(cond == 1){

            Intent main = new Intent(this, LoginActivity.class);
            main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main);
            finish();

        }else {

            Intent main = new Intent(this, MainMenuActivity.class);
            main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main);
            finish();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUnbinder.unbind();
    }
}
