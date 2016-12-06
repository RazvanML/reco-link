---
layout: post
title: Vaadin Temperature Conversion 
tags: Vaadin UI Ajax MVC GUI
categories: UI
---

This is one of my first MVC applications in Vaadin. It demonstrate a simple form and interaction with ```ObjectProperty```.

<!--more-->

The application performs temperature conversion between multiple scales. A list of formulas as well as a converter can be found <a href="http://www.csgnetwork.com/temp2conv.html">here</a>.

The architecture of the application consists of a temperature bean, which is written by multiple fields. The fields write to the bean through the converter. The bean retains the Kelvin version, while the controls show their specific scales.

<figure>
    <img src="{{'/static/vaadin/screenshot.png' | prepend: site.baseurl }}" 
    alt='Temperature conversion application' />
    <figcaption>Temperature conversion application</figcaption>
</figure>

Let's start by creating the data model.

```java
@Theme("mytheme")
public class MyUI extends UI {
	ObjectProperty<Double> temp = new ObjectProperty<Double>(100.0);
		@Override
	protected void init(VaadinRequest vaadinRequest) {
		final FormLayout layout = new FormLayout();

		final TextField raw = new TextField(temp);
		raw.setCaption("Kelvin - raw value");
	}
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
```

The ``ObjectProperty`` instance keeps the Kelvin temperature, while the raw field edits the value without performing any conversions.
Further, all Java statements will go inside the init method, except of the standalone class declarations.

Let's add a blank Kelvin converter and a Kelvin field. The only improvement compared to the raw control is the two decimal enforcement.

```java
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
	@Override
	public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) {
		return Double.parseDouble(value);
	}

	@Override
	public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale) {
		return String.format("%.2f", value);
	}
}
```
The generic ```StringDoubleAbstract``` describes the ```Converter``` nature: it will take a ```Double``` as model and expose a ```String``` to the UI control.

```java
		final TextField kelvin = new TextField(temp);
		kelvin.setConverter(new KelvinConverter());
		kelvin.setCaption("Kelvin");
```

Further converters can be developed on this pattern. Please note that the converter computes the Kelvin temperature when communicating from control to model and the specific scale when processing the model to control.


```java
class CelsiusConverter extends StringDoubleAbstract {
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
	@Override
	public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) {
		return Double.parseDouble(value) / 1.8;
	}
	@Override
	public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale) {
		return String.format("%.2f", value * 1.8);
	}
}
```

Although laborious, this technique allows to develop as many scales as required, with only two formulas per scale.

And in the ```init``` method:

```java
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
```

Next step is to have the controls added to the form layout. Addtional properies, such as ```immediate```, can be set as part of the interface build loop.

```java
		fields = new TextField[] {raw, kelvin, celsius, fahrenh, reaumur, rankine };
		for (TextField tf : fields) {
			tf.setImmediate(true);
			layout.addComponent(tf);
		}
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
```

In this point, a fully functional temperature converter has been developed. Let's add some "whistles and bells". Physically, a temperature equal or under zero is impossible. Such a value, introduced in a application, will probably force the application to crash or return invalid numeric results. We need to introduce a validator, so that no temperature equal or under zero is to be written to the bean.

```java
class TemperatureyValidator implements Validator {
	@Override
	public void validate(Object value) throws InvalidValueException {
		if ((Double) value <= 0)
			throw new InvalidValueException("Physically impossible temperature");
	}
};
```
This validator is being set to each control. Note that the validator operates with the model not with the presentation value. The following line goes in the loop executed for each ```TextField```:

```java
tf.addValidator(new TemperatureyValidator());
```
In this moment, introducing in any of the controls a temperature which will yield to a zero or sub-zero Kelvin, the control will display an error.
It would be useful to have the error cleared if a valid temperature is being introduce in another field. To implement this, a listener on the bean property is required, so that writing a valid value here will clear up the invalid values:

```java
		temp.addValueChangeListener(e->{
			if (fields != null)
				for (TextField tf : fields) {
					try {
						tf.validate();
					} catch(RuntimeException ex) {
						tf.discard();						
					}
				}			
		});
```

Note the ```validate``` call. This is necessary because as of Vaadin 7.7.5, the method ```getComponentError()``` returns null even for invalidated controls.

The whole Java file is available  <a href="{{'/static/vaadin/MyUI.java' | prepend: site.baseurl }}">here</a>.



