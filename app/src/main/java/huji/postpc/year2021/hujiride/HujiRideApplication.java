package huji.postpc.year2021.hujiride;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

import huji.postpc.year2021.hujiride.Groups.GroupsData;
import huji.postpc.year2021.hujiride.MyRides.MyRides;
import huji.postpc.year2021.hujiride.database.Database;

public class HujiRideApplication extends Application {

    private static HujiRideApplication instance;
    private GroupsData groupsData;
    private RidesPerGroups ridesPerGroups;
    private UserDetails userDetails;
    private MyRides myRides;
    private final HashMap<String, String> jerusalemNeighborhoods = new HashMap<String, String>(){{
      put("454", "מלחה");
      put("125", "גילה");
      put("744", "פסגת זאב");
      put("693", "רחביה");
    }
    };

    private static final String ALL_NOTIFICATIONS = "all_notifiactions";
    private static final String GROUPS_NOTIFICATIONS = "groups_notifactions";
    private String SHARED = "shared";
    private String FIRST_NAME = "first name";
    private String LAST_NAME = "last name";
    private String PHONE_NUMBER = "phone num";
    private Database db;
    private SharedPreferences sp;



    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null)
        {
            instance = this;
        }
        groupsData = new GroupsData();
        ridesPerGroups = new RidesPerGroups();
        myRides = new MyRides();
        sp = getSharedPreferences(SHARED, Context.MODE_PRIVATE);

        userDetails = new UserDetails(sp.getString(FIRST_NAME, ""),
                sp.getString(LAST_NAME, ""),
                sp.getString(PHONE_NUMBER, ""),
                sp.getBoolean(ALL_NOTIFICATIONS, true),
                sp.getBoolean(GROUPS_NOTIFICATIONS, false),
                "");
        // TODO get unique ID

        db = new Database();

        // fill the hashmap

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

    public MyRides getMyRides() {
        return myRides;
    }

    public Database getDb() {
        return db;
    }

    public static HujiRideApplication getInstance()
    {
        return instance;
    }

    public void addBug(String bug){
        // @bug to db
        Toast.makeText(this, "duvah", Toast.LENGTH_SHORT).show();
    }

    public void saveNotificationsState(boolean allNotifications, boolean justGroupsNotifications){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(ALL_NOTIFICATIONS, allNotifications);
        editor.putBoolean(GROUPS_NOTIFICATIONS, justGroupsNotifications);
        editor.apply();
    }
    public HashMap<String, String> getJerusalemNeighborhoods() {
        return jerusalemNeighborhoods;
    }
}
