var express = require('express');
var router = express.Router();
var async = require('async');
var http = require('http');

var MockItem = require('../models/mockItem');
var Resource = require('../models/resource');
var ResourceItem = require('../models/resourceItem');
var User = require('../models/user');
var config = require('../config.js').config;

router.route('/')
    .get(function(req, res) {    //get all mockItems
        MockItem.find(function(err, resources) {
            if(err) {
                res.send(err);
            }

            res.json(resources);
        });
    })
    .post(function(req, res) {   //crate a new item.
        var mockItem = new MockItem();
        mockItem.name = req.body.name;
        mockItem.content = req.body.content;
        mockItem.description = req.body.description;

        var domainName = req.body.domain;
        var resourceName = req.body.resource;


        Resource.findOne({name: resourceName, domain_id: domainName}, function(err, resource) {
            mockItem.resource_id = resource._id;

            mockItem.save(function(err) {
                if(err) {
                    res.send(err);
                }
                res.json(mockItem);
            });
        });
    });

router.route('/:mockItem_id')
    .get(function(req, res) {     //get all items with given resource_id
        MockItem.findById(req.params.mockItem_id, function(err, mockItem) {
            if(err) {
                res.send(err);
            }

            res.json(mockItem);
        });
    })
    .put(function(req, res) {  //update an item
        var name = req.body.name;
        if(name != null) {
            MockItem.findByIdAndUpdate(req.params.mockItem_id, {name: req.body.name,description: req.body.description, active: req.body.active, content: req.body.content}, function(err, mockItem) {
                if(err) {
                    res.send(err);
                }
                res.json(mockItem);
            })
        } else {
            var content = req.body.content;

            if(content == null) {
                console.log('active with id');

                MockItem.findByIdAndUpdate(req.params.mockItem_id, {active: req.body.active}, function(err, mockItem) {
                    if(err) {
                        res.send(err);
                    }
                    res.json(mockItem);
                })
            } else {
                console.log('active');

                MockItem.findByIdAndUpdate(req.params.mockItem_id, {active: req.body.active, content: req.body.content}, function(err, mockItem) {
                    if(err) {
                        res.send(err);
                    }
                    res.json(mockItem);
                })
            }

        }
    })
    .delete(function(req, res) {  //delete an item
        MockItem.findByIdAndRemove(req.params.mockItem_id, function(err) {
            if(err) {
                res.send(err);
            }

            res.json({ message: 'Successfully deleted' });
        });
    });

router.route('/domain/:domain_id')
    .post(function(req, res) {     //get all ResourceItems with given domain_id
        var username = req.body.username
        User.findOne({name: username}, function(err, user) {
            console.log(username + user);

            if(err) {
                res.send(err);
            }
            if (user == null) {
                res.send('input your username in url!');
            } else {
                Resource.find({domain_id: req.params.domain_id, user_id: user._id}, function(err, resources) {
                    if(err) {
                        res.send(err);
                    }

                    var resourceItems = [];


                    async.each(resources, function(resource, callback) {
                        MockItem.find({resource_id: resource._id}, {'name': 1, 'active':1, 'description': 1}, function(err, mockItems) {
                            if(err) {
                                res.send(err);
                            }

                            var item = new ResourceItem(resource, mockItems);
                            resourceItems.push(item);

                            callback();
                        });
                    }, function(err) {
                        if(err) {
                            res.send(err);
                        }
                        res.json(resourceItems);
                    });

                });

            }
        });


    });

router.route('/domain/:domain_id/search/:search_key')
    .get(function(req, res) {       //get all items with given domain_id and satisfy the search request.
        Resource.find({domain_id: req.params.domain_id}, function(err, resources) {
            if(err) {
                res.send(err);
            }

            var resourceItems = [];


            async.each(resources, function(resource, callback) {
                var name = req.param("search_key");
                var reg = new RegExp(name, 'i');
                MockItem.find({resource_id: resource._id, name: reg}, {'name': 1, 'active':1, 'description': 1}, function(err, mockItems) {
                    if(err) {
                        res.send(err);
                    }

                    if(mockItems.length > 0) {
                        var item = new ResourceItem(resource, mockItems);
                        resourceItems.push(item);
                    }

                    callback();
                });
            }, function(err) {
                if(err) {
                    res.send(err);
                }
                res.json(resourceItems);
            });

        });

    });

router.route('/verify')
    .post(function(req, res) {
        var content = req.body.content;
        var httpAgent = new http.Agent();
        httpAgent.maxSockets = 50;

        var options = {
            method: "POST",
            host: config.compilation.host,
            port: config.compilation.port,
            path: '/compile',
            headers: {
                'Content-Type': 'application/json'
            },
            agent:httpAgent
        };

        var reqHttp = http.request(options, function(resHttp) {
            console.log('STATUS: ' + resHttp.statusCode);
            resHttp.setEncoding('utf8');
            resHttp.on('data', function (chunk) {
                res.json(chunk);
            });
        });

        reqHttp.on('error', function(e) {
            console.log('problem with request: ' + e.message);
        });


        reqHttp.write(content);
        reqHttp.end();

    });



module.exports = router;