package br.com.jera.gasosa;

import android.content.SharedPreferences;

public class Calculator {

	private double gasolinePrice;
	private double ethanolPrice;
	SharedPreferences prefs;

	public Calculator(SharedPreferences prefs) {
		this.prefs = prefs;
	}

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

	public Fuel evaluatePrice() {
		float ethanolKm = prefs.getFloat("ethanol_km", 0);
		float gasKm = prefs.getFloat("gas_km", 0);
		boolean defaultPrefs = prefs.getBoolean("default_prefs", false);

		if (defaultPrefs || (gasKm <= 0 || ethanolKm <= 0)) {
			double finalValue = gasolinePrice / 100 * 70;

			if (finalValue <= ethanolPrice) {
				return Fuel.GASOLINE;
			} else {
				return Fuel.ETHANOL;
			}
		}
		else {
			if(ethanolPrice/ethanolKm < gasolinePrice/gasKm) {
				return Fuel.ETHANOL;
			}
			else{
				return Fuel.GASOLINE;
			}
		}
	}

	public double ratio() {
		return (ethanolPrice / gasolinePrice) * 100;
	}

	public void setGasolinePriceFromText(String text) {
		this.gasolinePrice = parsePrice(text);
	}

	public void setEthanolPriceFromText(String text) {
		this.ethanolPrice = parsePrice(text);
	}

	private double parsePrice(String text) {
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public double getGasolinePrice() {
		return gasolinePrice;
	}

	public double getEthanolPrice() {
		return ethanolPrice;
	}

}
