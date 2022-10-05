---
Layout: post
title: Robot cart
tags: Arduino toy wireless
categories: Arduino
---

After assembling <a href="https://www.aliexpress.com/item/3256802871526756.html">2WD Smart Robot Car Kit For ESP8266 ESP-12E D1 Wifi Board For Arduino Control by Mobile Ultrasonic Module </a> the car still doesn't work right. The phone remote control works, but there is just one speed, the motors go either max or zero. I've debugged and fixed the provided C++ code running on the microcontroller.

<!--more-->

The main mistake in the original code is that the PWM output has a range of 0 (0 voltage) to 255 (1 voltage). The assumption was that PWM goes from 0 to 1024 (that may be true for older releases of ESP8266) with 400 the starting value. 

A second mistake was fixing the turns, by pressing the buttons in the corners of the remote control. In this case the digitalWrite was used with the intention of analogWrite.

Below the code:

```C++
#define IN_1  14           // L9110S B-2A motors Right       GPIO14(D5)
#define IN_2  4            // L9110S B-1A motors Right       GPIO4(D4)
#define IN_3  13           // L9110S A-1B motors Left        GPIO13(D7)
#define IN_4  12           // L9110S A-1A motors Left        GPIO12(D6)


#include <ESP8266WiFi.h>
#include <WiFiClient.h> 
#include <ESP8266WebServer.h>

String command; 
int speedCar = 255;        
float speed_Coeff = 1.5;

const char* ssid = "TESLA"; //name
ESP8266WebServer server(80);

void setup() {
 
 pinMode(IN_1, OUTPUT);
 pinMode(IN_2, OUTPUT);
 pinMode(IN_3, OUTPUT);
 pinMode(IN_4, OUTPUT); 
 
 Serial.begin(115200);
 delay(200); 
// Connecting WiFi

  WiFi.mode(WIFI_AP);
  WiFi.softAP(ssid);

  IPAddress myIP = WiFi.softAPIP();
  Serial.println("\n");
  Serial.print("AP IP address: ");
  Serial.println(myIP);
 
 // Starting WEB-server 
     server.on ( "/", HTTP_handleRoot );
     server.onNotFound ( HTTP_handleRoot );
     server.begin();    
}

void goAhead(){ 

      analogWrite(IN_1, LOW);
      analogWrite(IN_2, speedCar);
      analogWrite(IN_3, LOW);
      analogWrite(IN_4, speedCar);
  }

void goBack(){ 

      analogWrite(IN_1, speedCar);
      analogWrite(IN_2, LOW);
      analogWrite(IN_3, speedCar);
      analogWrite(IN_4, LOW);
  }

void goRight(){ 

      analogWrite(IN_1, speedCar);
      analogWrite(IN_2, LOW);
      analogWrite(IN_3, LOW);
      analogWrite(IN_4, speedCar);
  }

void goLeft(){

      analogWrite(IN_1, LOW);
      analogWrite(IN_2, speedCar);
      analogWrite(IN_3, speedCar);
      analogWrite(IN_4, LOW);
  }

void goAheadRight(){
      
      analogWrite(IN_1, LOW);
      analogWrite(IN_2, speedCar/speed_Coeff); 
      analogWrite(IN_3, LOW);
      analogWrite(IN_4, speedCar);
   }

void goAheadLeft(){
      
      analogWrite(IN_1, LOW);
      analogWrite(IN_2, speedCar);
      analogWrite(IN_3, LOW);
      analogWrite(IN_4, speedCar/speed_Coeff);
  }

void goBackRight(){ 

      analogWrite(IN_1, speedCar/speed_Coeff);
      analogWrite(IN_2, LOW);
      analogWrite(IN_3, speedCar);
      analogWrite(IN_4, LOW);
  }

void goBackLeft(){ 

      analogWrite(IN_1, speedCar);
      analogWrite(IN_2, LOW);

      analogWrite(IN_3, speedCar/speed_Coeff);
      analogWrite(IN_4, LOW);
  }

void stopRobot(){  

      analogWrite(IN_1, LOW);
      analogWrite(IN_2, LOW);
      analogWrite(IN_3, LOW);
      analogWrite(IN_4, LOW);
 }

void loop() {
    server.handleClient();
    
      command = server.arg("State");
      if (command == "F") goAhead();
      else if (command == "B") goBack();
      else if (command == "L") goLeft();
      else if (command == "R") goRight();
      else if (command == "I") goAheadRight();
      else if (command == "G") goAheadLeft();
      else if (command == "J") goBackRight();
      else if (command == "H") goBackLeft();
      else if (command == "0") speedCar = 0 * 255 / 9;
      else if (command == "1") speedCar = 1 * 255 / 9;
      else if (command == "2") speedCar = 2 * 255 / 9;
      else if (command == "3") speedCar = 3 * 255 / 9;
      else if (command == "4") speedCar = 4 * 255 / 9;
      else if (command == "5") speedCar = 5 * 255 / 9;
      else if (command == "6") speedCar = 6 * 255 / 9;
      else if (command == "7") speedCar = 7 * 255 / 9;
      else if (command == "8") speedCar = 8 * 255 / 9;
      else if (command == "9") speedCar = 9 * 255 / 9;
      else if (command == "S") stopRobot();
}

void HTTP_handleRoot(void) {

if( server.hasArg("State") ){
       Serial.println(server.arg("State"));
  }
  server.send ( 200, "text/html", "Welcome to my electric car." );
  delay(1);
}
```
