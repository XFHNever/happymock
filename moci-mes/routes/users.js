/**
 * Created by fuxie on 2014/10/9.
 */
var express = require('express');
var router = express.Router();

var User = require('../models/user');

router.route('/')
    .get(function(req, res) {   // get all users.
        User.find(null, null, {sort: {'name': 1}}, function(err, users) {
            if(err) {
                res.send(err);
            }

            res.json(users);
        });
    })
    .post(function(req, res) {   // create a new user
        var user = new User();
        user.name = req.body.name;
        user.domain_id = req.body.domain_id;

        user.save(function(err, user) {
            if(err) {
                res.send(err);
            }
            res.json(user);
        });
    });

router.route('/:name')
    .get(function(req, res) {     // get a specific user by name
        User.findOne({name: req.params.name}, function(err, domain) {
            if(err) {
                res.send(err);
            }

            res.json(domain);
        });
    });

router.route('/:user_id')
    .get(function(req, res) {     // get a specific user by user_id
        User.findById(req.params.user_id, function(err, user) {
            if(err) {
                res.send(err);
            }

            res.json(user);
        });
    })
    .put(function(req, res) {    // update a user
        User.findByIdAndUpdate(req.params.user_id, {name: req.body.name}, function(err, user) {
            if(err) {
                res.send(err);
            }

            res.json(user);
        })
    })
    .delete(function(req, res) {  //delete a user
        User.findByIdAndRemove(req.params.user_id, function(err) {
            if(err) {
                res.send(err);
            }

            res.json({ message: 'Success'});
        });
    });


module.exports = router;