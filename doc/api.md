#HTTP APIs
Happy_mock_happy_work mainly focuses on server configuration. There are only two kinds of API right now: Request and Response.

##URI
If request uri is your major focus, Happy_mock_happy_work could be like this:

    {
      "request": {
        "uri": "/fulfillment/v1/window"
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }
What's more, Happy_mock_happy_work supports approximate matching and matches a set of uris. eg.
     
     {
       "request": {
         "uri": "/fulfillment/v1/window/**/listing/?eventid=123&pricemin=100"
       },
       "response": {
         "body": {
           "text": "stubhub"
         }
       }
     }
visit "..../fulfillment/v1/window/123/listing/?eventid=123&pricemin=100", "..../fulfillment/v1/window/abc/listing/?eventid=123&pricemin=100", ........ . Happy_mock_happy_work
will return the same response.

##Body
If you want to response according to request content, Happy_mock_happy_work can be configured as following:

    {
      "request": {
        "uri": "/fulfillment/v1/window",
        "body": 
        {
          "text":"content"
        }
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }
Also for XML:

    {
      "request": {
        "uri": "/fulfillment/v1/window",
        "body": 
        {
          "xml":"<request><parameters><id>1</id></parameters></request>"
        }
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }
and JSON :

    {
      "request": {
        "uri": "/fulfillment/v1/window",
        "body": 
        {
          "json":{
            "foo":"bar",
            "name":{
                "pd":123,
                "rd":"aaa"
            }
          }
        }
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }
    
##JSONPATH
For the JSON/HTML request, Happy_mock_happy_work allows us to match request with JSONPath.
   
    {
      "request": {
        "uri": "/fulfillment/v1/window",
        "json_paths": 
        {
          "$.book[*].price": "1"
        }
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }

##XPATH
For the XML/HTML request, Happy_mock_happy_work allows us to match request with XPath.

    {
      "request": {
        "uri": "/fulfillment/v1/window",
        "xpaths" : 
        {
          "/request/parameters/id/text()" : "1"
        }
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }
    
##HTTP METHOD
It's easy to response based on specified HTTP method:

    {
      "request": {
        "uri": "/fulfillment/v1/window",
        "method": "post"
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }
Happy_mock_happy_work uses GET method by default, If you do need other method, feel free to specify method directly.

##HEADER
We will focus HTTP header at times:

    {
      "request": {
        "uri": "/fulfillment/v1/window",
        "headers" : 
        {
          "content-type" : "application/json"
        }
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }
    
##COOKIE
Cookie is widely used in web development.

    {
      "request": {
        "uri": "/fulfillment/v1/window",
        "cookies" :
        {
          "name" : "Jason"
        }
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }


#Response
##BODY
As you have seen in previous example, response with content is pretty easy.

    {
      "request": {
        "uri": "/fulfillment/v1/window"
      },
      "response": {
        "body": {
          "text": "stubhub"
        }
      }
    }

Also for XML:

    {
      "request": {
        "uri": "/fulfillment/v1/window"
      },
      "response": {
        "body": {
          "xml":"<request><parameters><id>1</id></parameters></request>"
        }
      }
    }
and JSON:

    {
      "request": {
        "uri": "/fulfillment/v1/window"
      },
      "response": {
        "body": {
          "json":{
              "foo":"bar",
              "name":{
                  "pd":123,
                  "rd":"aaa"
              }
          }
        }
      }
    }
    
##HEADER
We can also specify HTTP header in response.

    {
      "request": {
        "uri": "/fulfillment/v1/window"
      },
      "response": {
        "body": {
          "text": "stubhub"
        },
        "headers" :
        {
          "Authorization": "gWwh4zP4l90Cj4wQCslKHpB67_8a"
        }
      }
    }
    
##STATUS Code
Happy_mock_happy_work also supports HTTP status code response.

    {
      "request": {
        "uri": "/fulfillment/v1/window"
      },
      "response": {
        "body": {
          "text": "stubhub"
        },
        "status" : 200
      }
    }

##Time Delay
Happy_mock_happy_work also supports to delay in a configured duration in millisecond unit .

    {
      "request": {
        "uri": "/fulfillment/v1/window"
      },
      "response": {
        "body": {
          "text": "stubhub"
        },
        "timeDelay" : 200
      }
    }

##Dynamic Response
In case you want to fetch the request details including ,headers,cookies,uri,url,query parameters, json value,xml value ensure you pass the express as following examples:

    {
      "request": {
            "method": "post",
            "uri": "/aaa/bbb/ccc/**/?foo=123&bar=345",
            "headers": {
                "h1": "hv1",
                "h2": "hv2"
            },
            "cookies": {
                "c1": "cv1",
                "c2": "cv2"
            },
            "body":{
                   "text": "12345"
            }
      },
      "response": {
        "status": 200,
        "headers": {
              "Authorization": "testauth",
              "Content-Type":"application/json"
            },
            "body":{ "json":{
                                          "message":"all mock request details contained in  #url#",
                                          "method":"#method#",
                                          "url":"#url#",
                                          "uri1":"#uri[1]#",
                                          "headers":"#header['h2']#",
                                          "cookies":"#cookie['c1']#",
                                          "jsonPath":"#json['$.store.book[0].author']#",
                                          "queryparams":"#query['q2']#"
                               }}
      }
    }

another example

    {
      "request": {
            "method": "post",
            "uri": "/aaa/bbb/ccc/**/?foo=123&bar=345",
            "headers": {
                "h1": "hv1",
                "h2": "hv2"
            },
            "cookies": {
                "c1": "cv1",
                "c2": "cv2"
            },
            "body":{
                     "text": "12345"
                   }
      },
      "response": {
        "status": 200,
        "headers": {
              "Authorization": "testauth",
              "Content-Type":"application/json"
            },
            "body":{ "xml":"<happymock><request><message>this is a mock test</message><method>#method#</method><url>#url#</url><uri1>#uri[1]#</uri1><header>#header['h2']#</header><cookie>#cookie['c1']#</cookie><xpath>#xml['//bookstore/book[price<40]/title']#</xpath><queryparams>#query['q2']#</queryparams></request></happymock>"}
      }
    }


##Performance report
###Tomcat 7###

Criteria:100 T/s
90% line 3
Throughput 97.5/s

Criteria:250 T/s
90% line 595
Throughput 98.1


Criteria:2000 T/s
90% line 10375
Throughput 33.2/sec

###Netty 5###

Criteria:100 T/s
90% line 21
Throughput 480.1/s

Criteria:250 T/s
90% line 767
Throughput 455.8/s

Criteria:2000 T/s
90% line 2703
Throughput 374.1/s