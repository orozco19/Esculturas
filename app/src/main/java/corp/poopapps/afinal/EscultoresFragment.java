package corp.poopapps.afinal;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class EscultoresFragment extends Fragment {

    private ArrayList<Escultores> escultoresList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterEscultores;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    public EscultoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_escultores, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        escultoresList = new ArrayList<>();

        adapterEscultores = new EscultorAdapter(escultoresList, R.layout.activity_escultores_udea,getActivity());

        recyclerView.setAdapter(adapterEscultores);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Escultores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                escultoresList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Log.d("data:", snapshot.toString());
                        Escultores escultor = snapshot.getValue(Escultores.class);
                        escultoresList.add(escultor);
                    }
                    adapterEscultores.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("load", databaseError.getMessage());

            }
        });

        return view;
    }


}
 class EscultorAdapter extends RecyclerView.Adapter<EscultorAdapter.EscultoresViewHolder> {

    private ArrayList<Escultores> EscultoresList;
    private int resource;
    private Activity activity;



    public EscultorAdapter(ArrayList<Escultores> escultoresList) {
        this.EscultoresList = escultoresList;
    }

    public EscultorAdapter(ArrayList<Escultores> escultoresList, int resource, Activity activity) {
        this.EscultoresList = escultoresList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public EscultoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new EscultoresViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EscultoresViewHolder holder, int position) {
        Escultores Escultor = EscultoresList.get(position);
        holder.bindEscultores(Escultor, activity);
    }

    @Override
    public int getItemCount() {
        return EscultoresList.size();
    }

    public class EscultoresViewHolder extends RecyclerView.ViewHolder {

        TextView id, nombre, fallecimiento,lugarNacimiento,nacimiento;
        public EscultoresViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tId);
            nombre = itemView.findViewById(R.id.tNombre);
            nacimiento = itemView.findViewById(R.id.tNacimiento);
            lugarNacimiento = itemView.findViewById(R.id.tLugar);
            fallecimiento = itemView.findViewById(R.id.tFallecimiento);
        }

        public void bindEscultores(Escultores escultor, Activity activity) {
            id.setText(escultor.getId());
            nombre.setText(escultor.getNombre());
            nacimiento.setText(escultor.getNacimiento());
            lugarNacimiento.setText(escultor.getLugarNacimiento());
            fallecimiento.setText(String.valueOf(escultor.getFallecimiento()));

        }
    }


}
