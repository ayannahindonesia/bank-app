package com.ayannah.asira.screen.detailangsuran;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.Angsuran;
import com.ayannah.asira.data.model.InstallmentDetails;
import com.ayannah.asira.screen.bantuan.isi_bantuan.IsiBantuanFragment;
import com.ayannah.asira.screen.detailangsuran.detail_pembayaran.DetailPembayaranFragment;
import com.ayannah.asira.util.ActivityUtils;

import java.util.ArrayList;
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
    ArrayList<InstallmentDetails> installmentDetails;

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
        mPresenter.dataProcessing(installmentDetails);
    }

    @Override
    protected void initView(Bundle state) {

        rvPaging.setLayoutManager(new LinearLayoutManager(parentActivity(), RecyclerView.HORIZONTAL, false));
        rvPaging.setHasFixedSize(true);
        rvPaging.setAdapter(adapter);

        rvInstallment.setLayoutManager(new LinearLayoutManager(parentActivity()));
        rvInstallment.setHasFixedSize(true);
        rvInstallment.setAdapter(adapterAngsuran);

    }

    @Override
    public void showAllPaging(List<Angsuran> results) {

        Log.e(TAG, "event in here");

        adapter.setListAngsuran(results);

//        adapterAngsuran.setListDetilAngsuran(results.get(0).getData());

        adapter.setOnClickListenerAngsuran(data -> {

            adapterAngsuran.setListDetilAngsuran(data.getData());
            rvInstallment.smoothScrollToPosition(0);

        });

        adapterAngsuran.setOnClickListenerDetailAngsuran(installment -> {

            Bundle bundle = new Bundle();
            bundle.putParcelable("details", installment);

            DetailPembayaranFragment detailPembayaranFragment = new DetailPembayaranFragment();
            detailPembayaranFragment.setArguments(bundle);

            ActivityUtils.moveFragment(parentActivity().getSupportFragmentManager(), detailPembayaranFragment, R.id.fragment_container);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
        adapter.clearAngsuran();
    }
}
