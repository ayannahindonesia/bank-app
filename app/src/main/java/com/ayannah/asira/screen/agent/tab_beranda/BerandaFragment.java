package com.ayannah.asira.screen.agent.tab_beranda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.MenuAgent;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.dialog.BottomDialogHandlingError;
import com.ayannah.asira.dialog.BottomErrorHandling;
import com.ayannah.asira.dialog.BottomSheetBorrowerAgent;
import com.ayannah.asira.screen.agent.registerborrower.choosebank.ChooseBankAgentActivity;
import com.ayannah.asira.screen.agent.services.ListServicesAgentActivity;
import com.ayannah.asira.screen.agent.viewBorrower.ViewBorrowerActivity;
import com.ayannah.asira.screen.otpphone.VerificationOTPActivity;
import com.ayannah.asira.screen.register.choosebank.ChooseBankActivity;
import com.ayannah.asira.util.CommonUtils;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class BerandaFragment extends BaseFragment implements BerandaContract.View {

    @Inject
    BerandaContract.Presenter mPresenter;

    @BindView(R.id.agentName) TextView tvname;
    @BindView(R.id.agentPhone) TextView tvphone;
    @BindView(R.id.emptyData) TextView tvEmptyData;
    @BindView(R.id.rvBeranda) RecyclerView recyclerView;
    @BindView(R.id.allClient) TextView allClient;

    @Inject @Named("borrowersAgent")
    CommonListAdapter adapter;

    @Inject
    public BerandaFragment(){

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getUserAttributes();

        mPresenter.fetchNasabah();

    }

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_beranda;
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String message, int code) {

        BottomErrorHandling error = new BottomErrorHandling(message, code);
        error.showNow(parentActivity().getSupportFragmentManager(), "error");
        error.setOnClickListener(code1 -> error.dismiss());
    }

    @Override
    public void showUserAttributes(String name, String phone) {

        tvname.setText(name);

        tvphone.setText(CommonUtils.formatPhoneNumberGlobal(phone));

    }

    @Override
    public void showRecentAgent(List<UserBorrower> userBorrowers) {

        if(userBorrowers.size() > 0){

            tvEmptyData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            adapter.setAgentsBorrowerList(userBorrowers);
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

        }else {

            recyclerView.setVisibility(View.GONE);
            tvEmptyData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void goToOTPInput(String agentPhone, String id) {

        Intent otp = new Intent(parentActivity(), VerificationOTPActivity.class);
        otp.putExtra(VerificationOTPActivity.PURPOSES, "REGISTER_BORROWER");
        otp.putExtra("id_borrower", id);
        startActivity(otp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }

    @OnClick(R.id.allClient)
    void onClickAllClient(){

        Intent intent = new Intent(parentActivity(), ViewBorrowerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.addMember)
    void onClickAddMember(){

        Intent intent =new Intent(parentActivity(), ChooseBankAgentActivity.class);
        intent.putExtra("isFrom", "regBorrower");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
