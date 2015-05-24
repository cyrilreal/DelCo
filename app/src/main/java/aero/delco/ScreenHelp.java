package aero.delco;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScreenHelp extends Activity implements OnClickListener {

	private Button btnHelpOK;
	private Button btnMail;
	private Button btnWeb;
	private TextView helpContent;
	
	protected void onCreate(Bundle savedInstanceState) {
		Utils.applySharedTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_help);
		initComponents();
	}

	private void initComponents() {
		btnHelpOK = (Button) findViewById(R.id.btnHelpOK);
		btnHelpOK.setOnClickListener(this);
		btnMail = (Button) findViewById(R.id.btnMail);
		btnMail.setOnClickListener(this);
		btnWeb = (Button) findViewById(R.id.btnWeb);
		btnWeb.setOnClickListener(this);
		
		helpContent = (TextView) findViewById(R.id.tvHelpContent);
		setupFontSize(helpContent);
	}

	@Override
	public void onClick(View v) {
		if (v == btnHelpOK) {
			setResult(RESULT_OK);
			finish();
		}

		if (v == btnMail) {
			final Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("text/plain");
			emailIntent.putExtra(Intent.EXTRA_EMAIL,
					new String[] { getString(R.string.email_adress) });
			emailIntent.putExtra(Intent.EXTRA_SUBJECT,
					getString(R.string.email_subject));

			try {
				startActivity(Intent.createChooser(emailIntent,""));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(ScreenHelp.this,
						getString(R.string.no_email_client), Toast.LENGTH_LONG)
						.show();
			}
		}

		if (v == btnWeb) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.web_adress)));
			startActivity(browserIntent);
		}
	}

	private void setupFontSize(TextView tv) {

		switch (Utils.getSharedFontSize(this)) {
		case R.string.FontSizeSmall:
			tv.setTextAppearance(this, R.style.fontTextSmall);
			break;

		case R.string.FontSizeMedium:
			tv.setTextAppearance(this, R.style.fontTextMedium);
			break;

		case R.string.FontSizeLarge:
			tv.setTextAppearance(this, R.style.fontTextLarge);
			break;
		default:
			break;
		}
	}
}
