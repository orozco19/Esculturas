package corp.poopapps.afinal;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EsculturasFragment extends Fragment {

    private ArrayList<Esculturas> esculturasList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterEsculturas;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    public EsculturasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_esculturas, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        esculturasList = new ArrayList<>();

        adapterEsculturas = new EsculturaAdapter(esculturasList, R.layout.activity_esculturas_udea,getActivity());

        recyclerView.setAdapter(adapterEsculturas);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Esculturas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                esculturasList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Log.d("data:", snapshot.toString());
                        Esculturas esculturas = snapshot.getValue(Esculturas.class);
                        esculturasList.add(esculturas);
                    }
                    adapterEsculturas.notifyDataSetChanged();
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

class EsculturaAdapter extends RecyclerView.Adapter<EsculturaAdapter.EsculturasViewHolder> {

    private ArrayList<Esculturas> EsculturasList;
    private int resource;
    private Activity activity;



    public EsculturaAdapter(ArrayList<Esculturas> esculturasList) {
        this.EsculturasList = esculturasList;
    }

    public EsculturaAdapter(ArrayList<Esculturas> esculturasList, int resource, Activity activity) {
        this.EsculturasList = esculturasList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public EsculturasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new EsculturasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EsculturasViewHolder holder, int position) {
        Esculturas Escultura = EsculturasList.get(position);
        holder.bindEsculturas(Escultura, activity);
    }

    @Override
    public int getItemCount() {
        return EsculturasList.size();
    }

    public class EsculturasViewHolder extends RecyclerView.ViewHolder {

        TextView id, nombre,ano,escultor,ubicacion, foto;

        public EsculturasViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tId);
            nombre = itemView.findViewById(R.id.tNombre);
            ano = itemView.findViewById(R.id.tAno);
            escultor = itemView.findViewById(R.id.tEscultor);
            ubicacion = itemView.findViewById(R.id.tUbicacion);
           // foto = itemView.findViewById(R.id.tFoto);
        }

        public void bindEsculturas(Esculturas escultura, Activity activity) {
            id.setText(escultura.getId());
            nombre.setText(escultura.getNombre());
            ano.setText(String.valueOf(escultura.getAno()));
            escultor.setText(escultura.getEscultor());
            ubicacion.setText(String.valueOf(escultura.getUbicacion()));
           // foto.setText(escultura.getFoto());

        }
    }


}
