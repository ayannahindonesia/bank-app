package com.ayannah.asira.screen.agent.navigationmenu.agentprofile.ListBanks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.BankList;
import com.mobsandgeeks.saripaar.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class AgentProfileBankListFragment extends BaseFragment implements AgentProfileBankListContract.View{

    List<BankDetail> listBanks;
    AlertDialog dialogAlert;

    @Inject
    AgentProfileBankListContract.Presenter mPresenter;

    @BindView(R.id.recyclerListBanks)
    RecyclerView recyclerListBanks;

    @BindView(R.id.txtBanksNull)
    TextView txtBanksNull;

    @Inject
    CommonListAdapter mAdapterLoans;

    ArrayList<BankDetail> bankSelected = new ArrayList<>();

    @Inject
    public AgentProfileBankListFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_list_banks;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialogAlert = builder.create();

        dialogAlert.show();
        mPresenter.getAllBanks();
    }

    @Override
    protected void initView(Bundle state) {
        recyclerListBanks.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerListBanks.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerListBanks.setHasFixedSize(true);
        recyclerListBanks.setAdapter(mAdapterLoans);
    }

    @Override
    public void showErrorMessage(String message) {
        dialogAlert.dismiss();
        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void successGetAllBanks(BankList response) {
        dialogAlert.dismiss();
        listBanks = new ArrayList<>();
        if (response.getData().size() > 0) {
            recyclerListBanks.setVisibility(View.VISIBLE);
            txtBanksNull.setVisibility(View.GONE);
            for (int i = 0; i < response.getTotalData(); i++) {
                BankDetail bankDetail = new BankDetail();
                bankDetail.setName(response.getData().get(i).getName());
                bankDetail.setId(response.getData().get(i).getId());

                listBanks.add(bankDetail);
            }
            mAdapterLoans.setBanks(listBanks);
        } else {
            txtBanksNull.setVisibility(View.VISIBLE);
            recyclerListBanks.setVisibility(View.GONE);
        }

        mAdapterLoans.setOnClickListenerBankList(new CommonListListener.BankListListener() {
            @Override
            public void onClickItem(BankDetail bankDetail, View itemView, LinearLayout linearLayout) {
                if (linearLayout.getBackground() == null) {
                    itemView.setBackgroundColor(getResources().getColor(R.color.transdarkgreen));
                    bankSelected.add(bankDetail);
                } else {
                    itemView.setBackground(null);
                    bankSelected.remove(bankDetail);
                }
            }
        });
    }

    @OnClick(R.id.btnSelectBanks)
    void banksSelected() {
        Intent intent = new Intent();
        intent.putExtra("intent", bankSelected);
        parentActivity().setResult(RESULT_OK, intent);
        parentActivity().finish();
    }
}
