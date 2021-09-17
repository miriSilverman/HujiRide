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
import java.util.List;
import java.util.Objects;

import huji.postpc.year2021.hujiride.HujiRideApplication;
import huji.postpc.year2021.hujiride.R;

public class SearchGroupJava extends Fragment{



    private SearchGroupAdapter adapter;
    List<String> neighborhoods;
    private BottomNavigationView bottomNavigationView;
    private HujiRideApplication app;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_group, container, false);
        FloatingActionButton addGroupBtn = view.findViewById(R.id.add_floating_btn);
        RecyclerView groupsRecycler = view.findViewById(R.id.search_recycler);
        EditText editText = view.findViewById(R.id.search_group_editText);

        app = HujiRideApplication.getInstance();
        adapter = new SearchGroupAdapter();
        neighborhoods =  app.getGroupsData().getNeighborhoods();

        app.getGroupsData().setFiltered((ArrayList<String>) neighborhoods);

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

        addGroupBtn.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_searchGroup_to_groups_home);


//            // add now all the checked groups todo - change
//            final List<SearchGroupItem> checkedGroups =  app.getGroupsData().getCurrentChanges();
//            for (SearchGroupItem group: checkedGroups) {
//                if (group.getChecked()){
//                    app.getGroupsData().addGroup(group);
//                }else {
//                    app.getGroupsData().removeGroup(group);
//                }
//            }

//            app.getGroupsData().setCurrentChanges(new ArrayList<>());


        });

        return view;
    }





    @SuppressLint("NotifyDataSetChanged")
    private void filter(String text) {
        ArrayList<String> filteredList = new ArrayList<>();
        for (int i = 0; i < neighborhoods.size(); i++) {
            if (neighborhoods.get(i).toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(neighborhoods.get(i));
            }
        }
        app.getGroupsData().setFiltered(filteredList);
        adapter.notifyDataSetChanged();
    }





}



//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.groups_home)
//        {
//            Toast.makeText(getActivity(), "grouppp", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return false;
//    }

//
//    private void clearChanges(){
//
//        for (SearchGroupItem gr : app.getGroupsData().getCurrentChanges()){
//            System.out.println("## "+ gr.getName()+" "+gr.getChecked());
//        }
//
//
//        final List<SearchGroupItem> checkedGroups =  app.getGroupsData().getCurrentChanges();
//        for (SearchGroupItem group: checkedGroups) {
//
//            for (SearchGroupItem g: app.getGroupsData().getNeighborhoods()){
//                if (g.getName().equals(group.getName())){
//                    group.setChecked(g.getChecked());
//                }
//            }
//        }
//        adapter.notifyDataSetChanged();
//        app.getGroupsData().setCurrentChanges(new ArrayList<>());
//
//
//    }