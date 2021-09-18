package huji.postpc.year2021.hujiride;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import huji.postpc.year2021.hujiride.Groups.GroupsData;
import huji.postpc.year2021.hujiride.MyRides.MyRides;
import huji.postpc.year2021.hujiride.database.Database;

public class HujiRideApplication extends Application {

    private static HujiRideApplication instance;
    private GroupsData groupsData;
    private RidesPerGroups ridesPerGroups;
    private UserDetails userDetails;
    private MyRides myRides;


    private String SHARED = "shared";
    private String FIRST_NAME = "first name";
    private String LAST_NAME = "last name";
    private String PHONE_NUMBER = "phone num";
    private Database db;



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


        SharedPreferences sp = this.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        userDetails = new UserDetails(sp.getString(FIRST_NAME, ""),
                sp.getString(LAST_NAME, ""),
                sp.getString(PHONE_NUMBER, ""), true, false);

        db = new Database();

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
        Toast.makeText(this, "added "+bug, Toast.LENGTH_SHORT).show();
    }
}
