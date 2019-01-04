package lb.yiimgo.storenote.Fragment.DialogFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import lb.yiimgo.storenote.Entity.Boards;
import lb.yiimgo.storenote.Entity.VolleySingleton;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.Utility;

@SuppressLint("ValidFragment")
public class CommentServicesFragment extends DialogFragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    public Context _context;
    public ArrayList<Boards> _boarDetail;
    public ProgressDialog progressDialog;
    public CommentServicesFragment(Context context, ArrayList<Boards> board) {
        this._context = context;
        this._boarDetail = board;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment_services, container, false);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
    private void webServiceDelete(String id,final int po) {

        progressDialog = new ProgressDialog(_context);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest;
        String url= Utility.BASE_URL +"Main/disableServices?Id="+id;

        stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                _boarDetail.remove(po);
                //notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(_context,"Not connection",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
        VolleySingleton.getIntanciaVolley(_context).addToRequestQueue(stringRequest);
    }
    @Override
    public void onResponse(JSONObject response) {

    }

}
