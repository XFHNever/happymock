happy mock release&backlog
===========================
todo:
- redefine the happy mock DSL ,support more expression and leverage freemarker
- docker integration
- support regex in request



done
---
- (mes)UI adjust done
- (mes)JS refactor  done
- (mes)System test  done
- (mrs)add child keyword on response including 1 code,2.head,4.xml,5.json done
- (mrs)improve request.text.json to support json object ,instead of json string ,it is painful for user to add a converting code "\" done
- (mrs)implement a HappyMockServlet to handle all the http request  done
- (mrs)cut to happymock 1.0 done
- (mrs)switch to DSL 2.0
- Save and Active improvement done
- On/Off Line switch done
- Help link done
- User space URL done
- support ognl in response done
- support dynamic binding in response,for example #listing.eventId#
- add timedelay in response done
- remove tomcat and using netty done
- remove domain and resource ,add dao to support findActiveByUser
- support -f and -m in cli
- support happy mock cli framework


happy mock backlog
============================
- Large data in the editor,json editor
- login(ldap)
- private data and public data?
- UX refinement
- UI tooling to add BO
- remove maven repository and using gradle
- performance testing
- add socket mock if needed
- bug fix
- xml comparision needs to be optimized
- xml does not support delimeter characters,that is pain for customer
- support template(freemarker)
- json path does not work on java8 ,may be using jackson as internal provider?
- add cli tasks(start,stop,http,https,socket)
- add cli for start ,stop mrs












