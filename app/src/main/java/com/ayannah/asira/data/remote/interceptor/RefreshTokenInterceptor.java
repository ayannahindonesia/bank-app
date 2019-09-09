package com.ayannah.asira.data.remote.interceptor;

import androidx.annotation.NonNull;

import com.ayannah.asira.data.local.PreferenceRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RefreshTokenInterceptor implements Interceptor {

    private PreferenceRepository preferenceRepository;

    @Inject
    public RefreshTokenInterceptor(PreferenceRepository preferenceRepository){

        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originRequest = chain.request();
        Response response = chain.proceed(originRequest);

        if(response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED){
            String publicToken = preferenceRepository.getPublicToken();

            if(!publicToken.isEmpty()){

                ///request token public to asira
                String credential = Credentials.basic("androkey", "androsecret");
                Request refreshTokenPublic = originRequest.newBuilder()
                        .url("http://asira.ayannah.com/api-borrower/clientauth") //http://asira.ayannah.com/
                        .header("Authorization", credential)
                        .build();

                Response refreshTokenResponse = chain.proceed(refreshTokenPublic);

                updateUserToken(refreshTokenResponse);

                String newToken = preferenceRepository.getPublicToken();
                if(!newToken.isEmpty()){
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

    private void updateUserToken(Response response) throws IOException {
        try {

            if(response.body() != null){

                JSONObject object = new JSONObject(response.body().string());

                preferenceRepository.setPublicToken("Bearer " + object.optString("token"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
