/**
 * Created by fuxie on 2014/10/9.
 */
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserSchema = new Schema({
    name: String,
    password: String,
    domain_id: String
});

module.exports = mongoose.model('User', UserSchema);