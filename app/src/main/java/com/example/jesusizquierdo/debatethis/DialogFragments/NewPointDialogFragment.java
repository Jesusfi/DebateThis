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

import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.NewDebate;
import com.example.jesusizquierdo.debatethis.R;

/**
 * Created by Jesus Izquierdo on 7/21/2017.
 */

public class NewPointDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_dialog_add_point, null);
        setCancelable(false);

        final EditText header = (EditText) v.findViewById(R.id.et_point_add_point_dialog);
        final EditText argument = (EditText) v.findViewById(R.id.et_arg_add_point_dialog);
        final EditText source = (EditText) v.findViewById(R.id.et_src_add_point_dialog);

        Button cancel = (Button) v.findViewById(R.id.btn_cancel_add_point_dialog);
        Button done = (Button) v.findViewById(R.id.btn_done_add_point_dialog);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = header.getText().toString().trim();
                String arg = argument.getText().toString().trim();
                String src = source.getText().toString().trim();
                if(TextUtils.isEmpty(title)|| TextUtils.isEmpty(arg) || TextUtils.isEmpty(src)){
                    Toast.makeText(getContext(),"One of the fields is empty",Toast.LENGTH_SHORT).show();
                }else{
                    ((NewDebate)getActivity()).updateRVWithNewPoint(title,arg,src);
                    dismiss();
                }
            }
        });



        builder.setView(v);
        return builder.create();
    }
}
