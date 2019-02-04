package com.imcool.julian.ratinglist;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RatingAdapter ratingAdapter;
    private static RatingObject ratingObject;
    private final String TAG = "RatingListFragment";


    public RatingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_list,container,false);
        Log.i(TAG,"Starting Fragment");
        //ratingObject = (RatingObject) getArguments().getSerializable("RatingObject");
        //Log.i(TAG,ratingObject.getRatingObjectName(ratingObject.getLength()-1));
        recyclerView = (RecyclerView) view.findViewById(R.id.mDrawerList);
        ratingAdapter = new RatingAdapter(getActivity());
        recyclerView.setAdapter(ratingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Inflate the layout for this fragment
        return view;
    }
    //used method with bundles not used in singleto
    public static void getData(){
        float[] ratings = new float[ratingObject.getLength()];
        String[] names = new String[ratingObject.getLength()];
        for(int i = 0; i < ratingObject.getLength();i++){
            ratings[i] = ratingObject.getRatingObjectRating(i);
            names[i] = ratingObject.getRatingObjectName(i);
        }

    }

}
