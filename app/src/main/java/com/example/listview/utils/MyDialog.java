package com.example.listview.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.listview.models.User;

public class MyDialog extends DialogFragment {
    Removable removable;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Removable) {
            removable = (Removable) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        User user = (User) requireArguments().getSerializable("User");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder.setTitle("Внимание")
                .setMessage("Удалить пользователя " + user + " ?")
                .setCancelable(false)
                .setNegativeButton("Нет", (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton("Да", (dialog, i) -> {
                    if (removable != null) removable.remove(user);
                }).create();
    }
}
