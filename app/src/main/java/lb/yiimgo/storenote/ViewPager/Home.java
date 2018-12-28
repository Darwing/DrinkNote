package lb.yiimgo.storenote.ViewPager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import lb.yiimgo.storenote.Fragment.HomeFragment;
import lb.yiimgo.storenote.Fragment.CategoryFragment;
import lb.yiimgo.storenote.Fragment.RoomDrinkFragment;
import lb.yiimgo.storenote.Fragment.UserFragment;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.SessionManager;
import lb.yiimgo.storenote.ViewPager.Category.Category;
import lb.yiimgo.storenote.ViewPager.RoomDrink.RoomDrink;
import lb.yiimgo.storenote.ViewPagerAdapter;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SessionManager sessionManager;
    //Fragments
    HomeFragment homeFragment;
    CategoryFragment ctFragment;
    RoomDrinkFragment rmFragment;
    UserFragment usFragment;
    TextView profile,fullName;
    NavigationView navigationView;

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

        drawer.addDrawerListener(toggle);
        sessionManager = new SessionManager(this);
        sessionManager.validateSession();
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        this.basicInfoUser(navigationView);
        hideMenuNavigation();

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
    public void hideMenuNavigation()
    {
        sessionManager = new SessionManager(this);
        Menu menu =navigationView.getMenu();
        MenuItem target =  menu.findItem(R.id.nav_user);

        if(Integer.valueOf(sessionManager.getDataFromSession().get(0)) == 1)
            target.setVisible(true);
        else
            target.setVisible(false);

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
       return super.onOptionsItemSelected(item);

    }

    private void setupViewPager(ViewPager viewPager)
    {
        sessionManager = new SessionManager(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        ctFragment = new CategoryFragment();
        rmFragment = new RoomDrinkFragment();
        usFragment = new UserFragment();
        int idProfile = Integer.valueOf(sessionManager.getDataFromSession().get(0));


        adapter.addFragment(homeFragment,"BOARD");
        adapter.addFragment(ctFragment,"SERVICES");
        adapter.addFragment(rmFragment,"ROOMS");

        if(idProfile == 1)
         adapter.addFragment(usFragment,"USERS");

        viewPager.setAdapter(adapter);
    }

    void basicInfoUser(NavigationView navigationView)
    {
        sessionManager = new SessionManager(this);
        View headerView = navigationView.getHeaderView(0);
        profile = (TextView) headerView.findViewById(R.id.profile_nav);
        fullName = (TextView) headerView.findViewById(R.id.title_nav);

        fullName.setText(sessionManager.getDataFromSession().get(2));
        profile.setText(sessionManager.getDataFromSession().get(3));

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        sessionManager = new SessionManager(this);

            switch (item.getItemId())
            {
                case R.id.nav_home:
                    viewPager.setCurrentItem(0,false);
                    break;
                case R.id.nav_services:
                    if(Integer.valueOf(sessionManager.getDataFromSession().get(0)) == 1)
                    {
                        Intent services = new Intent(Home.this,Category.class);
                        startActivity(services);
                    }else{
                        viewPager.setCurrentItem(1);
                    }
                    break;
                case R.id.nav_rooom:
                    if(Integer.valueOf(sessionManager.getDataFromSession().get(0)) == 1)
                    {
                        Intent rooms = new Intent(Home.this,RoomDrink.class);
                        startActivity(rooms);
                    }else{
                        viewPager.setCurrentItem(2);
                    }
                    break;
                case R.id.nav_user:
                        //Intent rooms = new Intent(Home.this,CreateRoomDrink.class);
                        //startActivity(rooms);
                    break;
                case  R.id.setting :
                    Toast.makeText(this,"ses", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logout :
                    sessionManager.logOut();
                    Toast.makeText(this,"Logout successful!", Toast.LENGTH_SHORT).show();
                    break;

            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
