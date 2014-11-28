var express = require('express');
var router = express.Router();

var Resource = require('../models/resource');
var User = require('../models/user');

router.route('/')
    .get(function(req, res) {    //get all resources
        Resource.find(function(err, resources) {
            if(err) {
                res.send(err);
            }

            res.json(resources);
        });
    })
    .post(function(req, res) {   //create a new resource
        var username = req.body.username;
        User.findOne({name: username}, function(err, user) {
            console.log(username + user);

            if(err) {
                res.send(err);
            }
            if (user == null) {
                res.send('input your username in url!');
            } else {
                var resource = new Resource();
                resource.name = req.body.name;
                resource.domain_id = req.body.domain_id;
                resource.user_id = user._id;

                resource.save(function(err) {
                    if(err) {
                        res.send(err);
                    }
                    res.json(resource);
                })
            }
        });


    });

router.route('/:resource_id')
    .get(function(req, res) {    //get a resource by given resource_id
        Resource.findById(req.params.resource_id, function(err, resource) {
            if(err) {
                res.send(err);
            }

            res.json(resource);
        });
    })
    .put(function(req, res) {   //update resource
        Resource.findByIdAndUpdate(req.params.resource_id, {name: req.body.name}, function(err, resource) {
            if(err) {
                res.send(err);
            }

            res.json(resource);
        })
    })
    .delete(function(req, res) {   //delete resource
        Resource.findByIdAndRemove(req.params.resource_id, function(err) {
            if(err) {
                res.send(err);
            }

            res.json({ message: 'Successfully deleted' });
        });
    });


module.exports = router;