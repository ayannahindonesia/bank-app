package com.ayannah.asira.screen.agent.viewBorrower;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.dialog.BottomDialogHandlingError;
import com.ayannah.asira.dialog.BottomErrorHandling;
import com.ayannah.asira.dialog.BottomSheetBorrowerAgent;
import com.ayannah.asira.screen.agent.services.ListServicesAgentActivity;
import com.ayannah.asira.screen.earninginfo.EarningFragment;
import com.ayannah.asira.screen.otpphone.VerificationOTPActivity;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ViewBorrowerFragment extends BaseFragment implements ViewBorrowerContract.View {

    private static final String TAG = ViewBorrowerFragment.class.getSimpleName();

    @Inject
    ViewBorrowerContract.Presenter mPresenter;

    @BindView(R.id.banks) Spinner spBanks;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @Inject
    CommonListAdapter adapter;

    @Inject
    public ViewBorrowerFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_view_borrower;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.retrieveBanks();
        mPresenter.getDataBorrower(null);

//        EarningFragment.isUpdate = false;
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String message, int code) {

        BottomErrorHandling error= new BottomErrorHandling(message, code);
        error.showNow(parentActivity().getSupportFragmentManager(), "error");
        error.setOnClickListener(new BottomErrorHandling.BottomSheetErrorListener() {
            @Override
            public void onClickClose(int code) {
                error.dismiss();
            }
        });
    }

    @Override
    public void getAllBank(List<BankDetail> results) {

        List<String> bankList = new ArrayList<>();
        bankList.add("Pilih bank...");

        for(BankDetail data: results){
            bankList.add(data.getName());
        }

        ArrayAdapter<String> adapterBankList = new ArrayAdapter<String>(parentActivity(), android.R.layout.simple_dropdown_item_1line, bankList){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    textView.setTextColor(Color.GRAY);
                }else {
                    textView.setTextColor(Color.BLACK);
                }

                return view;
            }
        };
        spBanks.setAdapter(adapterBankList);

        spBanks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0) {
                    Log.e(TAG, "selected: "+position);
                    Log.e(TAG, "bankId: "+results.get(position-1).getId());
                    String idBank = String.valueOf(results.get(position-1).getId());
                    mPresenter.getDataBorrower(idBank);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void getAllData(List<UserBorrower> results) {

        adapter.setAgentsBorrowerList(results, CommonListAdapter.VIEW_ALL_BORROWERS_AGENT);
        adapter.setOnClickAgentsBorrowerListener(new CommonListListener.AgentsClientListener() {
            @Override
            public void onClickClient(UserBorrower user) {

                BottomSheetBorrowerAgent dialog = new BottomSheetBorrowerAgent();
                dialog.setUserIdentity(user);
                dialog.showNow(parentActivity().getSupportFragmentManager(), "BottomDialogShow");
            }

            @Override
            public void onClickAjukan(UserBorrower user) {

                if(user.isOtpVerified()){

                    //revision jan 24, 2020
                    if(user.getBankAccountnumber().isEmpty() || user.getBankAccountnumber() == null){

                        BottomDialogHandlingError error = new BottomDialogHandlingError("Nasabah belum memiliki nomor rekening", 0);
                        error.showNow(parentActivity().getSupportFragmentManager(), "error message");
                        error.setOnClickLister(error::dismiss);

                    }else if(user.getLoanStatus().toLowerCase().equals("inactive")) {

                        Intent intent = new Intent(parentActivity(), ListServicesAgentActivity.class);
                        intent.putExtra("user", (Serializable) user);
                        intent.putExtra(ListServicesAgentActivity.BANK_ID, String.valueOf(user.getBank().getInt64()));
                        startActivity(intent);

                    }else {

                        BottomDialogHandlingError error = new BottomDialogHandlingError("Nasabah Masih Memiliki Pinjaman Aktif", 0);
                        error.showNow(parentActivity().getSupportFragmentManager(), "error message");
                        error.setOnClickLister(error::dismiss);

                    }

                }else {

                    mPresenter.postOTPRequestBorrowerAgent(String.valueOf(user.getId()));

                }


            }
        });

//        adapter.setOnClickListenerViewBorrower(new CommonListListener.ViewBorrowerListener() {
//            @Override
//            public void onClickButton(UserBorrower user) {
//
//                if(user.isOtpVerified()){
//
//                    //revision jan 24, 2020
//                    if(user.getBankAccountnumber().isEmpty() || user.getBankAccountnumber() == null){
//
//                        BottomDialogHandlingError error = new BottomDialogHandlingError("Nasabah belum memiliki nomor rekening", 0);
//                        error.showNow(parentActivity().getSupportFragmentManager(), "error message");
//                        error.setOnClickLister(error::dismiss);
//
//                    }else if(user.getLoanStatus().toLowerCase().equals("inactive")) {
//
//                        Intent intent = new Intent(parentActivity(), ListServicesAgentActivity.class);
//                        intent.putExtra("user", (Serializable) user);
//                        intent.putExtra(ListServicesAgentActivity.BANK_ID, String.valueOf(user.getBank().getInt64()));
//                        startActivity(intent);
//
//                    }else {
//
//                        BottomDialogHandlingError error = new BottomDialogHandlingError("Nasabah Masih Memiliki Pinjaman Aktif", 0);
//                        error.showNow(parentActivity().getSupportFragmentManager(), "error message");
//                        error.setOnClickLister(error::dismiss);
//
//                    }
//
//                }else {
//                    mPresenter.postOTPRequestBorrowerAgent(String.valueOf(user.getId()));
//
//                }
//            }
//
//            @Override
//            public void onClick(UserBorrower user) {
//                BottomSheetBorrowerAgent dialog = new BottomSheetBorrowerAgent();
//                dialog.setUserIdentity(user);
//                dialog.showNow(parentActivity().getSupportFragmentManager(), "BottomDialogShow");
//            }
//        });

    }

    @Override
    public void goToOTPInput(String agentPhone, String id) {
        Intent otp = new Intent(parentActivity(), VerificationOTPActivity.class);
        otp.putExtra(VerificationOTPActivity.PURPOSES, "REGISTER_BORROWER");
        otp.putExtra("id_borrower", id);
        startActivity(otp);
    }

}
