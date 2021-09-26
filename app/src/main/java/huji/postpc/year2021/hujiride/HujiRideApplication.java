package huji.postpc.year2021.hujiride;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import huji.postpc.year2021.hujiride.Groups.GroupsData;
import huji.postpc.year2021.hujiride.database.Database;

public class HujiRideApplication extends Application {

    private static HujiRideApplication instance;
    private GroupsData groupsData;
    private UserDetails userDetails;
    private final HashMap<String, String> jerusalemNeighborhoods = new HashMap<>();
    private final String JERUSALEM_NEIGHBORS_JSON_FILENAME = "JerusalemNeighborhoods.json";

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
        sp = getSharedPreferences(SHARED, Context.MODE_PRIVATE);

        userDetails = new UserDetails(sp.getString(FIRST_NAME, ""),
                sp.getString(LAST_NAME, ""),
                sp.getString(PHONE_NUMBER, ""),
                sp.getBoolean(ALL_NOTIFICATIONS, true),
                sp.getBoolean(GROUPS_NOTIFICATIONS, false),
                "");

        db = new Database();
        fillJerusNeighMap();
    }




    private void fillJerusNeighMap() {
        String neighborhoodsJson = null;
        try {
            InputStream is = getAssets().open(JERUSALEM_NEIGHBORS_JSON_FILENAME);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            neighborhoodsJson = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Gson gson = new Gson();
        jerusalemNeighborhoods.putAll(gson.fromJson(neighborhoodsJson, jerusalemNeighborhoods.getClass()));
    }

    public GroupsData getGroupsData()
    {
        return groupsData;
    }
    public UserDetails getUserDetails() {
        return userDetails;
    }


    public Database getDb() {
        return db;
    }

    public static HujiRideApplication getInstance()
    {
        return instance;
    }

    public void addBug(String bug){
        // todo: @bug to db
        Toast.makeText(this, "duvah", Toast.LENGTH_SHORT).show();
//        db.addBug(bug);
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

    public void setClientData(String firstName, String lastName,String phoneNumber, String uniqueID) {
        this.userDetails.setClientUniqueID(uniqueID);
        this.userDetails.setUserFirstName(firstName);
        this.userDetails.setUserLastName(lastName);
        this.userDetails.setUserPhoneNumber(phoneNumber);
    }
}
