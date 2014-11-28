## Moci MES

This is an appplication for moci-mes, mainly focus on the front-end implementation.

## Folder Structure Notice

```
moci-mes/
|---bin
|	|---www
|
|---models
|	|---domain.js
|	|---mockItem.js
| 	|---resource.js
|	|---resourceItem.js
|	|---user.js
|
|---routes
|	|---domains.js
|	|---index.js
|	|---mockItems.js
|	|---resources.js
|   |---users.js
|
|---source
|   |---mockup/
|	|---PSD/
|
|---views
|   |---footer.jade
|   |---header.jade
|   |---index.jade
|   |---layout.jade
|
|---www
|	|---js/
|	|---img/
|	|---css/
|	|---index.html
|	|---main.html
|
|---app.js
|---config.js
|---Gruntfile.js
|---README.md
|---package.json
```

- /bin/www specifics the port of this project which will start a HTTP web server listening on 3000 by default
- All the front-end mockup pictures are put into the **mockup/** folder under **source/** folder, and the associated PSD is under **PSD/** folder siblings to the **mockup/** folder
- **views** folder is the views of this project.
- **www** folder is the public static resource folder, all the JS, CSS and images are put under here.
- index.jade is the main page to create, add and delete mock service.
- index.html && main.html is deprecated for current version and you can modify the UI in views/*.jade. At the same time, you should visit our project with the uri: http://host:port/yourName...
- config.js includes DB configuration information.
- Package.json concludes the dependencies of this project.

## How to run it

Please issue the command `npm start`, then access the URL http://localhost:3000/yourName  on browser

## Technical System

- Jade + jQuery
- express.js (provide restful style interface)
- mongodb + mongoose
- grunt.js (build tool)


## TODO List

Below is the todo action items list:

- [] Smarter JsonEditor --- Large data,  Auto-complete
- [] User System
- [] UX Refinement
