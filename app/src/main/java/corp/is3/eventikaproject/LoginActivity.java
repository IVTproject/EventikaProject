package corp.is3.eventikaproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import corp.is3.eventikaproject.adapters.Adapter;
import corp.is3.eventikaproject.adapters.AdapterLoginUser;
import corp.is3.eventikaproject.adapters.AdapterVkLogin;
import corp.is3.eventikaproject.adapters.converters.EventInfoToMap;
import corp.is3.eventikaproject.reuests.CallbackFunction;
import corp.is3.eventikaproject.reuests.QueryDesigner;
import corp.is3.eventikaproject.reuests.QueryManager;
import corp.is3.eventikaproject.services.Services;
import corp.is3.eventikaproject.structures.UserInfo;

public class LoginActivity extends AppCompatActivity {

    private enum SOCIAL_NETWORK {VK}

    ;

    private final String[] sMyScope = new String[]{
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS
    };

    private SOCIAL_NETWORK authSosialNetworkId = SOCIAL_NETWORK.VK;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPassword;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void checkBox(View v) {
        if (mConfirmPassword.getVisibility() == View.GONE)
            mConfirmPassword.setVisibility(View.VISIBLE);
        else
            mConfirmPassword.setVisibility(View.GONE);

    }

    public void authVKClick(View v) {
        VKSdk.wakeUpSession(this);
        if (!VKSdk.isLoggedIn()) {
            authSosialNetworkId = SOCIAL_NETWORK.VK;
            VKSdk.login(this, sMyScope);
        } else {
            authVK();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (authSosialNetworkId) {
            case VK:
                authVK();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void authVK() {
        showProgress(true);
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "id,first_name,last_name,sex,bdate,city,country,photo_400_orig," +
                        "domain,has_mobile,contacts,connections,site," +
                        "universities,status"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                ArrayList<UserInfo> info = new AdapterVkLogin(response.json, LoginActivity.this).getResult();
                if (info != null && info.size() > 0) {
                    Services.dataManager.getUserData().setUserInfo(info.get(0));
                    EventInfoToMap converter = new EventInfoToMap();
                    converter.setUseSocialNetwork(UserInfo.VK);
                    requestId(QueryDesigner.QUERY_TYPE.AUTH_SOCIAL_NETWORK,
                            converter.convert(Services.dataManager.getUserData().getInformationFromUser()));
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                showProgress(true);
            }
        });
    }

    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmPassword.setError(null);
        boolean isRegistration = mConfirmPassword.getVisibility() == View.VISIBLE;
        boolean notError = true;
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(password) || password.length() < 4) {
            mPasswordView.setError(getResources().getString(R.string.error_password));
            notError = false;
        }

        if (isRegistration && !confirmPassword.equals(password)) {
            mConfirmPassword.setError(getResources().getString(R.string.error_confirm_password));
            notError = false;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getResources().getString(R.string.error_email_small));
            notError = false;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getResources().getString(R.string.error_email_not_right));
            notError = false;
        }

        if (notError) {
            Map<String, String> mapParam = new HashMap<>();
            mapParam.put("eMail", email);
            mapParam.put("password", password);
            if (isRegistration)
                requestId(QueryDesigner.QUERY_TYPE.ADD_NEW_USER, mapParam);
            else
                requestId(QueryDesigner.QUERY_TYPE.AUTH_USER, mapParam);
        }
    }

    private void requestId(final QueryDesigner.QUERY_TYPE type, final Map<String, String> mapParam) {
        showProgress(true);
        QueryDesigner queryDesigner = new QueryDesigner();
        queryDesigner.setType(type);
        queryDesigner.setConditions(mapParam);
        new QueryManager().query(queryDesigner, new CallbackFunction() {
            @Override
            public void callable(String response) {
                AdapterLoginUser al = new AdapterLoginUser(response, LoginActivity.this);
                al.getResult();
                if (al.getResultCode() == Adapter.CODE_OK) {
                    Long id = (Long) al.getResult().get(0);
                    String token = (String) al.getResult().get(1);
                    if (id != null) {

                        Services.dataManager.getUserData().getInformationFromUser().setId(id);
                        Services.dataManager.getUserData().getInformationFromUser().setKeyAPI(token);
                        Services.dataManager.getUserData().getInformationFromUser().setEmail(mapParam.get("eMail"));
                        finish();
                    }
                }
                showProgress(false);
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}

