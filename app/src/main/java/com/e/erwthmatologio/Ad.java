//package com.e.erwthmatologio;
//
//import android.content.Context;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
//
///**
// * Κλάση για την προβολή διαφήμισης banner. Περιλαμβάνει μία static συνάρτηση.
// */
//public class Ad
//{
//    /**
//     * Προβάλει adMob Ad banner
//     * @param co: context
//     * @param adView: layout view
//     */
//
//    // WHILE TESTING USE TEST ADS
//    public static void showAd(Context co, AdView adView)
//    {
//
//        MobileAds.initialize(co, new OnInitializationCompleteListener()
//        {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus)
//            {
//                // το τίποτα
//            }
//        });
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//    }
//}
