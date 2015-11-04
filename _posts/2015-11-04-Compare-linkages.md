---
layout: post
title: Evaluate record linkages
tags: record-linkage compare GUI
categories: Record-Linkage
---

Before implementing more algorithms, I considered necessary to have an approximative mechanism to evaluate 
the correctness of a linkage.
The <a href="/reco-link/2015-10-31/JaroWinkler/">previous post</a> was introducing the Jaro-Wrinkler dissimilarity,
together with a set of new parameters and options associated to the linkage test. Without a specialized interface, the
choice of the parameters ```alpha```, ```beta``` and the decision between boolean and confidence based adjustment of 
the test are tough calls.

Record linkage rarely operates with a golden standard, meaning that the "true" linkage it is not available, therefore
a linkage cannot be scored against a predetermined known solution.

The first step is to inspect the confidence level.

<figure>
    <img src="{{'/static/img/recolink/confidence1.png' | prepend: site.baseurl | prepend: site.url }}" 
    alt='Record linkage report with confidence level' />
    <figcaption>Record linkage report with confidence level</figcaption>
</figure>

The fact that the errors become more numerous as the confidence level decreases is a good sign, it means the scoring 
system used to perform the linkage is statistically sound.

Another GUI useful for this scenario is the linkage comparator. The user can save the state of linkage for different
scenarios and compare two of the previously stored linkages.

To compare two linkage, the user must provide the entity to compare, choose two of the previously saved linkages and 
decide which of the compared sides is the pivot.

<figure>
    <img src="{{'/static/img/recolink/compare1.png' | prepend: site.baseurl | prepend: site.url }}" 
    alt='Record linkage comparison parameters' />
    <figcaption>Record linkage comparison parameters</figcaption>
</figure>


The outcome of the comparison is a four list report:
* The unchanged entries, as of entities. Confidence level may have been changed.
* Matches only belonging to the first linkage
* Matches encountered in the second linkage only
* Changed matches


