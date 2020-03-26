package com.ayannah.asira.screen.borrower.tab_beranda;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.R;
import com.ayannah.asira.data.local.BankServiceInterface;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.BeritaPromo;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainMenuPresenter implements MainMenuContract.Presenter {

    private Application application;
    private BankServiceInterface bankServiceInterface;

    @Nullable
    private MainMenuContract.View mView;
    private PreferenceRepository prefRepo;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;


    @Inject
    MainMenuPresenter(Application application, PreferenceRepository prefRepo, RemoteRepository remotRepo, BankServiceInterface bankServiceInterface){
        this.application = application;
        this.prefRepo = prefRepo;
        this.remotRepo = remotRepo;
        this.bankServiceInterface = bankServiceInterface;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(MainMenuContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getMainMenu() {

        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getBankServices()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response ->{

            bankServiceInterface.setTotalData(response.getTotalData());
            bankServiceInterface.setBankService(response.getData());

            mView.loadAllServiceMenu(response.getData(), prefRepo.getBankID());


        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                }else {

                    mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                }
            }

        }));

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

    @Override
    public void fetchTopupTagihan() {

        if(mView == null){
            return;
        }

        List<String> data = new ArrayList<>();
        data.add("Pulsa");
        data.add("Listrik");
        data.add("Travel");

        mView.showTopUpTagihanMenu(data);

    }

    @Override
    public void notifLoanRequest() {

        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getAllLoans("")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            mView.showDataLoan(response.getData());


        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message") + " Code: "+anError.getErrorCode(), anError.getErrorCode());
                }else {

                    mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                }
            }

        }));

    }

    @Override
    public void getTokenLender() {
        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getTokenLender()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            prefRepo.setPublicTokenLender("Bearer "+response.getToken());

            mView.successGetPublicTokenLender();

        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message") + " Code: "+anError.getErrorCode(), anError.getErrorCode());
                }else {

                    mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                }
            }

        }));
    }

    @Override
    public void getTokenAdminLender() {
        mComposite.add(remotRepo.getTokenAdminLender()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            prefRepo.setAdminTokenLender("Bearer "+response.getToken());

            notifLoanRequest();

        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                }else {

                    mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                }
            }

        }));
    }

    @Override
    public void getCurrentTime() {
        mComposite.add(remotRepo.getCurrentTime()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {
            mView.successGetCurrentTime(res.getTime());
        }, err -> {
            mView.successGetCurrentTime(null);
        }));
    }

    @Override
    public void getProfile() {
        mComposite.add(remotRepo.getUserLogin()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {
            prefRepo.setLoanStatus(res.getLoanStatus());
            prefRepo.setBankAccountBorrower(res.getBankAccountnumber());
            mView.setLoanStatus(res.getLoanStatus(), prefRepo.getUserNickname());
        }, error -> {
            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                }else {

                    mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                }
            }
        }));
    }

    @Override
    public boolean getIsLogin() {
        return prefRepo.isUserLogged();
    }

    @Override
    public void getPublicToken() {
        if (mView == null) {
            return;
        }

        mComposite.add(remotRepo.getToken()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {
            prefRepo.setPublicToken("Bearer " + res.getToken());
        }, err -> {
            ANError anError = (ANError) err;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                }else {

                    mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                }
            }
        }));
    }

    @Override
    public void getBankName(int bankID) {
        if (mView == null) {
            return;
        }

        mComposite.add(remotRepo.getBanksDetail(String.valueOf(bankID))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            mView.setBankName(res.getName());

        }, err -> {

            ANError anError = (ANError) err;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                }else {

                    mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                }
            }

        }));
    }
}
