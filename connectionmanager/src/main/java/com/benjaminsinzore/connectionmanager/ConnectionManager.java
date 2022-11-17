package com.benjaminsinzore.connectionmanager;
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


import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.benjaminsinzore.connectionmanager.monitorconnection.LiveDataClassMonitorCon;
import com.benjaminsinzore.connectionmanager.utils.SharedPrefManager;
import com.bumptech.glide.Glide;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionManager extends AppCompatActivity {


    public AtomicBoolean connection (Context context,
                                     Application application,
                                     String connectedMessage,
                                     String disconnectedMessage,
                                     boolean showMessageDialog
    ){


        SharedPrefManager pref = new SharedPrefManager(context);
        AtomicBoolean returnValue = new AtomicBoolean(false);


        LiveDataClassMonitorCon connectionLiveData = new LiveDataClassMonitorCon(application);
        connectionLiveData.observe((LifecycleOwner) context, connectionModel -> {

            assert connectionModel != null;
            if (connectionModel.getIsConnected()) {

                returnValue.set(true);

                if (Objects.equals(pref.getMONITOR_CONNECTION(), "disconnected")){
                    boolean state = true;
                    pref.setMONITOR_CONNECTION("connected");

                    if (showMessageDialog){
                        showMyDialog(connectedMessage, context, state);
                    }

                }


            } else {


                returnValue.set(false);
                boolean state = false;
                pref.setMONITOR_CONNECTION("disconnected");

                if (showMessageDialog){
                    showMyDialog(disconnectedMessage, context, state);
                }

            }
        });

        return returnValue;
    }

    private void showMyDialog(String message, Context context, boolean state) {


        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.my_dialog);
        Window window = dialog.getWindow();
        ImageView iv = dialog.findViewById(R.id.ivConnection);
        TextView tx = dialog.findViewById(R.id.message);

        TextView okBtn = dialog.findViewById(R.id.ok_btn);
        okBtn.setVisibility(View.GONE);
        tx.setText(message);


        window.setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        if (state){

            Glide.with(context).load(R.drawable.wifi).error(R.drawable.ic_error)
                    .into(iv);

            new CountDownTimer(5000, 5000) {


                public void onTick(long millisUntilFinished) {


                }

                public void onFinish() {

                    dialog.dismiss();
                }
            }.start();

        } else {


            Glide.with(context).load(R.drawable.no_wifi).error(R.drawable.ic_error)
                    .into(iv);

            okBtn.setVisibility(View.VISIBLE);
            okBtn.setOnClickListener(v -> dialog.dismiss());

            new CountDownTimer(30000, 30000) {


                public void onTick(long millisUntilFinished) {


                }

                public void onFinish() {

                    dialog.dismiss();
                }
            }.start();

        }

    }
}
