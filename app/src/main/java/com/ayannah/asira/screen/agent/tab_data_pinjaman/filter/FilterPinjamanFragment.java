package com.ayannah.asira.screen.agent.tab_data_pinjaman.filter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.model.BankDetail;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FilterPinjamanFragment extends BaseFragment implements FilterPinjamanContract.View {

    @Inject
    FilterPinjamanContract.Presenter mPresenter;

    @BindView(R.id.banks) Spinner spBanks;
    @BindView(R.id.etNamaNasabah) EditText etNama;
    @BindView(R.id.rbInprogress) RadioButton rb_inProgress;
    @BindView(R.id.rbDisbursment) RadioButton rb_disbursment;
    @BindView(R.id.rbReject) RadioButton rb_reject;

    private ArrayAdapter<String> adapter;
    private int idBank;

    @Inject
    public FilterPinjamanFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_filter_pinjaman;
    }

    @Override
    protected void initView(Bundle state) {



    }

    @Override
    public void showErrorMessage(String message, int code) {

    }

    @Override
    public void getAllBanks(List<BankDetail> results) {

        List<String> bankNames = new ArrayList<>();
        bankNames.add("Pilih bank...");

        for(BankDetail data: results){
            bankNames.add(data.getName());
        }

        adapter = new ArrayAdapter<String>(parentActivity(), android.R.layout.simple_dropdown_item_1line, bankNames){

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                TextView textView = (TextView) view;

                if(position == 0){
                    textView.setTextColor(Color.DKGRAY);
                }else {
                    textView.setTextColor(Color.BLACK);
                }

                return view;
            }
        };

        spBanks.setAdapter(adapter);
        spBanks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                   idBank = results.get(position-1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.retrieveBanks();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }
}
