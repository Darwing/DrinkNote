package lb.yiimgo.drinknote.ViewPager;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import lb.yiimgo.drinknote.Fragment.LoginFragment;
import lb.yiimgo.drinknote.R;

/**
 * Created by Darwing on 22-Dec-18.
 */

public class LoginActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.login,new LoginFragment()).commit();
    }
}
