package com.ayannah.asira.screen.borrower.notifpage;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NotifPageFragment extends BaseFragment implements NotifPageContract.View {

    @Inject
    NotifPageContract.Presenter mPresenter;

    @BindView(R.id.rvNotif)
    RecyclerView rvNotif;

    @Inject
    CommonListAdapter adapter;

    @Inject
    public NotifPageFragment(){

    }


    @Override
    public void onResume() {
        super.onResume();

        mPresenter.takeView(this);

        mPresenter.getListNotification();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_notifpage;
    }

    @Override
    protected void initView(Bundle state) {

        rvNotif.setLayoutManager(new LinearLayoutManager(parentActivity()));

        rvNotif.setHasFixedSize(true);

        rvNotif.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));

        rvNotif.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showDataNotif(List<String> results) {

        adapter.setDataNotificationMessages(results);

        adapter.setOnClickListnerNotifAdapter(data -> Toast.makeText(parentActivity(), data, Toast.LENGTH_SHORT).show());

    }
}
