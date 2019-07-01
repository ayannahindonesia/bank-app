package com.ayannah.bantenbank.data.remote.interceptor;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.inject.Inject;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RefreshTokenInterceptor implements Interceptor {

    private Application application;
    private PreferenceRepository preferenceRepository;

    @Inject
    public RefreshTokenInterceptor(Application application, PreferenceRepository preferenceRepository){
        this.application = application;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        Response response = chain.proceed(originRequest);

        if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED){
            String usertoken = preferenceRepository.getPublicToken();

            Log.d("abceds", usertoken);

            if(!usertoken.isEmpty()){

                ///request token public to asira
                Request refreshTokenPublic = originRequest.newBuilder()
                        .url("https://asira.ayannah.com/clientauth")
                        .header("Authorization", Credentials.basic("androkey", "androsecret"))
                        .build();

                Response refreshTokenResponse = chain.proceed(refreshTokenPublic);
                updateUserToken(refreshTokenResponse);

                //getToken public
//                String tokenPublic = null;
//
//                try {
//
//                    JSONObject obj = new JSONObject(refreshTokenPublic.toString());
//                    tokenPublic = obj.optString("token");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                //request token client for borrower
//                Request refreshTokenClient = originRequest.newBuilder()
//                        .url("https://asira.ayannah.com/client/borrowe_login")
//                        .header("Authorization", tokenPublic)
//                        .build();
//
//                //update token client then update it to SharedPreferences
//                Response refreshTokenClientResponse = chain.proceed(refreshTokenClient);
//                updateUserToken(refreshTokenClientResponse);

                String newToken = preferenceRepository.getPublicToken();
                if(newToken.isEmpty()){
                    Request newOriginRequest = originRequest.newBuilder()
                            .removeHeader("Authorization")
                            .addHeader("Authorization", newToken)
                            .build();

                    response = chain.proceed(newOriginRequest);
                }


            }
        }

        return response;
    }

    private void updateUserToken(Response response) {
        try {

            if(response.body() != null){

                JSONObject object = new JSONObject(response.body().toString());

                Log.d("RefreshTokenKevin", "expires_in: "+object.optString("expires_in"));
                preferenceRepository.setPublicToken(object.optString("token"));

                Log.d("abceds", preferenceRepository.getPublicToken());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
