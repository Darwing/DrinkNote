package lb.yiimgo.storenote.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lb.yiimgo.storenote.Entity.Users;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.SessionManager;
import lb.yiimgo.storenote.Utility.Utility;
import lb.yiimgo.storenote.ViewPager.Home;


public class LoginFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    public View view;
    public Users users;
    public ProgressDialog progressDialog;
    private EditText username,password;
    private CardView btnStart;
    public RequestQueue requestQueue;
    public JsonObjectRequest jsonObjectRequest;
    private SessionManager sessionManager;
    private AlphaAnimation buttonClick = new AlphaAnimation(3F, 1.8F);

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_login,container,false);
            username = (EditText) view.findViewById(R.id.user);
            password = (EditText) view.findViewById(R.id.pass);
            btnStart = (CardView) view.findViewById(R.id.cardView);

            requestQueue = Volley.newRequestQueue(getContext());

            btnStart.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    getDataFromServices();
                    v.startAnimation(buttonClick);

                }
            });
            return view;
    }
       @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Error " + error.toString(),Toast.LENGTH_LONG).show();
        progressDialog.hide();
    }
    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("user");
        try{
            sessionManager = new SessionManager(getContext());
            users = new Users();
            JSONObject jsonObject = null;
            jsonObject =json.getJSONObject(0);

            if(jsonObject.optString("login") == "false")
            {
                Toast.makeText(getContext(),"User or password incorrect!",Toast.LENGTH_SHORT).show();
            }else
            {
                users.setId(jsonObject.optString("Id"));
                users.setUserName(jsonObject.optString("UserName"));
                users.setPassword(jsonObject.optString("Password"));
                users.setProfile(jsonObject.optString("Profile"));
                users.setCompany(jsonObject.optString("Company"));
                users.setFullName(jsonObject.optString("FullName"));
                users.setIdProfile(jsonObject.optInt("IdProfile"));

                Intent intent = new Intent(getContext(),Home.class);

                sessionManager.startSession(users);

                startActivity(intent);

             }

            }catch (JSONException e)
            {
                e.printStackTrace();
            }

            progressDialog.hide();


      }

      public void getDataFromServices()
      {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String usr =username.getText().toString().toLowerCase();
        String url = Utility.BASE_URL +"Main/getUserById?username="+usr
                +"&password="+password.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
      }


}
