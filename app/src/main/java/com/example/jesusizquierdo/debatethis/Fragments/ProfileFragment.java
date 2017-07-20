package com.example.jesusizquierdo.debatethis.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.User;
import com.example.jesusizquierdo.debatethis.DialogFragments.BioDialogFragment;
import com.example.jesusizquierdo.debatethis.LoginActivity;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import static android.R.attr.bitmap;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private static final int RC_PHOTO_PICKER =  2;

    int REQUEST_CODE = 2;
    Context context = getContext();
    ProfileFragment profileFragment = this;
    TextView bio;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final ProgressDialog progressDialog =new ProgressDialog(getContext());
        progressDialog.setMessage("uploading");
        progressDialog.setCancelable(false);
        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            progressDialog.show();
            StorageReference photoRef = storageReference.child(selectedImage.getLastPathSegment());
            photoRef.putFile(selectedImage).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String uri  = String.valueOf(taskSnapshot.getDownloadUrl());
                    String userID = ((MainActivity)getActivity()).getUserID();
                    DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
                    mFirebaseDatabase.child("Users").child(userID).child("photoUrl").setValue(uri);
                }
            }).addOnProgressListener(getActivity(), new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uplaod  " + progress);
                    if(progress >= 95){
                        progressDialog.dismiss();
                    }
                }
            });

        }
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 2) {
//            String editTextString = data.getStringExtra("KEY");
//            Toast.makeText(getContext(), editTextString, Toast.LENGTH_SHORT).show();
//            bio.setText(editTextString);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_profile, container, false);

        ((MainActivity) getContext()).getSupportActionBar().hide();

        final TextView name = (TextView) viewRoot.findViewById(R.id.tv_name_profileFragment);
        Button signOut = (Button) viewRoot.findViewById(R.id.btn_signout_profileFragment);
        bio = (TextView) viewRoot.findViewById(R.id.tv_bio_profileFragment);
        final ImageView profilePic = (ImageView) viewRoot.findViewById(R.id.imageView_profile_profileFragment);

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("user_photos");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userID;
                try{
                    userID = ((MainActivity)getContext()).getUserID();
                    Object imageUrl = dataSnapshot.child("Users").child(userID).child("photoUrl").getValue();

                    if(imageUrl != null){
                        Picasso.with(getContext())
                                .load(imageUrl.toString())
                                .resize(300,300)
                                .centerInside()
                                .noFade()
                                .into(profilePic);
                    }

                    Object bioText = dataSnapshot.child("Users").child(userID).child("userBio").getValue();
                    bio.setText(bioText.toString());


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        name.setText(((MainActivity) getContext()).getUserName());
        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getChildFragmentManager();
                Bundle args = new Bundle();
                String userID = ((MainActivity)getContext()).getUserID();
                args.putString("UserID", userID );
                args.putString("currentBio",bio.getText().toString());

                BioDialogFragment dialogFragment = new BioDialogFragment();
                dialogFragment.setArguments(args);
                int nim = 0;
                dialogFragment.setTargetFragment(profileFragment, REQUEST_CODE);
                dialogFragment.show(fm, "Bio");
            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Upload user Profile ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
               // intent.putExtra(Intent.EXTRA_STREAM, bitmap );

                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getContext()).signOut();
            }
        });
        return viewRoot;
    }

}
