---
layout: post
title: Vaadin Temperature Conversion 
tags: Vaadin UI Ajax MVC GUI
categories: UI
---

This is one of my first MVC applications in Vaadin. It demonstrate a simple form and interaction with ```ObjectProperty```.

<!--more-->

The application performs temperature conversion between multiple scales. A list of formulas as well as a converter can be found <a href="http://www.csgnetwork.com/temp2conv.html">here</a>.

The architecture of the application consists of a temperature bean, which is written by multiple fields. The fields write to the bean through the converter. The bean retains the Kelvin version, while the control show their specific scales.

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
}
```

The ``ObjectProperty`` instance keeps the Kelvin temperature, while the raw field edits the value without performing any conversions.
Further, all Java statements will go inside the init method, except of the standalone class declarations.

Let's add a blank Kelvin converter and a Kelvin field. The only difference is the two decimal enforcement.

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


```java
```

```java
```

```java
```

The whole Java file is available  <a href="{{'/static/vaadin/MyUI.java' | prepend: site.baseurl }}">here</a>.



