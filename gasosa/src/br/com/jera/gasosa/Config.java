package br.com.jera.gasosa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.jeramobstats.JeraAgent;

public class Config extends GasosaActivity {

	private Button saveButton;
	private EditText ethanolKm;
	private Button defaultButton;
	private EditText gasKm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		saveButton = (Button) findViewById(R.id.save_button);
		defaultButton = (Button) findViewById(R.id.deafault_button);
		ethanolKm = (EditText) findViewById(R.id.ethanol_km);
		gasKm = (EditText) findViewById(R.id.gas_km);
		defaultButton.setOnClickListener(new SaveDefaultPrefs());
		saveButton.setOnClickListener(new SavePrefs());
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		ethanolKm.setText(String.valueOf(prefs.getFloat("ethanol_km", 0)));
		gasKm.setText(String.valueOf(prefs.getFloat("gas_km", 0)));
	}

	private class SaveDefaultPrefs implements View.OnClickListener {

		public void onClick(View view) {

			AlertDialog.Builder builder = new AlertDialog.Builder(Config.this);
			builder.setMessage(Config.this.getString(R.string.config_alert)).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
					editor.putBoolean("default_prefs", true);
					editor.commit();
					startActivity(new Intent(Config.this, Principal.class));
				}
			}).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	private class SavePrefs implements OnClickListener {

		public void onClick(View view) {
			SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
			editor.putFloat("ethanol_km", Float.parseFloat(ethanolKm.getText().toString()));
			editor.putFloat("gas_km", Float.parseFloat(gasKm.getText().toString()));
			editor.putBoolean("default_prefs", false);
			editor.commit();
            JeraAgent.logEvent("SAVED_PREFERENCES");
			startActivity(new Intent(Config.this, Principal.class));
		}
	}

}