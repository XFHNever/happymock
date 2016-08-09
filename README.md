Background
----

Modern web applicaiton are all now based on restful architecture.
Restful increase the system scability on one hand ,but it does also introduce the dependency overheads for different components integration on the other hand.

How to minimize the impact bring by restful architecture in all phrase of the developpment work ?
The answer is calling the mock service.
We can delivery a standalone simulation server

1.for back-end developper for setting up integration test environment.

2.for QA for starting up automation tests ahead of code deployment.

3.for PE for testing the component itself's performance ,by minimize the external dependency.

4.for front-end engineer for speeding up their productivity ,without backend gets ready at first.

The mock server should provide a good look and feel interface for different roles to

a. easily create ,maintain his mock profile.

b. easily share ,reuse or extend from other groups mock profile.

c. respone in a configurable timely manner,even in a high peak duration without fail.

Introduction
----
Happy mock happy work comes from the idea of moco framwork,while moco is a mock rule engine and it provides in-jvm and standalone invoke mechanism.For better provide a more UX friendly tooling for all roles,the mocking system should seperate with two parts:

*Moci evaluation server:config DSL and publish to MRS 

*Moci runtime server:reading DSL from MES and respond to the external mock request.


Moci-DLS
---
moci DSL leverage the same pattern moco framework does,please refer to 
https://github.com/jicui/happymock/blob/master/doc/api.md for detailed API reference.
