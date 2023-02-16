package com.archivo.Animation;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.archivo.app.R;

public class progressDialog extends Dialog {


    public progressDialog(@NonNull Context context) {
        super(context);

        WindowManager.LayoutParams layoutParameters = getWindow().getAttributes();

        layoutParameters.gravity = Gravity.CENTER_HORIZONTAL;

        getWindow().setAttributes(layoutParameters);

        setTitle(null);

        setCancelable(false);

        setOnCancelListener(null);

        View view = LayoutInflater.from(context).inflate(R.layout.progressdialog_customloading, null);

        setContentView(view);
    }
}
