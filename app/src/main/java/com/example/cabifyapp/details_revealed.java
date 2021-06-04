package com.example.cabifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class details_revealed extends AppCompatActivity {

    private static final String TAG = "details_revealed";

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    FirebaseAuth mFirebaseAuth;
    private FirestoreRecyclerAdapter adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_revealed);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);
        mFirebaseAuth = FirebaseAuth.getInstance();
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("getphone");

        String myemail = mFirebaseAuth.getCurrentUser().getEmail();
        Query query = firebaseFirestore.collection("Booker").whereEqualTo("booker_email", myemail).whereEqualTo("requester_phone",value);
        FirestoreRecyclerOptions<BookerDisplay> options = new FirestoreRecyclerOptions.Builder<BookerDisplay>()
                .setQuery(query, BookerDisplay.class)
                .build();

        adapter2 = new FirestoreRecyclerAdapter<BookerDisplay, details_revealed.RequestsViewHolder>(options) {
            @NonNull
            @Override
            public details_revealed.RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single3,parent,false);
                return new details_revealed.RequestsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull details_revealed.RequestsViewHolder holder, int position, @NonNull BookerDisplay model) {

                holder.list_bemail.setText(model.getBooker_email());
                holder.list_remail.setText(model.getRequester_email());
                holder.list_rphone.setText(model.getRequester_phone());
                int y = model.getSeats_booked();
                String s = Integer.toString(y);
                holder.list_seats.setText(s);
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter2);

    }

    private class RequestsViewHolder extends RecyclerView.ViewHolder{

        private TextView list_bemail;
        private TextView list_remail;
        private TextView list_rphone;
        private TextView list_seats;
        public RequestsViewHolder(@NonNull View itemview)
        {
            super(itemview);
            list_bemail= itemview.findViewById(R.id.list_bemail);
            list_remail= itemview.findViewById(R.id.list_remail);
            list_rphone= itemview.findViewById(R.id.list_rphone);
            list_seats= itemview.findViewById(R.id.list_seats);
        }
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        adapter2.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter2.startListening();
    }
}
