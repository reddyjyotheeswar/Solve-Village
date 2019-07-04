package com.example.rgukt.myproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends android.support.v4.app.Fragment {
    public UsersFragment() {
        // Required empty public constructor
    }
    private ProgressDialog progressDialog;
    ListView listViewPresident;
    DatabaseReference databasePresident;
    List<President> presidentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        listViewPresident = (ListView) view.findViewById(R.id.listView_President);
        presidentList = new ArrayList<>();
        databasePresident = FirebaseDatabase.getInstance().getReference("President");



        FloatingActionButton fab = view.findViewById(R.id.adduser);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), addPresidentActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDialog=new ProgressDialog(getContext());

        progressDialog.setMessage("fetching data...");
        progressDialog.show();
        databasePresident.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presidentList.clear();

                for (DataSnapshot presidentSnapshot : dataSnapshot.getChildren()) {
                    President president = presidentSnapshot.getValue(President.class);

                    presidentList.add(president);

                }
                progressDialog.dismiss();
                PresidentList adapter = new PresidentList((Activity) getContext(), presidentList);
                listViewPresident.setAdapter(adapter);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
