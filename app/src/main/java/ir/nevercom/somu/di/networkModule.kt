package ir.nevercom.somu.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import de.vkay.api.tmdb.TMDb
import ir.nevercom.somu.BuildConfig
import ir.nevercom.somu.repositories.Api
import ir.nevercom.somu.util.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { AuthInterceptor(get()) }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    factory { provideSomuApi(get()) }
    single { TMDb.init(BuildConfig.TMDB_API_KEY) }
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(authInterceptor)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideSomuApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)