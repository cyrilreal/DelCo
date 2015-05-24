package aero.delco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ScreenPickKeyword extends Activity {

	EditText etKeyword = null;
	Button btnKeywordOK = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Utils.applySharedTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_pick_keyword);
		initComponents();
		etKeyword.requestFocus();
	}

	public void initComponents() {

		etKeyword = (EditText) findViewById(R.id.etKeyword);

		// software keyboard handling:
		// first retrieve the service that receive input from virtual keyboard
		final InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.restartInput(etKeyword);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

		etKeyword
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView arg0, int action,
							KeyEvent arg2) {

						if (action == EditorInfo.IME_ACTION_SEARCH) {
							// Force virtual keyboard to close
							imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
									0);
							Intent returnIntent = new Intent();
							returnIntent.putExtra("keyword", etKeyword
									.getText().toString());
							setResult(RESULT_OK, returnIntent);

							finish();
							return true;
						}
						return false;
					}
				});

		btnKeywordOK = (Button) findViewById(R.id.btnKeywordOK);
		btnKeywordOK.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Force virtual keyboard to close
				 imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

				Intent returnIntent = new Intent();
				returnIntent
						.putExtra("keyword", etKeyword.getText().toString());
				setResult(RESULT_OK, returnIntent);
				
				finish();
			}
		});
	}
}
