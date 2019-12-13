package com.ayannah.asira.screen.agent.listloan.ditolak;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.DummyLoanBorrower;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.dialog.BottomSheetLoanDetail;
import com.ayannah.asira.screen.agent.listloan.ListLoanActivtiy;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class DitolakFragment extends BaseFragment implements DitolakContract.View {

    @Inject
    DitolakContract.Presenter mPresenter;

    @Inject
    CommonListAdapter adapterDitolak;

    @BindView(R.id.recyclerViewDitolak)
    RecyclerView recyclerViewDitolak;

    private int bankId;

    @Inject
    public DitolakFragment(){

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getOnDitolak(bankId);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_ditolak;

    }

    @Override
    protected void initView(Bundle state) {

        bankId = parentActivity().getIntent().getIntExtra(ListLoanActivtiy.BANKID, 0);

        recyclerViewDitolak.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerViewDitolak.setHasFixedSize(true);
        recyclerViewDitolak.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewDitolak.setAdapter(adapterDitolak);

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOnDitolakLoan(List<DataItem> results) {

        adapterDitolak.setListLoanBorrowersAgent(results);
        adapterDitolak.setOnClickListenerLoanBorrowerOnAgent(new CommonListListener.ListLoanAgent() {
            @Override
            public void onClickItem(DataItem loan) {

                BottomSheetLoanDetail dialog = new BottomSheetLoanDetail();
                dialog.setDataLoan(loan);
                dialog.showNow(parentActivity().getSupportFragmentManager(), "ditolak");
                dialog.setOnClickListener(new BottomSheetLoanDetail.BottomSheetLoanDetailListener() {
                    @Override
                    public void close() {
                        dialog.dismiss();
                    }
                });

            }
        });
    }
}
