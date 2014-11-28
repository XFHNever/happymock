Happy Mock Happy Work
=====================
inspried by Moco framework

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
https://github.com/dreamhead/moco/blob/master/moco-doc/apis.md
for detailed API reference.


mock response by cookie ,headers and uri
```
{
  "request" :
    {
      "uri" : "/cookie",
      "cookies" :
      {
          "login" : "true"
      },
      "headers": 
      {
      "Content-Type": "application/json",
      "Authorization": "Bearer JYf0azPrf1RAvhUhpGZudVU9bBEa"
      }  
    },
  "response" :
    {
      "body":{"text" : "success"}
    }
}

```
mock response by uri only
```
{
  "request" :
    {
      "uri" : "/cookie"
    },
  "response" :
    {
      "body":{"text" : "success"}
    }
}

```
mock response by request entity body matching for json
```
{
  "request": 
    {
      "uri": "/json",
      "body":{
        "json": {
          "foo":"bar"
        }
      }  
    },
  "response": 
    {
      "body":{"text" : "success"}
    }
}


```
mock response by request entity body matching for xml
```
{
  "request": 
    {
      "uri": "/xml",
      "body": 
        {
          "xml": "<request><parameters><id>1</id></parameters></request>"
        }
    },
  "response": 
    {
      "body": 
        {
          "xml": "<request><parameters><id>1</id></parameters></request>"
       }
    }
}

```
mock response by request body xpath matching
```
{
  "request" :
    {
      "method" : "post",
      "xpaths" : 
        {
          "/request/parameters/id[text()='1']" : "1"
        }
    },
  "response" :
    {
     "body":{"json" :{"success":"true"}}
    }
}

```
mock response by request body jsonpath matching
```
{
  "request": 
    {
      "uri": "/jsonpath",
      "json_paths": 
        {
         "$.ticketSeat","2",
         "$.ticketSeat[?(@.type=='R')]","2"
        }
    },
  "response": 
    {
      "status": "400",
      "headers": {
      "Authorization": "gWwh4zP4l90Cj4wQCslKHpB67_8a"
      },
       "body":{ "xml":"<request><parameters><id>1</id></parameters></request>"}
    }
}


```
More please visit <a  href= "https://github.corp.ebay.com/jicui/happy_mock_happy_work/blob/master/moci-mrs/api.md">https://github.corp.ebay.com/jicui/happy_mock_happy_work/blob/master/moci-mrs/api.md</a>
