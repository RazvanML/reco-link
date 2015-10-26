---
layout: post
title: A Basic Record Linkage Model
tags: record-linkage introduction model
categories: Record Linkage
---

This post provides an introduction in the preparation of the record linkage model. Given the example of the previous post, the easiest approach is to import data in a spreadsheet software (Microsoft Excel or Libre Office will do just fine) and to actually start record linkage.

Let's prepare the file in the CSV format like:

``` 
ID,Name,Address,Company,Products
1,Joe Smith,"1234 56 St. New York, NY",Newyorkonics,"SuperProd,MegaProd"
2,Alice Thompson,"4789 Woodward Ave. Detroit, MI",Detroitics,"MegaProduct,TeraProd"
3,James Jones,"4789 Woodward Ave. Detroit, MI",Detroitics,GigaProd
4,Ramkrishna (Ram) Kumar,"New Orchard Road, Armonk, New York 10504 ",IBM,"GigaProduct, PetaProduct"
5,Jackob Jones," One Microsoft Way Redmond, WA 98052-7329",Microsoft,Peta Product
6,Mary Barry,"Renaissance Center, Detroit",GM,Tera product
7,James (Jim) Stephenson,"One Ford Way, Dearborn, MI 48126",Ford,Petaprod
8,Alicia Shepard,"1234 56 St. New York, NY",Newyorkonics,???
9,Michael Taylor,"Classified, Bethesda, MD",Lockheed Martin,TeraProd
10,Jim Jones,"1234 Woodward Ave. Detroit, MI","Moonlighting, Inc",MegaProd
``` 

and:

``` 
ID,Name,Address,Company,Products
1,Joel Smith," New York, NY",New York-onics,"MegaProduct,SuperProd"
2,Alicia Thomson,4789 Woodward Avenue Detroit,Detroitics,"MegaProd,TeraProd"
3,James Jones,"1234 Woodward Ave. Detroit , MI",Moonlighting,Mega-Prod
4,Ram Kumar,"New Orchard Road, Armonk, NY",International Business Machines, PetaProduct
5,Jack Jones, One Microsoft Way Redmond,Microsoft USA,PetaProd
6,Mary Kerry,"12345 Michigan Ave, Chicago IL",Chicagonics,MegaProd
7,Mary Barry,"GM Renaissance Center, Detroit, MI 48243",General Motors,"TeraProd,SuperProd"
8,James Stephenson,"One Ford Way, Dearborn, MI",Ford,Petaprod
9,Al Shepard," New York, NY",Newyorkonics,"SuperProd,MegaProd"
10,Mike Taylor,Unknown,Lockheed-M,Tera Product
11,James Jones,"4789 Woodward Ave. Detroit , MI",Detroitics,"GigaProd,MegaProd"
``` 

We will prepare the model of the record linkage, which is an XML file describing the relations between the entities. The model contains the connections to the databases, the entities to be linked (left to right) and the rules of linking. The basic structure of the file is shown below:


``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<matchdocument>
	<dbconnections local="local">
          .......
	</dbconnections>
	<entities>
		<left>
                    .......
 		</left>
		<right>
                    .......
		</right>
	</entities>
	<matches>
             ....
	</matches>
</matchdocument>

``` 