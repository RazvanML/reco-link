---
layout: post
title: Using Jaro-Winkler to enhance the previous model
tags: record-linkage Jaro-Winkler multiple-criteria
categories: Record Linkage
---

The <a href="/reco-link/2015-10-28/Basic-Model/">previous post</a> relied exclusively on the exact match of name. Out of the ten names, 
only two of them enjoyed the perfect equality. It is obvious that measuring the "degree of similarity" between the names will yield some better results. One such measure is the 
<a href="https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">"Jaro-Winkler"</a> metric from <a href="#b1">[1]</a>. This measure returns a value between zero and one, with one for the equal strings and zero for the totally different strings. The measure factors in the composing letters of the string, their sequentiality as well as their position inside the string.

To use this metric instead of plain string comparison, the entity match rule has to be changed accordingly:

```xml
		<match name="matchperson" left="sales" right="support" lcard="ZEROONE"
			rcard="ZEROONE" >
			<rules>
				<rule name="byname" lfield="name" rfield="name" type="STR_JARO_WRINKLER"  >
					<score alpha="0.9" beta="0.9" threshold="0.85" >
					</score>
				</rule>
			</rules>
		</match>
```
The significant change is ```type="STR_JARO_WRINKLER"```. A scoring criterion has been added as well. The meaning of parameters is as following:
* ```alpha``` is the probability of the test to return true, in case of a matching entity
* ```beta``` is the probability of the test to return false, in case of a mismatching entity
* ```threshold``` is a cutoff value used to provide the "match" and "no match" verdict.

The value choices for the three parameters will be the subject of a future post. Let's assume that the values are just right.

With this change, the result is as following (mismatches were marked manually):

<figure>
    <img src="{{'/static/img/recolink/jw2.png' | prepend: site.baseurl | prepend: site.url }}" alt='Record linkage report with Jaro-Winkler distance' />
    <figcaption>Record linkage report with Jaro-Winkler distance</figcaption>
</figure>

With this approach, the record linkage identified 10 linkages (this is the expected number), but out of them only five are correct. There are two causes contributing to this issue: first, the other data, like address, company and product are not used, and second, the quantitative part of the string similarity is discarded once passing the threshold.

By signaling the mismatches, the result turns in:
<figure>
    <img src="{{'/static/img/recolink/jw3.png' | prepend: site.baseurl | prepend: site.url }}" alt='Record linkage report with Jaro-Winkler distance - second step' />
    <figcaption>Record linkage report with Jaro-Winkler distance - second step</figcaption>
</figure>


<h2> Bibliography </h2>

<a name="b1"></a>[1] Winkler, W. E. (1990). "String Comparator Metrics and Enhanced Decision Rules in the Fellegi-Sunter Model of Record Linkage". Proceedings of the Section on Survey Research Methods (American Statistical Association): 354–359.

