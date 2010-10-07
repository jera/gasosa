package com.alcoolxgasolina;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends Activity {

	private EditText gasolinePriceText;
	private EditText etanolPriceText;
	private EditText gasolinePriceLabel;
	private EditText etanolPriceLabel;
	private TextView resultText;
	private TextView resultLabel;

	private final OnClickListener calc;
	private final OnClickListener clear;
	private final OnClickListener about;

	private final static double FACTOR = 70;

	// inicialização estática dos handlers de eventos
	{
		
		calc = calcHandler();
		clear = clearHandler();

		about = new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.about), Toast.LENGTH_LONG).show();
			}
		};
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button calcButton = (Button) findViewById(R.id.primeiroBotao);
		gasolinePriceText = (EditText) findViewById(R.id.gasolina_price);
		etanolPriceText = (EditText) findViewById(R.id.alcool_price);
		resultText = (TextView) findViewById(R.id.visualizar);
		calcButton.setOnClickListener(this.calc);

		Button clearButton = (Button) findViewById(R.id.segundoBotao);
		gasolinePriceLabel = (EditText) findViewById(R.id.gasolina_price);
		etanolPriceLabel = (EditText) findViewById(R.id.alcool_price);
		resultLabel = (TextView) findViewById(R.id.visualizar);
		clearButton.setOnClickListener(this.clear);

		ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebutton);
		aboutButton.setOnClickListener(about);

	}

	public double calculateFinalValue(double gasolinePrice) {
		return gasolinePrice / 100 * FACTOR;
	}

	public String evaluatePrice(double valor_final, double etanolPrice, double gasolinePrice) {
		if (valor_final <= etanolPrice) {
			return String.format("Para compensar o preço do Álcool deveria estar a: R$ %.2f", valor_final);
		} else {
			return String.format("O preço do álcool ( R$ %.2f )  atingiu %.0f%% do valor da gasolina ( R$ %.2f), está valendo a pena! ",
					etanolPrice, FACTOR, gasolinePrice);
		}
	}
	

		
	private OnClickListener calcHandler() {

		return new OnClickListener() {

			public void onClick(View view) {

				if (gasolinePriceText.length() <= 0) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.gasoline_required), Toast.LENGTH_LONG).show();
					resultText.setText(null);
					return;
				}

				if (etanolPriceText.length() <= 0) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.etanol_required), Toast.LENGTH_LONG).show();
					resultText.setText(null);
					return;
				}

				double gasolinePrice = Double.parseDouble(gasolinePriceText.getText().toString());
				double etanolPrice = Double.parseDouble(etanolPriceText.getText().toString());

				double valor_final = calculateFinalValue(gasolinePrice);

				resultText.setText(evaluatePrice(valor_final, etanolPrice, gasolinePrice));
			}
		};

	}

	private OnClickListener clearHandler() {
		return new View.OnClickListener() {

			public void onClick(View v) {
				resultLabel.setText(null);
				gasolinePriceLabel.setText(null);
				etanolPriceLabel.setText(null);
			}
		};
	}

}