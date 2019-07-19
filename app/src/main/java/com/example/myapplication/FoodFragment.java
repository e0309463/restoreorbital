package com.example.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {

    private ImageButton btnBack;
    private TextView info;
    private TextView billsExpenditureNumber;
    private TextView billsBudgetNumber;
    private TextView resultTv;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btnBack = (ImageButton) view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        info = (TextView) view.findViewById(R.id.tvbillsInfo);
        info.setMovementMethod(new ScrollingMovementMethod());
        billsBudgetNumber = (TextView)view.findViewById(R.id.tvBillBudgetNumber);
        billsExpenditureNumber = (TextView)view.findViewById(R.id.tvBillExpenditureNumber);
        info = (TextView)view.findViewById(R.id.tvbillsInfo);
        resultTv = (TextView)view.findViewById(R.id.tvAnalysis);
        float billsExpend = SecondActivitymodded.getBillsExpend();
        String BE = "$" + billsExpend + "";
        billsExpenditureNumber.setText(BE);
        float billsBudget = 40;
        String BB ="$" + billsBudget + "";
        billsBudgetNumber.setText(BB);
        String result = "";
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        if (billsExpend < billsBudget) {
            result = "You still have "  + df.format((1 - billsExpend/billsBudget)*100 )+ "% of your budget to spend!";
        } else if (billsExpend > billsBudget){
            result = "You have exceeded your budget by " + df.format(((billsExpend/billsBudget)-1) * 100) + "%!";
        } else {
            result = "You have used up exactly all your budget!";
        }

        resultTv.setText(result);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food, container, false);
    }

}
