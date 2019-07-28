package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ExpenditureFragment extends Fragment {
    EditText foodInput;
    EditText transportInput;
    EditText billsInput;
    EditText miscInput;
    private ImageButton btnBack;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        foodInput = (EditText) view.findViewById(R.id.foodInput);
        foodInput.setHint(FoodFragment.foodBudgetDollar + "");
        transportInput = (EditText) view.findViewById(R.id.transportInput);
        transportInput.setHint(TransportFragment.transportBudgetDollar + "");
        billsInput = (EditText) view.findViewById(R.id.billsInput);
        billsInput.setHint(BillFragment.billsBudgetDollar + "");
        miscInput = (EditText) view.findViewById(R.id.miscInput);
        miscInput.setHint(MiscFragment.miscBudgetDollar + "");

        if (foodInput.getText().toString().isEmpty() || transportInput.getText().toString().isEmpty() || billsInput.getText().toString().isEmpty() || miscInput.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(),"Please fill in all sections", Toast.LENGTH_SHORT).show();
        } else {
            FoodFragment.foodBudgetDollar = Float.parseFloat(foodInput.getText().toString());
            TransportFragment.transportBudgetDollar = Float.parseFloat(transportInput.getText().toString());
            BillFragment.billsBudgetDollar = Float.parseFloat(billsInput.getText().toString());
            MiscFragment.miscBudgetDollar = Float.parseFloat(miscInput.getText().toString());

            Toast.makeText(getActivity(),"Budget update successful",Toast.LENGTH_SHORT).show();

        }
            btnBack = (ImageButton) view.findViewById(R.id.btnBack2);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });

    }
}
