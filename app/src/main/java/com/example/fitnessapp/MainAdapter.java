package com.example.fitnessapp;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<NewsfeedModel, MainAdapter.myViewHolder> {

    DatabaseReference likeReference;
    Boolean testClick = false;
    StorageReference storageReference;
    private FirebaseAuth mAuth;
    String userID;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<NewsfeedModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull NewsfeedModel model) {
        holder.username.setText(model.getUsername());
        holder.description.setText(model.getDescription());
        userID = model.getUserID();

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+ userID +"/profile.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.userImage);
            }
        });


        Glide.with(holder.postImage.getContext())
                .load(model.getPostImage())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.postImage);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseUser.getUid();
        String postkey = getRef(position).getKey();
        holder.getLikeButtonStatus(postkey, userID);

        likeReference = FirebaseDatabase.getInstance().getReference("likes");

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testClick = true;
                likeReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(testClick==true) {
                            if(snapshot.child(postkey).hasChild(userID)) {
                                likeReference.child(postkey).removeValue();
                            } else  {
                                likeReference.child(postkey).child(userID).setValue(true);
                            }
                            testClick = false;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView username, description, likeTxt;
        ImageView postImage;
        ImageView btnLike;
        DatabaseReference likeReference;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = (CircleImageView)itemView.findViewById(R.id.userImage);
            username = (TextView)itemView.findViewById(R.id.username);
            description = (TextView)itemView.findViewById(R.id.description);
            postImage = (ImageView)itemView.findViewById(R.id.postImage);
            btnLike = (ImageView)itemView.findViewById(R.id.btnLike);
            likeTxt = (TextView)itemView.findViewById(R.id.likeTxt);

        }

        public void getLikeButtonStatus(String postkey, String userID) {

            likeReference = FirebaseDatabase.getInstance().getReference("likes");
            likeReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(postkey).hasChild(userID)) {
                        int likeCount = (int)snapshot.child(postkey).getChildrenCount();
                        likeTxt.setText(likeCount + " likes");
                        btnLike.setImageResource(R.drawable.ic_favoritefill);
                    } else  {
                        int likeCount = (int)snapshot.child(postkey).getChildrenCount();
                        likeTxt.setText(likeCount + " likes");
                        btnLike.setImageResource(R.drawable.ic_favourite);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
