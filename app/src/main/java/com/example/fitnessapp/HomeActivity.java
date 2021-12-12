package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeActivity extends AppCompatActivity {

    EditText inputSearch;
    RecyclerView recyclerView;
    FloatingActionButton floatingbtn;
    FirebaseRecyclerOptions<Car>options;
    FirebaseRecyclerAdapter<Car, MyViewHolderMeal>adapter;
    DatabaseReference ref;
    DrawerLayout drawerLayout;
    ImageView imageView;
    ImageButton imageBtnHome2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        ref = FirebaseDatabase.getInstance().getReference().child("car");
        inputSearch = findViewById(R.id.inputSearch);
        recyclerView = findViewById(R.id.recylerView);
        floatingbtn = findViewById(R.id.floatingbtn);
        drawerLayout = findViewById(R.id.drawer_layout);
        imageView = findViewById(R.id.bar3Menu);
        imageBtnHome2 = findViewById(R.id.imageBtnHome2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
            }
        });

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
                if(s.toString()!=null){
                    LoadData(s.toString());
                }
                else{
                    LoadData("");
                }
            }
        });

        imageBtnHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });
    }

    private void LoadData(String data) {
        Query query = ref.orderByChild("PlanName").startAt(data).endAt(data+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Car>().setQuery(query,Car.class).build();
        adapter = new FirebaseRecyclerAdapter<Car, MyViewHolderMeal>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderMeal holder, @SuppressLint("RecyclerView") int position, @NonNull Car model) {
                holder.PlanName.setText(model.getPlanName());
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(HomeActivity.this, ViewActivity.class);
                        i.putExtra("CarKey", getRef(position).getKey());
                        startActivity(i);
                    }
                });
            }
            @NonNull
            @Override
            public MyViewHolderMeal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
                return new MyViewHolderMeal(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i = new Intent(this, Feedback.class);
                startActivity(i);
                return true;
            case R.id.item2:
                Intent i2 = new Intent(this, All_feedback.class);
                startActivity(i2);
                return true;
            case R.id.item3:
                Intent i3 = new Intent(this, StepCounter.class);
                startActivity(i3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}