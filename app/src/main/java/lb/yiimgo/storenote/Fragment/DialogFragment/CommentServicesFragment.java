package lb.yiimgo.storenote.Fragment.DialogFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import lb.yiimgo.storenote.Entity.Boards;
import lb.yiimgo.storenote.Entity.VolleySingleton;
import lb.yiimgo.storenote.Fragment.BoardFragment;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.SessionManager;
import lb.yiimgo.storenote.Utility.Utility;
import lb.yiimgo.storenote.ViewPager.Board.BoardAdapter;
import lb.yiimgo.storenote.ViewPager.Board.BoardDetailAdapter;

@SuppressLint("ValidFragment")
public class CommentServicesFragment extends DialogFragment
{
    public Context _context;
    public ArrayList<Boards> _boarDetail;
    public ProgressDialog progressDialog;
    public BoardDetailAdapter _adapter;
    public View view;
    public CardView delete;
    public CardView cancele;
    public int _position;
    public TextView description;
    public SessionManager sessionManager;
    StringRequest stringRequest;

    public CommentServicesFragment(Context context, ArrayList<Boards> board, int p, BoardDetailAdapter adapter) {
        this._context = context;
        this._boarDetail = board;
        this._position = p;
        this._adapter =adapter;
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
        view = inflater.inflate(R.layout.fragment_comment_services, container, false);
        cancele = (CardView) view.findViewById(R.id.card_cancel);
        delete = (CardView) view.findViewById(R.id.card_view);
        description = (TextView) view.findViewById(R.id.description);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String strUserName = description.getText().toString();

                    if(strUserName.trim().equals("")) {
                        description.setError("You must place a comment in order to delete the service");
                        return;
                    }else {
                        webServiceDelete(_boarDetail.get(_position).getIdServices(),_boarDetail.get(_position).getSerCateId(),_position, description.getText().toString(),
                                _boarDetail.get(_position).getUbication());
                    }
            }
        });

        cancele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText("");
                closeDialogFragment();
            }
        });
        return view;
    }


    private void webServiceDelete(String id,final int serCateId ,final int po,
                                  String desc,String ub) {

        progressDialog = new ProgressDialog(_context);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        sessionManager = new SessionManager(_context);


        String url= Utility.BASE_URL +"Main/disableServices?id="+id+"&serCateId="+serCateId
                +"&po="+po+"&desc="+desc+"&ub="+ub+"&usr="+sessionManager.getDataFromSession().get(4);

        stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();

                _boarDetail.remove(_position);
                _adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(_context,"Not connection",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
         VolleySingleton.getIntanciaVolley(_context).addToRequestQueue(stringRequest);
         closeDialogFragment();
    }

    public void closeDialogFragment()
    {

        Fragment fragment =getActivity().getSupportFragmentManager().findFragmentByTag("commentDialog");
        if(fragment != null) {
            DialogFragment dialog = (DialogFragment) fragment;
            dialog.dismiss();
        }
    }

}
