---
layout: post
title: Using Jaro-Winkler to enhance the previous model
tags: record-linkage Jaro-Winkler multiple-criteria
categories: Record Linkage
---

The <a href="/reco-link/2015-10-28/Basic-Model/">previous post</a> relied exclusively on the exact match of name. Out of the ten names, 
only two of them enjoyed the perfect equality. It is obvious that measuring the "degree of similarity" between the names will yield some better results. One such measure is the "<a href="https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">Jaro-Winkler"</a> <a href="#b1">[1]</a> metric. This measure returns a value between zero and one, with one for the equal strings and zero for the totally different strings. The measure factors in the composing letters of the string, their sequentiality as well as their position inside the string.


## Bibligraphy

<a name="b1">[1] Winkler, W. E. (1990). "String Comparator Metrics and Enhanced Decision Rules in the Fellegi-Sunter Model of Record Linkage". Proceedings of the Section on Survey Research Methods (American Statistical Association): 354–359.

