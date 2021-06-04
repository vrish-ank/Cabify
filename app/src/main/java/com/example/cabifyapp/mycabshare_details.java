package com.example.cabifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class mycabshare_details extends AppCompatActivity {
    private static final String TAG = "mycabshare_details";

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    FirebaseAuth mFirebaseAuth;
    private FirestoreRecyclerAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycabshare_details);

       firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);
        mFirebaseAuth = FirebaseAuth.getInstance();

        String myemail = mFirebaseAuth.getCurrentUser().getEmail();
        Query query = firebaseFirestore.collection("Requests").whereEqualTo("email", myemail);
        FirestoreRecyclerOptions<CabshareDisplay> options = new FirestoreRecyclerOptions.Builder<CabshareDisplay>()
                .setQuery(query, CabshareDisplay.class)
                .build();

        adapter2 = new FirestoreRecyclerAdapter<CabshareDisplay, mycabshare_details.RequestsViewHolder>(options) {
            @NonNull
            @Override
            public mycabshare_details.RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single2,parent,false);
                return new mycabshare_details.RequestsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull mycabshare_details.RequestsViewHolder holder, int position, @NonNull CabshareDisplay model) {

                holder.list_name.setText(model.getName());
                holder.list_from.setText(model.getFrom());
                holder.list_to.setText(model.getTo());
                holder.list_date.setText(model.gettDate());
                holder.list_time.setText(model.gettTime());
                holder.list_avail.setText(model.getAvail());
                holder.list_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                         firebaseFirestore.collection("Requests").document(myemail+model.getFrom())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });

                        Toast.makeText(mycabshare_details.this,"Successfully deleted",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter2);

    }

    private class RequestsViewHolder extends RecyclerView.ViewHolder{

        private TextView list_name;
        private TextView list_from;
        private TextView list_to;
        private TextView list_date;
        private TextView list_time;
        private TextView list_avail;
        private TextView list_delete;
        public RequestsViewHolder(@NonNull View itemview)
        {
            super(itemview);
            list_name= itemview.findViewById(R.id.list_bemail);
            list_from= itemview.findViewById(R.id.list_remail);
            list_to= itemview.findViewById(R.id.list_rphone);
            list_date= itemview.findViewById(R.id.list_date);
            list_time= itemview.findViewById(R.id.list_time);
            list_avail= itemview.findViewById(R.id.list_avail);
            list_delete = itemview.findViewById(R.id.list_delete);
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
