var express = require('express');
var path = require('path');
var favicon = require('static-favicon');
var logger = require('morgan');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var cookieParser = require('cookie-parser');

var config = require('./config.js').config;
mongoose.connect('mongodb://' + config.domain + '/' + config.database);

var routes = require('./routes/index');
var mockItems = require('./routes/mockItems');
var domains = require('./routes/domains');
var resources = require('./routes/resources');
var users = require('./routes/users');


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(favicon());
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded());
app.use(express.static(path.join(__dirname, 'www')));
app.use(cookieParser());




app.use('/', routes);
//config the router
app.use('/mock/items', mockItems);
app.use('/mock/domains', domains);
app.use('/mock/resources', resources);
app.use('/mock/users', users);




module.exports = app;
