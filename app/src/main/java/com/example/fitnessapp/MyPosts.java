package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyPosts extends AppCompatActivity {

    RecyclerView recyclerView;
    PostAdapter postAdapter;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID, fullName;
    String userName = "Muditha Wishwanath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        recyclerView = (RecyclerView)findViewById(R.id.recycleView_MyPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        txtSearch(userID);

    }

    private void txtSearch(String str) {

        FirebaseRecyclerOptions<NewsfeedModel> options =
                new FirebaseRecyclerOptions.Builder<NewsfeedModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("newsfeed").orderByChild("userID").startAt(str).endAt(str+"~"), NewsfeedModel.class)
                        .build();

        postAdapter = new PostAdapter(options);
        postAdapter.startListening();
        recyclerView.setAdapter(postAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        postAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        postAdapter.stopListening();
    }

    public void BacktoHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void createNewPost(View view) {
        Intent intent = new Intent(this, CreatePost.class);
        startActivity(intent);
    }

}