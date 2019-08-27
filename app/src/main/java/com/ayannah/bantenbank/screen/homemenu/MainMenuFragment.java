package com.ayannah.bantenbank.screen.homemenu;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.bantenbank.adapter.MenuProductAdapter;
import com.ayannah.bantenbank.data.model.Loans.DataItem;
import com.ayannah.bantenbank.data.model.Loans.Loans;
import com.ayannah.bantenbank.data.model.MenuProduct;
import com.ayannah.bantenbank.dialog.BottomSheetDialogGlobal;
import com.ayannah.bantenbank.screen.earninginfo.EarningActivity;
import com.ayannah.bantenbank.screen.historyloan.HistoryLoanActivity;
import com.ayannah.bantenbank.screen.navigationmenu.datapendukung.DataPendukungActivity;
import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.adapter.BeritaPromoAdapter;
import com.ayannah.bantenbank.adapter.LoanAdapter;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.model.BeritaPromo;
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

public class MainMenuFragment extends BaseFragment implements MainMenuContract.View, NavigationView.OnNavigationItemSelectedListener{

    @Inject
    MainMenuContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_menuproducts)
    RecyclerView recycler_menuproducts;

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

    @Inject
    MenuProductAdapter mAdapterMenu;

    private TextView pinjamanSaya;

    private boolean isLoanReqAvail = false;

    private AlertDialog dialog;

    //untuk check setiap status loan PNS yang masih processing
    private String statusLoan = "";

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

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        dialog.show();
        mPresenter.notifLoanRequest();

        mPresenter.getCurrentUserIdentity();

        mPresenter.loadPromoAndNews();

        mPresenter.getMainMenu();


    }

    @Override
    protected void initView(Bundle state) {

        parentActivity().setSupportActionBar(toolbar);

        ActionBar actionBar = parentActivity().getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        Resources r = getContext().getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());

        pinjamanSaya = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_pinjaman_saya));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT) ;
        params.setMargins(0,px,0,0);

        pinjamanSaya.setLayoutParams(params);
//        if(isLoanReqAvail) {
//            pinjamanSaya.setGravity(Gravity.CENTER);
//            pinjamanSaya.setTypeface(null, Typeface.BOLD);
//            pinjamanSaya.setTextColor(Color.WHITE);
//            pinjamanSaya.setBackgroundResource(R.drawable.badge_navigation_menu_bg);
//            pinjamanSaya.setText("!");
//        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                parentActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        recycler_menuproducts.setLayoutManager(new GridLayoutManager(parentActivity(), 3));
        recycler_menuproducts.setHasFixedSize(true);
        recycler_menuproducts.setAdapter(mAdapterMenu);

        recyclerViewBeritaPromo.setLayoutManager(new LinearLayoutManager(parentActivity(), RecyclerView.HORIZONTAL, false));
        recyclerViewBeritaPromo.setHasFixedSize(true);
        recyclerViewBeritaPromo.setAdapter(mAdapterNewsPromo);

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

            Toast.makeText(parentActivity(), "Settings", Toast.LENGTH_SHORT).show();

        }else{

            BottomSheetDialogLogout logout = new BottomSheetDialogLogout();
            logout.showNow(parentActivity().getSupportFragmentManager(), "TAG");
            logout.setOnClickListener(new BottomSheetDialogLogout.BottomSheetDialofLogoutListener() {
                @Override
                public void onClickYes() {
                    logout.dismiss();

                    mPresenter.logout();
                }

                @Override
                public void onClickNo() {

                    logout.dismiss();
                }
            });
            
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showErrorMessage(String message) {
        dialog.dismiss();
//        CommonUtils.showToast(message, parentActivity());

        BottomSheetDialogGlobal dialog = new BottomSheetDialogGlobal().show(parentActivity().getSupportFragmentManager(),
                BottomSheetDialogGlobal.MAINTENANCE,
                "Sedang Dalam Perbaikan",
                String.format("%s. Silakan buka beberapa saat lagi, karena sedang dalam perbaikan", message),
                R.drawable.img_processing);
        dialog.setOnClickBottomSheetInstruction(new BottomSheetDialogGlobal.BottomSheetInstructionListener() {
            @Override
            public void onClickButtonDismiss() {
                //dont do anything in here
            }

            @Override
            public void onClickButtonYes() {
                //dont do anything in here

            }

            @Override
            public void closeApps() {

                parentActivity().finish();
            }
        });
    }

    @Override
    public void showMainMenu(List<MenuProduct> results) {

        mAdapterMenu.setMenuProducts(results);

        mAdapterMenu.setOnClickListener(menuProduct -> {

            switch (menuProduct.getLogoProduct()){

                case R.drawable.ic_menu_pns:

                    if(statusLoan.equals("approved")){

                        BottomSheetDialogGlobal dialog = new BottomSheetDialogGlobal().show(parentActivity().getSupportFragmentManager(),
                                BottomSheetDialogGlobal.FORBIDDEN_LOAN_PNS,
                                "Pengajuan Pinjaman PNS Kamu Sedang Proses",
                                "Kamu belum bisa mengajukan peminjaman PNS lagi hingga pengajuan sebelumnya telah selesai dari proses",
                                R.drawable.img_processing);
                    }else {

                        Intent intent = new Intent(parentActivity(), EarningActivity.class);
                        startActivity(intent);

                    }

                    break;

                case R.drawable.ic_menu_personal:

                    break;

                case R.drawable.ic_menu_pensiunan:

                    break;
            }


        });

    }

    @Override
    public void showPromoAndNews(List<BeritaPromo> results) {

        mAdapterNewsPromo.setBeritaNews(results);
    }

    @Override
    public void showLogoutComplete() {

        Intent logout = new Intent(parentActivity(), LoginActivity.class);
        startActivity(logout);
        parentActivity().finish();
    }

    @Override
    public void showDataLoan(List<DataItem> items) {

        for(DataItem data: items){

            if(data.getStatus().toLowerCase().equals("processing") || data.getStatus().toLowerCase().equals("approved")){
                isLoanReqAvail = true;
            }

        }

        for (DataItem data:items){

            if(data.getStatus().toLowerCase().equals("processing")){
                statusLoan = data.getStatus().toLowerCase();
                break;
            }

        }

        if(isLoanReqAvail) {
            pinjamanSaya.setGravity(Gravity.CENTER);
            pinjamanSaya.setTypeface(null, Typeface.BOLD);
            pinjamanSaya.setTextColor(Color.WHITE);
            pinjamanSaya.setBackgroundResource(R.drawable.badge_navigation_menu_bg);
            pinjamanSaya.setText("!");
        }

    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    @Override
    public void displayUserIdentity(String name, String email) {

        View headerViewNav = navigationView.getHeaderView(0);
        TextView navName = headerViewNav.findViewById(R.id.navHeader_name);
        TextView navEmail = headerViewNav.findViewById(R.id.navHeader_email);

        navName.setText(name);
        navEmail.setText(email);

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

        }
        else if (id == R.id.nav_akun_saya){

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
;