package com.qz.app.easypermissions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by win7 on 2017/1/21.
 */

public class PermissionGrantUtils {
    private static final String TAG = "PermissionGrantUtils";
    public interface PermissionCallbacks extends
            ActivityCompat.OnRequestPermissionsResultCallback {

        void onPermissionsGranted(int requestCode, List<String> perms);

        void onPermissionsDenied(int requestCode, List<String> perms);

    }

    /**
     * 查看是否拥有指定的权限
     * @param context
     * @param perms
     * @return
     */
    public static boolean hasPermissions(Context context,String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");
            return true;
        }

        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) ==
                    PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }

        return true;
    }
    public static void requestPermissions(final Object object, String rationale,
                                          final int requestCode, final String... perms) {
        requestPermissions(object, rationale,
                android.R.string.ok,
                android.R.string.cancel,
                requestCode, perms);
    }
    @SuppressLint("NewApi")
    public static void requestPermissions( final Object object,  String rationale,
                                           int positiveButton,  int negativeButton,
                                          final int requestCode,  final String... perms) {

        checkCallingObjectSuitability(object);

        // Determine if rationale should be shown (generally when the user has previously
        // denied the request);
        boolean shouldShowRationale = false;
        for (String perm : perms) {
            shouldShowRationale =
                    shouldShowRationale || shouldShowRequestPermissionRationale(object, perm);
        }

        if (shouldShowRationale) {
            // If we can get a FragmentManager, show a RationaleDialogFragmentCompat. Otherwise, show
//            // an AlertDialog. The Fragment has the advantage of surviving rotations.
//            if (getSupportFragmentManager(object) != null) {
//                // Show AppCompatDialogFragment
//                showRationaleDialogFragmentCompat(getSupportFragmentManager(object),
//                        rationale, positiveButton, negativeButton, requestCode, perms);
//            } else if (getFragmentManager(object) != null) {
//                // Show DialogFragment
//                showRationaleDialogFragment(getFragmentManager(object),
//                        rationale, positiveButton, negativeButton, requestCode, perms);
//            } else {
                // Revert to AlertDialog
                showRationaleAlertDialog(object, rationale, positiveButton, negativeButton,
                        requestCode, perms);
//            }
        } else {
            executePermissionsRequest(object, perms, requestCode);
        }
    }

    private static void checkCallingObjectSuitability(@Nullable Object object) {
        if (object == null) {
            throw new NullPointerException("Activity or Fragment should not be null");
        }
        // Make sure Object is an Activity or Fragment
        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        boolean isMinSdkM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (!(isSupportFragment || isActivity || (isAppFragment && isMinSdkM))) {
            if (isAppFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
    }
    @TargetApi(23)
    private static boolean shouldShowRequestPermissionRationale( Object object,
                                                                 String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    @TargetApi(23)
    static void executePermissionsRequest( Object object,  String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).requestPermissions(perms, requestCode);
        }
    }


    @Nullable
    @SuppressLint("NewApi")
    private static android.support.v4.app.FragmentManager getSupportFragmentManager(
             Object object) {

        if (object instanceof FragmentActivity) {
            // Support library FragmentActivity
            return ((FragmentActivity) object).getSupportFragmentManager();
        } else if (object instanceof Fragment) {
            // Support library Fragment
            return ((Fragment) object).getChildFragmentManager();
        }

        return null;
    }

    @Nullable
    private static android.app.FragmentManager getFragmentManager( Object object) {
        if (object instanceof Activity) {
            // Framework Activity
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                // Above SDK 11, we can get Fragment manager
                return ((Activity) object).getFragmentManager();
            }
        } else if (object instanceof android.app.Fragment) {
            // Framework Fragment
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                // Above SDK 17, we can get a child Fragment manager
                return ((android.app.Fragment) object).getChildFragmentManager();
            } else {
                // Otherwise, we just return the standard Fragment manager
                return ((android.app.Fragment) object).getFragmentManager();
            }
        }

        return null;

    }


//    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
//    private static void showRationaleDialogFragmentCompat(
//             final android.support.v4.app.FragmentManager fragmentManager,
//             String rationale,  int positiveButton,  int negativeButton,
//            final int requestCode,  final String... perms) {
//
//        RationaleDialogFragmentCompat fragment = RationaleDialogFragmentCompat
//                .newInstance(positiveButton, negativeButton, rationale, requestCode, perms);
//        fragment.show(fragmentManager, DIALOG_TAG);
//    }


//    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//    private static void showRationaleDialogFragment(
//            final android.app.FragmentManager fragmentManager,
//            String rationale,int positiveButton,int negativeButton,
//            final int requestCode, final String... perms) {
//
//        RationaleDialogFragment fragment = RationaleDialogFragment
//                .newInstance(positiveButton, negativeButton, rationale, requestCode, perms);
//        fragment.show(fragmentManager, DIALOG_TAG);
//    }
    /**
     * Show an {@link AlertDialog} explaining permission request rationale.
     */
    private static void showRationaleAlertDialog(
             final Object object,  String rationale,
             int positiveButton,  int negativeButton,
            final int requestCode,  final String... perms) {

        Activity activity = getActivity(object);
        if (activity == null) {
            throw new IllegalStateException("Can't show rationale dialog for null Activity");
        }

        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setMessage(rationale)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executePermissionsRequest(object, perms, requestCode);
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // act as if the permissions were denied
                        if (object instanceof PermissionCallbacks) {
                            ((PermissionCallbacks) object).onPermissionsDenied(requestCode, Arrays.asList(perms));
                        }
                    }
                })
                .create()
                .show();
    }

    @TargetApi(11)
    private static Activity getActivity( Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }
    public static void onRequestPermissionsResult(int requestCode,  String[] permissions,
                                                   int[] grantResults,
                                                   Object... receivers) {

        // Make a collection of granted and denied permissions from the request.
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        // iterate through all receivers
        for (Object object : receivers) {
            // Report granted permissions, if any.
            if (!granted.isEmpty()) {
                if (object instanceof PermissionCallbacks) {
                    ((PermissionCallbacks) object).onPermissionsGranted(requestCode, granted);
                }
            }
            // Report denied permissions, if any.
            if (!denied.isEmpty()) {
                if (object instanceof PermissionCallbacks) {
                    ((PermissionCallbacks) object).onPermissionsDenied(requestCode, denied);
                }
            }
        }

    }

    public static boolean somePermissionPermanentlyDenied( Object object,
                                                          List<String> deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (permissionPermanentlyDenied(object, deniedPermission)) {
                return true;
            }
        }

        return false;
    }
    public static boolean permissionPermanentlyDenied( Object object,
                                                      String deniedPermission) {
        return !shouldShowRequestPermissionRationale(object, deniedPermission);
    }

}
