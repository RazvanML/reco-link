package com.example.VaadinScratch;

import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Validator;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

// formulas from http://www.csgnetwork.com/temp2conv.html

@SuppressWarnings("serial")
abstract class StringDoubleAbstract implements Converter<String, Double> {

	@Override
	public Class<Double> getModelType() {
		return Double.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}

class KelvinConverter extends StringDoubleAbstract {

	private static final long serialVersionUID = -1697465337882126597L;

	@Override
	public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) {
		return Double.parseDouble(value);
	}

	@Override
	public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale) {
		return String.format("%.2f", value);
	}
}

class CelsiusConverter extends StringDoubleAbstract {

	private static final long serialVersionUID = -1524591030869715534L;

	@Override
	public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) {
		return Double.parseDouble(value) + 273.15;
	}

	@Override
	public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale) {
		return String.format("%.2f", value - 273.15);
	}
}

class FahrenheitConverter extends StringDoubleAbstract {

	private static final long serialVersionUID = -5183124170264846969L;

	@Override
	public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) {
		return 5d / 9d * (Double.parseDouble(value) - 32) + 273.15;
	}

	@Override
	public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale) {
		return String.format("%.2f", 9d / 5d * (value - 273.15) + 32);
	}
}

class ReaumurConverter extends StringDoubleAbstract {

	private static final long serialVersionUID = 6532528732688067418L;

	@Override
	public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) {
		return Double.parseDouble(value) * 1.25 + 273.15;
	}

	@Override
	public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale) {
		return String.format("%.2f", (value - 273.15) * 0.8);
	}
}

class RankineConverter extends StringDoubleAbstract {

	private static final long serialVersionUID = 1064145329443226638L;

	@Override
	public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) {
		return Double.parseDouble(value) / 1.8;
	}

	@Override
	public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale) {
		return String.format("%.2f", value * 1.8);
	}
}

class TemperatureyValidator implements Validator {

	private static final long serialVersionUID = 3592212675362803148L;

	@Override
	public void validate(Object value) throws InvalidValueException {
		if ((Double) value < 0)
			throw new InvalidValueException("Physically impossible temperature");
	}
};

@Theme("mytheme")
public class MyUI extends UI {
;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3390276707182872192L;

	ObjectProperty<Double> temp = new ObjectProperty<Double>(100.0);

	TextField[] fields;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final FormLayout layout = new FormLayout();

		final TextField raw = new TextField(temp);
		raw.setCaption("Kelvin - raw value");

		final TextField kelvin = new TextField(temp);
		kelvin.setConverter(new KelvinConverter());
		kelvin.setCaption("Kelvin");

		final TextField celsius = new TextField(temp);
		celsius.setConverter(new CelsiusConverter());
		celsius.setCaption("Celsius");

		final TextField fahrenh = new TextField(temp);
		fahrenh.setConverter(new FahrenheitConverter());
		fahrenh.setCaption("Fahrenheit");

		final TextField reaumur = new TextField(temp);
		reaumur.setConverter(new ReaumurConverter());
		reaumur.setCaption("Réaumur");

		final TextField rankine = new TextField(temp);
		rankine.setConverter(new RankineConverter());
		rankine.setCaption("Rankine");

		temp.addValueChangeListener(e->{
			if (fields != null)
				for (TextField tf : fields) {
					try {
						tf.validate();
					} catch(RuntimeException ex) {
						System.out.println(tf.getComponentError().getFormattedHtmlMessage());
						tf.discard();						
					}
				}			
		});
		
		fields = new TextField[] {raw, kelvin, celsius, fahrenh, reaumur, rankine };
		for (TextField tf : fields) {
			tf.setImmediate(true);
			tf.addValidator(new TemperatureyValidator());
			layout.addComponent(tf);

		}

		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = 4264575054696279753L;
	}
}
