package com.ayannah.asira.screen.borrower.tab_beranda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.adapter.MenuServiceAdapter;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.custom.GridSpacingItemDecoration;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.dialog.BottomSheetDialogGlobal;
import com.ayannah.asira.screen.chooselogin.ChooseLoginActivity;
import com.ayannah.asira.screen.earninginfo.EarningActivity;
import com.ayannah.asira.R;
import com.ayannah.asira.adapter.BeritaPromoAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.BeritaPromo;
import com.ayannah.asira.workmanager.RxNotifLoanWorker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class MainMenuFragment extends BaseFragment implements MainMenuContract.View{

    private String ACTIVE = "active";

    @Inject
    MainMenuContract.Presenter mPresenter;

    @BindView(R.id.recycler_menuproducts)
    RecyclerView recycler_menuproducts;

    @BindView(R.id.rvBeritaPromo)
    RecyclerView recyclerViewBeritaPromo;

    @BindView(R.id.rvTopup)
    RecyclerView recyclerViewTopupTagihan;

    @Inject
    BeritaPromoAdapter mAdapterNewsPromo;

    @Inject
    @Named("topuptagihan")
    CommonListAdapter mAdapterTopUpTagihan;

    @Inject
    MenuServiceAdapter mAdapterMenu;

    private boolean isLoanReqAvail = false;

    private AlertDialog dialog;

    private WorkManager mWorkManager;

    private final Date[] currentTime = {new Date()};

    //untuk check setiap status loan PNS yang masih processing
    private String statusLoan = "";
    private BottomSheetDialogGlobal bottomSheetDialogGlobal;

    @Inject
    public MainMenuFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_main_menu;
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
        statusLoan = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        dialog.show();
        mPresenter.getCurrentTime();

        mPresenter.loadPromoAndNews();

        mPresenter.fetchTopupTagihan();
    }

    @Override
    protected void initView(Bundle state) {

        mWorkManager = WorkManager.getInstance();

        bottomSheetDialogGlobal = new BottomSheetDialogGlobal();

        recycler_menuproducts.setLayoutManager(new GridLayoutManager(parentActivity(), 3));
        recycler_menuproducts.addItemDecoration(new GridSpacingItemDecoration(3, 50));
        recycler_menuproducts.setHasFixedSize(true);
        recycler_menuproducts.setAdapter(mAdapterMenu);

        recyclerViewBeritaPromo.setLayoutManager(new LinearLayoutManager(parentActivity(), RecyclerView.HORIZONTAL, false));
        recyclerViewBeritaPromo.setHasFixedSize(true);
        recyclerViewBeritaPromo.setAdapter(mAdapterNewsPromo);

        recyclerViewTopupTagihan.setLayoutManager(new LinearLayoutManager(parentActivity(), RecyclerView.HORIZONTAL, false));
        recyclerViewTopupTagihan.setHasFixedSize(true);
        recyclerViewTopupTagihan.setAdapter(mAdapterTopUpTagihan);

    }

    private void scheduleLoan(String name, String userToken){

        //sendData ro workmanager when run
        Data data = new Data.Builder()
                .putString("userToken", userToken)
                .putString("name", name)
                .build();

        PeriodicWorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(RxNotifLoanWorker.class, 1, TimeUnit.HOURS)
                        .setInputData(data)
                        .build();

        mWorkManager.enqueue(saveRequest);

        mWorkManager.getWorkInfoByIdLiveData(saveRequest.getId()).observe(parentActivity(), new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {

                if(workInfo != null){

                    WorkInfo.State state = workInfo.getState();
                    Log.e("statusWorkManager", state.toString());
                }
            }
        });

    }

    @Override
    public void showErrorMessage(String message) {

        dialog.dismiss();

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
    public void loadAllServiceMenu(List<BankService.Data> results) {

        List<BankService.Data> menus = new ArrayList<>();

        for(BankService.Data x:results) {
            if (x.getStatus().equals(ACTIVE)) {
                menus.add(x);

            }
        }

        mAdapterMenu.setMenuService(menus);

        mAdapterMenu.setOnClickListener(menuProduct -> {

            if (statusLoan.equals("processing")) {

                bottomSheetDialogGlobal = new BottomSheetDialogGlobal().show(parentActivity().getSupportFragmentManager(),
                        BottomSheetDialogGlobal.FORBIDDEN_LOAN_PNS,
                        "Pengajuan Pinjaman Terakhir Sedang Proses",
                        "Kamu belum bisa mengajukan peminjaman hingga pengajuan sebelumnya telah selesai dari proses.",
                        R.drawable.img_processing);
                bottomSheetDialogGlobal.setOnClickBottomSheetInstruction(new BottomSheetDialogGlobal.BottomSheetInstructionListener() {
                    @Override
                    public void onClickButtonDismiss() {

                        bottomSheetDialogGlobal.dismiss();

//                        Intent intent = new Intent(parentActivity(), HistoryLoanActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
                    }

                    @Override
                    public void onClickButtonYes() {
                        //nothing to do

                    }

                    @Override
                    public void closeApps() {
                        //nothing to do

                    }
                });
            } else {

                Intent intent = new Intent(parentActivity(), EarningActivity.class);
                intent.putExtra("isFrom", "borrower");
                intent.putExtra("id", menuProduct.getId());
                intent.putExtra("name", menuProduct.getName());
                intent.putExtra(EarningActivity.ID_SERVICE, String.valueOf(menuProduct.getId()));

                startActivity(intent);
            }

        });

        dialog.dismiss();
    }

    @Override
    public void showPromoAndNews(List<BeritaPromo> results) {

        mAdapterNewsPromo.setBeritaNews(results);
    }

    @Override
    public void showDataLoan(List<DataItem> items) {

        for(DataItem data: items){

            /*
            untuk triggered bottom sheet fragment jika user sebelumnya sudah mengajukan peminjaman
            karena user hanya boleh mengajukan peminjaman 1x
            */
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                Date dueDate = sdf.parse(data.getDisburseDate());
                Calendar dueDateCalendar = Calendar.getInstance(TimeZone.getDefault());
                dueDateCalendar.setTime(dueDate);
                dueDateCalendar.add(Calendar.MONTH, data.getInstallment());
                dueDateCalendar.add(Calendar.DATE, 1);
                dueDate = dueDateCalendar.getTime();

                Date finalDueDate = dueDate;
                if (currentTime[0].before(finalDueDate) || data.getStatus().toLowerCase().equals("processing") || !data.getDisburseStatus().equals("confirmed")) {
                    if (!data.getStatus().toLowerCase().equals("rejected")) {
                        statusLoan = "processing";
                        isLoanReqAvail = true;
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        mPresenter.getMainMenu();

    }

    @Override
    public void successGetPublicTokenLender() {
        mPresenter.getTokenAdminLender();
    }

    @Override
    public void successGetCurrentTime(String time) {
        SimpleDateFormat sdfCurrent = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            if (time != null) {
                currentTime[0] = sdfCurrent.parse(time);
            } else {
                currentTime[0] = Calendar.getInstance().getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            currentTime[0] = Calendar.getInstance().getTime();
        }

        mPresenter.notifLoanRequest();
    }

//    @Override
//    public void showUserData(String name, String token) {
//
//        mAdapterTopUpTagihan.setMenuTopupTagihan(results);
//        mAdapterTopUpTagihan.setOnClickMenuTopUpTaguhan(new CommonListListener.CommonStringItemClickListener() {
//            @Override
//            public void onClickItem(String item) {
//                Toast.makeText(parentActivity(), item, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void showUserData(String name, String token) {

        scheduleLoan(name, token);
    }

    public void showTopUpTagihanMenu(List<String> results) {

        mAdapterTopUpTagihan.setMenuTopupTagihan(results);
        mAdapterTopUpTagihan.setOnClickMenuTopUpTaguhan(new CommonListListener.CommonStringItemClickListener() {
            @Override
            public void onClickItem(String item) {
                Toast.makeText(parentActivity(), item, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
