package aero.delco;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

public class Utils {

    public static final String SHARED_PREFS_NAME = "delcoPrefs";

    public static final String PREFS_THEME = "prefs_theme";
    public static final int DEFAULT_THEME_RESID = 2; // Boeing grey as default
    public static final int THEME_AIRBUS = 0;
    public static final int THEME_BOEING_BRAUN = 1;
    public static final int THEME_BOEING_GREY = 2;

    public static final String PREFS_CODES = "prefs_codes";
    public static final int DEFAULT_CODES_TABLE = 0; // iata_en as default
    public static final int CODES_IATA_EN = 0;
    public static final int CODES_AIRFRANCE = 1;
    public static final int CODES_CORSAIR = 2;
    public static final int CODES_AIRCANADA = 3;
    public static final int CODES_AEROLINEAS_ARGENTINAS = 4;
    public static final int CODES_LUFTHANSA = 5;
    public static final int CODES_AEGAN = 6;
    public static final int CODES_EASYJET = 7;
    public static final int CODES_WIZZAIR = 8;
    public static final int CODES_TURKISH = 9;


    public static final String PREFS_FONT_SIZE = "prefs_fontsize";
    public static final int DEFAULT_FONT_SIZE = 1;
    public static final int FONT_SIZE_SMALL = 0;
    public static final int FONT_SIZE_MEDIUM = 1;
    public static final int FONT_SIZE_LARGE = 2;

    /**
     * Applies the theme saved in Preferences to the Activity parameter.
     *
     * @param act Activity to set the shared theme to.
     */

    public static void applySharedTheme(Activity act) {
        // do not use R.style.xxxxxxx reference to store styles as it may change
        // and cause crash after application update!!!
        act.getApplicationContext();
        SharedPreferences sPref = act.getSharedPreferences(SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);

        int themeID = sPref.getInt(PREFS_THEME, DEFAULT_THEME_RESID);
        switch (themeID) {

            case Utils.THEME_AIRBUS:
                act.setTheme(R.style.airbus);
                break;

            case Utils.THEME_BOEING_BRAUN:
                act.setTheme(R.style.boeing);
                break;

            case Utils.THEME_BOEING_GREY:
                act.setTheme(R.style.boeing_grey);
                break;

            default:
                act.setTheme(R.style.boeing_grey);
                break;
        }
    }

    public static int getSharedFontSize(Context c) {
        SharedPreferences sPref = c.getSharedPreferences(SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);

        return sPref.getInt(PREFS_FONT_SIZE, DEFAULT_FONT_SIZE);
    }

    public static int getSharedThemeResId(Context c) {
        SharedPreferences sPref = c.getSharedPreferences(SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);

        return sPref.getInt(PREFS_THEME, DEFAULT_THEME_RESID);
    }

    public static int getSharedCodesTable(Context c) {
        c.getApplicationContext();
        SharedPreferences sPref = c.getSharedPreferences(SHARED_PREFS_NAME,
                Context.MODE_PRIVATE);

        return sPref.getInt(PREFS_CODES, DEFAULT_CODES_TABLE);
    }

    public static int getSharedCodesTableId(Context c) {
        // deal with codes table
        switch (getSharedCodesTable(c)) {
            case CODES_IATA_EN:
                return R.raw.delay_codes_iata_en;

            case CODES_AIRFRANCE:
                return R.raw.delay_codes_airfrance;

            case CODES_CORSAIR:
                return R.raw.delay_codes_corsair;

            case CODES_AIRCANADA:
                return R.raw.delay_codes_air_canada;

            case CODES_AEROLINEAS_ARGENTINAS:
                return R.raw.delay_codes_aerolineasargentinas;

            case CODES_LUFTHANSA:
                return R.raw.delay_codes_lufthansa;

            case CODES_AEGAN:
                return R.raw.delay_codes_aegan;

            case CODES_EASYJET:
                return R.raw.delay_codes_easyjet;

            case CODES_WIZZAIR:
                return R.raw.delay_codes_wizzair;

            case CODES_TURKISH:
                return R.raw.delay_codes_turkish_airlines;

            default:
                return R.raw.delay_codes_iata_en;
        }
    }

    // public static String getSharedCodesTableName(Context c) {
    // // deal with codes table
    // switch (getSharedCodesTable(c)) {
    // case CODES_IATA_EN:
    // return c.getString(R.string.iata_en);
    //
    // case CODES_AIRFRANCE:
    // return c.getString(R.string.airfrance);
    //
    // case CODES_CORSAIR:
    // return c.getString(R.string.corsair);
    //
    // case CODES_AIRCANADA:
    // return c.getString(R.string.aircanada);
    //
    // case CODES_AEROLINEAS_ARGENTINAS:
    // return c.getString(R.string.aerolineas_argentinas);
    //
    // default:
    // return c.getString(R.string.iata_en);
    // }
    // }

    public static void setupFontSize(Context c, TextView tv) {

        switch (getSharedFontSize(c)) {
            case FONT_SIZE_SMALL:
                tv.setTextAppearance(c, R.style.fontTextSmall);
                break;

            case FONT_SIZE_MEDIUM:
                tv.setTextAppearance(c, R.style.fontTextMedium);
                break;

            case FONT_SIZE_LARGE:
                tv.setTextAppearance(c, R.style.fontTextLarge);
                break;

            default:
                tv.setTextAppearance(c, R.style.fontTextMedium);
                break;
        }
    }

    public static void setupFontSizeTitle(Context c, TextView tv) {

        switch (getSharedFontSize(c)) {
            case FONT_SIZE_SMALL:
                tv.setTextAppearance(c, R.style.fontTitleSmall);
                break;

            case FONT_SIZE_MEDIUM:
                tv.setTextAppearance(c, R.style.fontTitleMedium);
                break;

            case FONT_SIZE_LARGE:
                tv.setTextAppearance(c, R.style.fontTitleLarge);
                break;

            default:
                tv.setTextAppearance(c, R.style.fontTitleMedium);
                break;
        }
    }
}
