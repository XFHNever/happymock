var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var DomainSchema = new Schema({
    name: String
});

module.exports = mongoose.model('Domain', DomainSchema);