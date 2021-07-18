package com.taghda.workingtestapplication.ui

import android.app.AlertDialog
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject
import com.taghda.workingtestapplication.ui.ShoppingViewModel
import android.nfc.NfcAdapter
import android.os.Bundle
import com.taghda.workingtestapplication.R
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.taghda.workingtestapplication.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var mDialog: AlertDialog? = null
    private var binding: ActivityMainBinding? = null
    private val viewModel: ShoppingViewModel by viewModels()
    private var mAdapter: NfcAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)

      //  viewModel = ViewModelProvider(this).get(ShoppingViewModel::class.java);
        mDialog = AlertDialog.Builder(this)
            .setNeutralButton("Ok", null).create()
        mAdapter = NfcAdapter.getDefaultAdapter(this)
        viewModel!!.checkAndInitNFC()
        binding!!.btn1.setOnClickListener {
            viewModel!!.setUpFirstBalance(
                1,
                2,
                "onClick: price1 minus 2$"
            )
        }
        binding!!.btn2.setOnClickListener {
            viewModel!!.setUpFirstBalance(
                2,
                5,
                "onClick: price1 minus 5$"
            )
        }
        binding!!.btn3.setOnClickListener { viewModel!!.setBalancesToInit() }
    }

    override fun onResume() {
        super.onResume()
        if (mAdapter != null) {
            if (!mAdapter!!.isEnabled) {
                showNfcSettingsDialog()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mAdapter != null) {
            mAdapter!!.disableForegroundDispatch(this)
            mAdapter!!.disableForegroundNdefPush(this)
        }
    }

    /*this method is used when nfc is not on then tho show a dialog box
     * and click to go setting on phone and tune on the nfc  */
    private fun showNfcSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.nfc_disabled)
        builder.setPositiveButton(android.R.string.ok) { dialogInterface, i ->
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intent)
        }
        builder.setNegativeButton(android.R.string.cancel) { dialogInterface, i -> finish() }
        builder.create().show()
        return
    }
}