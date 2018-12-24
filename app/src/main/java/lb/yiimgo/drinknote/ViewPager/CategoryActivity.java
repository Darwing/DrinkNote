package lb.yiimgo.drinknote.ViewPager;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import lb.yiimgo.drinknote.Fragment.CategoryFragment;
import lb.yiimgo.drinknote.Fragment.HomeFragment;
import lb.yiimgo.drinknote.Fragment.RoomDrinkFragment;
import lb.yiimgo.drinknote.Fragment.UserFragment;
import lb.yiimgo.drinknote.R;
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
    UserFragment usFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }




}
