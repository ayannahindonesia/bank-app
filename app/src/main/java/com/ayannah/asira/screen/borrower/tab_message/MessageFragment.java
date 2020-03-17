package com.ayannah.asira.screen.borrower.tab_message;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.Notif;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class MessageFragment extends BaseFragment implements MessageContract.View {

    @Inject
    MessageContract.Presenter mPresenter;

    @Inject @Named("notif")
    CommonListAdapter adapter;

    @BindView(R.id.recyclerPesan)
    RecyclerView recyclerView;
    @BindView(R.id.errorMsg)
    TextView tvError;

    @Inject
    public MessageFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getNotification();

    }

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_pesan;
    }

    @Override
    protected void initView(Bundle state) {
    }

    @Override
    public void showErrorMessage(String message, int errorCode) {
        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDataNotif(List<Notif.Data> data) {
        adapter = new CommonListAdapter(CommonListAdapter.VIEW_NOTIFPAGE);
        adapter.setDataNotificationMessages(data);

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }
}
