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
import com.google.android.gms.common.util.CollectionUtils;
import com.mobsandgeeks.saripaar.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    CommonListAdapter mAdapterBanks;

    ArrayList<BankDetail> bankSelected = new ArrayList<>();
    private ArrayList<Integer> banksSelectedID = new ArrayList<>();
    private ArrayList<Integer> banksSelectedIDServer = new ArrayList<>();

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

        banksSelectedID = (ArrayList<Integer>) parentActivity().getIntent().getExtras().get("currentSelectedBanks");
        removeDuplicatesID(banksSelectedID);
        banksSelectedIDServer = (ArrayList<Integer>) parentActivity().getIntent().getExtras().get("currentSelectedBanksServer");

        dialogAlert.show();
        mPresenter.getAllBanks();
    }

    @Override
    protected void initView(Bundle state) {
        recyclerListBanks.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerListBanks.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerListBanks.setHasFixedSize(true);
        recyclerListBanks.setAdapter(mAdapterBanks);
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
                for (int j=0; j<banksSelectedIDServer.size(); j++) {
                    if (response.getData().get(i).getId().toString().equals(banksSelectedIDServer.get(j).toString())) {
                        bankSelected.add(response.getData().get(i));
                    }
                }
                for (int k=0; k<banksSelectedID.size(); k++) {
                    if (response.getData().get(i).getId().toString().equals(banksSelectedID.get(k).toString())) {
                        bankSelected.add(response.getData().get(i));
                    }
                }

            }

            removeDuplicates(bankSelected);
            mAdapterBanks.setBanks(listBanks, banksSelectedID, banksSelectedIDServer);
        } else {
            txtBanksNull.setVisibility(View.VISIBLE);
            recyclerListBanks.setVisibility(View.GONE);
        }

        mAdapterBanks.setOnClickListenerBankList(new CommonListListener.BankListListener() {
            @Override
            public void onClickItem(BankDetail bankDetail, View itemView, LinearLayout linearLayout) {
                removeDuplicates(bankSelected);
                if (linearLayout.getBackground() == null) {
                    itemView.setBackgroundColor(getResources().getColor(R.color.transdarkgreen));
                    bankSelected.add(bankDetail);
                } else {
                    itemView.setBackground(null);
//                    bankSelected.remove(bankDetail);
                    removeArrayItems(bankSelected, bankDetail.getId());
                }
            }
        });

    }

    private void removeArrayItems(ArrayList<BankDetail> currentBanks, Integer idToRemove) {
        for (int i =0; i<currentBanks.size(); i++) {
            if (currentBanks.get(i).getId().toString().equals(String.valueOf(idToRemove))) {
                currentBanks.remove(i);
            }
        }
    }

    private void removeDuplicates(ArrayList<BankDetail> bankSelecteds) {
        Set<BankDetail> set = new HashSet<>(bankSelecteds);
        bankSelected.clear();
        bankSelected.addAll(set);
    }

    private void removeDuplicatesID(ArrayList<Integer> bankSelectedsID) {
        Set<Integer> set = new HashSet<>(bankSelectedsID);
        banksSelectedID.clear();
        banksSelectedID.addAll(set);
    }

    @OnClick(R.id.btnSelectBanks)
    void banksSelected() {
        removeDuplicates(bankSelected);

        Intent intent = new Intent();
        intent.putExtra("intent", bankSelected);
        parentActivity().setResult(RESULT_OK, intent);
        parentActivity().finish();
    }


}
