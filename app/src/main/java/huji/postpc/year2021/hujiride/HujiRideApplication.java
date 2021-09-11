package huji.postpc.year2021.hujiride;

import android.app.Application;

import huji.postpc.year2021.hujiride.Groups.GroupsData;
import huji.postpc.year2021.hujiride.UserDetails;

public class HujiRideApplication extends Application {

    private static HujiRideApplication instance;
    private GroupsData groupsData;
    private RidesPerGroups ridesPerGroups;
    private UserDetails userDetails;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null)
        {
            instance = this;
        }
        groupsData = new GroupsData();
        ridesPerGroups = new RidesPerGroups();
        userDetails = new UserDetails();
    }

    public GroupsData getGroupsData()
    {
        return groupsData;
    }
    public RidesPerGroups getRidesPerGroup() {
        return ridesPerGroups;
    }
    public UserDetails getUserDetails() {
        return userDetails;
    }

    public static HujiRideApplication getInstance()
    {
        return instance;
    }
}
