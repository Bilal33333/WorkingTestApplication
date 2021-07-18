package com.taghda.workingtestapplication.repositories;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.taghda.workingtestapplication.data.local.ShoppingDao;
import com.taghda.workingtestapplication.data.local.ShoppingItem;
import com.taghda.workingtestapplication.data.remote.PixabayAPI;
import com.taghda.workingtestapplication.BuildConfig;
import com.taghda.workingtestapplication.data.remote.responses.ExchangeRateResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

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

    ExchangeRateResponse exchangeRateResponse = null;
    @NotNull
    @Override
    public ExchangeRateResponse searchForImage() {
        pixabayAPI.searchForImage(BuildConfig.API_KEY, "USD")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Call<ExchangeRateResponse>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull Call<ExchangeRateResponse> exchangeRateResponse2) {
                        try {
                            Response<ExchangeRateResponse> response =
                                    exchangeRateResponse2.execute();
                            if(response.isSuccessful()){
                                exchangeRateResponse = response.body();
                            }else{
                                Log.e(TAG, "onNext: !response.isSuccessful()" );
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onNext: exchangeRateResponse2: "+exchangeRateResponse2);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.e(TAG, "onError: ",e );
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return exchangeRateResponse;
    }
}
