keyword
    req,request,resp,response,url,headers,cookies,or,body
data type
    $j{...} ,$x{...},"plain text",4.89
expression

req.url  ^[a-z]*
   .method "put"
   .headers $j{
              "h1": "hv1",
              "h2": "hv2"
             }
   .body #j{
                 "store": {
                        "book": [
                          {
                           "category": "reference",
                           "author": "Nigel Rees",
                           "title": "Sayings of the Century",
                           "price": 8.95
                          }
                          ]
                        }
           }
          ||
         #x{
                  <request>
                      <parameters><id>1</id></parameters>
                  </request>
         }
---
resp.status 201
    .headers ${
        "Authorization": "gWwh4zP4l90Cj4wQCslKHpB67_8a"
    }
    .body $x{


    }.
    deplay 100