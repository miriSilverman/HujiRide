package huji.postpc.year2021.hujiride.toDelete;

import java.util.Map;

import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem;

public interface SearchCallback {

    void onCheckingItem(Map<String, SearchGroupItem> map);

}
