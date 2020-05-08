package com.andware.tetravex;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private TextInputEditText mEmailView;
    private View mLoginFormView;
    public DatabaseManager myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(view);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }


    //Pressing back button opens dialog box that asks the user if they wish the exit the application
    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selection) {
                        switch (selection) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                            default:

                                break;
                        }
                    }
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_quit_game));
        builder.setPositiveButton(getResources().getString(R.string.dialog_ok), dialogClickListener);
        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), dialogClickListener);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        builder.show();
    }

    //Checks username is corrent
    private void attemptLogin(View view) {
        // Reset errors.
        mEmailView.setError(null);
        // Store values at the time of the login attempt.
        String username = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        else if (!isAlphaCharactersOnly(username))
        {
            mEmailView.setError(getString(R.string.error_alphabetical_characters_only));
            focusView = mEmailView;
            cancel = true;
        }
        else if (!isEmailLongEnough(username)) {
            mEmailView.setError(getString(R.string.error_username_too_short));
            focusView = mEmailView;
            cancel = true;
        }
        else if (isEmailTooLong(username)){
            mEmailView.setError(getString(R.string.error_username_too_long));
            focusView = mEmailView;
            cancel = true;
        }
        else {
            myDb = new DatabaseManager(this);
            if (myDb.userDoesNotExist(username)){
                myDb.insertUsername(username);
                myDb.insertUnfinished(username);
                Toast.makeText(LoginActivity.this, "New User Added", Toast.LENGTH_LONG).show();
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            Intent intent = new Intent(view.getContext(), MainMenu.class);
            intent.putExtra("username", username);
            startActivityForResult(intent, 0);
        }
    }
    private boolean isAlphaCharactersOnly(String user){
        char[] chars = user.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmailTooLong(String user){
        return user.length() > 9;
    }

    private boolean isEmailLongEnough(String user) {
        return user.length() >= 3;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
    }
}

