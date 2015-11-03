---
layout: post
title: Using Jaro-Winkler to enhance record linkage
tags: record-linkage Jaro-Winkler multiple-criteria
categories: Record-Linkage
---

The <a href="/reco-link/2015-10-28/Basic-Model/">previous post</a> has exclusively relied on the exact match of name.  Out of the ten names, 
only two of them enjoyed the perfect equality. Due to a coincidence, one of the two names was associated with a wrong record. It seems that perfect equality does not yield the necessary accuracy; on the other hand, one can speculate that measuring the "degree of similarity" between the entries will yield some better results. One such measure is the 
<a href="https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">"Jaro-Winkler"</a> distance from <a href="#b1">[1]</a>. This measure returns a value between zero and one, with one for the equal strings and zero for the totally different strings. The measure factors in the similarity of the letters of a string, their sequentiality as well as the position of similar characters inside the string.

To use this metric instead of plain string comparison, the entity match rule has to be changed accordingly:

```xml
<match name="matchperson" left="sales" right="support" lcard="ZEROONE"
	rcard="ZEROONE" >
	<rules>
		<rule name="byname" lfield="name" rfield="name" type="STR_JARO_WRINKLER"  >
			<score alpha="0.9" beta="0.9" threshold="0.85" />
		</rule>
	</rules>
</match>
```
The significant change is ```type="STR_JARO_WRINKLER"```. A scoring criterion has been added as well. The meaning of parameters is as following:
* ```alpha``` is the probability of the test to return true, in case of a matching entity
* ```beta``` is the probability of the test to return false, in case of a mismatching entity
* ```threshold``` is a cutoff value used to provide the "match" and "no match" verdict.

The value choices for the three parameters will be the subject of a future post. Let's assume for the moment that the values are just right.

With this change, the result is as following (mismatches were marked manually):

<figure>
    <img src="{{'/static/img/recolink/jw2.png' | prepend: site.baseurl | prepend: site.url }}" alt='Record linkage report with Jaro-Winkler distance' />
    <figcaption>Record linkage report with Jaro-Winkler distance</figcaption>
</figure>

With the new approach, the record linkage identified 10 linkages (this is the expected number), but only four of them are correct. Two causes contribute to the poor performance: first, the other data, like address, company and product are not used, and second, the quantitative part of the string similarity is discarded once passing the threshold.

By signaling the mismatches, the result improve with three more right linkages:

<figure>
    <img src="{{'/static/img/recolink/jw3.png' | prepend: site.baseurl | prepend: site.url }}" alt='Record linkage report with Jaro-Winkler distance - second run' />
    <figcaption>Record linkage report with Jaro-Winkler distance - second run</figcaption>
</figure>

Once the first step mismatches have been highlighted, second step only performs three mistakes. By continuing to validate the correct linkages and mismatches, the linkage will turn to 100 percent.


For small amounts of data it is possible the complete data curation. Unfortunately, on large sets of data no manual linkage is expected, probably except the records of elevated importance.

To improve the accuracy, it is possible to use multiple linkage rules, below is the new configuration file:

```xml
<match name="matchperson" left="sales" right="support" lcard="ZEROONE"
	rcard="ZEROONE">
	<rules>
		<rule name="byname" lfield="name" rfield="name" type="STR_JARO_WRINKLER">
			<score alpha="0.9" beta="0.9" thresholdtype="ONE" threshold="0.85" />
		</rule>
		<rule name="byaddress" lfield="address" rfield="address"
			type="STR_JARO_WRINKLER">
			<score alpha="0.9" beta="0.9" thresholdtype="ONE" threshold="0.85" />
		</rule>
		<rule name="bycompany" lfield="company" rfield="company"
			type="STR_JARO_WRINKLER">
			<score alpha="0.9" beta="0.9" thresholdtype="ONE" threshold="0.85" />
		</rule>
		<rule name="by_products" lfield="products" rfield="products"
			type="STR_JARO_WRINKLER">
			<score alpha="0.9" beta="0.9" thresholdtype="ONE" threshold="0.85" />
		</rule>
	</rules>
</match>
```

To have a term of comparison, the manually curated matched and mismatches were deleted. The new run finds eight out of the total ten possible linkages; after marking the correct matches and the mismatches, the second run identifies properly all linkages.

<figure>
    <img src="{{'/static/img/recolink/jw_multi.png' | prepend: site.baseurl | prepend: site.url }}" alt='Record linkage report with Jaro-Winkler distance - multiple linkage rules' />
    <figcaption>Record linkage report with Jaro-Winkler distance - multiple linkage rules</figcaption>
</figure>


To address the second issue of the rule, the Jaro-Winkler score can be used in establishing of the verdict confidence of each matching rule. In this case, the rules deliver both a true/false verdict, and a confidence amount. The low confidence verdicts are considered less important than the high confidence ones.

```xml
<match name="matchperson" left="sales" right="support" lcard="ZEROONE"
	rcard="ZEROONE">
	<rules>
		<rule name="byname" lfield="name" rfield="name" type="STR_JARO_WRINKLER">
			<score alpha="0.9" beta="0.9" thresholdtype="ONE" threshold="0.85" 
			confidenceMode="LINEAR" />
		</rule>
	</rules>
</match>
```

Running this rule alone yields 100 percent correct linkages!

Also, running the combination of the two techniques demonstrated produces a 100 percent correct linkage. The difference will be observed in a future post, when it will be discussed the confidence of each linkage.

```xml
<match name="matchperson" left="sales" right="support" lcard="ZEROONE"
	rcard="ZEROONE">
	<rules>
		<rule name="byname" lfield="name" rfield="name" type="STR_JARO_WRINKLER">
			<score alpha="0.9" beta="0.9" thresholdtype="ONE" threshold="0.85" 
			confidenceMode="LINEAR" />
		</rule>
		<rule name="byaddress" lfield="address" rfield="address"
			type="STR_JARO_WRINKLER">
			<score alpha="0.9" beta="0.9" thresholdtype="ONE" threshold="0.85" 
			confidenceMode="LINEAR"/>
		</rule>
		<rule name="bycompany" lfield="company" rfield="company"
			type="STR_JARO_WRINKLER">
			<score alpha="0.9" beta="0.9" thresholdtype="ONE" threshold="0.85" 
			confidenceMode="LINEAR"/>
		</rule>
		<rule name="by_products" lfield="products" rfield="products"
			type="STR_JARO_WRINKLER">
			<score alpha="0.9" beta="0.9" thresholdtype="ONE" threshold="0.85" 
			confidenceMode="LINEAR" />
		</rule>
	</rules>
</match>
```

In this post was introduced a new matching rule type, the Jaro-Winkler distance. Also, the employment of multiple criteria relies on an adapted Bayesian algorithm inspired from Fellegi and Sunter in <a href="#b2">[2]</a>. Confidence levels can be computed for each score and factored in the final probability of two record linkage.

While the perfect equality provides a natural way of identifiying candidate linkages, the Jaro-Winkler similarity does not provide such a vehicle. For this data set, all the possible candidates (```10 x 11 = 110```) were generated and computed. Larger data sets will require alternative methods of candidates generation, with the goal to avoid the quadratic complexity of the problem. 


<h2> Bibliography </h2>

<a name="b1"></a>[1] Winkler, W. E. (1990). "String Comparator Metrics and Enhanced Decision Rules in the Fellegi-Sunter Model of Record Linkage". Proceedings of the Section on Survey Research Methods (American Statistical Association): 354-359.

<a name="b1"></a> [2] Fellegi, Ivan; Sunter, Alan (December 1969). "A Theory for Record Linkage". Journal of the American Statistical Association 64 (328): pp. 1183ֱ210. doi:10.2307/2286061. JSTOR 2286061.
