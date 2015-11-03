---
layout: post
title: Introduction in Record Linkage
tags: record-linkage introduction
categories: Record-Linkage
---

Welcome to my record linkage blog!

Record Linkage is a sum of techniques to associate records of two or multiple databases without sharing common keys.

ACME Corporation stores customer data provided by the sales and support departments. Since there is/was no global concept of "Customer ID", both departments use their own internal IDs, while collecting data like Customer Name, Customer Address, Customer Company and the list of registered products. Since the data of sales is disconnected of support data, the decision support system cannot identify the classes of customers that require the less support, even if these costumers are preferred to high maintenance customers.

During the following posts I will present a few basic techniques of record linkage, as well as the features of a software I develop.

How long would it take to a human being to associate the 11 records of the two fictional tables below? Can you think of associating the records in less than a minute?

If instead 11 records there will be 11 thousand, the time to link the records will increase even more than 1000 times! Without a proper strategy, the matching time will increase by one million. This means 1 million minutes, almost two years.


<h3>Sales data</h3>


<table  border="1">
<tbody>
<tr>
<td ><strong>Name</strong></td>
<td><strong>Address</strong></td>
<td ><strong>Company</strong></td>
<td ><strong>Products</strong></td>
</tr>
<tr>
<td>Joel Smith</td>
<td>&nbsp;New York, NY</td>
<td>New York-onics</td>
<td>MegaProduct,SuperProd</td>
</tr>
<tr>
<td>Alicia Thomson</td>
<td>4789 Woodward Avenue Detroit</td>
<td>Detroitics</td>
<td>MegaProd,TeraProd</td>
</tr>
<tr>
<td>James Jones</td>
<td>4789 Woodward Ave. Detroit, MI</td>
<td>Detroitics</td>
<td>GigaProd,MegaProd</td>
</tr>
<tr>
<td>Ram Kumar</td>
<td width="290">New Orchard Road, Armonk, NY</td>
<td>International Business Machines</td>
<td>&nbsp;PetaProduct</td>
</tr>
<tr>
<td>Jack Jones</td>
<td>&nbsp;One Microsoft Way Redmond</td>
<td>Microsoft USA</td>
<td>PetaProd</td>
</tr>
<tr>
<td>Mary Kerry</td>
<td>12345 Michigan Ave, Chicago IL</td>
<td>Chicagonics</td>
<td>MegaProd</td>
</tr>
<tr>
<td>Mary Barry</td>
<td>GM Renaissance Center, Detroit, MI 48243</td>
<td>General Motors</td>
<td>TeraProd,SuperProd</td>
</tr>
<tr>
<td>James Stephenson</td>
<td>One Ford Way, Dearborn, MI</td>
<td>Ford</td>
<td>Petaprod</td>
</tr>
<tr>
<td>Al Shepard</td>
<td>&nbsp;New York, NY</td>
<td>Newyorkonics</td>
<td>SuperProd,MegaProd</td>
</tr>
<tr>
<td>Mike Taylor</td>
<td>Unknown</td>
<td>Lockheed-M</td>
<td>Tera Product</td>
</tr>
<tr>
<td>James Jones</td>
<td>1234 Woodward Ave. Detroit, MI</td>
<td>Moonlighting</td>
<td>Mega-Prod</td>
</tr>
</tbody>
</table>

<h3>Support data</h3>

<table  border="1">
<tbody>
<tr>
<td width="166"><strong>Name</strong></td>
<td width="295"><strong>Address</strong></td>
<td width="110"><strong>Company</strong></td>
<td width="159"><strong>Products</strong></td>
</tr>
<tr>
<td>Jackob Jones</td>
<td>&nbsp;One Microsoft Way Redmond, WA 98052-7329</td>
<td>Microsoft</td>
<td>Peta Product</td>
</tr>
<tr>
<td>James (Jim) Stephenson</td>
<td>One Ford Way, Dearborn, MI 48126</td>
<td>Ford</td>
<td>Petaprod</td>
</tr>
<tr>
<td>Alicia Shepard</td>
<td>1234 56 St. New York, NY</td>
<td>Newyorkonics</td>
<td>???</td>
</tr>
<tr>
<td>Jim Jones</td>
<td>1234 Woodward Ave. Detroit, MI</td>
<td>Moonlighting, Inc</td>
<td>MegaProd</td>
</tr>
<tr>
<td>Joe Smith</td>
<td>1234 56 St. New York, NY</td>
<td>Newyorkonics</td>
<td>SuperProd,MegaProd</td>
</tr>
<tr>
<td>Mary Barry</td>
<td>Renaissance Center, Detroit</td>
<td>GM</td>
<td>Tera product</td>
</tr>
<tr>
<td>Ramkrishna (Ram) Kumar</td>
<td width="295">New Orchard Road, Armonk, New York 10504</td>
<td>IBM</td>
<td>GigaProduct, PetaProduct</td>
</tr>
<tr>
<td>James Jones</td>
<td>4789 Woodward Ave. Detroit, MI</td>
<td>Detroitics</td>
<td>GigaProd</td>
</tr>
<tr>
<td>Michael Taylor</td>
<td>Classified, Bethesda, MD</td>
<td>Lockheed Martin</td>
<td>TeraProd</td>
</tr>
<tr>
<td>Alice Thompson</td>
<td>4789 Woodward Ave. Detroit, MI</td>
<td>Detroitics</td>
<td>MegaProduct,TeraProd</td>
</tr>
</tbody>
</table>

Modern record matching techniques are able to link even hundreds of millions of records in reasonable time.

During this post I have only described the problem of record linkage, without attempting to provide any solution. The next posts will attempt different algorithms and reporting techniques to associate the provided sales and support data.