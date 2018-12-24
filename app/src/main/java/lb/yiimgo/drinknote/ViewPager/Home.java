package lb.yiimgo.drinknote.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import lb.yiimgo.drinknote.Fragment.HomeFragment;
import lb.yiimgo.drinknote.Fragment.CategoryFragment;
import lb.yiimgo.drinknote.Fragment.RoomDrinkFragment;
import lb.yiimgo.drinknote.Fragment.UserFragment;
import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.Utility.SessionManager;
import lb.yiimgo.drinknote.ViewPager.Category.CreateCategory;
import lb.yiimgo.drinknote.ViewPager.RoomDrink.CreateRoomDrink;
import lb.yiimgo.drinknote.ViewPagerAdapter;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SessionManager sessionManager;
    //Fragments
    HomeFragment homeFragment;
    CategoryFragment ctFragment;
    RoomDrinkFragment rmFragment;
    UserFragment usFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);


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
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        // Associate searchable configuration with the SearchView
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_category:
                Intent viewCreateCategory=new Intent(this,CreateCategory.class);
                startActivity(viewCreateCategory);
                return true;
            case R.id.action_room:
                Intent viewRoomDrink =new Intent(this,CreateRoomDrink.class);
                startActivity(viewRoomDrink);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        sessionManager = new SessionManager(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        ctFragment = new CategoryFragment();
        rmFragment = new RoomDrinkFragment();
        usFragment = new UserFragment();

        if(sessionManager.permission() == 1)
        {
        adapter.addFragment(homeFragment,"BOARD");
        adapter.addFragment(ctFragment,"SERVICES");
        adapter.addFragment(rmFragment,"ROOMS");
        adapter.addFragment(usFragment,"USERS");

        }else
        {
            adapter.addFragment(homeFragment,"BOARD");
            adapter.addFragment(ctFragment,"SERVICES");
            adapter.addFragment(rmFragment,"ROOMS");

        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return  false;
    }



}
