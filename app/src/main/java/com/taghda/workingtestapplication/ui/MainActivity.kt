package com.taghda.workingtestapplication.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.taghda.workingtestapplication.R;
import com.taghda.workingtestapplication.data.local.ShoppingItem;
import com.taghda.workingtestapplication.data.remote.responses.ExchangeRateResponse;
import com.taghda.workingtestapplication.databinding.ActivityMainBinding;
import com.taghda.workingtestapplication.repositories.DefaultNfcHelperReppository;

import dagger.hilt.android.AndroidEntryPoint;

import static android.content.ContentValues.TAG;
import static com.taghda.workingtestapplication.databinding.ActivityMainBinding.inflate;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private AlertDialog mDialog;
    private ActivityMainBinding binding;
    private ShoppingViewModel viewModel;
    private NfcAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);
        mDialog = new AlertDialog.Builder(this)
                .setNeutralButton("Ok", null).create();


        mAdapter = NfcAdapter.getDefaultAdapter(this);
        viewModel.checkAndInitNFC();

        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setUpFirstBalance(1, 2, "onClick: price1 minus 2$");
            }
        });

        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setUpFirstBalance(2, 5, "onClick: price1 minus 5$");
            }
        });

        binding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBalancesToInit();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                showNfcSettingsDialog();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
            mAdapter.disableForegroundNdefPush(this);
        }
    }
    /*this method is used when nfc is not on then tho show a dialog box
     * and click to go setting on phone and tune on the nfc  */
    private void showNfcSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.nfc_disabled);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.create().show();
        return;
    }

}