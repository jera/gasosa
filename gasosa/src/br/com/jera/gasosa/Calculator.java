package br.com.jera.gasosa;

public class Calculator {

	private double gasPrice;
	private double ethanolPrice;

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

	public Fuel evaluatePrice(float ethanolKm, float gasKm) {
		double rc = gasKm/ethanolKm;
		
		gasPrice = rc * gasPrice;
		
		if (gasPrice <= ethanolPrice) {
			return Fuel.GASOLINE;
		} else {
			return Fuel.ETHANOL;
		}
	}

	public double ratio() {
		return (ethanolPrice / gasPrice) * 100;
	}

	public void setGasolinePriceFromText(String text) {
		this.gasPrice = parsePrice(text);
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
		return gasPrice;
	}

	public double getEthanolPrice() {
		return ethanolPrice;
	}

}
