package com.ayannah.asira.screen.agent.viewBorrower;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.dialog.BottomSheetBorrowerAgent;
import com.ayannah.asira.screen.agent.services.ListServicesAgentActivity;
import com.ayannah.asira.screen.otpphone.VerificationOTPActivity;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ViewBorrowerFragment extends BaseFragment implements ViewBorrowerContract.View {

    @Inject
    ViewBorrowerContract.Presenter mPresenter;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tvEmptyNasabah)
    TextView tvEmptyNasabah;

    @BindView(R.id.title)
    TextView title;

    @Inject
    CommonListAdapter adapter;

    private String bank_Id;
    private String bankName;

    @BindView(R.id.lyResult) LinearLayout lyResult;
    @BindView(R.id.lyError) LinearLayout lyError;
    @BindView(R.id.errorCode) TextView errorCode;
    @BindView(R.id.message) TextView errorMessage;

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

        mPresenter.getLenderToken();

        bank_Id = parentActivity().getIntent().getStringExtra(ViewBorrowerActivity.BANK_ID);
        bankName = parentActivity().getIntent().getStringExtra(ViewBorrowerActivity.BANK_NAME);

        pbLoading.setVisibility(View.VISIBLE);
        mPresenter.getDataBorrower(bank_Id);

    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String code) {

        pbLoading.setVisibility(View.GONE);

        lyResult.setVisibility(View.GONE);

        lyError.setVisibility(View.VISIBLE);

        if(code.equals("0")){

            errorCode.setText("0");
            errorMessage.setText(getResources().getString(R.string.no_connection));

        }else {

            errorCode.setText(code);
            errorMessage.setText(getResources().getString(R.string.error_msg_http));

        }
    }

    @Override
    public void getAllData(int totalData, List<UserBorrower> results) {

        //UI
        pbLoading.setVisibility(View.GONE);

        if(totalData > 0){

            title.setText(String.format("Daftar nasabah %s", bankName.replaceAll("bank", "").replace("Bank", "")));

            lyResult.setVisibility(View.VISIBLE);
            lyError.setVisibility(View.GONE);

            //result
            adapter.setListNasabah(results);

            adapter.setOnClickListenerViewBorrower(new CommonListListener.ViewBorrowerListener() {
                @Override
                public void onClickButton(UserBorrower user) {
                    if(user.isOtpVerified()){

                        mPresenter.setDataSelectedBorrower(user);

                        Intent intent = new Intent(parentActivity(), ListServicesAgentActivity.class);
                        intent.putExtra("user", (Serializable) user);
                        intent.putExtra(ListServicesAgentActivity.BANK_ID, bank_Id);
                        startActivity(intent);

                    }else {

                        Intent otp = new Intent(parentActivity(), VerificationOTPActivity.class);
                        otp.putExtra(VerificationOTPActivity.PURPOSES, "REGISTER_BORROWER");
                        otp.putExtra("id_borrower", String.valueOf(user.getId()));
                        startActivity(otp);
                    }
                }

                @Override
                public void onClick(UserBorrower user) {
                    BottomSheetBorrowerAgent dialog = new BottomSheetBorrowerAgent();
                    dialog.setUserIdentity(user);
                    dialog.showNow(parentActivity().getSupportFragmentManager(), "BottomDialogShow");
                }
            });

        }else {

            lyError.setVisibility(View.GONE);

            lyResult.setVisibility(View.GONE);

            tvEmptyNasabah.setVisibility(View.VISIBLE);

        }

    }

}
