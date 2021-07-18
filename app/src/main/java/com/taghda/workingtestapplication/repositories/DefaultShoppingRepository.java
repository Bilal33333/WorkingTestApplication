package com.taghda.workingtestapplication.repositories;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.taghda.workingtestapplication.data.local.ShoppingDao;
import com.taghda.workingtestapplication.data.local.ShoppingItem;
import com.taghda.workingtestapplication.data.remote.PixabayAPI;
import com.taghda.workingtestapplication.BuildConfig;
import com.taghda.workingtestapplication.data.remote.responses.ExchangeRateResponse;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import retrofit2.Response;

public class DefaultShoppingRepository implements ShoppingRepository {

    ShoppingDao shoppingDao;
    PixabayAPI pixabayAPI;

    @Inject
    public DefaultShoppingRepository(ShoppingDao shoppingDao, PixabayAPI pixabayAPI) {
        this.shoppingDao = shoppingDao;
        this.pixabayAPI = pixabayAPI;
    }

    @Override
    public void insertShoppingItem(@NotNull ShoppingItem shoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem);
    }

    @NotNull
    @Override
    public LiveData<ShoppingItem> observeTupleByPageoffset(int pageOffset) {
        return shoppingDao.observeTupleByPageoffset(pageOffset);
    }

    @NotNull
    @Override
    public ExchangeRateResponse searchForImage() {
        ExchangeRateResponse exchangeRateResponse = null;
        try {
            Response<ExchangeRateResponse> response = pixabayAPI.searchForImage(BuildConfig.API_KEY, "USD");
            if(response.isSuccessful()) {
                exchangeRateResponse = response.body();
            } else {
                Log.e("tag", "searchForImage error" + response.code() );
            }
        } catch(Exception e) {
            Log.e("EXCEPTION", "EXCEPTION:", e);
        }
        return exchangeRateResponse;
    }
}
