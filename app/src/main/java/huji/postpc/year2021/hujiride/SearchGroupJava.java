package huji.postpc.year2021.hujiride;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SearchGroupJava extends Fragment {

    private  List<SearchGroupItem>  neighborhoods =  new ArrayList<SearchGroupItem>(Arrays.asList(            new SearchGroupItem("Malcha"),
            new SearchGroupItem("Bakaa"), new SearchGroupItem("Talpiyot"),
            new SearchGroupItem("Pisga zeev"), new SearchGroupItem("Gilo")));


    private SearchGroupAdapter adapter;

     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_group, container, false);

         adapter = new SearchGroupAdapter();
        adapter.setGroupsList(neighborhoods);

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

//        adapter.onItemClickCallback = {group: SearchGroupItem ->
//            Navigation.findNavController(view).navigate(R.id.action_groups_home_to_ridesList)
//        }

//        val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
//        groupsRecycler.addItemDecoration(dividerItemDecoration)

        return view;
    }


    private void filter(String text)
    {
        ArrayList<SearchGroupItem> filteredList = new ArrayList<>();
        for (SearchGroupItem item: neighborhoods)
        {
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.setGroupsList(filteredList);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
         inflater.inflate(R.menu.search_group_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);

    }
}
