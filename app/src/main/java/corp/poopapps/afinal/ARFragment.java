package corp.poopapps.afinal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ARFragment extends Fragment {


    private ImageView Ar;
    public ARFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ar, container, false);
        Ar = view.findViewById(R.id.IAr);

        return inflater.inflate(R.layout.fragment_ar, container, false);
    }

}
