package com.example.gmca_user.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gmca_user.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {
    private RecyclerView department;
    private List<TeacherData> list;

    private TeacherAdapter adapter;

    private DatabaseReference reference,dbRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_faculty, container, false);

        department = view.findViewById(R.id.department);


        reference = FirebaseDatabase.getInstance().getReference().child("teacher");

        teacherDepartment();


        return view;
    }

    private void teacherDepartment() {
        department.setAdapter(adapter);
        dbRef = reference.child("category");
        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if (!snapshot.exists()) {
                    department.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Teachers are not Available", Toast.LENGTH_LONG).show();
                } else {
                    department.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list.add(data);
                    }
                    department.setHasFixedSize(true);
                    department.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list, getContext());
                    department.setAdapter(adapter);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}