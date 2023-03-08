package com.example.erealtorapp.AddManagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.FragmentSearchAdBinding;


public class SearchAdFragment extends Fragment {

    FragmentSearchAdBinding bind;

    public SearchAdFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentSearchAdBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        bind.ApplyFiltersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String pricesmaller = bind.pricelowertext.getText().toString();
                String priceHigher = bind.pricehighertext.getText().toString();
                String sizesmaller = bind.SizeLowertext.getText().toString();
                String sizeHigher = bind.SizeHigherText.getText().toString();
                String location = bind.Locationtext.getText().toString();
                String noofrooms = bind.Noofroomstext.getText().toString();
                bundle.putString("pricelower", pricesmaller);
                bundle.putString("pricehigher", priceHigher);
                bundle.putString("sizelower", sizesmaller);
                bundle.putString("sizehigher", sizeHigher);
                bundle.putString("location", location);
                bundle.putString("rooms", noofrooms);
                ViewAddFragment fragment = new ViewAddFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.DashboardContainer, fragment)
                        .commit();
            }
        });
        return view;
    }
}