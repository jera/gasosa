package br.com.jera.gasosa;

import android.text.TextUtils;

public class Calculator {

	private double gasolinePrice;
	private double ethanolPrice;

	private final static double FACTOR = 70;

	public enum Fuel {

		GASOLINE("Gasolina"), ETHANOL("Etanol");

		private String name;

		Fuel(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	public Calculator(double gasolinePrice, double ethanolPrice) {
		this.ethanolPrice = ethanolPrice;
		this.gasolinePrice = gasolinePrice;
	}

	public Calculator() {
	}

	public Fuel evaluatePrice() {

		double finalValue = gasolinePrice / 100 * FACTOR;
		if (finalValue <= ethanolPrice) {
			return Fuel.GASOLINE;
		} else {
			return Fuel.ETHANOL;
		}
	}
	 public double ratio(){
		 return (ethanolPrice/gasolinePrice)*100;
	 }
	public void setGasolinePriceFromText(String text) {

		if (TextUtils.isEmpty(text)) {
			this.gasolinePrice = 0;
		} else {
			try {
				this.gasolinePrice = Double.parseDouble(text);
			} catch (NumberFormatException e) {
				this.gasolinePrice = 0;
			}
		}
	}

	public void setEthanolPriceFromText(String text) {

		if (TextUtils.isEmpty(text)) {
			this.ethanolPrice = 0;
		} else {
			try {
				this.ethanolPrice = Double.parseDouble(text);
			} catch (NumberFormatException e) {
				this.ethanolPrice = 0;
			}
		}

	}

	public double getGasolinePrice() {
		return gasolinePrice;
	}

	public double getEthanolPrice() {
		return ethanolPrice;
	}

}
