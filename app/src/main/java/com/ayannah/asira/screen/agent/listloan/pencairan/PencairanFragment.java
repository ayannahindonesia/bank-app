package com.ayannah.asira.screen.agent.listloan.pencairan;

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

public class PencairanFragment extends BaseFragment implements PencairanContract.View {

    @Inject
    PencairanContract.Presenter mPresenter;

    @Inject
    @Named("adapterPencairan")
    CommonListAdapter adapterPencairan;

    @BindView(R.id.recyclerViewPencairan)
    RecyclerView recyclerViewPencairan;

    @Inject
    public PencairanFragment(){

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_pencairan;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getOnPencairan();
    }

    @Override
    protected void initView(Bundle state) {

        recyclerViewPencairan.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerViewPencairan.setHasFixedSize(true);
        recyclerViewPencairan.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewPencairan.setAdapter(adapterPencairan);

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOnPencairan(List<DummyLoanBorrower> results) {

        adapterPencairan.setListLoanBorrowersAgent(results);
        adapterPencairan.setOnClickListenerLoanBorrowerOnAgent(new CommonListListener.ListLoanAgent() {
            @Override
            public void onClickItem(DummyLoanBorrower loan) {

            }
        });
    }
}
