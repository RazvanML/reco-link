---
layout: post
title: Robot cart
tags: Arduino toy wireless
categories: Arduino
---

Few days ago I've bought <a href="https://www.aliexpress.com/item/3256802871526756.html">2WD Smart Robot Car Kit For ESP8266 ESP-12E D1 Wifi Board For Arduino Control by Mobile Ultrasonic Module </a>.

Here is the step by step assembly and the sequence of deploying software.
<!--more-->

<figure>
    <img src="{{'/static/robot/IMG_1069.JPG' | prepend: site.baseurl }}" 
    alt='Start by installing the raisers for the microcontroller and the motor control board' />
    <figcaption>Start by installing the raisers for the microcontroller and the motor control board</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1071.JPG' | prepend: site.baseurl }}" 
    alt='Install both motors (just one shown) by holding it between two brackets' />
    <figcaption>Install both motors (just one shown) by holding it between two brackets</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1074.JPG' | prepend: site.baseurl }}" 
    alt='The back wheel' />
    <figcaption>The back wheel. Do not add the board bold, they will come after the installation of battery case </figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1075.JPG' | prepend: site.baseurl }}" 
    alt='The battery case' />
    <figcaption>The battery case, bolted in the raisers of the back wheel.</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1078.JPG' | prepend: site.baseurl }}" 
    alt='The servo for ultrasound mount' />
    <figcaption>The ultrasound mount servo top view</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1079.JPG' | prepend: site.baseurl }}" 
    alt='The servo for ultrasound mount' />
    <figcaption>The ultrasound mount servo bottom view</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1080.JPG' | prepend: site.baseurl }}" 
    alt='The ultrasound mount' />
    <figcaption>The ultrasound mount view from behind</figcaption>
</figure>


<figure>
    <img src="{{'/static/robot/IMG_1081.JPG' | prepend: site.baseurl }}" 
    alt='The ultrasound mount' />
    <figcaption>The ultrasound mount front view</figcaption>
</figure>


<figure>
    <img src="{{'/static/robot/IMG_1082.JPG' | prepend: site.baseurl }}" 
    alt='Motor control board' />
    <figcaption>Motors control board</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1091.JPG' | prepend: site.baseurl }}" 
    alt='Motor control board power output wiring bottom view' />
    <figcaption>Motors control board  power output bottom view</figcaption>
</figure>
Note: do not use the large round hole, but the narrow rectangular hole near the bottom wheel. The breadboard will need to be installed above it.

<figure>
    <img src="{{'/static/robot/IMG_1083.JPG' | prepend: site.baseurl }}" 
    alt='Motor control board power output wiring' />
    <figcaption>Motors control board  power output wiring</figcaption>
</figure>
Polarity of the wires is essential. Otherwise it will either not work or it will reverse the direction of the affected motor.

<figure>
    <img src="{{'/static/robot/IMG_1085.JPG' | prepend: site.baseurl }}" 
    alt='Power supply of the ultrasound sensor' />
    <figcaption>Power supply of the ultrasound sensor</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1088.JPG' | prepend: site.baseurl }}" 
    alt='Output power supply for the breadboard' />
    <figcaption>Output power supply for the breadboard</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1089.JPG' | prepend: site.baseurl }}" 
    alt='Breadboard power wires.' />
    <figcaption>Breadboard power wires.</figcaption>
</figure>
I've taped together all adjacent connectors, will have to worry less about one connector leaving its place.



<figure>
    <img src="{{'/static/robot/IMG_1090.JPG' | prepend: site.baseurl }}" 
    alt='Power wires layout' />
    <figcaption>Power wires layout.</figcaption>
</figure>


<figure>
    <img src="{{'/static/robot/IMG_1094.JPG' | prepend: site.baseurl }}" 
    alt='Connecting the motor control' />
    <figcaption>Connecting the motor control.</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1096.JPG' | prepend: site.baseurl }}" 
    alt='Connecting the motor control' />
    <figcaption>Connecting the motor control.</figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/IMG_1094.JPG' | prepend: site.baseurl }}" 
    alt='Connecting the motor control' />
    <figcaption>Connecting the motor control.</figcaption>
</figure>

Disregard my layout of colors. I've figured out I've connected a few of them wrong; you can debug upon deploying the software to the board. Only meed to make sure the power cables are right, I won't risk applying power to the wrong terminals or causing a shortcircuit of the battery.

<figure>
    <img src="{{'/static/robot/IMG_1098.JPG' | prepend: site.baseurl }}" 
    alt='Added an additional nut as an additional raiser for the Arduino board, so that the power cables are not compressed against it' />
    <figcaption>Added an additional nut as an additional raiser for the Arduino board, so that the power cables are not compressed against it</figcaption>
</figure>

Once you are done with wiring, the vehicle is ready for the software upload.

No batteries are required for testing. Make sure that the vehicle is immobilized, the wheels don't touch the table.

To start testing, connect the micro USB 2 cable (included) to the Arduino card and your PC.



