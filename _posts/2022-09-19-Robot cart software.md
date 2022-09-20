---
layout: post
title: Robot cart software
tags: Arduino toy wireless "Arduino Studio" Linux
categories: Arduino
---

IN PROGRESS

Few days ago I've bought <a href="https://www.aliexpress.com/item/3256802871526756.html">2WD Smart Robot Car Kit For ESP8266 ESP-12E D1 Wifi Board For Arduino Control by Mobile Ultrasonic Module </a>.

Here is the step by step instruction to install the Development environment.
<!--more-->

I'm using Linux for this task. There is no particular reason to use Linux or Windows, only that my test laptop is an old Dell Latitude E6430 on which I've installed the latest Ubuntu LTS.

To prepare the system, uninstall brltty the Braille software interferes with /dev/USBtty. If you need Braille support you likely know what to do to configure the software not to use all available USBtty devices, and I will link your note if you notify me about.



I've installed the <a href="https://downloads.arduino.cc/arduino-nightly-linux64.tar.xz">downloaded Arduino Studio</a>, current version was 1.8.20


First you must make sure the IDE is configured with the board. I've received a ESP8266 board which is not supported out of the box in Android Studio.

http://arduino.esp8266.com/stable/package_esp8266com_index.json

Tools, board manager, search for ESP8266 install version 3.x

Choose WeMos D1 R1 from the board list if you run the version 2.x of the library, or LOLIN (WeMOS) D1 R1 if you have  3.x version of the library.

```
cd ~/.arduino15/packages/esp8266/tools/python/3.7.2-post1
rm python 
ln -s  /usr/bin/python3 python

```


ArduinoStudio.png
SelectBoard.png
USBSerialConsole.png
Preferences.png
/successfulCompileAndUpdate.png
