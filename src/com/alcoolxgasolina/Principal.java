package com.alcoolxgasolina;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends Activity {
	
	private static final String ETANOL = "etanol";
	private static final String GASOLINE = "gasoline";
	
	private EditText gasolinePriceText;
	private EditText etanolPriceText;
	private ImageView resultImage;
	private TextView resultGas;
	private TextView resultEtanol;
	private ImageView link;
	
	private Button calcButton;

	private final OnClickListener calc;
	
	private InputMethodManager imm;

	private final static double FACTOR = 70;

	// inicialização estática dos handlers de eventos
	{
		calc = calcHandler();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		resultImage = (ImageView) findViewById(R.id.resultImage);
		
		calcButton = (Button) findViewById(R.id.primeiroBotao);
		gasolinePriceText = (EditText) findViewById(R.id.gasolina_price);
		etanolPriceText = (EditText) findViewById(R.id.alcool_price);
		resultGas = (TextView) findViewById(R.id.resultGas);
		resultEtanol = (TextView) findViewById(R.id.resultEtanol);
		link = (ImageView) findViewById(R.id.link);
		calcButton.setOnClickListener(this.calc);
		
		link.setOnClickListener(this.openLink());
		
		

	}

	public double calculateFinalValue(double gasolinePrice) {
		return gasolinePrice / 100 * FACTOR;
	}

	public String evaluatePrice(double valor_final, double etanolPrice, double gasolinePrice) {
		if (valor_final <= etanolPrice) {
			return GASOLINE;
		} else {
			return ETANOL;
		}
	}
	
	private OnClickListener openLink(){
		return new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.m.jera.com.br") );
				startActivity(intent);
				
			}
		};
	}
		
	private OnClickListener calcHandler() {

		return new OnClickListener() {

			public void onClick(View view) {

				if (gasolinePriceText.length() <= 0) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.gasoline_required), Toast.LENGTH_LONG).show();
					return;
				}

				if (etanolPriceText.length() <= 0) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.etanol_required), Toast.LENGTH_LONG).show();
					return;
				}

				double gasolinePrice = Double.parseDouble(gasolinePriceText.getText().toString());
				double etanolPrice = Double.parseDouble(etanolPriceText.getText().toString());

				double valor_final = calculateFinalValue(gasolinePrice);
				
				String r = evaluatePrice(valor_final, etanolPrice, gasolinePrice);
				if(r.equals(GASOLINE)){
					resultImage.setImageDrawable(getResources().getDrawable(R.drawable.gas));
					resultEtanol.setVisibility(View.INVISIBLE);
					resultGas.setVisibility(View.VISIBLE);
					
					String format = getResources().getString(R.string.result);
					resultGas.setText(String.format(format, (etanolPrice/gasolinePrice)*100 ));
					resultGas.startAnimation(AnimationUtils.loadAnimation( Principal.this, R.anim.fade ));
				}
				else{
					resultImage.setImageDrawable(getResources().getDrawable(R.drawable.etanol));
					resultEtanol.setVisibility(View.VISIBLE);
					resultGas.setVisibility(View.INVISIBLE);
					
					
					String format = getResources().getString(R.string.result);
					resultEtanol.setText(String.format(format, (etanolPrice/gasolinePrice)*100));
					resultEtanol.startAnimation(AnimationUtils.loadAnimation( Principal.this, R.anim.fade ));
				}
				
				resultImage.startAnimation(AnimationUtils.loadAnimation( Principal.this, R.anim.fade ));
				resultImage.setVisibility(View.VISIBLE);
				
				//oculta o teclado virtual do android
				imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(calcButton.getWindowToken(), 0);

			}
		};

	}

	private OnClickListener clearHandler() {
		return new View.OnClickListener() {

			public void onClick(View v) {
				gasolinePriceText.setText(null);
				etanolPriceText.setText(null);
			}
		};
	}

}