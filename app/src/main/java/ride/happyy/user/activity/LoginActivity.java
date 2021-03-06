package ride.happyy.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ride.happyy.user.R;
import ride.happyy.user.app.App;
import ride.happyy.user.listeners.LoginListener;
import ride.happyy.user.model.AuthBean;
import ride.happyy.user.net.DataManager;
import ride.happyy.user.util.AppConstants;

public class LoginActivity extends BaseAppCompatNoDrawerActivity {

    private EditText etxtUserName,etxPhone;
    private EditText etxtPassword;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private String TAG = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);
//        lytBase.setFitsSystemWindows(false);

        initViews();
    }

    public void initViews() {

        etxtUserName = (EditText) findViewById(R.id.etxt_login_email);
        etxPhone = (EditText) findViewById(R.id.etxt_login_phone);
        etxtPassword = (EditText) findViewById(R.id.etxt_login_password);

        etxtPassword.setTypeface(typeface);
        etxtUserName.setTypeface(typeface);
        etxPhone.setTypeface(typeface);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            onBackPressed();
        }
        return true;
    }

    public void onLoginButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (collectLoginData()) {
            if (App.isNetworkAvailable()) {
                performLogin();
            } else {
                Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean collectLoginData() {

        if (etxPhone.getText().toString().equals("") | etxPhone.getText().length()!=11) {
            Snackbar.make(coordinatorLayout, "Phone is requered and valid!", Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        if (etxtPassword.getText().toString().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_password_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }


    public void performLogin() {

        swipeView.setRefreshing(true);
        JSONObject postData = getLoginJSObj();

        DataManager.performLogin(postData, new LoginListener() {
            @Override
            public void onLoadCompleted(AuthBean authBean) {
                swipeView.setRefreshing(false);
                authBean.setPhoneVerified(true);
                App.saveToken(authBean);

                Log.i(TAG, "onLoadCompleted: UserId " + authBean.getUserID());

                Toast.makeText(getApplicationContext(), R.string.message_login_is_successful,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, LandingPageActivity.class));
                finish();

            }

            @Override
            public void onLoadFailed(String error) {

                Snackbar.make(coordinatorLayout, R.string.message_wrong_password_or_mobile_number, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                swipeView.setRefreshing(false);

            }
        });
    }

    private JSONObject getLoginJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("phone", "+88"+etxPhone.getText().toString());
            postData.put("password", etxtPassword.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onForgotPasswordClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);
        Intent intentForgetPassword =new Intent(this,RegistrationActivity.class);
        intentForgetPassword.putExtra("forgetpass", "forgetpass");

        startActivity(new Intent(intentForgetPassword));
    }
}

