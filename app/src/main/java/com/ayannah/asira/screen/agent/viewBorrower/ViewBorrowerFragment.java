package com.ayannah.asira.screen.agent.viewBorrower;

import android.os.Bundle;
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

    @Inject
    CommonListAdapter adapter;

    @Inject
    String bankId;

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

        mPresenter.getDataBorrower(bankId);

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

            lyResult.setVisibility(View.VISIBLE);
            lyError.setVisibility(View.GONE);

            //result
            adapter.setListNasabah(results);

            adapter.setOnClickListenerViewBorrower(new CommonListListener.ViewBorrowerListener() {
                @Override
                public void onClickButton() {

                    Toast.makeText(parentActivity(), "Test click", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClick(UserBorrower user) {

                    BottomSheetBorrowerAgent dialog = new BottomSheetBorrowerAgent();
                    dialog.showNow(parentActivity().getSupportFragmentManager(), "test");

                }
            });

        }else {

            lyError.setVisibility(View.GONE);

            lyResult.setVisibility(View.GONE);

            tvEmptyNasabah.setVisibility(View.VISIBLE);

        }

    }

}
