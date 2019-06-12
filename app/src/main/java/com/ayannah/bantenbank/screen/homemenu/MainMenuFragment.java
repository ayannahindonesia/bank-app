package com.ayannah.bantenbank.screen.homemenu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.bantenbank.screen.earninginfo.EarningActivity;
import com.ayannah.bantenbank.screen.historyloan.HistoryLoanActivity;
import com.ayannah.bantenbank.screen.navigationmenu.datapendukung.DataPendukungActivity;
import com.ayannah.bantenbank.screen.detailloan.DetailTransaksiActivity;
import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.adapter.BeritaPromoAdapter;
import com.ayannah.bantenbank.adapter.LoanAdapter;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.model.BeritaPromo;
import com.ayannah.bantenbank.data.model.Loans;
import com.ayannah.bantenbank.dialog.BottomSheetDialogLogout;
import com.ayannah.bantenbank.screen.login.LoginActivity;
import com.ayannah.bantenbank.screen.navigationmenu.akunsaya.AkunSayaActivity;
import com.ayannah.bantenbank.screen.navigationmenu.infopribadi.InfoPribadiActivity;
import com.ayannah.bantenbank.screen.navigationmenu.infokeuangan.InformasiKeuanganActivity;
import com.ayannah.bantenbank.util.CommonUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainMenuFragment extends BaseFragment implements MainMenuContract.View, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    MainMenuContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    @BindView(R.id.recyclerViewPinjaman)
//    RecyclerView recyclerViewPinjaman;

    @BindView(R.id.rvBeritaPromo)
    RecyclerView recyclerViewBeritaPromo;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Inject
    LoanAdapter mAdapter;

    @Inject
    BeritaPromoAdapter mAdapterNewsPromo;

    private TextView pinjamanSaya;

    @Inject
    public MainMenuFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_mainmenu;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.loadPromoAndNews();
//        mPresenter.loadLoanhistory();

    }

    @Override
    protected void initView(Bundle state) {

        parentActivity().setSupportActionBar(toolbar);

        ActionBar actionBar = parentActivity().getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.kaya_credit_logo);

        pinjamanSaya = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_pinjaman_saya));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(65, 65) ;
        params.setMargins(0, 30, 0,0);

        pinjamanSaya.setLayoutParams(params);
        pinjamanSaya.setGravity(Gravity.CENTER);
        pinjamanSaya.setTypeface(null, Typeface.BOLD);
        pinjamanSaya.setTextColor(Color.WHITE);
        pinjamanSaya.setBackgroundResource(R.drawable.badge_navigation_menu_bg);
        pinjamanSaya.setText("5");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                parentActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        recyclerViewPinjaman.setLayoutManager(new LinearLayoutManager(parentActivity()));
//        recyclerViewPinjaman.setHasFixedSize(true);
//        recyclerViewPinjaman.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
//        recyclerViewPinjaman.setAdapter(mAdapter);

        recyclerViewBeritaPromo.setLayoutManager(new LinearLayoutManager(parentActivity(), RecyclerView.HORIZONTAL, false));
        recyclerViewBeritaPromo.setHasFixedSize(true);
        recyclerViewBeritaPromo.setAdapter(mAdapterNewsPromo);

    }

    @OnClick(R.id.pinjaman)
    void onClickMenu(){

        Intent intent = new Intent(parentActivity(), EarningActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        parentActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showErrorMessage(String message) {
        CommonUtils.showToast(message, parentActivity());
    }

    @Override
    public void showMainMenu() {

    }

    @Override
    public void showPromoAndNews(List<BeritaPromo> results) {

        mAdapterNewsPromo.setBeritaNews(results);
    }

    @Override
    public void showLoandHistory(List<Loans> results) {

//        mAdapter.setLoanData(results);
//        mAdapter.setLoanListener(new LoanAdapter.LoansAdapterListener() {
//            @Override
//            public void onClickItem(Loans loans) {
//                Intent intent = new Intent(parentActivity(), DetailTransaksiActivity.class);
//                startActivity(intent);
//            }
//        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_personalInfo) {

            Intent perInfo = new Intent(parentActivity(), InfoPribadiActivity.class);
            startActivity(perInfo);

        } else if (id == R.id.nav_infoPekerjaan) {

            Intent keuangan = new Intent(parentActivity(), InformasiKeuanganActivity.class);
            startActivity(keuangan);

        } else if(id == R.id.nav_datapendukung){

            Intent datapendukung = new Intent(parentActivity(), DataPendukungActivity.class);
            startActivity(datapendukung);

        } else if (id == R.id.nav_logout) {

            BottomSheetDialogLogout logout = new BottomSheetDialogLogout();
            logout.showNow(parentActivity().getSupportFragmentManager(), "TAG");
            logout.setOnClickListener(new BottomSheetDialogLogout.BottomSheetDialofLogoutListener() {
                @Override
                public void onClickYes() {
                    logout.dismiss();
                    Intent logout = new Intent(parentActivity(), LoginActivity.class);
                    startActivity(logout);
                    parentActivity().finish();
                }

                @Override
                public void onClickNo() {

                    logout.dismiss();
                }
            });

        } else if (id == R.id.nav_akun_saya){

            Intent akusaya  = new Intent(parentActivity(), AkunSayaActivity.class);
            startActivity(akusaya);
        } else if(id == R.id.nav_pinjaman_saya){

            Intent pinjamansaya = new Intent(parentActivity(), HistoryLoanActivity.class);
            startActivity(pinjamansaya);
        }

        drawer.closeDrawer(GravityCompat.START);

        return false;
    }
}
