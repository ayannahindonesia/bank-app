package com.ayannah.asira.screen.agent.lpagent;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.screen.agent.registerborrower.choosebank.ChooseBankAgentActivity;
import com.ayannah.asira.screen.agent.selectbank.SelectBankActivity;
import com.ayannah.asira.screen.agent.tab_agent_profile.AgentProfileFragment;
import com.ayannah.asira.screen.agent.tab_beranda.BerandaFragment;
import com.ayannah.asira.screen.agent.tab_data_pinjaman.DataPinjamanFragment;
import com.ayannah.asira.screen.agent.viewBorrower.ViewBorrowerActivity;
import com.ayannah.asira.screen.borrower.login.LoginActivity;
import com.ayannah.asira.screen.chooselogin.ChooseLoginActivity;
import com.ayannah.asira.util.ActivityUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LPAgentFragment extends BaseFragment implements LPAgentContract.View {

    private String isFrom;
    @Inject
    LPAgentContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottomMenus)
    BottomNavigationView bottomNavigationView;

    @Inject
    public LPAgentFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

//        mPresenter.getCurrentAgentIdentity();
//
        mPresenter.getTokenLender();

        isFrom = parentActivity().getIntent().getStringExtra("isFrom");

        if (isFrom != null && !isFrom.equals("")) {
            if (isFrom.equals("agentProfile")) {
                parentActivity().getIntent().removeExtra("isFrom");
                isFrom = "";
                bottomNavigationView.setSelectedItemId(R.id.akun);
            }
        }

    }

    @Override
    protected int getLayoutView() {
        return R.layout.base_fragment_landing_page_agent;
    }

    @Override
    protected void initView(Bundle state) {

        parentActivity().setSupportActionBar(toolbar);
        ActionBar actionBar = parentActivity().getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        BerandaFragment beranda = new BerandaFragment();
        DataPinjamanFragment dataPinjaman = new DataPinjamanFragment();
        AgentProfileFragment agentProfil = new AgentProfileFragment();

        ActivityUtils.replaceFragmentToActivity(getFragmentManager(), beranda, R.id.tab_menus);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){

                case R.id.beranda:
                    ActivityUtils.replaceFragmentToActivity(getFragmentManager(), beranda, R.id.tab_menus);
                    return true;

                case R.id.dataPinjaman:
                    ActivityUtils.replaceFragmentToActivity(getFragmentManager(), dataPinjaman, R.id.tab_menus);
                    return true;

                case R.id.dataPencairan:
                    Toast.makeText(parentActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.akun:
                    AgentProfileFragment.bankSelectFromList = false;
                    ActivityUtils.replaceFragmentToActivity(getFragmentManager(), agentProfil, R.id.tab_menus);
                    return true;


            }

            return false;
        });

    }

    @OnClick(R.id.notification)
    void onClickNotif(){

        Toast.makeText(parentActivity(), "notif", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void showErrorMessage(String errorResponseWithStatusCode) {
        Toast.makeText(parentActivity(), errorResponseWithStatusCode, Toast.LENGTH_SHORT).show();
    }
}
