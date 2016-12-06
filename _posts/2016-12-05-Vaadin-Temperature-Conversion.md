---
layout: post
title: Vaadin Temperature Conversion 
tags: Vaadin UI Ajax MVC GUI
categories: UI
---

This is one of my first MVC applications in Vaadin. It demonstrate a simple form and interaction with ObjectProperty.

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
}
```

The whole Java file is available  <a href="{{'/static/vaadin/MyUI.java' | prepend: site.baseurl }}">here</a>.



