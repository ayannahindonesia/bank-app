package com.ayannah.asira.data.remote;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.R;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.AgentProfile;
import com.ayannah.asira.data.model.AgentProviderDetail;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.BankList;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.CheckAccount;
import com.ayannah.asira.data.model.CheckBorrower;
import com.ayannah.asira.data.model.CurrentTime;
import com.ayannah.asira.data.model.FCMTokenResponse;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.Loans.Loans;
import com.ayannah.asira.data.model.NasabahAgent;
import com.ayannah.asira.data.model.Notif;
import com.ayannah.asira.data.model.Provinsi;
import com.ayannah.asira.data.model.ReasonLoan;
import com.ayannah.asira.data.model.ServiceProducts;
import com.ayannah.asira.data.model.ServiceProductsAgent;
import com.ayannah.asira.data.model.Token;
import com.ayannah.asira.data.model.UserBorrower;
import com.ayannah.asira.data.model.UserProfile;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.Credentials;
import okhttp3.Response;

public class RemoteDataSource implements RemoteRepository {

    private PreferenceRepository preferenceRepository;
    private Application application;

    @Inject
    RemoteDataSource(PreferenceRepository preferenceRepository, Application application){
        this.preferenceRepository = preferenceRepository;
        this.application = application;
    }

    @Override
    public Single<Provinsi> getProvinsi() {

        return Rx2AndroidNetworking.get(BuildConfig.API_ASIRA_GEO_MAP + "client/provinsi")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Provinsi.class);

    }

    @Override
    public Single<Kabupaten> getKabupaten(String idProvinsi) {
        return Rx2AndroidNetworking.get(BuildConfig.API_ASIRA_GEO_MAP + "client/provinsi/{idprovinsi}/kota")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addPathParameter("idprovinsi", idProvinsi)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Kabupaten.class);
    }

    @Override
    public Single<Kecamatan> getKecamatan(String idKabupaten) {
        return Rx2AndroidNetworking.get(BuildConfig.API_ASIRA_GEO_MAP + "client/provinsi/kota/{idkabupaten}/kecamatan")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addPathParameter("idkabupaten", idKabupaten)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Kecamatan.class);
    }

    @Override
    public Single<Kelurahan> getKelurahan(String idKecamatan) {
        return Rx2AndroidNetworking.get(BuildConfig.API_ASIRA_GEO_MAP + "client/provinsi/kota/kecamatan/{idkecamatan}/kelurahan")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addPathParameter("idkecamatan", idKecamatan)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Kelurahan.class);
    }

    @Override
    public Single<Token> getTokenLender() {

        String credential = Credentials.basic(application.getString(R.string.lender_cred_id), application.getString(R.string.lender_cred_sandi));
        return Rx2AndroidNetworking.get(BuildConfig.API_URL_LENDER + "clientauth")
                .addHeaders("Authorization", credential)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Token.class);
    }

    @Override
    public Single<Token> getToken() {

        String credential = Credentials.basic(application.getString(R.string.borrower_cred_id), application.getString(R.string.borrower_cred_sandi));
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "clientauth")
                .addHeaders("Authorization", credential)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Token.class);
    }

    @Override
    public Single<Token> getTokenClient(JsonObject json) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "client/borrower_login")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Token.class);
    }

    @Override
    public Single<UserProfile> getUserLogin() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/profile")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(UserProfile.class);

    }

    @Override
    public Single<Loans> getAllLoans(String sortByStatus) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/loan")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addQueryParameter("sort", "desc")
                .addQueryParameter("orderby", "id")
                .addQueryParameter("status", sortByStatus)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Loans.class);

    }

    @Override
    public Single<Response> postBorrowerRegister(JsonObject jsonObject) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL  + "client/register_borrower")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Response.class);
    }

    @Override
    public void postOTPRequestBorrower(JsonObject json) {
        AndroidNetworking.post(BuildConfig.API_URL + "client/otp_request")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {
                        if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
//                            mView.showErrorMessage("Connection Error");
                            Toast.makeText(application, "Connection Error", Toast.LENGTH_LONG).show();
                        }else {

                            if(anError.getErrorBody() != null){

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(anError.getErrorBody());
                                    Toast.makeText(application, jsonObject.optString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                mView.showErrorMessage(jsonObject.optString("message"));
                            }else {

                                Toast.makeText(application, "Error "+anError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public Single<Response> postVerifyOTP(JsonObject jsonObject) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "unverified_borrower/otp_verify")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Response.class);
    }

    @Override
    public Single<DataItem> postLoan(JsonObject json) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(DataItem.class);
    }


    @Override
    public void getOTPForLoan(String idLoan) {

        AndroidNetworking.get(BuildConfig.API_URL + "borrower/loan/{loan_id}/otp")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("loan_id", idLoan)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public void verifiedLoanByOTP(String idLoan, JsonObject json) {

        AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan/{idloan}/verify")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("idloan", idLoan)
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("verify Loan: ", "Sukses");
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("verify Loan: ", "gagal");
                    }
                });
    }

    @Override
    public Single<DataItem> getLoanDetails(String idLoan) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/loan/{idloan}/details")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("idloan", idLoan)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(DataItem.class);
    }

    @Override
    public Single<UserProfile> updateProfile(JsonObject json) {
        return Rx2AndroidNetworking.patch(BuildConfig.API_URL + "borrower/profile")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(UserProfile.class);
    }

    @Override
    public Single<CheckAccount> checkAccount(String email, String phone, String idcard, String taxid) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "client/check_unique")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addQueryParameter("email", email)
                .addQueryParameter("idcard_number", idcard)
                .addQueryParameter("taxid_number", taxid)
                .addQueryParameter("phone", phone)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(CheckAccount.class);
    }

    @Override
    public Single<CheckAccount> sendEmailToResetPassword(JsonObject jsonObject) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "client/reset_password")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(CheckAccount.class);
    }

    @Override
    public Single<UserProfile> postNewPassword(JsonObject jsonObject) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "client/change_password")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(UserProfile.class);
    }

    @Override
    public Single<BankDetail> getBanksDetail(String bankID) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "client/banks/{bankID}")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addPathParameter("bankID", bankID)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BankDetail.class);
    }

    @Override
    public Single<BankList> getAllBanks() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "client/banks")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BankList.class);
    }

    @Override
    public Single<BankService> getBankServices() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/bank_services")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BankService.class);
    }

    @Override
    public Single<ServiceProducts> getAllProducts(String idService) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/bank_products")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addQueryParameter("service_id", idService)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(ServiceProducts.class);
    }

    @Override
    public Single<ServiceProducts> getAllProductsGlobal() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/bank_products")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(ServiceProducts.class);
    }

    @Override
    public Single<ReasonLoan> getReasons() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "client/loan_purposes")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(ReasonLoan.class);
    }

    @Override
    public Single<Token> getTokenAdminLender() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key", "adminkey");
        jsonObject.addProperty("password", "adminsecret");

        return Rx2AndroidNetworking.post(BuildConfig.API_URL_LENDER + "client/admin_login")
                .addHeaders("Authorization", preferenceRepository.getPublicTokenLender())
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Token.class);
    }

    @Override
    public Single<FCMTokenResponse> sendUserFCMToken(String fcmToken) {

        JsonObject json = new JsonObject();
        json.addProperty("fcm_token", fcmToken);

        return Rx2AndroidNetworking.patch(BuildConfig.API_URL + "borrower/fcm_token_update")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(FCMTokenResponse.class);
    }

    @Override
    public Single<Token> getClientAgentToken(JsonObject json) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "client/agent_login")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Token.class);
    }

    @Override
    public Single<AgentProfile> getAgentProfile() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "agent/profile")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(AgentProfile.class);
    }

    @Override
    public Single<Notif> getListNotification() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/notifications")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Notif.class);

    }

    @Override
    public Single<UserBorrower> postBorrowerRegisterAgent(JsonObject jsonObject) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL  + "agent/register_borrower")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(UserBorrower.class);
    }

    @Override
    public Single<BankList> getAllBanksAgent() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "agent/banks")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BankList.class);
    }

    @Override
    public Single<NasabahAgent> getListBorrower(String idBank) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "agent/borrowers/{bankId}")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("bankId", idBank)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(NasabahAgent.class);

    }

    @Override
    public Single<NasabahAgent> getListBorrower_new(String idBank) {

        if(idBank == null){

            return Rx2AndroidNetworking.get(BuildConfig.API_URL + "agent/borrowers")
                    .addHeaders("Authorization", preferenceRepository.getUserToken())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getObjectSingle(NasabahAgent.class);

        }else {

            return Rx2AndroidNetworking.get(BuildConfig.API_URL + "agent/borrowers")
                    .addHeaders("Authorization", preferenceRepository.getUserToken())
                    .addQueryParameter("bank_id", idBank)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getObjectSingle(NasabahAgent.class);
        }

    }

    @Override
    public Single<CheckBorrower> checkExistingBorrowerAgent(JsonObject paramCheckBorrower) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "agent/checks_borrower")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(paramCheckBorrower)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(CheckBorrower.class);
    }

    @Override
    public Single<BankService> getServicesAgent(String bank_id) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "agent/bank_services")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addQueryParameter("bank_id", bank_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BankService.class);
    }

    @Override
    public Single<ServiceProductsAgent> getAllProductsAgent(String idService) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "agent/bank_products")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addQueryParameter("service_id", idService)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(ServiceProductsAgent.class);
    }

    @Override
    public void getOTPForLoanAgent(String idLoan) {

        AndroidNetworking.get(BuildConfig.API_URL + "agent/loan/{loan_id}/otp")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("loan_id", idLoan)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public Single<Loans> getAgentLoan(String idBank) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "agent/loan")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addQueryParameter("bank", idBank)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Loans.class);
    }

    @Override
    public Single<AgentProfile> patchAgentProfile(JsonObject jsonPatchAgentProfile) {
        return Rx2AndroidNetworking.patch(BuildConfig.API_URL + "agent/profile")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(jsonPatchAgentProfile)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(AgentProfile.class);
    }

    @Override
    public Single<AgentProviderDetail> getAgentProvider(String agentProviderID) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL_LENDER + "admin/agent_providers/{agentProviderID}")
                .addHeaders("Authorization", preferenceRepository.getAdminTokenLender())
                .addPathParameter("agentProviderID", agentProviderID)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(AgentProviderDetail.class);
    }

    @Override
    public Single<UserProfile> updateProfileFromAgent(JsonObject json, String borrowerID) {
        return Rx2AndroidNetworking.patch(BuildConfig.API_URL + "agent/borrower/{borrower_id}")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("borrower_id", borrowerID)
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(UserProfile.class);
    }

    @Override
    public Single<CurrentTime> getCurrentTime() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "client/serviceinfo")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(CurrentTime.class);
    }

    @Override
    public Single<CheckAccount> checkEmailUser(String email) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "client/check_unique")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addQueryParameter("email", email)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(CheckAccount.class);
    }

    @Override
    public void postOTPRequestBorrowerAgent(String id_borrower) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", preferenceRepository.getAgentPhone());

        AndroidNetworking.post(BuildConfig.API_URL + "agent/otp_request/{id}")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("id", String.valueOf(id_borrower))
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {
                        if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
//                            mView.showErrorMessage("Connection Error");
                            Toast.makeText(application, "Connection Error", Toast.LENGTH_LONG).show();
                        }else {

                            if(anError.getErrorBody() != null){

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(anError.getErrorBody());
                                    Toast.makeText(application, jsonObject.optString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                mView.showErrorMessage(jsonObject.optString("message"));
                            }else {

                                Toast.makeText(application, "Error "+anError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public Single<CheckAccount> checkUnique(String phone, String email) {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "client/check_unique")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addQueryParameter("email", email)
                .addQueryParameter("phone", phone)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(CheckAccount.class);
    }

    @Override
    public Single<UserProfile> postRegisterMandatoryPersonal(JsonObject jsonObject) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "client/register_borrower")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(UserProfile.class);
    }

    @Override
    public Single<BankService> getAllServices() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "client/bank_services")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(BankService.class);
    }
}
