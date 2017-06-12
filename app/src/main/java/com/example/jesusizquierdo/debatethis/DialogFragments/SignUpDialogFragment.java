package com.example.jesusizquierdo.debatethis.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.LoginActivity;
import com.example.jesusizquierdo.debatethis.R;

/**
 * Created by Jesus Izquierdo on 5/14/2017.
 */

public class SignUpDialogFragment extends DialogFragment {
    EditText firstNameEdit, lastNameEdit, emailEdit, passwordEdit;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_dialog_signup, null);

        firstNameEdit = (EditText) v.findViewById(R.id.et_first_name_signup);
        lastNameEdit = (EditText) v.findViewById(R.id.et_last_name_signup);
        emailEdit = (EditText) v.findViewById(R.id.et_email_signup);
        passwordEdit = (EditText) v.findViewById(R.id.et_password_signup);

        Button signUpButton = (Button) v.findViewById(R.id.btn_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailPassword(firstNameEdit.getText().toString().trim(), lastNameEdit.getText().toString().trim(), emailEdit.getText().toString().trim(), passwordEdit.getText().toString().trim());

            }
        });


        builder.setView(v);
        return builder.create();

    }

    public void checkEmailPassword(String firstName, String lastName, String email, String password) {

        Boolean cancel = false;
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
            Toast.makeText(getActivity(), "Enter First AND Last name", Toast.LENGTH_SHORT).show();
        }

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
            cancel = true;
        } else if (!isPasswordValid(password)) {
            Toast.makeText(getActivity(), "Password too short", Toast.LENGTH_SHORT).show();
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();

            cancel = true;
        } else if (!isEmailValid(email)) {
            Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();

            cancel = true;
        }

        if (!cancel) {
            dismiss();
            ((LoginActivity) getContext()).Signup(email, password, firstName);
        }

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
