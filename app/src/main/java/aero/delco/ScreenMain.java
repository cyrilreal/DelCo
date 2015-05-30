package aero.delco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ScreenMain extends FragmentActivity implements
        OnGroupExpandListener, OnClickListener,
        OnScrollListener {

    private List<DelayCode> codes;

    // ID of the active codes table
    private int activeTable = 0;

    // indicates whether the list is shifted down to prevent top part to be
    // displayed in the blended transition of the list view

    private boolean isShifted = false;

    // constants
    private static final int USER_NUMBER_PICKED = 0;
    private static final int USER_KEYWORD_PICKED = 1;
    private static final int USER_SETTINGS_PICKED = 2;

    private static final int CONTEXT_MENU_COLLAPSE_ALL = 1;

//    DelayHandler delayHandler = null;

    private ExpandableListView expListView = null;
    MyExpandableListAdapter adapter;
    private Button btnNumberSearch;
    private Button btnKeywordSearch;
    private ImageButton ibtnSettings;
    private ImageButton ibtnHelp;

    boolean isShowCloseDialog = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Utils.applySharedTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);
        activeTable = Utils.getSharedCodesTable(this);
        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShowCloseDialog) {
            isShowCloseDialog = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogFragment df = new CloseDialogFragment();
            df.show(fragmentManager, "close_frag_tag");
        }
    }

    private void initComponents() {
        try {
            generateCodesList();
        } catch (IOException e) {
        } catch (XmlPullParserException e) {
        }
        this.expListView.setOnScrollListener(this);

        registerForContextMenu(this.expListView);

        btnNumberSearch = (Button) findViewById(R.id.btnNumber);
        btnNumberSearch.setOnClickListener(this);

        btnKeywordSearch = (Button) findViewById(R.id.btnWord);
        btnKeywordSearch.setOnClickListener(this);

        ibtnSettings = (ImageButton) findViewById(R.id.ibtnSettings);
        ibtnSettings.setOnClickListener(this);

        ibtnHelp = (ImageButton) findViewById(R.id.ibtnHelp);
        ibtnHelp.setOnClickListener(this);
    }

    private void generateCodesList() throws IOException, XmlPullParserException {

        DelCoParser delCoParser = new DelCoParser();
        InputStream in = null;
        in = this.getResources().openRawResource(Utils.getSharedCodesTableId(this
                .getApplicationContext()));
//			parseDatabase(Utils.getSharedCodesTableId(this
//					.getApplicationContext()));
        codes = delCoParser.parse(in);

        in.close();
        expListView = (ExpandableListView) findViewById(R.id.expListView);

        adapter = new MyExpandableListAdapter(this, createGroupList2(),
                createChildList2());

        expListView.setAdapter(adapter);
    }

    /**
     * This method parses XML file content
     *
     * @return
     * @throws Exception
     */
//    private void parseDatabase(int tableID) throws Exception {
//        /* Get a SAXParser from the SAXPArserFactory. */
//        SAXParserFactory spf = SAXParserFactory.newInstance();
//        SAXParser parser = spf.newSAXParser();
//
//		/* Get the XMLReader of the SAXParser we created. */
//        XMLReader xr = parser.getXMLReader();
//        /* Create a new ContentHandler and apply it to the XML-Reader */
//        delayHandler = new DelayHandler();
//        xr.setContentHandler(delayHandler);
//		/* Load xml file from raw folder */
//        InputStream in = null;
//        in = this.getResources().openRawResource(tableID);
//
//		/* Begin parsing */
//        xr.parse(new InputSource(in));
//        in.close();
//    }

    public void onClick(View v) {
        if (v == btnNumberSearch) {
            Intent intent = new Intent(this, ScreenNumber.class);
            startActivityForResult(intent, USER_NUMBER_PICKED);
        }

        if (v == btnKeywordSearch) {
            Intent intent = new Intent(this, ScreenPickKeyword.class);
            startActivityForResult(intent, USER_KEYWORD_PICKED);
        }

        if (v == ibtnSettings) {
            Intent intent = new Intent(this, ScreenSettings.class);
            startActivityForResult(intent, USER_SETTINGS_PICKED);
        }

        if (v == ibtnHelp) {
            Intent intent = new Intent(this, ScreenHelp.class);
            startActivity(intent);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == USER_NUMBER_PICKED) {
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra("numberTyped");
                int index = getSelectionByNumber(s);
                if (index == -1) {
                    Toast.makeText(this, getString(R.string.toastNoCodeFound),
                            Toast.LENGTH_LONG);
                    return;
                }
                // contruct arrays
//                String[] arrCodeContent = new String[delayHandler.getCodes()
//                        .size()];
//                String[] arrCodeNumber = new String[delayHandler.getCodes()
//                        .size()];
//
//                for (int i = 0; i < delayHandler.getCodes().size(); i++) {
//                    arrCodeNumber[i] = delayHandler.getCodes().get(i)
//                            .getNumber();
//                    arrCodeContent[i] = delayHandler.getCodes().get(i)
//                            .getContent();
//                }

                String[] arrCodeContent = new String[codes.size()];
                String[] arrCodeNumber = new String[codes.size()];

                for (int i = 0; i < codes.size(); i++) {
                    arrCodeNumber[i] = codes.get(i).getNumber();
                    arrCodeContent[i] = codes.get(i).getContent();
                }

                Intent intent = new Intent(this, ScreenResultNumber.class);
                intent.putExtra("codeNumbers", arrCodeNumber);
                intent.putExtra("codeContents", arrCodeContent);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        }

        if (requestCode == USER_KEYWORD_PICKED) {
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra("keyword");
                // build an arraylist containing all the matching codes
                if (s.equals("")) {
                    Toast.makeText(this, R.string.toastKeywordStringEmpty,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                ArrayList<DelayCode> alFilteredCodes = getFilteredCodesList(s);
                String[] strNumberArray = new String[alFilteredCodes.size()];
                String[] strContentArray = new String[alFilteredCodes.size()];

                // build a float array and a string array that will be passed to
                // the intent sent to the result screen
                for (int i = 0; i < alFilteredCodes.size(); i++) {
                    strNumberArray[i] = alFilteredCodes.get(i).getNumber();
                    strContentArray[i] = alFilteredCodes.get(i).getContent();
                }
                // display a message and exit the method if the search returns
                // nothing
                if (strNumberArray.length == 0 || strContentArray.length == 0) {
                    Toast.makeText(this, R.string.toastNoCodeFound,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(this, ScreenResultKeyword.class);
                intent.putExtra("codeNumbers", strNumberArray);
                intent.putExtra("codeContents", strContentArray);
                startActivity(intent);
            }
        }

        if (requestCode == USER_SETTINGS_PICKED) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                boolean bln = bundle.getBoolean("theme_has_changed");
                if (bln == true) {

                    // use a boolean because of ICS bug with support package
                    isShowCloseDialog = true;
                }

                int i = Utils.getSharedCodesTable(this);
                if (i != activeTable) {
                    activeTable = i;
                    try {
                        generateCodesList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                }

                // update the expandablelistadapter with fontsize
                adapter.setFontSize(Utils
                        .getSharedFontSize(getApplicationContext()));
                expListView.setAdapter(adapter);
            }
        }
    }

    /**
     * create a list of all different categories found in the codes list
     *
     * @return a list of string (the category name)
     */
    private List<String> createGroupList2() {
        ArrayList<String> result = new ArrayList<String>();
        // scan the list of codes, add each category value in result list
        String s;
//        for (int i = 0; i < delayHandler.getCodes().size(); ++i) {
//            s = delayHandler.getCodes().get(i).getCategory();
//
//            // add the string only if not already present
//            if (!result.contains(s)) {
//                result.add(s);
//            }
//        }

        for (int i = 0; i < codes.size(); ++i) {
            s = codes.get(i).getCategory();

            // add the string only if not already present
            if (!result.contains(s)) {
                result.add(s);
            }
        }
        return (List<String>) result;
    }

    private List<ArrayList<DelayCode>> createChildList2() {
        // a list containing all differents hashmap lists
        ArrayList<ArrayList<DelayCode>> result = new ArrayList<ArrayList<DelayCode>>();
        // a list storing all different category names
        ArrayList<String> categories = (ArrayList<String>) createGroupList2();

        // iterate over the list, create a new list for each category and put
        // all codes elements in each list
        for (String category : categories) {
            ArrayList<DelayCode> list = new ArrayList<DelayCode>();
            DelayCode code = null;
//            for (int i = 0; i < delayHandler.getCodes().size(); i++) {
//                code = delayHandler.getCodes().get(i);
//                if (code.getCategory().equals(category)) {
//                    list.add(code);
//                }
//            }
            for (int i = 0; i < codes.size(); i++) {
                code = codes.get(i);
                if (code.getCategory().equals(category)) {
                    list.add(code);
                }
            }
            result.add(list);
        }
        return result;
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        ((OnGroupExpandListener) expListView).onGroupExpand(groupPosition);
        this.expListView.setSelectedGroup(groupPosition);
    }

    private int getSelectionByNumber(String str) {
//        for (int i = 0; i < delayHandler.getCodes().size(); i++) {
//            if (delayHandler.getCodes().get(i).getNumber().equals(str)) {
//                return i;
//            }
//        }
        for (int i = 0; i < codes.size(); i++) {
            if (codes.get(i).getNumber().equals(str)) {
                return i;
            }
            // case of code numbers < 10 which could be numbered X or 0X
            if (str.length() < 2){
                str = "0" + str;

                if (codes.get(i).getNumber().equals(str)) {
                    return i;
                }
            }
        }

        Toast.makeText(this, getString(R.string.toastNoCodeFound),
                Toast.LENGTH_LONG).show();
        return -1;
    }

    private ArrayList<DelayCode> getFilteredCodesList(String s) {
        boolean flag = true;
        String[] keywords = s.split(" ");
        ArrayList<DelayCode> FilteredCodesList = new ArrayList<DelayCode>();
        DelayCode code = null;
//        for (int i = 0; i < delayHandler.getCodes().size(); i++) {
//            code = delayHandler.getCodes().get(i);
//            for (int j = 0; j < keywords.length; j++) {
//                flag = true;
//                if (code.getContent().toLowerCase()
//                        .contains(keywords[j].toLowerCase()) == false) {
//                    flag = false;
//                    break;
//                }
//            }
//            if (flag == true) {
//                FilteredCodesList.add(delayHandler.getCodes().get(i));
//            }
//        }
        for (int i = 0; i < codes.size(); i++) {
            code = codes.get(i);
            for (int j = 0; j < keywords.length; j++) {
                flag = true;
                if (code.getContent().toLowerCase()
                        .contains(keywords[j].toLowerCase()) == false) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                FilteredCodesList.add(codes.get(i));
            }
        }

        return FilteredCodesList;
    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // reset the view shift if necessary
        if (scrollState == SCROLL_STATE_IDLE) {
            if (isShifted) {
                this.expListView.scrollBy(0, 8);
                isShifted = false;
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {

        View view = getLayoutInflater().inflate(R.layout.context_menu_header,
                null);

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, CONTEXT_MENU_COLLAPSE_ALL, 0, R.string.all_categories);

        menu.setHeaderView(view);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case CONTEXT_MENU_COLLAPSE_ALL:
                for (int i = 0; i < this.expListView.getExpandableListAdapter()
                        .getGroupCount(); i++) {
                    expListView.collapseGroup(i);
                }
                expListView.setSelectedGroup(0);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    // private void exportCodesTable() {
    // if (Environment.getExternalStorageState().equals(
    // Environment.MEDIA_MOUNTED) != true) {
    // // TODO: display a toast mentioning media is not mounted
    // return;
    // }
    //
    // ClipboardManager clipboard = (ClipboardManager)
    // getSystemService(CLIPBOARD_SERVICE);
    //
    // clipboard.setText(convertTableToXML().toString());
    //
    // File path = Environment.getExternalStorageDirectory();
    // File file = new File(path, "/DelCo/testDelco.xml");
    //
    // try {
    // OutputStream os = new FileOutputStream(file);
    //
    // os.write(convertTableToXML().toString().getBytes());
    // os.close();
    //
    // } catch (IOException e) {
    // // Unable to create file, likely because external storage is
    // // not currently mounted.
    // Log.w("ExternalStorage", "Error writing " + file, e);
    // }
    // }

    // private StringBuilder convertTableToXML() {
    // StringBuilder sb = new StringBuilder();
    // sb.append("<?xml version=\"1.0'\" encoding=\"UTF-8\"?>");
    // sb.append("<annuaire>");
    // // read each category
    // for (int i = 0; i < delayHandler.getCodes().size(); i++) {
    // DelayCategory cat = delayHandler.getCodes().get(i);
    // sb.append("<category><id>");
    // if (cat.getId() < 10) {
    // sb.append("0");
    // }
    // sb.append(cat.getId()).append("</id><title>");
    // sb.append(cat.getTitle()).append("</title>");
    // // read each code of the category
    // for (int j = 0; j < cat.getAlCodesList().size(); j++) {
    // DelayCode code = cat.getAlCodesList().get(j);
    // sb.append("<code><number>").append(code.getCodeNumber());
    // sb.append("</number><content>");
    // sb.append(code.getCodeContent()).append("</content></code>");
    // }
    // sb.append("</category>");
    // }
    // sb.append("</annuaire>");
    // return sb;
    // }
}