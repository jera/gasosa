package br.com.jera.gasosa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import br.com.jeramobstats.JeraAgent;

public class GasosaActivity extends Activity {

	public static final String PREFS_NAME = "br.com.jera.gasosa.Config";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		JeraAgent.onStartSession(this, "QI4YUGV5K7FN7I42RPA1");
	}

	@Override
	protected void onStop() {
		super.onStop();
		JeraAgent.onEndSession(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.config: {
			startActivity(new Intent(this, Config.class));
			return true;
		}
		case R.id.principal: {
			Intent intent = new Intent(this, Principal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class MoneyTextWatcher implements TextWatcher {
		EditText editText;

		public MoneyTextWatcher(EditText editText) {
			this.editText = editText;
		}

		@Override
		public void afterTextChanged(Editable editable) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

			if (!s.toString().matches("^(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{3})?$")) {
				String userInput = "" + s.toString().replaceAll("[^\\d]", "");
				StringBuilder cashAmountBuilder = new StringBuilder(userInput);

				while (cashAmountBuilder.length() >= 3 && cashAmountBuilder.charAt(0) == '0') {
					cashAmountBuilder.deleteCharAt(0);
				}
				while (cashAmountBuilder.length() <= 3) {
					cashAmountBuilder.insert(0, '0');
				}
				cashAmountBuilder.insert(cashAmountBuilder.length() - 3, '.');

				editText.setText(cashAmountBuilder.toString());
				editText.setTextKeepState(cashAmountBuilder.toString());
				Selection.setSelection(editText.getText(), cashAmountBuilder.toString().length());
			}
		}
	}
}
