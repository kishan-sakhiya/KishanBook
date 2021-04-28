package com.rku_18fotca11036.kishanbook.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rku_18fotca11036.kishanbook.DatabaseHelperIncome;
import com.rku_18fotca11036.kishanbook.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeFragment extends Fragment {
    DatabaseHelperIncome helperIncome;
    EditText edtCalendar, edtMoney;
    ImageView imgCalendar;
    int day;
    int month;
    int year;
    Button btnInsert, btnClear, btnShow,btnUpdate,btnDelete;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeFragment newInstance(String param1, String param2) {
        IncomeFragment fragment = new IncomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        helperIncome = new DatabaseHelperIncome(getContext());

        imgCalendar = view.findViewById(R.id.imgCalendar);
        edtCalendar = view.findViewById(R.id.edtCalendar);
        btnInsert = view.findViewById(R.id.btnInsert);
        edtMoney = view.findViewById(R.id.edtMoney);
        btnClear = view.findViewById(R.id.btnClear);
        btnShow = view.findViewById(R.id.btnShow);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);

        Calendar calendar = Calendar.getInstance();
        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtCalendar.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valDate = edtCalendar.getText().toString();
                String valMoney = edtMoney.getText().toString();

                if (valDate.equals("")) {
                    Toast.makeText(getActivity(), R.string.ple_sel_date, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (valMoney.equals("")) {
                    Toast.makeText(getActivity(), R.string.ple_ent_money, Toast.LENGTH_SHORT).show();
                    return;
                }
                UIUtil.hideKeyboard(getActivity());

                boolean inserted = helperIncome.insertData(valDate, valMoney);
                if (inserted = true) {
                    Toast.makeText(getActivity(),R.string.data_inserted, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), R.string.data_not_inserted, Toast.LENGTH_SHORT).show();

                }
                edtCalendar.getText().clear();
                edtMoney.getText().clear();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = helperIncome.getData();
                if (cursor.getCount() == 0) {
                    show_alertDialog("Data", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    stringBuffer.append("S.R.No.: " + cursor.getString(0) + "\n");
                    stringBuffer.append("Date: " + cursor.getString(1) + "\n");
                    stringBuffer.append("Money: " + cursor.getString(2) + "\n \n");
                }
                show_alertDialog("Data", stringBuffer.toString());

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.incomefragment_update_alert, null);

                final EditText edt1 = (EditText) view1.findViewById(R.id.edit1);
                final EditText edtMoneyUpdate = (EditText) view1.findViewById(R.id.edtMoney);
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

                        boolean isupdate = helperIncome.updateData(edt1.getText().toString(), edtMoneyUpdate.getText().toString());
                        if (isupdate == true) {
                            UIUtil.hideKeyboard(getActivity());
                            alertDialog.dismiss();

                            Toast.makeText(getActivity(),R.string.data_updated, Toast.LENGTH_SHORT).show();
                        } else {
                            UIUtil.hideKeyboard(getActivity());
                            Toast.makeText(getActivity(),R.string.data_not_updated, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.show();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.incomefragment_delete_alert, null);

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

                        Integer deleteRows = helperIncome.deleteData(edtId.getText().toString());
                        if (deleteRows != 0) {
                            UIUtil.hideKeyboard(getActivity());


                            Toast.makeText(getActivity(), R.string.data_deleted, Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();

                        } else {
                            UIUtil.hideKeyboard(getActivity());


                            Toast.makeText(getActivity(),R.string.data_not_deleted, Toast.LENGTH_SHORT).show();
                        }
                        edtId.getText().clear();
                    }
                });
                alertDialog.show();

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtCalendar.getText().clear();
                edtMoney.getText().clear();
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