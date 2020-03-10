package com.ayannah.asira.screen.bantuan;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.Question;
import com.ayannah.asira.dialog.BottomErrorHandling;
import com.ayannah.asira.screen.bantuan.isi_bantuan.IsiBantuanFragment;
import com.ayannah.asira.util.ActivityUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class BantuanFragment extends BaseFragment implements BantuanContract.View {

    @Inject
    BantuanContract.Presenter mPresenter;

    @Inject
    CommonListAdapter adapter;

    @BindView(R.id.nodata) TextView tvNodata;
    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.recyclerFaq) RecyclerView recyclerView;

    @Inject
    public BantuanFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_bantuan;
    }

    @Override
    protected void initView(Bundle state) {

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mPresenter.retrieveFaq(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {

            recyclerView.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);

            mPresenter.retrieveFaq("");

            return false;
        });

    }

    @Override
    public void showErrorMessage(String message, int code) {

        BottomErrorHandling error = new BottomErrorHandling(message, code);
        error.showNow(parentActivity().getSupportFragmentManager(), "error");
        error.setOnClickListener(new BottomErrorHandling.BottomSheetErrorListener() {
            @Override
            public void onClickClose(int code) {
                error.dismiss();
            }
        });

    }

    @Override
    public void showAllResult(List<Question.Data> results) {

        if(results.size() > 0) {

            recyclerView.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);

            adapter.setListQuestion(results);

            adapter.setOnClickListenerQuestions(new CommonListListener.QuestionListener() {
                @Override
                public void onClickQuestion(Question.Data param) {

                    Bundle bundle = new Bundle();
                    bundle.putString("desc", param.getDescription());

                    IsiBantuanFragment isiBantuanFragment = new IsiBantuanFragment();
                    isiBantuanFragment.setArguments(bundle);

                    ActivityUtils.moveFragment(parentActivity().getSupportFragmentManager(), isiBantuanFragment, R.id.fragment_container);
                }
            });

        }else {

            recyclerView.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.retrieveFaq("");

    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }
}
