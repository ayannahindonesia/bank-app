package com.ayannah.asira.screen.agent.services;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.BeritaPromoAdapter;
import com.ayannah.asira.adapter.MenuServiceAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.BeritaPromo;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.screen.earninginfo.EarningActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ListServicesAgentFragment extends BaseFragment implements ListServicesAgentContract.View {

    @Inject
    ListServicesAgentContract.Presenter mPresenter;

    @Inject
    BeritaPromoAdapter mAdapterNewsPromo;

    @BindView(R.id.recycler_servicesmenu)
    RecyclerView recycler_servicesmenu;

    @BindView(R.id.rvBeritaPromo)
    RecyclerView recyclerViewBeritaPromo;

    @Inject
    MenuServiceAdapter mAdapterMenu;

    String bank_id;
    UserBorrower user;

    @Inject
    public ListServicesAgentFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_services_list;
    }

    @Override
    protected void initView(Bundle state) {

        recycler_servicesmenu.setLayoutManager(new GridLayoutManager(parentActivity(), 3));
        recycler_servicesmenu.setHasFixedSize(true);
        recycler_servicesmenu.setAdapter(mAdapterMenu);

        recyclerViewBeritaPromo.setLayoutManager(new LinearLayoutManager(parentActivity(), RecyclerView.HORIZONTAL, false));
        recyclerViewBeritaPromo.setHasFixedSize(true);
        recyclerViewBeritaPromo.setAdapter(mAdapterNewsPromo);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        bank_id = getActivity().getIntent().getStringExtra(ListServicesAgentActivity.BANK_ID);

        user = (UserBorrower) getActivity().getIntent().getSerializableExtra("user");

        mPresenter.getAllService(bank_id);

        mPresenter.loadPromoAndNews();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }

    @Override
    public void setAllServices(List<BankService.Data> datas) {
        List<BankService.Data> menus = new ArrayList<>();

        for(BankService.Data x:datas) {
            if (x.getStatus().toLowerCase().equals("active")) {
                menus.add(x);
            }
        }

        mAdapterMenu.setMenuService(menus);

        mAdapterMenu.setOnClickListener(menuProduct -> {

            Intent intent = new Intent(parentActivity(), EarningActivity.class);
            intent.putExtra("user", (Serializable) user);
            intent.putExtra("isFrom", "agent");
            intent.putExtra("id", menuProduct.getId());
            intent.putExtra("name", menuProduct.getName());
            intent.putExtra(EarningActivity.ID_SERVICE, String.valueOf(menuProduct.getId()));
            startActivity(intent);

        });

    }

    @Override
    public void showPromoAndNews(List<BeritaPromo> listBeritaPromo) {
        mAdapterNewsPromo.setBeritaNews(listBeritaPromo);
    }

    @Override
    public void showErrorMessage(String err) {
        Toast.makeText(parentActivity(), err, Toast.LENGTH_SHORT).show();
    }

}
