package com.example.cabifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.firestore.FieldValue.increment;

public class cabshare_details extends AppCompatActivity {
    private static final String TAG = "cabshare_details";
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    FirebaseAuth mFirebaseAuth;

    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabshare_details);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);
        mFirebaseAuth = FirebaseAuth.getInstance();

        String myemail = mFirebaseAuth.getCurrentUser().getEmail();
       Query query = firebaseFirestore.collection("Requests").whereNotEqualTo("email",myemail).whereIn("Avail", Arrays.asList("1", "2","3", "4","5"));
       FirestoreRecyclerOptions<CabshareDisplay> options = new FirestoreRecyclerOptions.Builder<CabshareDisplay>()
               .setQuery(query, CabshareDisplay.class)
               .build();

         adapter = new FirestoreRecyclerAdapter<CabshareDisplay, RequestsViewHolder>(options) {
            @NonNull
            @Override
            public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                return new RequestsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RequestsViewHolder holder, int position, @NonNull CabshareDisplay model) {
                holder.list_name.setText(model.getName());
                holder.list_from.setText(model.getFrom());
                holder.list_to.setText(model.getTo());
                holder.list_date.setText(model.gettDate());
                holder.list_time.setText(model.gettTime());
                holder.list_avail.setText(model.getAvail());

                holder.list_book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                         String n= model.getAvail();
                        int m = Integer.parseInt(n);
                        if(m>0) {
                            m = m - 1;

                            firebaseFirestore.collection("Requests").document(model.getEmail()+model.getFrom())
                                    .update("Avail", m + "")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully deleted!");

                                            Map<String, Object> booking = new HashMap<>();
                                            booking.put("requester_email", model.getEmail());
                                            booking.put("requester_phone",model.getPhone());
                                            booking.put("booker_email", myemail);
                                            booking.put("Seats_booked", FieldValue.increment(1));

                                            firebaseFirestore.collection("Booker").document(myemail + model.getEmail()+ model.getFrom())
                                                    .set(booking, SetOptions.merge())
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                                            Intent getData = new Intent(cabshare_details.this,details_revealed.class);
                                                            getData.putExtra("getphone",model.getPhone());
                                                            startActivity(getData);
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error writing document", e);
                                                        }
                                                    });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error deleting document", e);
                                        }
                                    });

                            Toast.makeText(cabshare_details.this, "Successfully Booked", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(cabshare_details.this, "Fully Booked", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);
    }

    public class RequestsViewHolder extends RecyclerView.ViewHolder{

         TextView list_name;
         TextView list_from;
         TextView list_to;
         TextView list_date;
         TextView list_time;
         TextView list_avail;
         TextView list_book;
        public RequestsViewHolder(@NonNull View itemview)
        {
            super(itemview);
            list_name= itemview.findViewById(R.id.list_bemail);
            list_from= itemview.findViewById(R.id.list_remail);
            list_to= itemview.findViewById(R.id.list_rphone);
            list_date= itemview.findViewById(R.id.list_date);
            list_time= itemview.findViewById(R.id.list_time);
            list_avail= itemview.findViewById(R.id.list_avail);
            list_book = itemview.findViewById(R.id.list_book);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(cabshare_details.this,"Successfully Clicked itemview",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
