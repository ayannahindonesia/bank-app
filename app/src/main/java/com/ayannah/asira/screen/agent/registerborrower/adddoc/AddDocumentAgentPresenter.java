package com.ayannah.asira.screen.agent.registerborrower.adddoc;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddDocumentAgentPresenter implements AddDocumentAgentContract.Presenter {

    @Nullable
    private AddDocumentAgentContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;
    private PreferenceRepository preferenceRepository;

    @Inject
    AddDocumentAgentPresenter(Application application, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();
    }

    public void checkExistingBorrowerAgent(String ktp, String phoneNumber, String email, String npwp) {

        if(mView == null){
            Toast.makeText(application, "something wrong on check mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject paramCheckBorrower = new JsonObject();
        paramCheckBorrower.addProperty("idcard_number", ktp);
        paramCheckBorrower.addProperty("taxid_number", npwp);
        paramCheckBorrower.addProperty("phone", phoneNumber);
        paramCheckBorrower.addProperty("email", email);

        mComposite.add(remoteRepository.checkExistingBorrowerAgent(paramCheckBorrower)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

//                    if (response.isStatus()) {
//                        StringBuilder field = new StringBuilder();
//                        for (int i=0; i<response.getFields().size(); i++) {
//                            field.append(response.getFields().get(i)).append(" ");
//                        }
//                        mView.showErrorMessage(field + " Sudah Digunakan");
//                    } else {
//                        mView.successCheckMandotryEntity(phoneNumber);
//                    }

                    if(response.isStatus()){

                        mView.successCheckMandotryEntity(phoneNumber);

                    }

                }, error -> {
                    ANError anError = (ANError)error;
//                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
//                        mView.showErrorMessage("Tidak Ada Koneksi");
//                    } else if (anError.getErrorBody() != null){
//
//                        try {
//                            JSONObject json = new JSONObject(anError.getErrorBody());
//                            mView.showErrorMessage(json.optString("message"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        mView.showErrorMessage("Something wrong happend!");
//                    }

                    if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                        mView.showErrorMessage("Tidak Ada Koneksi", 0);
                    } else {

                        if (anError.getErrorBody() != null){

                            try {
                                JSONObject json = new JSONObject(anError.getErrorBody());
                                mView.showErrorMessage(json.optString("message") , anError.getErrorCode());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {

                            mView.showErrorMessage("Bad Gateway (%s)", anError.getErrorCode());

                        }


                    }


                }));
    }

    @Override
    public void checkPublicToken() {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    preferenceRepository.setPublicToken("Bearer "+response.getToken());
                    mView.dialogDismiss();

                }, error ->{

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Tidak Ada Koneksi", 0);
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                        }else {
                            mView.showErrorMessage("Bad Gateway (%s)", anError.getErrorCode());

                        }
                    }

                }));

    }

    @Override
    public void takeView(AddDocumentAgentContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }
}
