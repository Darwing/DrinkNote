package lb.yiimgo.storenote.Fragment;


import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import lb.yiimgo.storenote.Entity.Services;
import lb.yiimgo.storenote.Entity.VolleySingleton;
import lb.yiimgo.storenote.Fragment.DialogFragment.AddServiceRoomFragment;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.Utility;
import lb.yiimgo.storenote.ViewPager.Service.ServiceAdapter;

public class ServiceFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener,ServiceAdapter.OnItemClickListener
{
    public View view;
    public RecyclerView recyclerService;
    public ArrayList<Services> listServices;
    public ArrayList<Services> newList;
    public ProgressDialog progressDialog;
    public ServiceAdapter adapter;
    public Services services = null;
    public RequestQueue requestQueue;
    public JsonObjectRequest jsonObjectRequest;
    public SearchView searchView;
    public TextView notFound;
    public boolean ifSearch = false;
    public Utility utility;
    int spanCount = 1;
    int spacing = 50;

    public ServiceFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_service, container, false);
        listServices = new ArrayList<>();
        recyclerService = (RecyclerView) view.findViewById(R.id.idRecycler);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), spanCount){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                  new Spruce.SpruceBuilder(recyclerService)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerService, 1000),
                                ObjectAnimator.ofFloat(recyclerService, "translationX", -recyclerService.getWidth(), 0f).setDuration(1000))
                        .start();
            }
        };
        recyclerService.setLayoutManager(mLayoutManager);
        recyclerService.addItemDecoration(new GridSpacingItemDecoration(spacing));
        recyclerService.setHasFixedSize(true);
        notFound = (TextView) view.findViewById(R.id.not_found);

        adapter = new ServiceAdapter(getActivity(), listServices);
        requestQueue = Volley.newRequestQueue(getContext());
        loadWebServices();

        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Error " + error.toString(),Toast.LENGTH_LONG).show();
        progressDialog.hide();
    }
    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("services");
        try{
            for(int i =0; i<json.length(); i++)
            {
                services = new Services();
                JSONObject jsonObject = null;
                jsonObject =json.getJSONObject(i);

                services.setId(jsonObject.optString("Id"));
                services.setName(jsonObject.optString("Name"));
                services.setAmount(jsonObject.optDouble("Amount"));
                services.setService(jsonObject.optString("Service"));
                services.setStatus(jsonObject.optString("Status"));
                services.setNumStatus(jsonObject.optInt("StatusId"));
                services.setServiceId(jsonObject.getInt("ServiceId"));
                listServices.add(services);

            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        progressDialog.hide();
        recyclerService.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }


    public void addDialog(View v)
    {
        Services value;
        if(ifSearch)
            value = newList.get(recyclerService.getChildAdapterPosition(v));
        else
            value = listServices.get(recyclerService.getChildAdapterPosition(v));

        AddServiceRoomFragment addServiceRoomFragment = new AddServiceRoomFragment(getContext(),value);
        addServiceRoomFragment.show(getActivity().getFragmentManager(),"roomDialog");

    }

    public void loadWebServices()
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String url = Utility.BASE_URL +"Main/getDataServices";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    private void webServiceDelete(String id,final int po) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest;
        String url= Utility.BASE_URL + "Main/deleteService?Id="+id;

        stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                listServices.remove(po);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Not connection",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) item.getActionView();

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //Toast.makeText(getContext(), "onMenuItemActionExpand called", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                notFound.setVisibility(View.GONE);
                return true;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(listServices.size() > 0){
                    ifSearch = true;
                    newText = newText.toLowerCase();
                    newList = new ArrayList<>();

                    for(Services c : listServices)
                    {
                        String name = c.getName().toLowerCase();
                        if(name.contains(newText)){
                            newList.add(c);
                        }
                    }

                    if (newList.size() == 0){
                        if(!newText.isEmpty()){
                            notFound.setVisibility(View.VISIBLE);
                            notFound.setText("Record not found with '"+newText+"'");
                        }
                    }else
                    {
                        notFound.setVisibility(View.GONE);
                    }

                    adapter.updateList(newList);

                    return true;
                }else{
                    return false;
                }
            }
        });
    }

    @Override
    public void onClickAddButton(View v) {

        if(listServices.get(recyclerService.getChildAdapterPosition(v)).getNumStatus() == 1)
        {
            addDialog(v);

        }else
        {
            String message = listServices.get(recyclerService.getChildAdapterPosition(v)).getName();
            utility = new Utility(getContext());
            utility.showDialogAnimation(R.style.DialogSlide,
                    "This item: "+ message
                            +", is not available.","Not Available");
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int halfSpace;

        public GridSpacingItemDecoration(int space) {
            this.halfSpace = space / 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getPaddingLeft() != halfSpace) {
                parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
                parent.setClipToPadding(false);
            }

            outRect.top = halfSpace;
            outRect.bottom = halfSpace;
            outRect.left = halfSpace;
            outRect.right = halfSpace;
        }
    }

}
