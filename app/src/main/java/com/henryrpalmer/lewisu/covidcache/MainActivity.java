package com.henryrpalmer.lewisu.covidcache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDb;

    private FirebaseRecyclerAdapter<Tracker, TrackerAdapter.TrackerHolder> mAdapter;
    private Query query;
    private static final int RC_SIGN_IN = 123;

    private Button startNowButton;
    private Button customButton;
    private Button shareTextButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setTheme(R.style.Theme_CovidCache)
                    .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))
                    .build(), RC_SIGN_IN);
                }
            }
        };

        RecyclerView recyclerView = findViewById(R.id.tracker_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDb = FirebaseDatabase.getInstance();
        query = mDb.getReference().child("tracker");
        FirebaseRecyclerOptions<Tracker> options = new FirebaseRecyclerOptions.Builder<Tracker>().setQuery(query, Tracker.class).build();
        mAdapter = new TrackerAdapter(options);
        recyclerView.setAdapter(mAdapter);


    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuthListener);
        mAdapter.stopListening();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
        mAdapter.startListening();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED && requestCode == RC_SIGN_IN) {
            finish();
        }
    }


}