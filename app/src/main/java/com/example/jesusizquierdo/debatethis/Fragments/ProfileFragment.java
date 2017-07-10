package com.example.jesusizquierdo.debatethis.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.DialogFragments.BioDialogFragment;
import com.example.jesusizquierdo.debatethis.LoginActivity;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    int REQUEST_CODE = 2;
    Context context = getContext();
    ProfileFragment profileFragment = this;
    TextView bio;
    FirebaseAuth firebaseAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            String editTextString = data.getStringExtra("KEY");
            Toast.makeText(getContext(),editTextString,Toast.LENGTH_SHORT).show();
            bio.setText(editTextString);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot =  inflater.inflate(R.layout.fragment_profile, container, false);
        ((MainActivity)getContext()).getSupportActionBar().hide();
        TextView name = (TextView) viewRoot.findViewById(R.id.tv_name_profileFragment);
        Button signOut = (Button) viewRoot.findViewById(R.id.btn_signout_profileFragment);
        bio = (TextView) viewRoot.findViewById(R.id.tv_bio_profileFragment);
        name.setText(((MainActivity)getContext()).getUserName());
        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getChildFragmentManager();
                BioDialogFragment dialogFragment = new BioDialogFragment();
                dialogFragment.setTargetFragment(profileFragment, REQUEST_CODE);
                dialogFragment.show(fm,"Bio");
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).signOut();
            }
        });
        return viewRoot;
    }

}
