package com.ayannah.asira.screen.borrower.borrower_landing_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.screen.borrower.login.LoginActivity;
import com.ayannah.asira.screen.borrower.tab_beranda.MainMenuFragment;
import com.ayannah.asira.screen.borrower.tab_historyloan.HistoryLoanFragment;
import com.ayannah.asira.screen.borrower.notifpage.NotifPageActivity;
import com.ayannah.asira.screen.borrower.tab_profile.ProfileFragment;
import com.ayannah.asira.screen.borrower.tab_message.MessageFragment;
import com.ayannah.asira.util.ActivityUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class BorrowerLandingFragment extends BaseFragment implements BorrowerLandingContract.View {

    @Inject
    BorrowerLandingContract.Presenter mPresenter;

    public static Toolbar toolbar;

    @BindView(R.id.bottomMenus)
    BottomNavigationView bottomNavigationView;

    @Inject
    public BorrowerLandingFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.base_fragment_landing_page;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

    }

    @Override
    protected void initView(Bundle state) {

        toolbar = (Toolbar) parentActivity().findViewById(R.id.toolbar);
        parentActivity().setSupportActionBar(toolbar);
        ActionBar actionBar = parentActivity().getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        MainMenuFragment beranda = new MainMenuFragment();
        HistoryLoanFragment pinjaman = new HistoryLoanFragment();
        MessageFragment rewards = new MessageFragment();
        ProfileFragment profile = new ProfileFragment();

        ActivityUtils.replaceFragmentToActivity(getFragmentManager(), beranda, R.id.tab_menus);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (mPresenter.getIsLogin()) {
                    switch (item.getItemId()){

                        case R.id.beranda:
                            ActivityUtils.replaceFragmentToActivity(getFragmentManager(), beranda, R.id.tab_menus);
                            return true;

                        case R.id.pinjaman:
                            ActivityUtils.replaceFragmentToActivity(getFragmentManager(), pinjaman, R.id.tab_menus);
                            return true;

                        case R.id.rewards:
                            ActivityUtils.replaceFragmentToActivity(getFragmentManager(), rewards, R.id.tab_menus);
                            return true;

                        case R.id.profile:
                            ActivityUtils.replaceFragmentToActivity(getFragmentManager(), profile, R.id.tab_menus);
                            return true;

                    }
                } else {
                    Intent intent = new Intent(parentActivity(), LoginActivity.class);
                    intent.putExtra("hasTop", "false");
                    startActivity(intent);
                }

                return false;
            }
        });

    }

    @OnClick(R.id.notification)
    void onClickNotifList(){

        Intent notif = new Intent(parentActivity(), NotifPageActivity.class);
        notif.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(notif);
    }

    @Override
    public void showErrorMessage(String message) {

    }
}
