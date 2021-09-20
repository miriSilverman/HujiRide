package huji.postpc.year2021.hujiride.SearchGroups;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import huji.postpc.year2021.hujiride.HujiRideApplication;
import huji.postpc.year2021.hujiride.R;
import kotlin.Pair;

public class SearchGroupJava extends Fragment{



    private SearchGroupAdapter adapter;
//    List<String> neighborhoods;
    HashMap<String, String> neighborhoods;
    private HujiRideApplication app;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_group, container, false);
        RecyclerView groupsRecycler = view.findViewById(R.id.search_recycler);
        EditText editText = view.findViewById(R.id.search_group_editText);

        app = HujiRideApplication.getInstance();
        adapter = new SearchGroupAdapter();
//        neighborhoods =  app.getGroupsData().getNeighborhoods();
//        app.getGroupsData().setFiltered((ArrayList<String>) neighborhoods);
        neighborhoods =  app.getJerusalemNeighborhoods();

        app.getGroupsData().setFiltered(neighborhoods);

        groupsRecycler.setAdapter(adapter);
        groupsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

//

        return view;
    }



    @SuppressLint("NotifyDataSetChanged")
    private void filter(String text) {
//        HashMap<String, String> filteredList = new HashMap<>();
        ArrayList<Pair<String, String>> filteredList = new ArrayList<>();
        for (String groupId: neighborhoods.keySet()){
            String groupsName = neighborhoods.get(groupId);
            if (groupsName.toLowerCase().contains(text.toLowerCase())){
                filteredList.add(new Pair(groupId, groupsName));
            }

        }
        app.getGroupsData().setFiltered(filteredList);
        adapter.notifyDataSetChanged();
    }


//
//    @SuppressLint("NotifyDataSetChanged")
//    private void filter(String text) {
//        ArrayList<String> filteredList = new ArrayList<>();
//        for (int i = 0; i < neighborhoods.size(); i++) {
//            if (neighborhoods.get(i).toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(neighborhoods.get(i));
//            }
//        }
//        app.getGroupsData().setFiltered(filteredList);
//        adapter.notifyDataSetChanged();
//    }
//
//



}
