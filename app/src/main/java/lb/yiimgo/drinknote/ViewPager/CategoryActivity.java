package lb.yiimgo.drinknote.ViewPager;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import lb.yiimgo.drinknote.Entity.Category;
import lb.yiimgo.drinknote.Fragment.CategoryFragment;
import lb.yiimgo.drinknote.Fragment.HomeFragment;
import lb.yiimgo.drinknote.Fragment.RoomDrinkFragment;
import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.ViewPager.Category.CategoryAdapter;
import lb.yiimgo.drinknote.ViewPagerAdapter;


/**
 * Created by Darwing on 21-Dec-18.
 */

public class CategoryActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    HomeFragment homeFragment;
    CategoryFragment ctFragment;
    RoomDrinkFragment rmFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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


    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        ctFragment = new CategoryFragment();
        rmFragment = new RoomDrinkFragment();

        adapter.addFragment(homeFragment,"DASHBOARD");
        adapter.addFragment(ctFragment,"SERVICES");
        adapter.addFragment(rmFragment,"ROOMS");

        viewPager.setAdapter(adapter);
    }

}
