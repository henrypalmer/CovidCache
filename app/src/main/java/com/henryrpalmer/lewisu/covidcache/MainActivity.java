package com.henryrpalmer.lewisu.covidcache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDb;

    private FirebaseRecyclerAdapter<Tracker, TrackerAdapter.TrackerHolder> mAdapter;
    private Query query;
    private static final int RC_SIGN_IN = 123;

    private Button startNowButton;
    private Button clearTrackersButton;
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

        startNowButton = findViewById(R.id.start_now);
        startNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                startActivity(intent);
            }
        });



        clearTrackersButton = findViewById(R.id.clear_trackers);
        clearTrackersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb.getReference().child("tracker").removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Successfully removed", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        });


        shareTextButton = findViewById(R.id.textShare);

        searchButton = findViewById(R.id.mapSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri testing = Uri.parse("geo:0,0?q=COVID+Testing+Near+Me");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, testing);
                startActivity(mapIntent);
            }
        });


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

    public void shareTextClick(View v) {
        String text = "I am tracking my COVID exposures with CovidCache! Check it out in the appstore!";
        String mimeType = "text/plain";
        String title = "Sharing Symptoms";
        ShareCompat.IntentBuilder.from(this).setType(mimeType).setChooserTitle(title).setText(text).startChooser();
    }

}