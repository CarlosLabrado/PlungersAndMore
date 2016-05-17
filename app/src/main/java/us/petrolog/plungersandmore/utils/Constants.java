package us.petrolog.plungersandmore.utils;

import us.petrolog.plungersandmore.BuildConfig;

/**
 * Constants class store most important strings and paths of the app
 */
public class Constants {

    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;


    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_LOCATION_WELLS = "wells";

    public static final String FIREBASE_URL_WELLS = FIREBASE_URL + "/" + FIREBASE_LOCATION_WELLS;
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_USER_PID = "USER_PUSH_ID";

    /**
     * Constants for Firebase object properties
     */
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    /**
     * Constants for Firebase login
     */
    public static final String PASSWORD_PROVIDER = "password";
    public static final String GOOGLE_PROVIDER = "google";
    public static final String PROVIDER_DATA_DISPLAY_NAME = "displayName";

}
