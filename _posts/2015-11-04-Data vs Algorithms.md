---
layout: post
title: Data versus Algorithms
tags: record-linkage data-mining business-intelligence
categories: Record-Linkage
---

 <img src="{{'/static/img/recolink/logo.svg' | prepend: site.baseurl | prepend: site.url }}" 
    alt='Record linkage logo' style="float:left;width:200px;padding:15px;margin:10px"   /> 
I'm writing this blog post  to launch a question. Given a fixed amount of resources, what will provide more impact:
improvements of the data mining algorithms, or acquisition of more data?
My past experience tells that improvements in algorithms
 will yield a marginal (at most 5-10%) improvement of the accuracy or performance.
 If we are not talking about large leverages or high costs of false positives/negatives, 
these improvements are not impactful in their environment.

Also, the "overengineering" of the artificial intelligence methods will yield to overfitting, a phenomenon where an
excellent accuracy is observed, but the system has a low capability of generalization, which will cause
lower performance in the future, as the input data changes. This situation can be easily spotted, by testing the 
newly enhanced algorithm with fresh data, unknown until now to the system.

Bringing in more data is not a free ride either. The new data has to "link" to the existing data. An independent data set
is useless, as it cannot be "integrated" in the existing echosystem. If the new data also duplicates some of the 
existing records, the de-duplication of the unified data set may eliminate valid records, while avoidance of the
de-duplication will yield statistically incorrect results. Generally because of the reasons 
enumerated above,nowadays most of the people complain of having too much data, and not too less!

For example, we can build today a neural network to identify welfare fraud based on a variety of factors and data points collected
by the welfare office. However, by linking with databases like "car registrations" or "traffic offenses" the welfare
office may build a clearer picture on possible fraudsters and their whereabouts. The new data can be fed into existing
algorithms, which can produce significantly better results with no or less changes.

Record linkage is able to cover the issue of "too much data that cannot be used". Done without any helping tool, record
linkage is a time consuming and error prone process. Starting from scratch, one will need about 2-6 months to understand
the nature of data and have a "home-backed" record linkage system, which is far from being perfect. My attempt is to provide
the backbone of a record linkage system, that will assist the users in data and rule exploration, will keep the 
linkage logic in a very succinct, easy to maintain manner while delivering a very good performance. 
Also it will allow incremental record linkage, eliminating the need to perform the linkage from scratch each time data changes.

I think I have answered the question, considering additional data as more viable direction; I would like to hear other
opinions, experiences or ideas.

