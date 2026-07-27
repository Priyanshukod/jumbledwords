package com.stupendous.jumbledwords;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;

/**
 * Created by vinodtakhar on 28/4/16.
 */
public class BaseActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private String permissionBeingAsked;
    private int clientRequestCode;

    //private InterstitialAd mInterstitialAd;
    private ProgressDialog progressDialog;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstial_id));

//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                requestNewInterstitial();
//            }
//        });

   //     requestNewInterstitial();
    }

//    protected void showInterstitial(){
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
//    }

    protected void initBanner() {
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
               // .addTestDevice("69EB1DA1ED362DB39724BB7BFA35F3AB")
               // .addTestDevice("69EB1DA1ED362DB39724BB7BFA35F3AB")
                .build();
        mAdView.loadAd(adRequest);
       // new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("4AB7E178A6DA09095DAC8021645AC5FC"));
    }

//    private void requestNewInterstitial() {
//        AdRequest adRequest = new AdRequest.Builder()
//               // .addTestDevice("69EB1DA1ED362DB39724BB7BFA35F3AB")
//               // .addTestDevice("69EB1DA1ED362DB39724BB7BFA35F3AB")
//                .build();
//
//        mInterstitialAd.loadAd(adRequest);
//    }

    protected void requestPermission(int requestCode,String permission) {

        permissionBeingAsked = permission;
        clientRequestCode = requestCode;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission) || !AppPreferences.getBooleanSharedPreference(this, permission, false)) {
                    AppPreferences.setBooleanSharedPreference(this, permission, true); /*set  that we have already asked the permission to handle rational*/

                    ActivityCompat.requestPermissions(this,
                            new String[]{permission},
                            PERMISSIONS_REQUEST_CODE);
                } else
                    Utility.showPermissionRequiredDialog(this, "Permission Required", "Please grant required permissions in Application Settings under Permissions", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utility.openAppSettings(BaseActivity.this);
                        }
                    });
            } else {
                onPermissionGranted(clientRequestCode, permissionBeingAsked);
            }
        }else {
            onPermissionGranted(clientRequestCode, permissionBeingAsked);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onPermissionGranted(clientRequestCode,permissionBeingAsked);
                else
                    onPermissionDenied(clientRequestCode,permissionBeingAsked);
                break;
        }
    }

    public void onPermissionGranted(int requstCode,String grantedPermission) {
    }

    public void onPermissionDenied(int requestCode,String deniedPermission) {
    }

    protected void hideProgress() {
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected void showProgress(String s) {
        progressDialog = ProgressDialog.show(this,"",s,true,false);
    }

    protected void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
