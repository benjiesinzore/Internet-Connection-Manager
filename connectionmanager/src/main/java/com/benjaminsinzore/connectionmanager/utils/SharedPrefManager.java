package com.benjaminsinzore.connectionmanager.utils;
/*
 * Benjamin Sinzore
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    int PRIVATE_MODE = 0;
    SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public String SHARED_PREF_NAME = "pos_pref";
    Context _context;
    public String MONITOR_CONNECTION = "monitorConnection";

    public String DIALOG_STATE_NO_CONNECTION = "initial";


    // Constructor
    public SharedPrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public String getDIALOG_STATE_NO_CONNECTION() {
        if (pref.getString(DIALOG_STATE_NO_CONNECTION, null) != null){

            return pref.getString(DIALOG_STATE_NO_CONNECTION, null);
        }
        return null;
    }

    public void setDIALOG_STATE_NO_CONNECTION(String sDIALOG_STATE_NO_CONNECTION) {

        editor.putString(DIALOG_STATE_NO_CONNECTION, sDIALOG_STATE_NO_CONNECTION);
        editor.apply();
    }


    public String getMONITOR_CONNECTION() {

        if (pref.getString(MONITOR_CONNECTION, null) != null)
            return pref.getString(MONITOR_CONNECTION, null);
        else
            return null;

    }

    public void setMONITOR_CONNECTION(String sMONITOR_CONNECTION) {

        editor.putString(MONITOR_CONNECTION, sMONITOR_CONNECTION);
        editor.apply();
    }
}
