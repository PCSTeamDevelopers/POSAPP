package jo.com.pcstores.rpos.pos.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Adapters.RecItemAdapter;
import jo.com.pcstores.rpos.pos.Fragments.EndShiftFragment;
import jo.com.pcstores.rpos.pos.Fragments.ItemFragment;
import jo.com.pcstores.rpos.pos.Fragments.MainFragment;
import jo.com.pcstores.rpos.pos.Fragments.RecieptFragment;
import jo.com.pcstores.rpos.pos.Fragments.SalesChartFragment;

public class NavMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("POS");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_main, menu);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            try {
                MainFragment frag = new MainFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content , frag);
                ft.addToBackStack(null);
                ft.commit();
            }catch (Exception ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_items) {
            try {
                ItemFragment frag = new ItemFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content , frag);
                ft.addToBackStack(null);
                ft.commit();
            }catch (Exception ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_receipts) {
            try {
                RecieptFragment frag = new RecieptFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content , frag);
                ft.addToBackStack(null);
                ft.commit();
            }catch (Exception ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_sales) {
            try {
                SalesChartFragment frag = new SalesChartFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content , frag);
                ft.addToBackStack(null);
                ft.commit();
            }catch (Exception ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_shift) {
            try {
                EndShiftFragment frag = new EndShiftFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content , frag);
                ft.addToBackStack(null);
                ft.commit();
            }catch (Exception ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
//        else if (id == R.id.nav_day) {
//            try {
//                EndDayFragment frag = new EndDayFragment();
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.content , frag);
//                ft.addToBackStack(null);
//                ft.commit();
//            }catch (Exception ex)
//            {
//                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
//            }
        //}
          else if (id == R.id.nav_logout) {
                try {
                    SharedPreferences sharedPreferences=   PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor pen=sharedPreferences.edit();
                    pen.putBoolean("keep me logged",false);
                    pen.commit();

                    Intent i = new Intent(this, LoginActivity.class);
                    startActivity(i);
                }catch (Exception ex)
                {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//
}


