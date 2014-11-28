var express = require('express');
var router = express.Router();

var Domain = require('../models/domain');
var Resource = require('../models/resource');
var User = require('../models/user');

router.route('/')
    .get(function(req, res) {   // get all domains.
        Domain.find(null, null, {sort: {'name': 1}}, function(err, domains) {
            if(err) {
                res.send(err);
            }

            res.json(domains);
        });
    })
    .post(function(req, res) {   // create a new domain
        var domain = new Domain();
        domain.name = req.body.name;

        domain.save(function(err, domain) {
           if(err) {
               res.send(err);
           }
           res.json(domain);
        });
    });
router.route('/skip/:skip')
    .get(function(req, res) {   // get all domains with given conditions.
        Domain.find(null, null, { skip: req.params.skip}, function(err, domains) {
            if(err) {
                res.send(err);
            }

            res.json(domains);
        });
    });
router.route('/skip/:skip/limit/:limit')
    .get(function(req, res) {   // get all domains with given conditions.
        Domain.find(null, null, { skip: req.params.skip, limit: req.params.limit}, function(err, domains) {
            if(err) {
                res.send(err);
            }

            res.json(domains);
        });
    });
router.route('/:domain_id')
    .get(function(req, res) {     // get a specific domain by domain_id
        Domain.findById(req.params.domain_id, function(err, domain) {
            if(err) {
                res.send(err);
            }

            res.json(domain);
        });
    })
    .put(function(req, res) {    // update a domain
        Domain.findByIdAndUpdate(req.params.domain_id, {name: req.body.name}, function(err, domain) {
            if(err) {
                res.send(err);
            }

            res.json(domain);
        })
    })
    .delete(function(req, res) {  //delete a domain
        Domain.findByIdAndRemove(req.params.domain_id, function(err) {
            if(err) {
                res.send(err);
            }

            res.json({ message: 'Success'});
        });
    });


router.route('/name/:name')
    .get(function(req, res) {
        Domain.findOne({name: req.params.name}, function(err, domain) {
            if(err) {
                res.send(err);
            }
            res.json(domain);
        });
    });

router.route('/resources')   //get all resources with the given domain_id
    .post(function(req, res) {
        var domain = req.body.domain;
        var username = req.body.username;

        User.findOne({name: username}, function(err, user) {
            if (err) {
                res.send(err);
            }
            if(user !== null) {
                Resource.find({domain_id: domain, user_id: user._id}, null, {sort: {'name': 1}}, function(err, resource) {

                    if (err) {
                        res.send(err);
                    }

                    res.json(resource);
                });
            }
        });
    });

module.exports = router;