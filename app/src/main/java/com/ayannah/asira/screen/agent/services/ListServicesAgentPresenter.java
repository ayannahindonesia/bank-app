package com.ayannah.asira.screen.agent.services;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.data.local.BankServiceInterface;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.BeritaPromo;
import com.ayannah.asira.data.remote.RemoteRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ListServicesAgentPresenter implements ListServicesAgentContract.Presenter {

    private Application application;

    @Nullable
    private ListServicesAgentContract.View mView;
    private PreferenceRepository prefRepo;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    ListServicesAgentPresenter(Application application, PreferenceRepository prefRepo, RemoteRepository remotRepo) {
        this.application = application;
        this.prefRepo = prefRepo;
        this.remotRepo = remotRepo;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(ListServicesAgentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getAllService() {

        List<BankService.Data> datas = new ArrayList<>();

        BankService.Data data = new BankService.Data();
        data.setId(1);
        data.setBankId(1);
        data.setName("Pinjaman HardCoded 1");
        data.setImageId(1);
        data.setStatus("Active");

        datas.add(data);

        mView.setAllServices(datas);
    }

    @Override
    public void loadPromoAndNews() {
        if(mView == null){
            return;
        }

        List<BeritaPromo> listBeritaPromo = new ArrayList<>();

        BeritaPromo data = new BeritaPromo();
        data.setImg(R.drawable.breaking_news);
        data.setTitle("Berita 1");
        data.setDescription(application.getResources().getString(R.string.desc_example));
        listBeritaPromo.add(data);

        BeritaPromo data2 = new BeritaPromo();
        data2.setImg(R.drawable.promo_banner);
        data2.setDescription(application.getResources().getString(R.string.desc_example));
        data2.setTitle("Promo 2");
        listBeritaPromo.add(data2);

        mView.showPromoAndNews(listBeritaPromo);

    }
}
