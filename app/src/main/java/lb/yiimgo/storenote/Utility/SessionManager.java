package lb.yiimgo.storenote.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;

import lb.yiimgo.storenote.Entity.Users;
import lb.yiimgo.storenote.ViewPager.LoginActivity;

/**
 * Created by Darwing on 23-Dec-18.
 */

public class SessionManager
{
    private SharedPreferences sp;
    private Context _context;
    private SharedPreferences.Editor editor;
    public ArrayList<String> list;

    public SessionManager(Context context)
    {
        this._context = context;
    }

    public void startSession(Users users)
    {
        sp = _context.getSharedPreferences
                ("dataUser",_context.MODE_PRIVATE);

        editor = sp.edit();
        editor.putString("company", users.getCompany());
        editor.putInt("idProfile", users.getIdProfile());
        editor.putString("fullName", users.getFullName());
        editor.putString("profile", users.getProfile());

        editor.commit();
    }
    public ArrayList<String> getDataFromSession()
    {
        sp = _context.getSharedPreferences
                ("dataUser", _context.MODE_PRIVATE);

        list = new ArrayList<>();

        int idProfile= sp.getInt("idProfile",2);
        String company = sp.getString("company","Null");
        String fullName = sp.getString("fullName","Null");
        String profile = sp.getString("profile","Null");
        list.add(String.valueOf(idProfile));
        list.add(company);
        list.add(fullName);
        list.add(profile);

        return list;
    }


    public void logOut()
    {

        SharedPreferences preferences = _context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(_context, LoginActivity.class);
        _context.startActivity(intent);
    }
}
