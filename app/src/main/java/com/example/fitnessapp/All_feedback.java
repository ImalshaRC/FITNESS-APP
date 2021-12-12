package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class All_feedback extends AppCompatActivity {

    EditText inputSearch;
    RecyclerView recyclerView;
    FloatingActionButton floatingbtn;
    FirebaseRecyclerOptions<FeedbackClass>Foptions;
    FirebaseRecyclerAdapter<FeedbackClass, FeedbackHolder> Fadapter;
    DatabaseReference ref;
    ImageButton imageBtnfeedAll, FloatingReviews;
    TextView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_feedback);
        //getSupportActionBar().setTitle("Feedback page");

        ref = FirebaseDatabase.getInstance().getReference().child("feedback");
        inputSearch = findViewById(R.id.inputSearch_feedback);
        recyclerView = findViewById(R.id.recylerView_feedback);
        floatingbtn = findViewById(R.id.floatingbtn_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        imageBtnfeedAll = findViewById(R.id.imageBtnFeedAll);
        floatingbtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Feedback.class)));
        FloatingReviews = findViewById(R.id.FloatingReviews);

        LoadData("");

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                s.toString();
                LoadData(s.toString());
            }
        });

        imageBtnfeedAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });

        FloatingReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(All_feedback.this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
            }
        });
    }
    private void LoadData(String data) {
        Query query = ref.orderByChild("AutoFeed").startAt(data).endAt(data+"\uf8ff");
        Foptions = new FirebaseRecyclerOptions.Builder<FeedbackClass>().setQuery(query,FeedbackClass.class).build();

        Fadapter = new FirebaseRecyclerAdapter<FeedbackClass, FeedbackHolder>(Foptions) {
            @Override
            protected void onBindViewHolder(@NonNull FeedbackHolder holder, @SuppressLint({ "RecyclerView"}) int position, @NonNull FeedbackClass model) {
                holder.AutoFeed.setText(model.getAutoFeed());
                holder.Value.setText(model.getValue());
                holder.Feedback.setText(model.getFeedback());

                holder.vv.setOnClickListener(view -> {
                    Intent i = new Intent(All_feedback.this, ViewFeedback.class);
                    i.putExtra("FeedKey", getRef(position).getKey());
                    startActivity(i);

                });

                //a.setText("sss");

            }

            @NonNull
            @Override
            public FeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_feedback,parent,false);
                return new FeedbackHolder(v);
            }
        };
        Fadapter.startListening();
        recyclerView.setAdapter(Fadapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i = new Intent(this, Feedback.class);
                startActivity(i);
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}