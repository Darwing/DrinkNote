package lb.yiimgo.drinknote.ViewPager;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Menu;
        import android.view.MenuItem;

        import lb.yiimgo.drinknote.Fragment.HomeFragment;
        import lb.yiimgo.drinknote.Fragment.CategoryFragment;
       // import lb.yiimgo.drinknote.Fragment.ContactsFragment;
        import lb.yiimgo.drinknote.Fragment.RoomDrinkFragment;
        import lb.yiimgo.drinknote.R;
        import lb.yiimgo.drinknote.ViewPager.Category.CreateCategory;
        import lb.yiimgo.drinknote.ViewPager.RoomDrink.CreateRoomDrink;
        import lb.yiimgo.drinknote.ViewPagerAdapter;

public class Home extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    //Fragments
    HomeFragment homeFragment;
    CategoryFragment ctFragment;
    RoomDrinkFragment rmFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        ctFragment = new CategoryFragment();
        rmFragment = new RoomDrinkFragment();
        //contactsFragment=new ContactsFragment();
        adapter.addFragment(homeFragment,"DASHBOARD");
        adapter.addFragment(ctFragment,"DRINK");
        adapter.addFragment(rmFragment,"ROOMS");

        viewPager.setAdapter(adapter);
    }

}
