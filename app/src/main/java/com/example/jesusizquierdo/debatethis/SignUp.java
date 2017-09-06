package com.example.jesusizquierdo.debatethis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class SignUp extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressDialog;
    // UI references.
    private AutoCompleteTextView emailSignUpView;
    private AutoCompleteTextView firstNameView;
    private AutoCompleteTextView lastNameView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Set up the login form.
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        emailSignUpView = (AutoCompleteTextView) findViewById(R.id.et_email_signup);
        firstNameView = (AutoCompleteTextView) findViewById(R.id.et_firstname_signup);
        lastNameView = (AutoCompleteTextView) findViewById(R.id.et_lastname_signup);
        mPasswordView = (EditText) findViewById(R.id.et_password_signup);
        Button signUp = (Button) findViewById(R.id.btn_signup);
        TextView alreadyMember = (TextView) findViewById(R.id.tv_already_member_signup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        alreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        // Reset errors.
        emailSignUpView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailSignUpView.getText().toString();
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid email address.
        if (TextUtils.isEmpty(firstName)) {
            firstNameView.setError(getString(R.string.error_field_required));
            focusView = firstNameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastName)){
            lastNameView.setError(getString(R.string.error_field_required));
            focusView = lastNameView;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailSignUpView.setError(getString(R.string.error_field_required));
            focusView = emailSignUpView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailSignUpView.setError(getString(R.string.error_invalid_email));
            focusView = emailSignUpView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user signUp attempt.
            SignUp(email,password,firstName,lastName);
        }


    }
    public void SignUp(String email, String password, final String firstName, final String lastName) {
        progressDialog.setMessage("Creating User");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this,R.string.auth_success,Toast.LENGTH_SHORT).show();
                            saveInformation(firstName,lastName);
                            startActivity(new Intent(SignUp.this, MainActivity.class));
                            finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    public void saveInformation(String userFirstName,String userLastName) {

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            User newUser = new User(userFirstName,userLastName,mAuth.getCurrentUser().getEmail(),user.getUid());
            databaseReference.child("Users").child(user.getUid()).setValue(newUser);
            Toast.makeText(SignUp.this, "Information saved", Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(SignUp.this,"Failed to save any information",Toast.LENGTH_SHORT).show();
        }

    }


}

