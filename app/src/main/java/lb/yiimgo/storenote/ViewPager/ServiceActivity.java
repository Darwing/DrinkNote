package lb.yiimgo.storenote.ViewPager;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import lb.yiimgo.storenote.Fragment.ServiceFragment;
import lb.yiimgo.storenote.Fragment.BoardFragment;
import lb.yiimgo.storenote.Fragment.RoomFragment;
import lb.yiimgo.storenote.Fragment.UserFragment;
import lb.yiimgo.storenote.R;


/**
 * Created by Darwing on 21-Dec-18.
 */

public class ServiceActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    BoardFragment boardFragment;
    ServiceFragment ctFragment;
    RoomFragment rmFragment;
    UserFragment usFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }




}
