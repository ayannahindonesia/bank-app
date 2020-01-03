package com.ayannah.asira.screen.agent.tab_beranda;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.MenuAgent;
import com.ayannah.asira.screen.agent.registerborrower.choosebank.ChooseBankAgentActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class BerandaFragment extends BaseFragment implements BerandaContract.View {

    @Inject
    BerandaContract.Presenter mPresenter;

    @BindView(R.id.rvBeranda)
    RecyclerView recyclerView;

    @Inject
    CommonListAdapter adapter;

    @Inject
    public BerandaFragment(){

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.fetchMenus();

    }

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_beranda;
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showMenus(List<MenuAgent> results) {

        adapter.setMenuAgent(results);

        adapter.setOnClickMenuAgent(new CommonListListener.MenuAgentListener() {
            @Override
            public void onClick(MenuAgent param) {

                Intent intent = new Intent(parentActivity(), ChooseBankAgentActivity.class);

                if(param.getId() == 1){

                    intent.putExtra("isFrom", "regBorrower");
                    startActivity(intent);

                }else {

                    intent.putExtra("isFrom", "listBorrower");
                    startActivity(intent);

                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }
}
