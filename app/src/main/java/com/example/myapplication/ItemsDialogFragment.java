package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ItemsDialogFragment extends DialogFragment {
    private static final String TAG = "ItemsDialogFragment";
    private String title;
    private String[] items;
    private DialogInterface.OnClickListener onClickListener;

    public void show(String title, String[] items, FragmentManager fragmentManager,
                     DialogInterface.OnClickListener onClickListener
                     ){
        this.title = title;
        this.items = items;
        this.onClickListener = onClickListener;
        show(fragmentManager,TAG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setItems(items,onClickListener);
        return builder.create();
    }
}
