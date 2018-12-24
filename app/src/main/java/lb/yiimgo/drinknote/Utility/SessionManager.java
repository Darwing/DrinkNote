package lb.yiimgo.drinknote.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import lb.yiimgo.drinknote.ViewPager.Home;

/**
 * Created by Darwing on 23-Dec-18.
 */

public class SessionManager
{
    private SharedPreferences sp;
    private Context _context;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context)
    {
        this._context = context;
    }
    public void startSession(String company,int idProfile)
    {
        sp = _context.getSharedPreferences
                ("dataUser",_context.MODE_PRIVATE);

        String companyName = company;

        editor = sp.edit();
        editor.putString("company", companyName);
        editor.putInt("idProfile", idProfile);

        editor.commit();
    }
    public int permission()
    {
        sp = _context.getSharedPreferences
                ("dataUser", _context.MODE_PRIVATE);

        int com = sp.getInt("idProfile",2);

        return com;
    }
}
