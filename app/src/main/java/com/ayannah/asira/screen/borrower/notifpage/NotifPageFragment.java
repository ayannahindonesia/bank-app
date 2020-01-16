package com.ayannah.asira.screen.borrower.notifpage;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.Notif;

import java.util.Collections;
import java.util.Comparator;
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
    public void showDataNotif(List<Notif.Data> list) {

        Collections.sort(list, new Comparator<Notif.Data>() {
            @Override
            public int compare(Notif.Data o1, Notif.Data o2) {
                return Integer.parseInt(o2.getId()) - Integer.parseInt(o1.getId());
            }
        });

        adapter.setDataNotificationMessages(list);

        adapter.setOnClickListnerNotifAdapter(data -> Toast.makeText(parentActivity(), data, Toast.LENGTH_SHORT).show());

    }
}
