package com.ayannah.asira.screen.agent.lpagent;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LPAgentFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    LPAgentContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Inject
    public LPAgentFragment(){}

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_lp_agent;
    }

    @Override
    protected void initView(Bundle state) {

        parentActivity().setSupportActionBar(toolbar);

        ActionBar actionBar = parentActivity().getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                parentActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

//        if (id == R.id.nav_personalInfo) {
//
//            Intent perInfo = new Intent(parentActivity(), InfoPribadiActivity.class);
//            startActivity(perInfo);
//
//        } else if (id == R.id.nav_infoPekerjaan) {
//
//            Intent keuangan = new Intent(parentActivity(), InformasiKeuanganActivity.class);
//            startActivity(keuangan);
//
//        } else if(id == R.id.nav_datapendukung){
//
//            Intent datapendukung = new Intent(parentActivity(), DataPendukungActivity.class);
//            startActivity(datapendukung);
//
//        }
//        else if (id == R.id.nav_akun_saya){
//
//            Intent akusaya  = new Intent(parentActivity(), AkunSayaActivity.class);
//            startActivity(akusaya);
//        } else if(id == R.id.nav_pinjaman_saya){
//
//            Intent pinjamansaya = new Intent(parentActivity(), HistoryLoanActivity.class);
//            startActivity(pinjamansaya);
//        }

        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    @OnClick(R.id.notification)
    void onClickNotif(){

    }

    @OnClick(R.id.nasabahBaru)
    void onClickNasabahBaru(){


    }

    @OnClick(R.id.nasabahTerdaftar)
    void onClickNasabahTerdaftar(){


    }
}
