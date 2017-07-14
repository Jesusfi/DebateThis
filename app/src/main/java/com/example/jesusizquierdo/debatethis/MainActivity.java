package com.example.jesusizquierdo.debatethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Articles;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.Classes.User;
import com.example.jesusizquierdo.debatethis.Fragments.FullDescriptionFragment;
import com.example.jesusizquierdo.debatethis.Fragments.HomePageFragment;
import com.example.jesusizquierdo.debatethis.Fragments.NewDebateFragment;
import com.example.jesusizquierdo.debatethis.Fragments.NewDiscussionFragment;
import com.example.jesusizquierdo.debatethis.Fragments.NewsFragment;
import com.example.jesusizquierdo.debatethis.Fragments.ProfileFragment;
import com.example.jesusizquierdo.debatethis.Fragments.WebViewFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    User person;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.navigation_signOut) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (i == android.R.id.home) {
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            fragmentManager1.popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                person = dataSnapshot.child("Users").child(user.getUid()).getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final HomePageFragment mainPageFragment = new HomePageFragment();
        final ProfileFragment profileFragment = new ProfileFragment();
        fragmentManager.beginTransaction()
                .add(R.id.content, mainPageFragment)
                .commit();
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, mainPageFragment)
                                .commit();
                        return true;
                    case R.id.navigation_news:
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, new NewsFragment())
                                .commit();
                        return true;
                    case R.id.navigation_notifications:
                        fragmentManager.beginTransaction()
                                .replace(R.id.content, profileFragment)
                                .commit();
                        return true;

                }
                return false;
            }

        });
    }

    public void startWebViewFragment(String url) {

        FragmentManager manager = getSupportFragmentManager();
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("KEY", url);
        webViewFragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.content, webViewFragment)
                .addToBackStack(null)
                .commit();

    }

    public void startNewDiscussionFragment(Articles articles) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NewDiscussionFragment newDiscussionFragment = new NewDiscussionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Articles", articles);
        newDiscussionFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.content, newDiscussionFragment)
                .addToBackStack(null)
                .commit();

    }

    public void startFullDiscussionFragment(DiscussionCard discussionCard) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable("DiscussionCard", discussionCard);
        FullDescriptionFragment fullDescriptionFragment = new FullDescriptionFragment();
        fullDescriptionFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.content, fullDescriptionFragment)
                .addToBackStack(null)
                .commit();
    }

    public void startNewDebateFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FullDescriptionFragment fullDescriptionFragment = new FullDescriptionFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.content, new NewDebateFragment())
                .addToBackStack(null)
                .commit();
    }

    public void startCategoriesFragment() {

    }

    public String getUserName() {
        return person.getFirstName() + " " + person.getLastName();
    }
    public String getUserID(){
        return person.getCredentails();
    }

    public void signOut(){
        firebaseAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
