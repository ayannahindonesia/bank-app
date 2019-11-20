package com.ayannah.asira.screen.agent.viewBorrower;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.NasabahAgent;
import com.ayannah.asira.dialog.BottomSheetBorrowerAgent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ViewBorrowerFragment extends BaseFragment implements ViewBorrowerContract.View {

    @Inject
    ViewBorrowerContract.Presenter mPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

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

        mPresenter.getDataBorrower();

    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void getAllData(List<NasabahAgent> results) {
        adapter.setListNasabah(results);

        adapter.setOnClickListenerViewBorrower(new CommonListListener.ViewBorrowerListener() {
            @Override
            public void onClickButton() {
                Toast.makeText(parentActivity(), "Test click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick() {
                BottomSheetBorrowerAgent dialog = new BottomSheetBorrowerAgent();
                dialog.showNow(parentActivity().getSupportFragmentManager(), "test");

            }
        });
    }

}
