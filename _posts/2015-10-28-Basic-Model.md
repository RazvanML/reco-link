---
layout: post
title: A Basic Record Linkage Model
tags: record-linkage introduction model
categories: Record Linkage
---

This post provides an introduction in the preparation of the record linkage model. Given the <a href="../../2015-10-25/Record-linkage-intro/">example of the previous post</a>, the easiest approach is to import data in a spreadsheet software (Microsoft Excel or Libre Office will do just fine) and to actually start record linkage.

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
The goal of the exercise is to establish a relation between the identifiers of the two sets, both provided on the first column.

We will prepare the model of the record linkage, which is an XML file describing the relations between the entities. The model contains the connections to the databases, the entities to be linked (left to right) and the rules of linking. The basic structure of the file is shown below:


``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<matchdocument>
	<dbconnections>
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

The file contains the model of the linkage. The first section describes the set of database connections to retrieve data from:

``` xml
	<dbconnections local="local">
		<drivers>
			<driver>org.postgresql.Driver</driver>
			<driver>org.relique.jdbc.csv.CsvDriver</driver>
		</drivers>
		<connections>
			<connection id="local" url="jdbc:postgresql://127.1:5432/match2"
				user="postgres" password="***" dialect="pgsql"/>
			<connection id="testdata" url="jdbc:relique:csv:data/test1" dialect="csvjdbc">
				<property name="raiseUnsupportedOperationException" value="false"></property>
			</connection>
		</connections>
	</dbconnections>
``` 

Each connection is described by its URL, user name and password. The data can be read from any connection. The local connection, provided by the attribute of the ```<dbconnections>``` is where the temporary data and linkage results are stored. Reading data from CSV files is being performed using the same approach as data being retrieved from a database.


