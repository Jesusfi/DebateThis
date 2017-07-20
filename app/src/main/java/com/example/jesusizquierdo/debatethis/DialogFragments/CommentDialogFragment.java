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

import com.example.jesusizquierdo.debatethis.FullDiscussion;
import com.example.jesusizquierdo.debatethis.R;

/**
 * Created by Jesus Izquierdo on 7/20/2017.
 */

public class CommentDialogFragment extends DialogFragment {
    EditText comment;
    Button done;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_dialog_write_comment, null);

        comment = (EditText) v.findViewById(R.id.et_comment_dialog);
        done = (Button) v.findViewById(R.id.btn_done_comment_dialog);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(comment.getText().toString().trim())) {
                    ((FullDiscussion)getActivity()).saveComment(comment.getText().toString().trim());
                    dismiss();
                }
            }
        });

        builder.setView(v);
        return builder.create();
    }
}
