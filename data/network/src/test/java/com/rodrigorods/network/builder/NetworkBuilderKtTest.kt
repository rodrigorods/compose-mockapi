package com.rodrigorods.network.builder

import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory

class NetworkBuilderKtTest {

    private val client = mockk<OkHttpClient>()

    @Test
    fun provideRetrofit_createDefaultRetrofit() {
        val retrofit = provideRetrofit("http://com.rods/", client)
        Assert.assertEquals(retrofit.baseUrl().toString(), "http://com.rods/")
        Assert.assertTrue(retrofit.callFactory() is OkHttpClient)
        Assert.assertNotNull(retrofit.converterFactories().find { it is GsonConverterFactory })
    }

    @Test
    fun provideOkHttpClient_createOkHttpClient() {
        val client = provideOkHttpClient()
        Assert.assertNotNull(client.interceptors.find { it is HttpLoggingInterceptor })
    }
}
