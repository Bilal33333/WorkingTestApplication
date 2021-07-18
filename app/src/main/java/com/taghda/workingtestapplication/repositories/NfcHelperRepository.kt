package com.taghda.workingtestapplication.repositories

import android.nfc.Tag

interface NfcHelperRepository {


    fun writeTag(tag: Tag?, pageOffset: Int, tagText: Int)

    fun readTag(tag: Tag?, pageOffset: Int): Int
}