package com.ayannah.bantenbank.data.remote.interceptor;

import android.app.Application;
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
            String usertoken = preferenceRepository.getUserToken();
            String userid = preferenceRepository.getIdUser();

            if(!usertoken.isEmpty() && !userid.isEmpty()){

                String credential = Credentials.basic("androkey", "androsecret");

                Request refreshTokenRequest = originRequest.newBuilder()
                        .url("https://asira.ayannah.com/clientauth")
                        .header("Authorization", credential)
                        .build();

                Response refreshTokenResponse = chain.proceed(refreshTokenRequest);

                updateUserToken(refreshTokenResponse);

                String newToken = preferenceRepository.getUserToken();
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
                String message = object.optString("message");

                Toast.makeText(application, "Token Renew: "+message, Toast.LENGTH_SHORT).show();
                preferenceRepository.setUserToken(object.optString("token"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
