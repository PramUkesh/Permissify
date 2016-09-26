package com.holidaycheck.permissify;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.HashMap;

public class PermissifyConfig {

    private static PermissifyConfig sInstance;

    private PermissionCallOptions defaultPermissionCallOptions;
    private HashMap<String, DialogText> defaultTextForPermissions;
    private DialogText permissionTextFallback;
    private AlertDialogFactory rationaleDialogFactory;
    private AlertDialogFactory denyDialogFactory;

    static PermissifyConfig get() {
        if (sInstance == null) {
            throw new RuntimeException("Permissify is not initialized");
        }

        return sInstance;
    }

    private PermissifyConfig() {
    }

    public static class Builder {

        private PermissifyConfig instance = new PermissifyConfig();

        public Builder withDefaultPermissionCallOptions(PermissionCallOptions callOptions) {
            instance.defaultPermissionCallOptions = callOptions;
            return this;
        }

        public Builder withDefaultTextForPermissions(HashMap<String, DialogText> wording) {
            instance.defaultTextForPermissions = wording;
            return this;
        }

        public Builder withPermissionTextFallback(DialogText dialogText) {
            instance.permissionTextFallback = dialogText;
            return this;
        }

        public Builder withDialogRationaleDialogFactory(AlertDialogFactory factory) {
            instance.rationaleDialogFactory = factory;
            return this;
        }

        public Builder withDenyDialogFactory(AlertDialogFactory factory) {
            instance.denyDialogFactory = factory;
            return this;
        }

        public PermissifyConfig build() {
            if (instance.denyDialogFactory == null) {
                instance.denyDialogFactory = PermissionDeniedInfoDialogFragment.getDefaultDialogFactory();
            }

            if (instance.rationaleDialogFactory == null) {
                instance.rationaleDialogFactory = PermissionRationaleDialogFragment.getDefaultDialogFactory();
            }

            if (instance.defaultPermissionCallOptions == null) {
                instance.defaultPermissionCallOptions = new PermissionCallOptions.Builder()
                    .withDefaultDenyDialog(true)
                    .withDefaultRationaleDialog(true)
                    .build();
            }

            if (instance.permissionTextFallback == null) {
                instance.permissionTextFallback = new DialogText(R.string.permissify_no_text_fallback, R.string.permissify_no_text_fallback);
            }

            return instance;
        }
    }

    public static void initDefault(PermissifyConfig calligraphyConfig) {
        sInstance = calligraphyConfig;
    }

    PermissionCallOptions getDefaultPermissionCallOptions() {
        return defaultPermissionCallOptions;
    }

    DialogText getPermissionTextFallback() {
        return permissionTextFallback;
    }

    HashMap<String, DialogText> getDefaultTextForPermissions() {
        return defaultTextForPermissions;
    }

    AlertDialogFactory getRationaleDialogFactory() {
        return rationaleDialogFactory;
    }

    AlertDialogFactory getDenyDialogFactory() {
        return denyDialogFactory;
    }

    public interface AlertDialogFactory {
        AlertDialog createDialog(Context context, String dialogMsg, DialogInterface.OnClickListener onClickListener);
    }

}
