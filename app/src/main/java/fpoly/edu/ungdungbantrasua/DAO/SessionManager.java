package fpoly.edu.ungdungbantrasua.DAO;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "USER_FILE";
    private static final String KEY_CUSTOMER_ID = "role";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Set the customer's session upon successful login
    public void setLoggedInCustomer(int customerId) {
        editor.putInt(KEY_CUSTOMER_ID, customerId);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    // Check if a customer is logged in
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Get the logged-in customer's ID
    public int getLoggedInCustomerId() {
        return sharedPreferences.getInt(KEY_CUSTOMER_ID, -1);
    }

    // Clear the session data when the customer logs out
    public void logoutCustomer() {
        editor.clear();
        editor.apply();
    }

}
