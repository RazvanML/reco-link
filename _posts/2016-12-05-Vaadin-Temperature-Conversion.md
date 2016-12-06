---
layout: post
title: Vaadin Temperature Conversion 
tags: Vaadin UI Ajax MVC
categories: UI
---

This is one of my first MVC applications in Vaadin. It demonstrate a simple form and interaction with Object Properties.

<!--more-->

The application performs temperature conversion between multiple scales. A list of formulas as well as a converter can be found <a href="http://www.csgnetwork.com/temp2conv.html">here</a>.

The architecture of the application consists of a temperature bean, which is written by multiple fields. The fields write to the bean through the converter. The bean retains the Kelvin version, while the control show their specific scales.

<figure>
    <img src="{{'/static/vaadin/screenshot.png' | prepend: site.baseurl | prepend: site.url }}" 
    alt='Temperature conversion application' />
    <figcaption>Temperature conversion application</figcaption>
</figure>
