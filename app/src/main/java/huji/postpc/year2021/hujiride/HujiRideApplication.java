package huji.postpc.year2021.hujiride;

import android.app.Application;

import huji.postpc.year2021.hujiride.Groups.GroupsData;

public class HujiRideApplication extends Application {

    private static HujiRideApplication instance;
    private GroupsData groupsData;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null)
        {
            instance = this;
        }
        groupsData = new GroupsData();
    }

    public GroupsData getGroupsData()
    {
        return groupsData;
    }

    public static HujiRideApplication getInstance()
    {
        return instance;
    }
}
