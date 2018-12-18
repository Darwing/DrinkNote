package lb.yiimgo.drinknote.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import lb.yiimgo.drinknote.ViewPager.RoomDrink.BackGroundTask;

import lb.yiimgo.drinknote.R;

public class RoomDrinkFragment extends Fragment {
    View view;

    public RoomDrinkFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        BackGroundTask backGroundTask = new BackGroundTask(getContext());
        backGroundTask.execute("get_info_room");
        view = inflater.inflate(R.layout.fragment_room_drink, container, false);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_room_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
