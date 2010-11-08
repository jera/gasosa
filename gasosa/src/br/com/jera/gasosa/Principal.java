package br.com.jera.gasosa;

import br.com.jera.gasosa.Calculator.Fuel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends Activity {
	
	private EditText gasolinePriceText;
	private EditText etanolPriceText;
	private ImageView resultImage;
	private TextView resultGas;
	private TextView resultEtanol;
	private ImageView link;
	
	private Button calcButton;

	private InputMethodManager imm;
	private Calculator calculator;
	
	private Animation fadeAnimation; 
	
	String format;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		calculator = new Calculator();
		
		resultImage = (ImageView) findViewById(R.id.resultImage);
		
		calcButton = (Button) findViewById(R.id.calcButton);
		gasolinePriceText = (EditText) findViewById(R.id.gasolina_price);
		etanolPriceText = (EditText) findViewById(R.id.alcool_price);
		resultGas = (TextView) findViewById(R.id.resultGas);
		resultEtanol = (TextView) findViewById(R.id.resultEthanol);
		link = (ImageView) findViewById(R.id.link);
		
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade);
		
		format = getResources().getString(R.string.result);
		
		/*
		 *  o operador this.new faz com que a inner class seja associada
		 *  à essa mesma instância da outer class (Principal) ao invés
		 *  de criar uma instância nova
		 */
		calcButton.setOnClickListener(this.new CalcHandler());
		link.setOnClickListener(this.new OpenLink());
	}
	
	
	private class OpenLink implements OnClickListener{
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.m.jera.com.br") );
				startActivity(intent);
				
			}
	}
	
	
	private class CalcHandler implements OnClickListener {

			public void onClick(View view) {
				
				calculator.setEthanolPriceFromText(etanolPriceText.getText().toString());
				calculator.setGasolinePriceFromText(gasolinePriceText.getText().toString());
				
				if (calculator.getEthanolPrice() <= 0) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.etanol_required), Toast.LENGTH_SHORT).show();
					return;
				}
				
				if (calculator.getGasolinePrice()  <= 0) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.gasoline_required), Toast.LENGTH_SHORT).show();
					return;
				}

				Fuel fuel = calculator.evaluatePrice();
				
				if(fuel.equals(Fuel.GASOLINE)){
					showResult(R.drawable.gas,resultGas,resultEtanol);
				}
				else{
					showResult(R.drawable.ethanol,resultEtanol,resultGas);
				}
				
				//oculta o teclado virtual do android
				imm.hideSoftInputFromWindow(calcButton.getWindowToken(), 0);
			}

	}
	
	private void showResult(int imageId, TextView show, TextView hide  ){
		resultImage.setImageResource(imageId);
		show.setVisibility(View.VISIBLE);
		hide.setVisibility(View.INVISIBLE);
		
		show.setText(String.format(format, calculator.ratio() ));
		show.startAnimation(fadeAnimation);
		
		resultImage.startAnimation(fadeAnimation);
		resultImage.setVisibility(View.VISIBLE);
	}


}

