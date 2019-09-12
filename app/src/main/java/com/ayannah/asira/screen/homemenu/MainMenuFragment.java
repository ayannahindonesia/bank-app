package com.ayannah.asira.screen.homemenu;

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

import com.ayannah.asira.adapter.MenuProductAdapter;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.MenuProduct;
import com.ayannah.asira.dialog.BottomSheetDialogGlobal;
import com.ayannah.asira.screen.earninginfo.EarningActivity;
import com.ayannah.asira.screen.historyloan.HistoryLoanActivity;
import com.ayannah.asira.screen.navigationmenu.datapendukung.DataPendukungActivity;
import com.ayannah.asira.R;
import com.ayannah.asira.adapter.BeritaPromoAdapter;
import com.ayannah.asira.adapter.LoanAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.BeritaPromo;
import com.ayannah.asira.dialog.BottomSheetDialogLogout;
import com.ayannah.asira.screen.login.LoginActivity;
import com.ayannah.asira.screen.navigationmenu.akunsaya.AkunSayaActivity;
import com.ayannah.asira.screen.navigationmenu.infopribadi.InfoPribadiActivity;
import com.ayannah.asira.screen.navigationmenu.infokeuangan.InformasiKeuanganActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainMenuFragment extends BaseFragment implements MainMenuContract.View, NavigationView.OnNavigationItemSelectedListener{

    public static final String ACTIVE = "active";
    public static final String INACTIVE = "inactive";
    

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
    private BottomSheetDialogGlobal bottomSheetDialogGlobal;

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

        bottomSheetDialogGlobal = new BottomSheetDialogGlobal();

        ActionBar actionBar = parentActivity().getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        Resources r = getContext().getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());

        pinjamanSaya = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_pinjaman_saya));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT) ;
        params.setMargins(0,px,0,0);

        //to show notif in navigation menu
        pinjamanSaya.setLayoutParams(params);

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

//        mAdapterMenu.setMenuProducts(results);
//
//        mAdapterMenu.setOnClickListener(menuProduct -> {
//
//            switch (menuProduct.getLogoProduct()){
//
//                case R.drawable.ic_menu_pns:
//
//                    if(statusLoan.equals("processing")){
//
//                        BottomSheetDialogGlobal dialog = new BottomSheetDialogGlobal().show(parentActivity().getSupportFragmentManager(),
//                                BottomSheetDialogGlobal.FORBIDDEN_LOAN_PNS,
//                                "Pengajuan Pinjaman PNS Kamu Sedang Proses",
//                                "Kamu belum bisa mengajukan peminjaman PNS lagi hingga pengajuan sebelumnya telah selesai dari proses",
//                                R.drawable.img_processing);
//                    }else {
//
//                        Intent intent = new Intent(parentActivity(), EarningActivity.class);
//                        startActivity(intent);
//
//                    }
//
//                    break;
//
//                case R.drawable.ic_menu_personal:
//
//                    break;
//
//                case R.drawable.ic_menu_pensiunan:
//
//                    break;
//            }
//
//
//        });

    }

    @Override
    public void loadAllServiceMenu(List<BankService.Data> results) {

        List<BankService.Data> menus = new ArrayList<>();

        for(BankService.Data x:results) {
            if (x.getStatus().equals(ACTIVE)) {
                menus.add(x);
            }
        }

        mAdapterMenu.setMenuService(menus);

        mAdapterMenu.setOnClickListener(new MenuProductAdapter.MenuProductListener() {
            @Override
            public void onClickMenu(BankService.Data menuProduct) {

                Intent intent = new Intent(parentActivity(), EarningActivity.class);
                intent.putExtra("id", menuProduct.getId());
                intent.putExtra("name", menuProduct.getName());
                startActivity(intent);

//                if (statusLoan.equals("processing")) {
//
//                    bottomSheetDialogGlobal = new BottomSheetDialogGlobal().show(parentActivity().getSupportFragmentManager(),
//                            BottomSheetDialogGlobal.FORBIDDEN_LOAN_PNS,
//                            "Pengajuan Pinjaman Terakhir Sedang Proses",
//                            "Kamu belum bisa mengajukan peminjaman hingga pengajuan sebelumnya telah selesai dari proses.",
//                            R.drawable.img_processing);
//                    bottomSheetDialogGlobal.setOnClickBottomSheetInstruction(new BottomSheetDialogGlobal.BottomSheetInstructionListener() {
//                        @Override
//                        public void onClickButtonDismiss() {
//
//                            bottomSheetDialogGlobal.dismiss();
//
//                            Intent intent = new Intent(parentActivity(), HistoryLoanActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        }
//
//                        @Override
//                        public void onClickButtonYes() {
//                            //nothing to do
//
//                        }
//
//                        @Override
//                        public void closeApps() {
//                            //nothing to do
//
//                        }
//                    });
//                }else {
//
//                    Intent intent = new Intent(parentActivity(), EarningActivity.class);
//                    intent.putExtra("id", menuProduct.getId());
//                    intent.putExtra("name", menuProduct.getName());
//                    startActivity(intent);
//                }

            }
        });
    }

    private void CallBottomSheetDialog(String reason) {
        if(statusLoan.equals("processing")){

            BottomSheetDialogGlobal dialog = new BottomSheetDialogGlobal().show(parentActivity().getSupportFragmentManager(),
                    BottomSheetDialogGlobal.FORBIDDEN_LOAN_PNS,
                    "Pengajuan Pinjaman Kamu Sedang Proses",
                    "Sementara kamu belum bisa mengajukan peminjaman " + reason + " hingga pengajuan sebelumnya telah selesai dari proses",
                    R.drawable.img_processing);
        }else {

            Intent intent = new Intent(parentActivity(), EarningActivity.class);
            startActivity(intent);

        }
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