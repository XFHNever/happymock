var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var MockItemSchema = new Schema({
    name: String,
    resource_id: String,
    content: String,
    description: String,
    active: {type: Boolean, default: false}
});

module.exports = mongoose.model('MockItem', MockItemSchema);