package com.ayannah.asira.screen.chooselogin;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.ayannah.asira.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class ChooseLoginActivity extends DaggerAppCompatActivity implements ChooseLoginContract.View {

    @Inject
    ChooseLoginContract.Presenter mPresenter;

    @BindView(R.id.btnPersonal)
    TextView btnPersonal;

    @BindView(R.id.btnAgent)
    TextView btnAgent;

    private Unbinder mUnbinder;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_login);
        mUnbinder = ButterKnife.bind(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
