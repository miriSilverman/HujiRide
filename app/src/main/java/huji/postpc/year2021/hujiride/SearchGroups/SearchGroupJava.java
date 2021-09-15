package huji.postpc.year2021.hujiride.SearchGroups;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import huji.postpc.year2021.hujiride.HujiRideApplication;
import huji.postpc.year2021.hujiride.R;

public class SearchGroupJava extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{



    private SearchGroupAdapter adapter;
    List<SearchGroupItem> neighborhoods;
    private BottomNavigationView bottomNavigationView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_group, container, false);
        FloatingActionButton addGroupBtn = view.findViewById(R.id.add_floating_btn);
        adapter = new SearchGroupAdapter();
        HujiRideApplication app = HujiRideApplication.getInstance();
        neighborhoods =  app.getGroupsData().getNeighborhoods();

//        bottomNavigationView = view.findViewById(R.id.bottom_nav_view);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        // init current changes to be empty
        app.getGroupsData().setCurrentChanges(new ArrayList<>());

        app.getGroupsData().setChecked((ArrayList<SearchGroupItem>) neighborhoods);

        RecyclerView groupsRecycler = view.findViewById(R.id.search_recycler);
        groupsRecycler.setAdapter(adapter);
        groupsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        EditText editText = view.findViewById(R.id.search_group_editText);
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

        addGroupBtn.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_searchGroup_to_groups_home);


            // add now all the checked groups todo - change
            final List<SearchGroupItem> checkedGroups =  app.getGroupsData().getCurrentChanges();
            for (SearchGroupItem group: checkedGroups) {
                if (group.getChecked()){
                    app.getGroupsData().addGroup(group);
                }else {
                    app.getGroupsData().removeGroup(group);
                }
            }

        });

        return view;
    }


    @SuppressLint("NotifyDataSetChanged")
    private void filter(String text) {
        ArrayList<SearchGroupItem> filteredList = new ArrayList<>();
        HujiRideApplication app = HujiRideApplication.getInstance();

        for (SearchGroupItem item : neighborhoods) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        app.getGroupsData().setChecked(filteredList);
        adapter.notifyDataSetChanged();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.groups_home)
        {
            Toast.makeText(getActivity(), "grouppp", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
