package com.lpf.common.interfaces;

import android.content.DialogInterface;

/**
 * Created by liupengfei on 17/2/7.
 * dialog interface for confirm/cancel
 */

public interface AlertDialogInterface {

    public void alertConfirm(DialogInterface dialog);

    public void alertCancel(DialogInterface dialog);
}
