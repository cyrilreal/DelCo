package aero.delco;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

public class ScreenSettings extends Activity {

    private RadioGroup rgCodesTable;
    private RadioGroup rgFontSize;
    private RadioGroup rgThemeSelection;
    private Button btnBack = null;
    private int themeResId; // hold the theme loaded at startup to see if user
    // changed it
    private boolean themeHasChanged = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Utils.applySharedTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_settings);
        initComponents();
    }

    private void initComponents() {
        rgCodesTable = (RadioGroup) findViewById(R.id.rgCodesTable);
        rgCodesTable.clearCheck();
        rgFontSize = (RadioGroup) findViewById(R.id.rgFontSize);
        rgFontSize.clearCheck();
        rgThemeSelection = (RadioGroup) findViewById(R.id.rgThemeSelection);
        rgThemeSelection.clearCheck();

        assignValuesFromPrefs();

        btnBack = (Button) findViewById(R.id.btnSettingsBack);
        btnBack.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                updatePrefsFromValues();
                Bundle b = new Bundle();
                b.putBoolean("theme_has_changed", themeHasChanged);
                Intent mIntent = new Intent();
                mIntent.putExtras(b);
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });
    }

    private void assignValuesFromPrefs() {
        // set the correct radiobutton on with parameters from preferences

        // deal with codes table
        switch (Utils.getSharedCodesTable(this)) {
            case Utils.CODES_IATA_EN:
                rgCodesTable.check(R.id.rbtnIataStdEnglish);
                break;

            case Utils.CODES_AIRFRANCE:
                rgCodesTable.check(R.id.rbtnAirFrance);
                break;

            case Utils.CODES_CORSAIR:
                rgCodesTable.check(R.id.rbtnCorsair);
                break;

            case Utils.CODES_AIRCANADA:
                rgCodesTable.check(R.id.rbtnAirCanada);
                break;

            case Utils.CODES_AEROLINEAS_ARGENTINAS:
                rgCodesTable.check(R.id.rbtnAerolineasArgentinas);
                break;

            case Utils.CODES_LUFTHANSA:
                rgCodesTable.check(R.id.rbtnLufthansa);
                break;

            case Utils.CODES_AEGAN:
                rgCodesTable.check(R.id.rbtnAegan);
                break;

            case Utils.CODES_EASYJET:
                rgCodesTable.check(R.id.rbtnEasyJet);
                break;

            case Utils.CODES_WIZZAIR:
                rgCodesTable.check(R.id.rbtnWizzAir);
                break;

            case Utils.CODES_TURKISH:
                rgCodesTable.check(R.id.rbtnTurkish);
                break;

            default:
                rgCodesTable.check(R.id.rbtnIataStdEnglish);
                break;
        }

        // deal with font size
        switch (Utils.getSharedFontSize(this)) {
            case Utils.FONT_SIZE_SMALL:
                rgFontSize.check(R.id.rbtnFontSmall);
                break;

            case Utils.FONT_SIZE_MEDIUM:
                rgFontSize.check(R.id.rbtnFontMedium);
                break;

            case Utils.FONT_SIZE_LARGE:
                rgFontSize.check(R.id.rbtnFontLarge);
                break;

            default:
                rgFontSize.check(R.id.rbtnFontMedium);
                break;
        }

        // deal with ThemeSelection radiobuttons
        switch (Utils.getSharedThemeResId(this)) {

            case Utils.THEME_AIRBUS:
                rgThemeSelection.check(R.id.rbtnThemeAirbus);
                themeResId = Utils.THEME_AIRBUS;
                break;

            case Utils.THEME_BOEING_BRAUN:
                rgThemeSelection.check(R.id.rbtnThemeBoeing);
                themeResId = Utils.THEME_BOEING_BRAUN;
                break;

            case Utils.THEME_BOEING_GREY:
                rgThemeSelection.check(R.id.rbtnThemeBoeingGrey);
                themeResId = Utils.THEME_BOEING_GREY;
                break;

            default:
                rgThemeSelection.check(R.id.rbtnThemeBoeing);
                themeResId = Utils.THEME_BOEING_BRAUN;
                break;
        }
    }

    private void updatePrefsFromValues() {
        // Save user preferences
        SharedPreferences prefs = getSharedPreferences(Utils.SHARED_PREFS_NAME,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        switch (rgCodesTable.getCheckedRadioButtonId()) {
            case R.id.rbtnIataStdEnglish:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_IATA_EN);
                break;

            case R.id.rbtnAirFrance:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_AIRFRANCE);
                break;

            case R.id.rbtnCorsair:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_CORSAIR);
                break;

            case R.id.rbtnAirCanada:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_AIRCANADA);
                break;

            case R.id.rbtnAerolineasArgentinas:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_AEROLINEAS_ARGENTINAS);
                break;

            case R.id.rbtnLufthansa:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_LUFTHANSA);
                break;

            case R.id.rbtnAegan:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_AEGAN);
                break;

            case R.id.rbtnEasyJet:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_EASYJET);
                break;

            case R.id.rbtnWizzAir:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_WIZZAIR);
                break;

            case R.id.rbtnTurkish:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_TURKISH);
                break;

            default:
                editor.putInt(Utils.PREFS_CODES, Utils.CODES_IATA_EN);
                break;
        }

        // getting the checked font size
        switch (rgFontSize.getCheckedRadioButtonId()) {
            case R.id.rbtnFontSmall:
                editor.putInt(Utils.PREFS_FONT_SIZE, Utils.FONT_SIZE_SMALL);
                break;

            case R.id.rbtnFontMedium:
                editor.putInt(Utils.PREFS_FONT_SIZE, Utils.FONT_SIZE_MEDIUM);
                break;

            case R.id.rbtnFontLarge:
                editor.putInt(Utils.PREFS_FONT_SIZE, Utils.FONT_SIZE_LARGE);
                break;

            default:
                editor.putInt(Utils.PREFS_FONT_SIZE, Utils.FONT_SIZE_MEDIUM);
                break;
        }

        // getting the checked theme selection method
        // do not hold R.style.xxxxxxx reference as it may change and cause
        // crash after application update!!!
        switch (rgThemeSelection.getCheckedRadioButtonId()) {
            case R.id.rbtnThemeAirbus:
                editor.putInt(Utils.PREFS_THEME, Utils.THEME_AIRBUS);
                if (themeResId != Utils.THEME_AIRBUS) {
                    themeHasChanged = true;
                }
                break;

            case R.id.rbtnThemeBoeing:
                editor.putInt(Utils.PREFS_THEME, Utils.THEME_BOEING_BRAUN);
                if (themeResId != Utils.THEME_BOEING_BRAUN) {
                    themeHasChanged = true;
                }
                break;

            case R.id.rbtnThemeBoeingGrey:
                editor.putInt(Utils.PREFS_THEME, Utils.THEME_BOEING_GREY);
                if (themeResId != Utils.THEME_BOEING_GREY) {
                    themeHasChanged = true;
                }
                break;

            default:
                editor.putInt(Utils.PREFS_THEME, Utils.THEME_BOEING_BRAUN);
                if (themeResId != Utils.THEME_BOEING_BRAUN) {
                    themeHasChanged = true;
                }
                break;
        }

        editor.commit();
    }
}
