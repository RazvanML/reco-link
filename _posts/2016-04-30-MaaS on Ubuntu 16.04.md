---
layout: post
title: MaaS on Ubuntu Server 16.04 
tags: linux MAAS Juju cloud
categories: could
---

Today I installed a cluster of 5 nodes with MAAS and Ubuntu Server 16.04 "Xenial Xerus". 

<!--more-->

The installation went smooth and easy for the MaaS controller and the four nodes of my cluster. Ubuntu delivers now MAAS 2 beta, which personally I consider a risky choice
for a server enviroment. MAAS 2 is not much different of the former; the only notable difference in setup was the activation of the DHCP/PXE for the internal cluster network.
In the previous version it was part of "Settings", now goes under "Fabric".

Still missing is the WOL (Wake on Lan) Power Type. My understanding here is that MAAS has an issue with the ablility to shut-down nodes 
(since no permanent agent is deployed on the nodes) rather than starting the nodes with the rack manager.

I'm still working on Juju 2 (another default of "Xenial Xerus"). Again, this is a beta, but here most of the concepts have been re-fatored. The poor choice of
configuration files in YAML denies the user the understanding of configuration requirements. Practically, the user is constrained to "copy,paste and adapt" configurations from
the Internet.

Currently I'm fighting with this error:
``` 
razvan@cluster1:~$ juju bootstrap 10.149 my-maas
ERROR loading credentials: credentials.maas.rosaura.maas-oauth: expected string, got nothing
``` 

Who is  ```rosaura```? Where is this name coming from?


