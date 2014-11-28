var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ResourceSchema = new Schema({
    name: String,
    domain_id: String,
    user_id: String
});

module.exports = mongoose.model('Resource', ResourceSchema);