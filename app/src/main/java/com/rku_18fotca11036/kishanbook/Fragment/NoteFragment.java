package com.rku_18fotca11036.kishanbook.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rku_18fotca11036.kishanbook.HomeActivity;
import com.rku_18fotca11036.kishanbook.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    Button btnSave,btnClear;
    EditText edtNote;
    TextView txtNote;

    SharedPreferences preferences;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public NoteFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
//    // TODO: Rename and change types and number of parameters
//    public static NoteFragment newInstance(String param1, String param2) {
//        NoteFragment fragment = new NoteFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        btnSave = view.findViewById(R.id.btnSave);
        edtNote = view.findViewById(R.id.edtNote);
        txtNote = view.findViewById(R.id.txtNote);
        btnClear = view.findViewById(R.id.btnClear);
        txtNote.setMovementMethod(new ScrollingMovementMethod());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ss = edtNote.getText().toString();
                preferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("one",ss);
                editor.commit();
                txtNote.setText(ss);
            }
        });

        preferences = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        String s = preferences.getString("one","First Save Your Data");
        txtNote.setText(s);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtNote.getText().toString();
                preferences = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                edtNote.getText().clear();
                editor.clear();
                clear();
                editor.commit();
                txtNote.setText(s);
            }
        });
        txtNote.setText(s);

        return view;
    }

    public void clear(){
        edtNote.setText("");
    }

}