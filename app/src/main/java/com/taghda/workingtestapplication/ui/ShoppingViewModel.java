package com.taghda.workingtestapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.taghda.workingtestapplication.data.local.ShoppingItem;
import com.taghda.workingtestapplication.data.remote.responses.ExchangeRateResponse;
import com.taghda.workingtestapplication.repositories.DefaultNfcHelperReppository;
import com.taghda.workingtestapplication.repositories.NfcHelperRepository;
import com.taghda.workingtestapplication.repositories.ShoppingRepository;

import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.qualifiers.ApplicationContext;

import static android.content.ContentValues.TAG;

class ShoppingViewModel extends ViewModel {

    ShoppingRepository repository;
    NfcHelperRepository nfcHelperRepository;
    private MutableLiveData<ShoppingItem> _price ;
    LiveData<ShoppingItem> price  = _price;
    private NfcAdapter mAdapter;
    Context context;

    @ViewModelInject
    public ShoppingViewModel(
            ShoppingRepository repository
            ,@ApplicationContext Context context
            , NfcHelperRepository nfcHelperRepository) {
        this.repository = repository;
        this.nfcHelperRepository = nfcHelperRepository;
        this.context = context;
    }

    public void insertShoppingItemIntoDb( ShoppingItem shoppingItem) {
        repository.insertShoppingItem(shoppingItem);
        }

        public ExchangeRateResponse getRates_from_api() {
        return repository.searchForImage();

        }

        public void checkAndInitNFC() {
        mAdapter = NfcAdapter.getDefaultAdapter(context);

            if (mAdapter == null) {
                showMessage("error", "no_nfc");
                new MainActivity().finish();
            }else {
                Tag tagFromIntent = new MainActivity().getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
                if (String.valueOf(nfcHelperRepository.readTag(tagFromIntent, 1)) == null) {
                    nfcHelperRepository.writeTag(tagFromIntent, 1, 20);
                    nfcHelperRepository.writeTag(tagFromIntent, 2, 10);
                }
            }
    }

    private void showMessage(String title, String message) {
        Toast.makeText(context, title + " - " + message, Toast.LENGTH_LONG).show();
    }
    public void setBalancesToInit() {
        /*Tag tagFromIntent = new MainActivity().getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
        nfcHelperRepository.writeTag(tagFromIntent,1 ,20);
        nfcHelperRepository.writeTag(tagFromIntent,2 ,10);
        */ExchangeRateResponse exchangeRateResponse = getRates_from_api();
        ShoppingItem shoppingItem = new ShoppingItem(1,1,10
        , 10
        , exchangeRateResponse.getRates().getEUR() * (10)
        ,1);
        insertShoppingItemIntoDb(shoppingItem);
        ShoppingItem shoppingItem2 = new ShoppingItem(1,1,20
        , 20
        , exchangeRateResponse.getRates().getEUR() * (20)
        ,1);
        insertShoppingItemIntoDb(shoppingItem2);
        Log.d(TAG, "onClick: return to initial values!!");
        }

public void setUpFirstBalance(int i, int i2, String s) {
    Tag tagFromIntent = new MainActivity().getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
    int price = nfcHelperRepository.readTag(tagFromIntent, i);
        nfcHelperRepository.writeTag(tagFromIntent, i, price - i2);
        ExchangeRateResponse exchangeRateResponse = getRates_from_api();
        ShoppingItem shoppingItem = new ShoppingItem(i, i, price
        , price - i2
        , exchangeRateResponse.getRates().getEUR() * (price - i2)
        , i);
        insertShoppingItemIntoDb(shoppingItem);

        Log.d(TAG, s);
        }

        }



