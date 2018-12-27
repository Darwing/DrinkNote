package lb.yiimgo.storenote.ViewPager;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import lb.yiimgo.storenote.Fragment.CategoryFragment;
import lb.yiimgo.storenote.Fragment.HomeFragment;
import lb.yiimgo.storenote.Fragment.RoomDrinkFragment;
import lb.yiimgo.storenote.Fragment.UserFragment;
import lb.yiimgo.storenote.R;


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
