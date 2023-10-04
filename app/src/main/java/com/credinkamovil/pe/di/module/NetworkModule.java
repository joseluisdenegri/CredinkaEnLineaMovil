package com.credinkamovil.pe.di.module;

import android.util.Log;

import com.credinkamovil.pe.BuildConfig;
import com.credinkamovil.pe.data.local.prefs.PreferenceHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
        return gsonConverterFactory;
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(HttpLoggingInterceptor loggingInterceptor, PreferenceHelper preferenceHelper) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String sToken = preferenceHelper.getObtenerToken();
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer " + sToken)
                        .method(original.method(), original.body())
                        .build();

                if (BuildConfig.DEBUG) {
                    if (original.body() != null) {
                        Buffer buffer = new Buffer();
                        original.body().writeTo(buffer);
                        String body = buffer.readUtf8();
                    }
                    Log.wtf("URL_SERVICE:: ", request.url() + " *********************************");

                }
                return chain.proceed(request);
                /*return chain.withConnectTimeout(2, TimeUnit.MINUTES)
                        .withReadTimeout(30, TimeUnit.SECONDS)
                        .withWriteTimeout(15, TimeUnit.SECONDS)
                        .proceed(request);*/
            }
        });
        httpClient.addNetworkInterceptor(loggingInterceptor);
        httpClient.connectTimeout(2, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(25, TimeUnit.SECONDS);
        return httpClient.build();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build();
    }
}
