package com.example.rickymorty.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rickymorty.R;

public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ProgressWheel progress;
    public boolean isProgressShowing = false;
    Dialog dialog;


    public void showAlert(String message, String title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);

        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public ProgressWheel getProgress() {
        if (progress == null) {
            progress = new ProgressWheel(this);
        }
        return progress;
    }



    public void showProgressWheel() {

        isProgressShowing = true;
        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if (parent != null) {
            parent.removeView(getProgress());
        }

        FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
        rootLayout.addView(getProgress());
    }

    public void hideProgressWheel(boolean animation) {
        ViewGroup parent = (ViewGroup) getProgress().getParent();
        if (parent != null) {
            parent.removeView(getProgress());
            progress = null;
        }

        if (progress != null) {
            progress = null;
        }
        isProgressShowing = false;

    }
}
