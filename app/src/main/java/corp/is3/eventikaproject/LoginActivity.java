package corp.is3.eventikaproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import corp.is3.eventikaproject.adapters.Adapter;
import corp.is3.eventikaproject.adapters.AdapterLoginUser;
import corp.is3.eventikaproject.reuests.CallbackFunction;
import corp.is3.eventikaproject.reuests.QueryDesigner;
import corp.is3.eventikaproject.reuests.QueryManager;
import corp.is3.eventikaproject.services.Services;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPassword;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

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

    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }
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

        Map<String, String> mapParam = new HashMap<>();
        mapParam.put("eMail", email);
        mapParam.put("password", password);
        if (notError) {
            if (isRegistration)
                registration(mapParam);
            else
                authorization(mapParam);

        }
    }

    private void registration(Map<String, String> mapParam) {
        showProgress(true);
        QueryDesigner queryDesigner = new QueryDesigner();
        queryDesigner.setType(QueryDesigner.QUERY_TYPE.ADD_NEW_USER);
        queryDesigner.setConditions(mapParam);
        new QueryManager().query(queryDesigner, new CallbackFunction() {
            @Override
            public void callable(String response) {
                AdapterLoginUser al = new AdapterLoginUser(response, LoginActivity.this);
                if (al.getResultCode() == Adapter.CODE_OK) {
                    Long id = (Long)al.getResult().get(0);
                    if (id != null) {
                        Services.dataManager.getUserData().getInformationFromUser().setId(id);
                        finish();
                    }
                }
                showProgress(false);
            }
        });
    }

    private void authorization(Map<String, String> mapParam) {
        showProgress(true);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
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

