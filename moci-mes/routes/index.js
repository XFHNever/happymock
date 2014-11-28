/**
 * Created by fuxie on 2014/10/10.
 *
 */

var express = require('express');
var router = express.Router();
var async = require('async');

var User = require('../models/user');
var Domain = require('../models/domain');
var Resource = require('../models/resource');
var MockItem = require('../models/mockItem');
var ResourceItem = require('../models/resourceItem');

/* GET home page. */
router.get('/', function(req, res) {
    res.render('index', { title: 'Happy mock happy work' });
});

router.get('/:username', function(req, res) {
    User.findOne({name: req.params.username}, function(err, user) {
        if(err) {
            res.send(err);
        }

        if(user == null) {
       //     res.send('User with name: ' + req.params.username + 'is not existing!');
            var defaultDomain = 'Fulfillment';
            var user = new User();
            user.name = req.params.username;

            Domain.findOne({name: defaultDomain}, function(err, domain) {
                if(err) {
                    res.send(err);
                }
                user.domain_id = domain._id;

                user.save(function(err, user) {
                    if(err) {
                        res.send(err);
                    }
                });
            });




            Domain.find(null, null, {sort: {'name': 1}}, function(err, domains) {
                if(err) {
                    res.send(err);
                }
                res.render('index', { title: 'Happy mock happy work',domain:'Account Management', domains:domains});
            });


        } else {
            async.parallel([
                function(callback){
                    Domain.findById(user.domain_id, function(err, domain) {
                        if(err) {
                            res.send(err);
                        }

                        callback(null, domain);
                    });
                },
                function(callback) {
                    Domain.find(null, null, {sort: {'name': 1}}, function(err, domains) {
                        if(err) {
                            res.send(err);
                        }
                        callback(null, domains);
                    });
                },
                function(rootCallback) {
                    Resource.find({domain_id: user.domain_id, user_id: user._id}, function(err, resources) {
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

                            rootCallback(null, resourceItems);
                        });

                    });
                }
            ],function(err, results) {
                if(err) {
                    res.send(err);
                }
               // res.send(results[2]);
                res.render('index', { title: 'Happy mock happy work',domain:results[0], domains:results[1], resourceItems:results[2]});
            })
        }
    });

});

module.exports = router;