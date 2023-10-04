package com.credinkamovil.pe.data.remote.ingreso;

import android.util.Log;

import com.credinkamovil.pe.BuildConfig;
import com.credinkamovil.pe.data.remote.security.TLSSocketFactory;
import com.credinkamovil.pe.utils.AppConstantes;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class FactoryIngreso {
    public static ApiIngreso create() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    if (BuildConfig.DEBUG) {
                        Log.d("OkHttp RPTA =======>", String.format("--> Sending request %s on %s%n%s", original.url(), chain.connection(), original.headers()));
                        if (original.body() != null) {
                            Buffer buffer = new Buffer();
                            original.body().writeTo(buffer);
                            Log.d("OkHttp BUFFER ==========> ", buffer.readUtf8());
                        }
                    }

                    Request request = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization", AppConstantes.PUBLIC_KEY)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                    /*return chain.withConnectTimeout(2, TimeUnit.MINUTES)
                            .withReadTimeout(30, TimeUnit.SECONDS)
                            .withWriteTimeout(10, TimeUnit.SECONDS)
                            .proceed(request);*/
                }
            });
            builder.addNetworkInterceptor(loggingInterceptor);
            builder.connectTimeout(2, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(15, TimeUnit.SECONDS);
            //builder.sslSocketFactory(new TLSSocketFactory());
            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            return retrofit.create(ApiIngreso.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
