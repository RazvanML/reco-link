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

To prepare the system, uninstall brltty the Braille software interferes with ``/dev/USBtty``. If you need Braille support you likely know what to do to configure the software not to use all available ``USBtty`` devices, and I will link your note if you notify me about.

I've installed the <a href="https://downloads.arduino.cc/arduino-nightly-linux64.tar.xz">downloaded Arduino Studio</a>, current version was 1.8.20. The file has to be unzipped and run ``install.sh`` and then ``arduino``. 

First you must make sure the IDE is configured with the board. I've received a ESP8266 board which is not supported out of the box in Android Studio.

Set the following at Files / Preferences / Additional Boards Manager URLs:
``http://arduino.esp8266.com/stable/package_esp8266com_index.json``

Then go to Tools, Board manager, search for ESP8266 install version 3.x

Choose WeMos D1 R1 from the board list if you run the version 2.x of the library, or LOLIN (WeMOS) D1 R1 if you have  3.x version of the library.

```
cd ~/.arduino15/packages/esp8266/tools/python/3.7.2-post1
rm python 
ln -s  /usr/bin/python3 python
```

<figure>
    <img src="{{'/static/robot/ArduinoStudio.png' | prepend: site.baseurl }}" 
    alt='' />
    <figcaption></figcaption>
</figure>

<figure>
    <img src="{{'/static/robot/SelectBoard.png' | prepend: site.baseurl }}" 
    alt='' />
    <figcaption></figcaption>
</figure>
<figure>
    <img src="{{'/static/robot/USBSerialConsole.png' | prepend: site.baseurl }}" 
    alt='' />
    <figcaption></figcaption>
</figure>
<figure>
    <img src="{{'/static/robot/Preferences.png' | prepend: site.baseurl }}" 
    alt='' />
    <figcaption></figcaption>
</figure>
<figure>
    <img src="{{'/static/robot/successfulCompileAndUpdate.png' | prepend: site.baseurl }}" 
    alt='' />
    <figcaption></figcaption>
</figure>

Successful compile and deploy:
```
Writing at 0x0002c000... (85 %)
Writing at 0x00030000... (92 %)
Writing at 0x00034000... (100 %)
Wrote 295664 bytes (215420 compressed) at 0x00000000 in 4.9 seconds (effective 486.0 kbit/s)...
Hash of data verified.

Leaving...
Hard resetting via RTS pin...
```

Unsuccessful deploy, due to failure to connect:

```
Global variables use 28472 bytes (34%) of dynamic memory, leaving 53448 bytes for local variables. Maximum is 81920 bytes.
esptool.py v3.0
Serial port /dev/ttyUSB0
Traceback (most recent call last):
  File "/home/raz/.arduino15/packages/esp8266/hardware/esp8266/3.0.2/tools/pyserial/serial/serialposix.py", line 322, in open
    self.fd = os.open(self.portstr, os.O_RDWR | os.O_NOCTTY | os.O_NONBLOCK)
FileNotFoundError: [Errno 2] No such file or directory: '/dev/ttyUSB0'

During handling of the above exception, another exception occurred:

Traceback (most recent call last):
  File "/home/raz/.arduino15/packages/esp8266/hardware/esp8266/3.0.2/tools/upload.py", line 66, in <module>
    esptool.main(cmdline)
  File "/home/raz/.arduino15/packages/esp8266/hardware/esp8266/3.0.2/tools/esptool/esptool.py", line 3551, in main
    esp = chip_class(each_port, initial_baud, args.trace)
  File "/home/raz/.arduino15/packages/esp8266/hardware/esp8266/3.0.2/tools/esptool/esptool.py", line 271, in __init__
    self._port = serial.serial_for_url(port)
  File "/home/raz/.arduino15/packages/esp8266/hardware/esp8266/3.0.2/tools/pyserial/serial/__init__.py", line 90, in serial_for_url
    instance.open()
  File "/home/raz/.arduino15/packages/esp8266/hardware/esp8266/3.0.2/tools/pyserial/serial/serialposix.py", line 325, in open
    raise SerialException(msg.errno, "could not open port {}: {}".format(self._port, msg))
serial.serialutil.SerialException: [Errno 2] could not open port /dev/ttyUSB0: [Errno 2] No such file or directory: '/dev/ttyUSB0'
the selected serial port serial.serialutil.SerialException: [Errno 2] could not open port /dev/ttyUSB0: [Errno 2] No such file or directory: '/dev/ttyUSB0'
 does not exist or your board is not connected
Board at /dev/ttyUSB0 is not available
```

Unsuccessful due to compile errors:
```
/home/raz/arduino/4-Arduino Code/1.goAhead/1.goAhead.ino: In function 'void goAhead()':
1.goAhead:74:2: error: 'unknownsymbol' was not declared in this scope
   74 |  unknownsymbol();
      |  ^~~~~~~~~~~~~
exit status 1
'unknownsymbol' was not declared in this scope
```


