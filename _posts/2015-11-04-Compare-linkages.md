---
layout: post
title: Evaluate record linkages
tags: record-linkage compare GUI
categories: Record-Linkage
---

Today's topic is how to evaluate the correctness of a linkage. Even with the few options described in the previous posts (such as field equality and Jaro-Winkler dissimilarity), a number of models were created, each with its own specific accuracy. Without a specialized interface, the
choice of the parameters ```alpha```, ```beta```, the true/false threshold and the decision between boolean and confidence based adjustment of the test are tough calls. 
<!--more-->
Record linkage rarely operates with a golden standard, meaning that the "true" linkage it is not available, therefore
a linkage cannot be scored against a predetermined known solution.

The first step is to inspect the confidence level yielded by the battery of matching rules. This number is a normalization of the probability that the two records match.

<figure>
    <img src="{{'/static/img/recolink/confidence1.png' | prepend: site.baseurl | prepend: site.url }}" 
    alt='Record linkage report with confidence level' />
    <figcaption>Record linkage report with confidence level</figcaption>
</figure>

The fact that the errors become more numerous as the confidence level decreases is a good aspect, it means the scoring 
system used to perform the linkage is statistically sound.

Another GUI useful for the evaluation scenario is the linkage comparator.
The user can save the state of linkage for different settings and compare any two of them.

To compare two linkages, the user must point to the entity to compare, choose two of the previously saved linkages and 
decide which of the left and right entities to be matched is the reference. In the example below are saved the linkages described in the previous post, as well as the <a href="/reco-link/2015-10-28/Basic-Model/">field equality</a> example.

<figure>
    <img src="{{'/static/img/recolink/compare1.png' | prepend: site.baseurl | prepend: site.url }}" 
    alt='Record linkage comparison parameters' />
    <figcaption>Record linkage comparison parameters</figcaption>
</figure>


The outcome of the comparison is a four list report:
* The unchanged matches, with respect of the two entities. Confidence level may have been changed.
* Matches only belonging to the first linkage.
* Matches encountered in the second linkage only.
* Changed matches.

All but the first uses a reference entity (either the left or the right) into consideration. For example if the reference is set to the left entity, then the second list is composed of the left entity records only matched in the first chosen linkage.

The previous post has shown a 100% correct linkage by using two methods, first was using the name column only while the
second employed all the available data. Although the two methods yielded the same result, the confidence levels were
different:

<figure>
    <img src="{{'/static/img/recolink/compare2.png' | prepend: site.baseurl | prepend: site.url }}" 
    alt='Comparison of name only and all-data linkages' />
    <figcaption>Comparison of name only and all-data linkages</figcaption>
</figure>


A more meaningful report can be inspected on comparing the two applications of Jaro-Winkler dissimilarity, with 
or without confidence adjustment based on the observed dissimilarity score:

<figure>
    <img src="{{'/static/img/recolink/compare3.png' | prepend: site.baseurl | prepend: site.url }}" 
    alt='Comparison of two linkage methods (part 1)' />
    <figcaption>Comparison of two linkage methods (part 1)</figcaption>
</figure>

<figure>
    <img src="{{'/static/img/recolink/compare4.png' | prepend: site.baseurl | prepend: site.url }}" 
    alt='Comparison of two linkage methods (part 2)' />
    <figcaption>Comparison of two linkage methods (part 2)</figcaption>
</figure>

A basic reporting method of record linkage result analysis was described in this post.
The reports help to decide if a change in the linkage model leads to better results or not.
