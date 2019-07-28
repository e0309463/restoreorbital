package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


public class SecondActivitymodded extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private PieChart pieChart;
    private PieDataSet dataSet;
    private PieData data;
    private Button syncBtn;
    private TextView headerName;
    public static TextView headerEmail;
    static float billsExpend;
    static float transportExpend;
    static float miscExpend;
    static float foodExpend;

    private String clientId = "dd35d1dd-1c3a-4233-b25a-6ee6c5e9a70c";
    private String clientSecret = "57ac89db-ea47-4fc4-a122-0295063fd211";
    private String redirectUri = "http://www.example.com/restoreorbital111";

    Button scan_btn;
    public static TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activitymodded);
        setTitle("RESTORE");
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        syncBtn = (Button)findViewById(R.id.syncBtn);
        pieChart = findViewById(R.id.pieChart);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(this.getResources().getColor(R.color.colorTransparent));
        drawChart(pieChart);

        /**
         * Updates the receipt history and the pie chart when the user clicks the sync button.
         * Currently using a randomiser to randomly generate the values for each category.
         */
        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChart(pieChart);
                //startActivity(new Intent(SecondActivitymodded.this,Oauth.class));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dbs.com/sandbox/api/sg/v1/oauth/authorize" + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=Read&response_type=code&state=0399"));
                //startActivity(intent);
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            Fragment newFragment = new BillFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.linearLayout2, newFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        headerName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.headerName);
        headerEmail = (TextView)navigationView.getHeaderView(0).findViewById(R.id.headerEmail);

        headerName.setText(firebaseAuth.getCurrentUser().getDisplayName());
        headerEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        scan_btn = (Button)findViewById(R.id.btn_scan);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view){
               startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if(uri != null && uri.toString().startsWith(redirectUri)) {
            String code = uri.getQueryParameter("code");

            Toast.makeText(this, "yay!", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second_activitymodded, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
BillFragment billfragment = null;
FoodFragment foodfragment = null;
MiscFragment miscfragment = null;
TransportFragment transportfragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        }else if (id == R.id.nav_bills) {
            billfragment = new BillFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.drawer_layout,billfragment, billfragment.getTag()).addToBackStack(null).commit();
        } else if (id == R.id.nav_food) {
            foodfragment = new FoodFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.drawer_layout,foodfragment, foodfragment.getTag()).addToBackStack(null).commit();
        } else if (id == R.id.nav_misc) {
            miscfragment = new MiscFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.drawer_layout,miscfragment, miscfragment.getTag()).addToBackStack(null).commit();
        } else if (id == R.id.nav_transport) {
            transportfragment = new TransportFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.drawer_layout,transportfragment, transportfragment.getTag()).addToBackStack(null).commit();
        } else if (id == R.id.nav_logout) {
            Logout();
        }
        /*if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.linearLayout2, fragment);
            ft.commit();
        }*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Logout() {
        progressDialog.setMessage("Logging Out");
        progressDialog.show();
        firebaseAuth.signOut();
        finish();
        progressDialog.dismiss();
        Toast.makeText(SecondActivitymodded.this,"Logout Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SecondActivitymodded.this,MainActivity.class));
    }

    private void drawChart(PieChart pieChart) {
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        Random rand = new Random();
        float f = FoodFragment.foodtotal;
        foodExpend = f;
        float t = TransportFragment.transporttotal;
        transportExpend = t;
        float b = BillFragment.billtotal;
        billsExpend = b;
        float m = MiscFragment.misctotal;
        miscExpend = m;

        yvalues.add(new PieEntry(f, "Food", 0));
        yvalues.add(new PieEntry(t, "Transport", 1));
        yvalues.add(new PieEntry(b, "Bills", 2));
        yvalues.add(new PieEntry(m, "Misc", 3));

        dataSet = new PieDataSet(yvalues, "");
        data = new PieData(dataSet);

        pieChart.setEntryLabelColor(this.getResources().getColor(R.color.colorDarkBlue));
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(58f);
        int[] colors = {
                this.getResources().getColor(R.color.colorLightBlue),
                this.getResources().getColor(R.color.colorMedBlue),
                this.getResources().getColor(R.color.colorIntenseBlue),
                this.getResources().getColor(R.color.colorGrey)
        };
        dataSet.setColors(colors);
        //dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(this.getResources().getColor(R.color.colorDarkBlue));
        pieChart.getLegend().setEnabled(false);

    }

    private void updateChart(PieChart pieChart) {
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        Random rand = new Random();
        float f = FoodFragment.foodtotal;
        foodExpend = f;
        float t = TransportFragment.transporttotal;
        transportExpend = t;
        float b = BillFragment.billtotal;
        billsExpend = b;
        float m = MiscFragment.misctotal;
        miscExpend = m;

        yvalues.add(new PieEntry(f, "Food", 0));
        yvalues.add(new PieEntry(t, "Transport", 1));
        yvalues.add(new PieEntry(b, "Bills", 2));
        yvalues.add(new PieEntry(m, "Misc", 3));

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
        int[] colors = {
                this.getResources().getColor(R.color.colorLightBlue),
                this.getResources().getColor(R.color.colorMedBlue),
                this.getResources().getColor(R.color.colorIntenseBlue),
                this.getResources().getColor(R.color.colorGrey)
        };
        dataSet.setColors(colors);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);

    }

    public static float getBillsExpend() {
        return billsExpend;
    }


}
