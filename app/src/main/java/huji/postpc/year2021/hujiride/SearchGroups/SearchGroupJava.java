package huji.postpc.year2021.hujiride.SearchGroups;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import huji.postpc.year2021.hujiride.HujiRideApplication;
import huji.postpc.year2021.hujiride.R;
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupAdapter;
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem;

public class SearchGroupJava extends Fragment {

//    // todo: delete neighborhoods
//    private List<SearchGroupItem> neighborhoods = new ArrayList<SearchGroupItem>(Arrays.asList(new SearchGroupItem("Malcha", false),
//            new SearchGroupItem("Bakaa", false), new SearchGroupItem("Talpiyot", false),
//            new SearchGroupItem("Pisga zeev", false), new SearchGroupItem("Gilo", false)));

    private List<SearchGroupItem> checkedGroups = new ArrayList<>();

    private SearchGroupAdapter adapter;
    private FloatingActionButton addGroupBtn;
    List<SearchGroupItem> neighborhoods;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_group, container, false);
        addGroupBtn = view.findViewById(R.id.add_floating_btn);
        adapter = new SearchGroupAdapter();
        HujiRideApplication app = HujiRideApplication.getInstance();
        neighborhoods =  app.getGroupsData().getNeighborhoods();


        // init current changes to be empty
        app.getGroupsData().setCurrentChanges(new ArrayList<SearchGroupItem>());

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

        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_searchGroup_to_groups_home);


                // add now all the checked groups todo - change
                final List<SearchGroupItem> checkedGroups =  app.getGroupsData().getCurrentChanges();
                for (SearchGroupItem group: checkedGroups) {
                    if (group.getChecked()){
                        app.getGroupsData().addGroup(group);
                    }else {
                        app.getGroupsData().removeGroup(group);
                    }
                }

            }
        });

        return view;
    }


    private void filter(String text) {
        ArrayList<SearchGroupItem> filteredList = new ArrayList<>();
        HujiRideApplication app = HujiRideApplication.getInstance();

        for (SearchGroupItem item : neighborhoods) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        app.getGroupsData().setChecked((ArrayList<SearchGroupItem>) filteredList);
        adapter.notifyDataSetChanged();
    }


//    @Override
//    public void onCheckingItem(Map<String, SearchGroupItem> map) {
//
//        HujiRideApplication app = HujiRideApplication.getInstance();
//
//        for (Pair<String, SearchGroupItem> item: map)
//            app.getGroupsData().addGroup(new Group(item.first));
//
//    }
}
