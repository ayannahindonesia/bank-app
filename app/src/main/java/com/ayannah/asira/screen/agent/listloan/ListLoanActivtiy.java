package com.ayannah.asira.screen.agent.listloan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.TabAdapterListLoan;
import com.ayannah.asira.screen.agent.listloan.ditolak.DitolakFragment;
import com.ayannah.asira.screen.agent.listloan.pencairan.PencairanFragment;
import com.ayannah.asira.screen.agent.listloan.pengajuan.PengajuanFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class ListLoanActivtiy extends DaggerAppCompatActivity implements ListLoanContract.View {

    public static final String BANKID = "BANKID";

    @BindView(R.id.tabLayout)
    TabLayout tabs;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Inject
    TabAdapterListLoan tabAdapter;

    @Inject
    PengajuanFragment mPengajuan;

    @Inject
    PencairanFragment mPencairan;

    @Inject
    DitolakFragment mDitolak;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_loan_activtiy);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Pinjaman");

        setUpAdapter();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setUpAdapter() {

        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();


        fragments.add(mPengajuan);
        fragments.add(mPencairan);
        fragments.add(mDitolak);

        titles.add("Pengajuan");
        titles.add("Pencairan");
        titles.add("Ditolak");

        tabAdapter.addFragment(fragments, titles);

        viewPager.setAdapter(tabAdapter);
        tabs.setupWithViewPager(viewPager);

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
