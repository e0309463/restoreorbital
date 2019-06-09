package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Color;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button syncBtn;
    private PieChart pieChart;
    private PieDataSet dataSet;
    private PieData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        syncBtn = (Button)findViewById(R.id.syncBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        toolbar = (Toolbar)findViewById(R.id.toolbarMain);

        pieChart = findViewById(R.id.pieChart);

        drawChart(pieChart);

        /**
         * Updates the receipt history and the pie chart when the user clicks the sync button.
         * Currently using a randomiser to randomly generate the values for each category.
         */
        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChart(pieChart);
            }
        });



        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Settings");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Logout");

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,item2
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch(position) {
                            case 1:
                            case 2: Logout();
                        }
                        return true;
                    }
                })
                
                .build();
    }

    private void drawChart(PieChart pieChart) {
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        Random rand = new Random();
        int f = rand.nextInt(20);
        int t = rand.nextInt(25);
        int b = rand.nextInt(30);
        int m = rand.nextInt(25);

        yvalues.add(new PieEntry(f+1, "Food", 0));
        yvalues.add(new PieEntry(t+1, "Transport", 1));
        yvalues.add(new PieEntry(b+1, "Bills", 2));
        yvalues.add(new PieEntry(m+1, "Misc", 3));

        dataSet = new PieDataSet(yvalues, "");
        data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);

    }

    private void updateChart(PieChart pieChart) {

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        Random rand = new Random();
        int f = rand.nextInt(20);
        int t = rand.nextInt(25);
        int b = rand.nextInt(30);
        int m = rand.nextInt(25);

        yvalues.add(new PieEntry(f+1, "Food", 0));
        yvalues.add(new PieEntry(t+1, "Transport", 1));
        yvalues.add(new PieEntry(b+1, "Bills", 2));
        yvalues.add(new PieEntry(m+1, "Misc", 3));

        dataSet = new PieDataSet(yvalues, "");
        data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        data.notifyDataChanged();
        pieChart.setData(data);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);

    }
    private void Logout() {
        progressDialog.setMessage("Logging Out");
        progressDialog.show();
        firebaseAuth.signOut();
        finish();
        progressDialog.dismiss();
        Toast.makeText(SecondActivity.this,"Logout Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SecondActivity.this,MainActivity.class));
    }
}
