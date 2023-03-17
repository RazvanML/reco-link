---
Layout: post
title: Notes on Telemetry
tags: OpenTelemetry, OTELCollector Prometheus Grafana Jaegger ElasticSearch
categories: Open Telemetry
---

Notes after learning some stuff on OpenTelemetry.

New: some of the code has been generated with ChatGPT!

<!--more-->

It seems that Telemetry refers to separate classes of data:

1. Metrics: they are instant numbers, for example a temperature, the level of charge in a battery, the free memory or the used CPU.
2. Traces: traces are segments, something like "The backup ran between 20:00 and 20:05".
3. Logs: standard log files, dumped to local files but uploaded to a centralized location
4. Events - to learn more about them.

Started by creating a simple Java project to generate metrics and traces. Adding the following dependencies:
```XML
    <!-- Project dependencies -->
    <dependencies>
        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-sdk</artifactId>
            <version>1.14.0</version>
        </dependency>
        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-exporter-jaeger</artifactId>
            <version>1.14.0</version>
        </dependency>

        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-api-metrics</artifactId>
            <version>1.9.0-alpha</version>
        </dependency>
        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-sdk-metrics</artifactId>
            <version>1.14.0</version>
        </dependency>
        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-exporter-otlp-http-metrics</artifactId>
            <version>1.14.0</version>
        </dependency>
    </dependencies>
```
The option here is to export metrics to gRPC/Jaeger and Traces using OTLP.



```Java
package com.example;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.exporter.otlp.http.metrics.OtlpHttpMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;



import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Example {


    Tracer tracer;
    LongCounter counter;
    double sineValue = 0;
    Resource resource = Resource.builder().put("service.name","theTestService").build();


    private Example() {
        buildTracer();
        buildMeter();

        new SinGenerator().start();
    }


    private void buildMeter() {

        OtlpHttpMetricExporter metricExporter = OtlpHttpMetricExporter.builder()
                .setEndpoint("http://localhost:4318/v1/metrics")
//                .setAggregationTemporalitySelector(AggregationTemporalitySelector.deltaPreferred())
                .build();

        SdkMeterProvider meterProvider = SdkMeterProvider.builder()
                .setResource(resource) //.setResource(dtMetadata)
                .registerMetricReader(PeriodicMetricReader.builder(metricExporter).setInterval(500, TimeUnit.MILLISECONDS).build()) //The metricExporter setup is explained in Step 2.
                .build();

        OpenTelemetry openTelemetry = OpenTelemetrySdk
                .builder()
                .setMeterProvider(meterProvider)
                .buildAndRegisterGlobal();

        Meter meter = openTelemetry
                .meterBuilder("my-meter")
                .setInstrumentationVersion("1.0.0")
                .build();

        counter = meter
                .counterBuilder("my-counter")
                .setDescription("This is my counter.")
                .build();
         meter.gaugeBuilder("sinewave")
                .setDescription("Should look like a sine wave")
                .setUnit("meter").buildWithCallback(x-> x.record(sineValue));
         
    }

    private void buildTracer () {
        // Create a Jaeger exporter with the ManagedChannel
        JaegerGrpcSpanExporter jaegerExporter = JaegerGrpcSpanExporter.builder()
                .setEndpoint("http://localhost:14250")
                .setTimeout(160, TimeUnit.SECONDS) // makes no difference - maybe an error timeout?
                .build();

        // Create a TracerProvider with a batch span processor and a probabilistic sampler
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(jaegerExporter).build())
                .setSampler(Sampler.traceIdRatioBased(0.5))
                .setResource(resource)
                .build();

        // Set the global TracerProvider
        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .build();
        // OpenTelemetry.setGlobalOpenTelemetry(openTelemetrySdk);

        // Get the Tracer
        tracer = openTelemetrySdk.getTracer("example");
    }



    void loop() throws InterruptedException {
        while (true) {
            // Start a new Span
            counter.add(1);
            counter.add(new Random().nextInt(5));
            Span span = tracer.spanBuilder("example-span").startSpan();
            try (Scope scope = span.makeCurrent()) {
                // Do some work
                Thread.sleep(1000);
                // Add an attribute to the Span
                span.setAttribute("key", "value");
            } finally {
                // End the Span
                span.end();
            }
            counter.add(new Random().nextInt(3));
            Thread.sleep(1000);
        }
    }


    class SinGenerator extends Thread {
        @Override
        public void run() {
            Random rnd = new Random();
            while (true) {
                Span span = tracer.spanBuilder("sinewave").startSpan();
                Span span2 = tracer.spanBuilder("coswave").startSpan();

                try {
                    Thread.sleep((long) (20 + rnd.nextDouble()*50));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sineValue = Math.sin(new Date().getTime()*Math.PI*2 / 1000 / 7); // every 7 seconds a revolution
                double sineValue2 = Math.cos(new Date().getTime()*Math.PI*2 / 1000 / 15) * 5; // every 15 seconds a revolution
                span.setAttribute("value", sineValue);
                if (rnd.nextInt(70) == 42) {
                    span.setStatus(StatusCode.ERROR);
                    span.addEvent("Error: some error");
                }
                    else
                    span.setStatus(StatusCode.OK);
                span.end();
                span2.setAttribute("value", sineValue2);
                span2.setStatus(StatusCode.OK);
                span2.end();

//                System.out.println("Sin value: " + sineValue);
            }
        }
    }

    public static void main(String[] args) throws Exception{

        Example ex = new Example();

        ex.loop();

    }
}

```

This code does a lot. It initializes both metrics and traces exporters.
It then runs a loop to generate spans and increment counters and runs another thread to produce spans with a value in the form of a sinewave.


The Java code connects to a collector. Even if the collector is not available the rest of the code will execute properly while outputing an error.

I've used otel collector to read the telemetry. Exemplifying everything in a docker-compose file.

```yaml
  otel-collector:
    image: otel/opentelemetry-collector-contrib:latest
    volumes:
      - ./otel-collector-config.yaml:/etc/otel/collector-config.yaml:ro
    ports:
      - "4318:4318" # Otlp      
      - "14250:14250"
    restart: always
    command: ["--config", "/etc/otel/collector-config.yaml" ]   
```

