package br.com.jera.gasosa.test;

import br.com.jera.gasosa.Calculator;
import br.com.jera.gasosa.Calculator.Fuel;
import android.test.AndroidTestCase;

public class CalculatorTest extends AndroidTestCase {

	Calculator calculator;

	@Override
	protected void setUp() throws Exception {
		calculator = new Calculator();
	}

	public void testSetGasolinePriceFromText() {
		calculator.setGasolinePriceFromText("1.33");
		assertEquals("Should convert to 1.33", 1.33,
				calculator.getGasolinePrice());
	}

	public void testSetGasolinePriceFromBlankText() {
		calculator.setGasolinePriceFromText("");
		assertEquals("Should convert to 0", 0.0, calculator.getGasolinePrice());
	}

	public void testSetEthanolPriceFromText() {
		calculator.setEthanolPriceFromText("1.33");
		assertEquals("Should convert to 1.33", 1.33,
				calculator.getEthanolPrice());
	}

	public void testSetEthanolPriceFromBlankText() {
		calculator.setEthanolPriceFromText("");
		assertEquals("Should convert to 0", 0.0, calculator.getEthanolPrice());
	}

	public void testEvaluatePriceGasoline() {

		calculator.setEthanolPriceFromText("1.89");
		calculator.setGasolinePriceFromText("2.69");
		Fuel fuel = calculator.evaluatePrice();

		assertEquals("Should return Fuel.Gasoline", Fuel.GASOLINE, fuel);

	}

	public void testEvaluatePriceEthanol() {

		calculator.setEthanolPriceFromText("1.88");
		calculator.setGasolinePriceFromText("2.69");
		Fuel fuel = calculator.evaluatePrice();

		assertEquals("Should return Fuel.Ethanol", Fuel.ETHANOL, fuel);

	}
	
	public void testRatio(){
		
		calculator.setEthanolPriceFromText("1.88");
		calculator.setGasolinePriceFromText("2.69");
		double ratio = calculator.ratio();
		assertEquals("Should return 69.88847583643123", 69.88847583643123, ratio);
	}

}
