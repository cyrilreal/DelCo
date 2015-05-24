package aero.delco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ScreenNumber extends Activity implements OnClickListener {

	// buttons declarations
	private Button btn01;
	private Button btn02;
	private Button btn03;
	private Button btn04;
	private Button btn05;
	private Button btn06;
	private Button btn07;
	private Button btn08;
	private Button btn09;
	private Button btn0;
	private Button btnDecimal;
	private Button btnClear;
	private Button btnOK;

	private EditText etNumber;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Utils.applySharedTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_number);

		initComponents();

	}

	private void initComponents() {
		etNumber = (EditText) findViewById(R.id.etNumber);

		// Buttons initialization
		btn01 = (Button) this.findViewById(R.id.Button01);
		btn01.setOnClickListener(this);

		btn02 = (Button) this.findViewById(R.id.Button02);
		btn02.setOnClickListener(this);

		btn03 = (Button) this.findViewById(R.id.Button03);
		btn03.setOnClickListener(this);

		btn04 = (Button) this.findViewById(R.id.Button04);
		btn04.setOnClickListener(this);

		btn05 = (Button) this.findViewById(R.id.Button05);
		btn05.setOnClickListener(this);

		btn05 = (Button) this.findViewById(R.id.Button05);
		btn05.setOnClickListener(this);

		btn06 = (Button) this.findViewById(R.id.Button06);
		btn06.setOnClickListener(this);

		btn07 = (Button) this.findViewById(R.id.Button07);
		btn07.setOnClickListener(this);

		btn08 = (Button) this.findViewById(R.id.Button08);
		btn08.setOnClickListener(this);

		btn09 = (Button) this.findViewById(R.id.Button09);
		btn09.setOnClickListener(this);

		btn0 = (Button) this.findViewById(R.id.Button0);
		btn0.setOnClickListener(this);

		btnDecimal = (Button) this.findViewById(R.id.btnDecimal);
		btnDecimal.setOnClickListener(this);

		btnClear = (Button) this.findViewById(R.id.btnClear);
		btnClear.setOnClickListener(this);

		btnOK = (Button) findViewById(R.id.btnOK);
		btnOK.setOnClickListener(this);
	}

	public void onClick(View v) {

		// Buttons action
		if (v == btn01) {
			updateNumberField("1");
		}

		if (v == btn02) {
			updateNumberField("2");
		}

		if (v == btn03) {
			updateNumberField("3");
		}

		if (v == btn04) {
			updateNumberField("4");
		}

		if (v == btn05) {
			updateNumberField("5");
		}

		if (v == btn06) {
			updateNumberField("6");
		}

		if (v == btn07) {
			updateNumberField("7");
		}

		if (v == btn08) {
			updateNumberField("8");
		}

		if (v == btn09) {
			updateNumberField("9");
		}

		if (v == btn0) {
			updateNumberField("0");
		}

		if (v == btnDecimal) {
			updateNumberField(".");
		}

		if (v == btnClear) {
			etNumber.setText(fieldBackspace(etNumber.getText().toString()));
		}

		if (v == btnOK) {
			Intent returnIntent = new Intent();
			returnIntent.putExtra("numberTyped", etNumber.getText().toString());

			setResult(RESULT_OK, returnIntent);
			finish();
		}
	}

	private String fieldBackspace(String str) {
		// this option prevents from erasing only the decimal symbol
		if (str.length() == 4) {
			return str.substring(0, str.length() - 2);
		}

		if (str.length() > 1) {
			return str.substring(0, str.length() - 1);
		}
		return "";
	}

	private void updateNumberField(String s) {
		if (etNumber.getText().length() == 2 && s != "."
				&& etNumber.getText().toString().contains(".") == false) {
			etNumber.setText(etNumber.getText().toString() + "." + s);
		} else {
			etNumber.setText(etNumber.getText().toString() + s);
		}
	}
}
