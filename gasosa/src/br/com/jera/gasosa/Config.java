package br.com.jera.gasosa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Config extends GasosaActivity {

	private Button saveButton;
	private EditText ethanolKm;
	private EditText ethanolLiters;
	private EditText gasKm;
	private EditText gasLiters;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		saveButton = (Button) findViewById(R.id.saveButton);
		ethanolKm = (EditText) findViewById(R.id.ethanol_km);
		ethanolLiters = (EditText) findViewById(R.id.ethanol_liters);
		gasKm = (EditText) findViewById(R.id.gas_km);
		gasLiters = (EditText) findViewById(R.id.gas_liters);

		saveButton.setOnClickListener(this.new SavePrefs(this));
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		ethanolKm.setText(String.valueOf(prefs.getFloat("ethanol_km", 0)));
		ethanolLiters.setText(String.valueOf(prefs.getFloat("ethanol_liters", 0)));
		gasKm.setText(String.valueOf(prefs.getFloat("gas_km", 0)));
		gasLiters.setText(String.valueOf(prefs.getFloat("gas_liters", 0)));
	}

	private class SavePrefs implements OnClickListener {

		private Activity activiy;

		public SavePrefs(Activity activity) {
			this.activiy = activity;
		}

		public void onClick(View view) {
			SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
			editor.putFloat("ethanol_km", Float.parseFloat(ethanolKm.getText().toString()));
			editor.putFloat("ethanol_liters", Float.parseFloat(ethanolLiters.getText().toString()));
			editor.putFloat("gas_km", Float.parseFloat(gasKm.getText().toString()));
			editor.putFloat("gas_liters", Float.parseFloat(gasLiters.getText().toString()));
			editor.commit();
			startActivity(new Intent(this.activiy, Principal.class));
		}
	}

}