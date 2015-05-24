package aero.delco;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class ScreenResultKeyword extends ListActivity implements OnItemClickListener {
	ArrayAdapter<DelayCode> dcAdapter = null;
	
	ArrayList<DelayCode> alResultCodes = null;
	Button btnScrKeywordOK = null;

	String[] strNumberArray;
	String[] strContentArray;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Utils.applySharedTheme(this);
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.screen_result_keyword);
        
        Bundle extras = getIntent().getExtras();
        alResultCodes = new ArrayList<DelayCode>();
        if (extras != null) {
        	strNumberArray = extras.getStringArray("codeNumbers");
        	strContentArray = extras.getStringArray("codeContents");
        	
        	for (int i = 0; i < strNumberArray.length; i++) {
				alResultCodes.add(new DelayCode(null, strNumberArray[i], strContentArray[i]));
			}
        }

        dcAdapter = new DelayAdapter(this, R.layout.child_row, alResultCodes);
		setListAdapter(dcAdapter);
		getListView().setOnItemClickListener(this);
        initComponents();  
    }
	
	private void initComponents() {

		btnScrKeywordOK = (Button) findViewById(R.id.btnResultKeywordOK);
		btnScrKeywordOK.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
		
		Intent intent = new Intent(this, ScreenResultNumber.class);
		intent.putExtra("codeNumbers", strNumberArray);
		intent.putExtra("codeContents", strContentArray);
		intent.putExtra("index", pos);
		startActivity(intent);
	}
}


