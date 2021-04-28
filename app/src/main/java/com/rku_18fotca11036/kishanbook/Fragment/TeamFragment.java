package com.rku_18fotca11036.kishanbook.Fragment;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rku_18fotca11036.kishanbook.DatabaseHelperTeam;
import com.rku_18fotca11036.kishanbook.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamFragment extends Fragment {
    EditText edtOfname, edtOlname;
    Button btnInsert, btnShow, btnUpdate, btnDelete;
    DatabaseHelperTeam myDb;
    Button btnClear;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamFragment newInstance(String param1, String param2) {
        TeamFragment fragment = new TeamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        myDb = new DatabaseHelperTeam(getActivity());

        edtOfname = view.findViewById(R.id.edtOFname);
        edtOlname = view.findViewById(R.id.edtOLname);
        btnInsert = view.findViewById(R.id.btnInsert);
        btnShow = view.findViewById(R.id.btnShow);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnClear = view.findViewById(R.id.btnClear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOfname.getText().clear();
                edtOlname.getText().clear();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valfname = edtOfname.getText().toString();
                String vallname = edtOlname.getText().toString();

                if (valfname.equals("")) {
//                    Snackbar.make(v, "Please, Enter Firstname", BaseTransientBottomBar.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Please Enter Firstname", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (vallname.equals("")) {
//                    Snackbar.make(v, "Please, Enter Lastname", BaseTransientBottomBar.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Please Enter Lastname", Toast.LENGTH_SHORT).show();
                    return;
                }
                UIUtil.hideKeyboard(getActivity());

//                boolean inserted = myDb.insertData(edtOfname.getText().toString(), edtOlname.getText().toString());
                boolean inserted = myDb.insertData(valfname, vallname);
                if (inserted == true) {
                    Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_SHORT).show();

//                    Snackbar.make(v, "Your Data Inserted", BaseTransientBottomBar.LENGTH_LONG).show();
                } else {
//                    Snackbar.make(v, "Your Data Not Inserted", BaseTransientBottomBar.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Data Not Inserted", Toast.LENGTH_SHORT).show();

                }
                edtOfname.getText().clear();
                edtOlname.getText().clear();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.getData();
                if (cursor.getCount() == 0) {
                    show_alertDialog("Data", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    stringBuffer.append("S.R.No.: " + cursor.getString(0) + "\n");
                    stringBuffer.append("First Name: " + cursor.getString(1) + "\n");
                    stringBuffer.append("Last Name: " + cursor.getString(2) + "\n \n");
                }
                show_alertDialog("Data", stringBuffer.toString());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.teamfragment_update_alert, null);

                final EditText edt1 = (EditText) view1.findViewById(R.id.edit1);
                final EditText edtfname = (EditText) view1.findViewById(R.id.edtfname);
                final EditText edtlname = (EditText) view1.findViewById(R.id.edtlname);
                final TextView txt = (TextView) view1.findViewById(R.id.textView);
                final Button btndismiss = (Button) view1.findViewById(R.id.btnDismiss);
                final Button btnok = (Button) view1.findViewById(R.id.btnOk);

                builder.setView(view1);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btndismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIUtil.hideKeyboard(getActivity());

                        boolean isupdate = myDb.updateDate(edt1.getText().toString(), edtfname.getText().toString(), edtlname.getText().toString());
                        if (isupdate == true) {
                            UIUtil.hideKeyboard(getActivity());
                            alertDialog.dismiss();
//                            Snackbar.make(v, "Data Updated", BaseTransientBottomBar.LENGTH_LONG).show();

                            Toast.makeText(getActivity(), "Data Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            UIUtil.hideKeyboard(getActivity());

//                            Snackbar.make(v, "Data Not Updated", BaseTransientBottomBar.LENGTH_LONG).show();

                            Toast.makeText(getActivity(), "Data Not Updated", Toast.LENGTH_SHORT).show();
                        }
//                        Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.teamfragment_delete_alert, null);

                final EditText edtId = (EditText) view1.findViewById(R.id.editId);
                final Button btndismiss = (Button) view1.findViewById(R.id.btnDismiss);
                final Button btnRemove = (Button) view1.findViewById(R.id.btnRemove);

                builder.setView(view1);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                btndismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteData(edtId.getText().toString());
                        if (deleteRows != 0) {
//                            UIUtil.hideKeyboard(getActivity());

//                            Snackbar.make(v, "Data Deleted", BaseTransientBottomBar.LENGTH_LONG).show();

                            Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();

                        } else {
//                            UIUtil.hideKeyboard(getActivity());

//                            Snackbar.make(v, "Data Not Deleted", BaseTransientBottomBar.LENGTH_LONG).show();

                            Toast.makeText(getActivity(), "Data Not Deleted", Toast.LENGTH_SHORT).show();
                        }
                        edtId.getText().clear();
                    }
                });
                alertDialog.show();
            }
        });


        return view;
    }

    public void show_alertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.show();
    }

}