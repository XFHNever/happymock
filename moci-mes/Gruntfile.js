'use strict';
var fs = require('fs');
//var appConfig = JSON.parse(fs.readFile('./grunt_config.json', 'utf-8'));

module.exports = function(grunt){
	// Grunt init config
	grunt.initConfig({
		//config: appConfig,
		connect:{
			dev: {
				options: {
					protocol: 'http',
					port: 3000,
					keepalive: true,
					base: 'www',
					middleware: function myMiddleWare(connect, options, middlewares){
						middlewares.unshift(function(req, res, next){
							if(req.url === '/hello'){
								res.writeHeader(200, {'Content-Type': 'text/html'});
								res.end('<h1>Hello Page, port is # '+options.port+'</h1>');
							}
							else{
								next();
							}
						});
						return middlewares;
					}
				}
			}
		}
	});
	
	// Load grunt plugins
	grunt.loadNpmTasks('grunt-contrib-connect');

};
