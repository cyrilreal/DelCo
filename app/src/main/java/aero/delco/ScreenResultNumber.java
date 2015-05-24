package aero.delco;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScreenResultNumber extends Activity implements OnClickListener {

	Button btnCodePrevious;
	Button btnCodeNext;
	Button btnOK;

	TextView tvCodeNumber;
	TextView tvCodeContent;
	TextView tvCode;
	
	String[] arrayCodeNumber;
	String[] arrayCodeContent;

	int index;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Utils.applySharedTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_result_number);

		// construct arrays
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			arrayCodeNumber = extras.getStringArray("codeNumbers");
			arrayCodeContent = extras.getStringArray("codeContents");
			index = extras.getInt("index", 0);
		}
		initComponents();

		// display the appropriate code
		displayCode(index);
	}

	public void initComponents() {
		tvCodeNumber = (TextView) findViewById(R.id.tvCodeNumber);
		tvCodeContent = (TextView) findViewById(R.id.tvCodeContent);
		btnCodePrevious = (Button) findViewById(R.id.btnPreviousCode);
		btnCodePrevious.setOnClickListener(this);
		btnCodeNext = (Button) findViewById(R.id.btnNextCode);
		btnCodeNext.setOnClickListener(this);
		btnOK = (Button) findViewById(R.id.btnOK);
		btnOK.setOnClickListener(this);
		
		Utils.setupFontSizeTitle(this,tvCodeNumber);
		Utils.setupFontSize(this, tvCodeContent);
	}

	@Override
	public void onClick(View v) {

		if (v == btnCodePrevious) {
			if (index > 0) {
				index--;
				displayCode(index);
			}
		}

		if (v == btnCodeNext) {
			if (index < arrayCodeNumber.length - 1) {
				index++;
				displayCode(index);
			}
		}

		if (v == btnOK) {
			setResult(RESULT_OK);
			finish();
		}
	}

	private void displayCode(int index) {
		tvCodeNumber.setText("Code  " + arrayCodeNumber[index]);
		tvCodeContent.setText(Html.fromHtml(arrayCodeContent[index]));
	}
}
