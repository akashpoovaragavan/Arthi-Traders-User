package com.arthi.traders.constant;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class Helper {
    public static ProgressBarHandler mProgressBarHandler;
    public static SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "arthitradersuser" ;
    public static String onback = "";

    /**************************API***********************************/
    public static String login_url              = "https://treevibestech.com/ats/api/login_api.php";
    public static String product_url            = "https://treevibestech.com/ats/api/prod_api.php";
    public static String requestview_url        = "https://treevibestech.com/ats/api/request_view.php";
    public static String requestentry_url       = "https://treevibestech.com/ats/api/requests.php";
    public static String orderentry_url         = "https://treevibestech.com/ats/api/test.php";
    public static String paymenthistory_url     = "https://treevibestech.com/ats/api/payment_history_api.php";
    public static String orderhistory_url       = "https://treevibestech.com/ats/api/order_history_api.php";
    public static String payment_url            = "https://treevibestech.com/ats/api/payments.php";
    public static String balance_url            = "https://treevibestech.com/ats/api/balance.php";
    public static String category_url           = "https://treevibestech.com/ats/api/cat_api.php";
    public static String imageurl_url           = "https://treevibestech.com/ats/doc/";
    public static String notificationlist_url   = "https://treevibestech.com/ats/api/push_his.php";
    public static String orderstatus_url        = "https://treevibestech.com/ats/api/cart_history.php";
    public static String offers_url             = "https://treevibestech.com/ats/api/banner.php";


    public static void loading(Activity activity) {
        mProgressBarHandler = new ProgressBarHandler(activity); // In onCreate
        mProgressBarHandler.show(); // To show the progress bar
    }
    public static class ProgressBarHandler {
        private ProgressBar mProgressBar;
        private Context mContext;

        public ProgressBarHandler(Activity context) {
            mContext = context;
            ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();
            mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
            mProgressBar.setIndeterminate(true);
            RelativeLayout.LayoutParams params = new
                    RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            RelativeLayout rl = new RelativeLayout(context);
            rl.setGravity(Gravity.CENTER);
            rl.addView(mProgressBar);
            layout.addView(rl, params);
            hide();
        }
        public void show() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        public void hide() {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
