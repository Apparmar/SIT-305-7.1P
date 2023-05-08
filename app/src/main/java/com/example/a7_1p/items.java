package com.example.a7_1p;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a7_1p.data.DatabaseHelper;
import com.example.a7_1p.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link items#newInstance} factory method to
 * create an instance of this fragment.
 */
public class items extends Fragment implements itemAdaptor.ItemClickListener{

    DatabaseHelper db;
    List<Item> itemList = new ArrayList<>();

    RecyclerView recyclerview;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    // TODO: Rename and change types of parameters
    private String mParam1;

    public items() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static items newInstance(String param1) {
        items fragment = new items();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db= new DatabaseHelper(getContext());
        itemList = db.fetchItem();

        recyclerview = view.findViewById(R.id.itemsRec);
        recyclerview.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        itemAdaptor itemAdaptor = new itemAdaptor(itemList, getContext(), this);
        recyclerview.setAdapter(itemAdaptor);
        itemAdaptor.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Item item) {
        Intent signupIntent = new Intent(getContext(), itemToRemove.class);
        Bundle extras = new Bundle();
        extras.putInt("itemId", item.getItemId());
        extras.putString("itemDesc", item.getDescription());
        extras.putString("itemDate", item.getDate());
        extras.putString("itemLoc", item.getLocation());
        extras.putString("itemPhone", item.getPhone().toString());
        extras.putInt("itemLost", item.isLost());
        signupIntent.putExtras(extras);
        startActivity(signupIntent);
    }
}