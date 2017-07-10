package com.example.jesusizquierdo.debatethis.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jesusizquierdo.debatethis.R;

/**
 * Created by Jesus Izquierdo on 7/9/2017.
 */

public class BioDialogFragment extends DialogFragment {
    EditText bio;
    Button done;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_dialog_write_bio, null);

        bio = (EditText) v.findViewById(R.id.et_bio_dialog);
        done = (Button) v.findViewById(R.id.btn_done_dialog);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(2,bio.getText().toString());
                dismiss();
            }
        });
        builder.setView(v);
        return builder.create();


    }

    private void sendResult(int REQUEST_CODE,String editTextString) {
        Intent intent = new Intent();
        intent.putExtra("KEY",editTextString);
        //intent.putStringExtra(EDIT_TEXT_BUNDLE_KEY, editTextString);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(), REQUEST_CODE, intent);
    }
}
