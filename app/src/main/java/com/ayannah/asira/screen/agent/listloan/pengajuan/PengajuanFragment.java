package com.ayannah.asira.screen.agent.listloan.pengajuan;

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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class PengajuanFragment extends BaseFragment implements PengajuanContract.View {

    @Inject
    PengajuanContract.Presenter mPresenter;

    @Inject
    @Named("adapterPengajuan")
    CommonListAdapter adapterPengajuan;

    @BindView(R.id.recyclerViewPengajuan)
    RecyclerView recyclerViewPengajuan;

    @Inject
    public PengajuanFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_pengajuan;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getOnProcessLoan();

    }

    @Override
    protected void initView(Bundle state) {

        recyclerViewPengajuan.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerViewPengajuan.setHasFixedSize(true);
        recyclerViewPengajuan.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewPengajuan.setAdapter(adapterPengajuan);

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showOnProcessLoan(List<DummyLoanBorrower> results) {

        adapterPengajuan.setListLoanBorrowersAgent(results);
        adapterPengajuan.setOnClickListenerLoanBorrowerOnAgent(new CommonListListener.ListLoanAgent() {
            @Override
            public void onClickItem(DummyLoanBorrower loan) {

            }
        });
    }
}
