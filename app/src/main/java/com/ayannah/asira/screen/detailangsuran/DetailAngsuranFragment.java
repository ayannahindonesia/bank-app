package com.ayannah.asira.screen.detailangsuran;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.Angsuran;
import com.ayannah.asira.data.model.Installment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class DetailAngsuranFragment extends BaseFragment implements DetailAngsuranContract.View{

    private static final String TAG = DetailAngsuranFragment.class.getSimpleName();

    @Inject
    DetailAngsuranContract.Presenter mPresenter;

    @BindView(R.id.paging) RecyclerView rvPaging;
    @BindView(R.id.installment) RecyclerView rvInstallment;

    @Inject @Named("paging")
    CommonListAdapter adapter;

    @Inject @Named("angsuran")
    CommonListAdapter adapterAngsuran;

    @Inject
    ArrayList<Installment> installments;


    @Inject
    public DetailAngsuranFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_detail_angsuran;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        mPresenter.dataProcessing();
    }

    @Override
    protected void initView(Bundle state) {

        for (Installment x: installments) {
            Log.e(TAG, "id: "+x.getId());
            Log.e(TAG, "created_at: "+x.getCreatedAt());
            Log.e(TAG, "updated_at: "+x.getUpdatedAt());
            Log.e(TAG, "period: "+x.getPeriod());
            Log.e(TAG, "loan_payment: "+x.getLoanPayment());
            Log.e(TAG, "interest_payment: "+x.getInteresetPayment());
            Log.e(TAG, "paid_date: "+x.getPaidDate());
            Log.e(TAG, "paid_status: "+x.isPaidStatus());
            Log.e(TAG, "due_date: "+x.getDueDate());

        }

        rvPaging.setLayoutManager(new LinearLayoutManager(parentActivity(), RecyclerView.HORIZONTAL, false));
        rvPaging.setHasFixedSize(true);
        rvPaging.setAdapter(adapter);

        rvInstallment.setLayoutManager(new LinearLayoutManager(parentActivity()));
        rvInstallment.setHasFixedSize(true);
        rvInstallment.setAdapter(adapterAngsuran);

    }

    @Override
    public void showAllPaging(List<Angsuran> results) {

        adapter.setListAngsuran(results);

        adapterAngsuran.setListDetilAngsuran(results.get(0).getData());

        adapter.setOnClickListenerAngsuran(new CommonListListener.AngsuranListener() {
            @Override
            public void onClickAngsuran(Angsuran data) {


                adapterAngsuran.setListDetilAngsuran(data.getData());

            }
        });
    }
}
