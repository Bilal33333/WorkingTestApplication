package com.taghda.workingtestapplication.repositories;

import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.util.Log;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

public class DefaultNfcHelperReppository implements NfcHelperRepository{
    private static final String TAG = DefaultNfcHelperReppository.class.getSimpleName();

    @Inject
    public DefaultNfcHelperReppository() {
    }

    @Override
    public void writeTag(Tag tag,int pageOffset, int tagText) {
        MifareUltralight ultralight = MifareUltralight.get(tag);
        try {
            ultralight.connect();
            ultralight.writePage(pageOffset, String.valueOf(tagText).getBytes(Charset.forName("US-ASCII")));
             } catch (IOException e) {
            Log.e(TAG, "IOException while writing MifareUltralight...", e);
        } finally {
            try {
                ultralight.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException while closing MifareUltralight...", e);
            }
        }
    }

    @Override
    public int readTag(Tag tag, int pageOffset) {
        MifareUltralight mifare = MifareUltralight.get(tag);
        try {
            mifare.connect();
            byte[] payload = mifare.readPages(pageOffset);
            return Integer.parseInt(new String(payload, Charset.forName("US-ASCII")));
        } catch (IOException e) {
            Log.e(TAG, "IOException while reading MifareUltralight message...", e);
        } finally {
            if (mifare != null) {
                try {
                    mifare.close();
                }
                catch (IOException e) {
                    Log.e(TAG, "Error closing tag...", e);
                }
            }
        }
        return 0;
    }
}