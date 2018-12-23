package lb.yiimgo.drinknote.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import lb.yiimgo.drinknote.Entity.Users;
import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.ViewPager.Home;


public class LoginFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    public View view;
    public Users users;
    public ProgressDialog progressDialog;
    private EditText username,password;
    private Button btnStart;
    public RequestQueue requestQueue;
    public JsonObjectRequest jsonObjectRequest;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_login,container,false);
            username = (EditText) view.findViewById(R.id.user);
            password = (EditText) view.findViewById(R.id.pass);
            btnStart = (Button) view.findViewById(R.id.btnStart);

        requestQueue = Volley.newRequestQueue(getContext());

            btnStart.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    startSession();
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

            users = new Users();
            JSONObject jsonObject = null;
            jsonObject =json.getJSONObject(0);
            if(jsonObject.optString("login") == "false"){
                Toast.makeText(getContext(),"User or Password is wrong!",Toast.LENGTH_SHORT).show();
            }else
            {
            users.setUserName(jsonObject.optString("UserName"));
            users.setPassword(jsonObject.optString("Password"));
            users.setProfile(jsonObject.optString("Profile"));
            users.setCompany(jsonObject.optString("Company"));
            users.setFullName(jsonObject.optString("FullName"));

              
             Intent intent = new Intent(getContext(),Home.class);
             intent.putExtra(Home.company,users.getCompany().toString());
             startActivity(intent);
             }


            }catch (JSONException e)
            {
                e.printStackTrace();
            }

            progressDialog.hide();


      }

      public void startSession()
      {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String usr =username.getText().toString().toLowerCase();
        String url = "http://rizikyasociados.com.do/wsDrinkNote/Main/getUserById?username="+usr
                +"&password="+password.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
      }


}
