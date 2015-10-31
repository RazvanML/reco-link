---
layout: post
title: Using Jaro-Winkler to enhance the previous model
tags: record-linkage Jaro-Winkler multiple-criteria
categories: Record Linkage
---

The <a href="/reco-link/2015-10-28/Basic-Model/">previous post</a> relied exclusively on the exact match of name. Out of the ten names, 
only two of them enjoyed the perfect equality. It is obvious that measuring the "degree of similarity" between the names will yield some better results. One such measure is the "<a href="https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">Jaro-Winkler"</a> <a href="#b1">[1]</a> metric. This measure returns a value between zero and one, with one for the equal strings and zero for the totally different strings. The measure factors in the composing letters of the string, their sequentiality as well as their position inside the string.

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

<h2> Bibliography </h2>

<a name="b1"/>[1] Winkler, W. E. (1990). "String Comparator Metrics and Enhanced Decision Rules in the Fellegi-Sunter Model of Record Linkage". Proceedings of the Section on Survey Research Methods (American Statistical Association): 354–359.

