package com.ayannah.bantenbank.data.remote.interceptor;

import android.app.Application;

import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.util.CommonUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.inject.Inject;

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

                RequestBody requestBody = new FormBody.Builder()
                        .add("ip_address", CommonUtils.getipAddress(application))
                        .build();




            }
        }

        return null;
    }
}
