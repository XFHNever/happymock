(function(window, $, undefined){
        //Global variable
        var global = {
            //current domain
            domain: {
                id: '',
                name: ''
            },
            //current resource
            resource: {
                id: '',
                name: ''
            },
            //current item
            item: {
                id: '',
                name: '',
                description: '',
                content: '',
                active: ''
            },
            //the state of input box, when the box is inputted, the state becomes true.
            edit: {
                name: false,
                description: false
            },
            create: {
                name: false,
                description: false,
                resource: false
            },
            user: {
                name: '',
                id: ''
            }
        };

        //parse url, and get query content with given name.
        function getQueryStringV(vhref, name) {
            if (vhref.indexOf("?") == -1 || vhref.indexOf(name + '=') == -1) {
                return '';
            }
            var queryString = vhref.substring(vhref.indexOf("?") + 1);
            var parameters = queryString.split("&");
            var pos, paraName, paraValue;
            for (var i = 0; i < parameters.length; i++) {
                pos = parameters[i].indexOf('=');
                if (pos == -1) {
                    continue;
                }
                paraName = parameters[i].substring(0, pos);
                paraValue = parameters[i].substring(pos + 1);

                if (paraName == name) {
                    return paraValue;
                }
            }
            return '';
        }

        function getUserName(vhref) {
            var parameters = vhref.split('/');
            if(parameters.length !== 4) {
                return '';
            }
            var username = parameters[3];

            return username;
        }

        // create the editor
        var container = $("#editor")[0];
        var options = {
            mode: 'code',
            change: function() {

            },
            error: function (err) {
                alert(err.toString());
            }
        };
        var editor = new JSONEditor(container, options);
        editor.set();

        //bind the change event listener.
        editor.options.change = function() {
            var $save = $('.actionBtn.save'),
                 $add = $('.actionBtn.add'),
                 $active = $('.actionBtn.active');
            if(getJSON() === '') {
                if(!$save.hasClass('disabled')) {
                    $save.addClass('disabled');
                }
                if(!$add.hasClass('disabled')) {
                    $add.addClass('disabled');
                }
                if(!$active.hasClass('disabled')) {
                    $active.addClass('disabled');
                }
            } else {
                if(global.domain.id !== '') {
                    if(global.item.id !== '' && !global.item.active) {
                        $save.removeClass('disabled');
                    }
                    $add.removeClass('disabled');
                    $active.removeClass('disabled');
                }
            }

        };

		//get json
		function getJSON() {
			return editor.getText();
		}

		var mockService = {
			attachEvents: function(){
                /** Begin of logo **/
                $('.header img').on('click', function() {
                    window.location.href = 'index.html';
                });

                $('.header span').on('click', function() {
                    window.location.href = 'index.html';
                });

                $('.menubar a[name=home]').on('click', function() {
                    window.location.href = 'index.html';
                });
                /** End of logo **/


				/** Begin of domainName selector **/
				$('.domainName>a').bind('click', function(){         //toggle domainList
					$('.domainName-selector').slideToggle(200);
				});

				$('.domainName-selector').on('click', '>li>a', function(){    //select a domain
					var $this = $(this);
					$('.domainName-selector').slideToggle(200, function(){
						mockService.clearData();

                        //get selected data
                        var selectedDomainName = $this.text();
                        var selectedDomainId = $this.attr('domain_id');

                        //update global variable
                        global.domain.name = selectedDomainName;
                        global.domain.id = selectedDomainId;

                        //update the page
                        var $domainName = $('.domainName>span');
                        $domainName.html(selectedDomainName);
                        $domainName.attr('domain_id', selectedDomainId);
                        mockService.updateDomainItemDesc();
                        //load new itemList with selected domain
                        mockService.getMockItemList(selectedDomainId);

                        //enable createResource and search
                        $('.createResourceBtn').removeClass('disabled');
                        var $search = $('.searchBox input');
                        $search.removeAttr('disabled');

                        $search.val('');
                        //disable other button except the add button if the editor is not empty.
                        mockService.disabledBtn();
                        if(getJSON() !== '') {
                            $('.actionBtn.add').removeClass('disabled');
                        }
					});
				});
				/** End of domainName selector **/


                /** Begin of searchBox **/
                $('.searchBox>a').on('click', function() {
                    var $this = $(this);
                    var input = $this.siblings('input').val();
                    if(input !== '') {
                        mockService.searchItem(global.domain.id, input);
                    } else {
                        mockService.getMockItemList(global.domain.id);
                    }
                });

                $('.searchBox input').on('keypress', function(e) {
                    if(e.which === 13) {    //press Enter
                        var $this = $(this);
                        var input = $this.val();
                        if(input !== '') {
                            mockService.searchItem(global.domain.id, input);
                        } else {
                            mockService.getMockItemList(global.domain.id);
                        }
                    } else if(e.which === 116) {  //press F5
                        //update global domain data
                        mockService.clearData();
                        var $domainName = $('.domainName>span');
                        global.domain.name = $domainName.text();
                        global.domain.id = $domainName.attr('domain_id');
                    }
                });
                /** End of searchBox **/


                /** Begin of createResourceBtn **/
                $('.createResourceBtn').on("click", function(){
                   // initial the createModel(clear data in the input box and disable related button)
                    var $createModel = $('#createModel');
                    var $input = $createModel.find('input');
                    $input.val('');
                    var $rightBtn = $createModel.find('.btn-right');
                    if(!$rightBtn.hasClass('disabled')) {
                        $rightBtn.addClass('disabled');
                    }

                    $createModel.modal({
                        backdrop: 'static'
                    });
                });

                $('#createModel')
                .on('click', '.btn-right', function() {    //create a new resource
                    var $createModel = $(this).parents('#createModel');
                    var $name = $createModel.find('input[name=name]');
                    var name = $name.val();

                    //validate if the domain you want to create is existing.
                    var isExist = false;
                    $('ul.mockResourceList').each(function() {
                        $(this).find('li .resourceName').each(function() {
                            if($(this).text() === name){
                                isExist = true;
                                return false;
                            }
                        });
                    });

                    if(isExist) {
                        //show error notification for 2s.
                        var $alert = $createModel.find('.alert');
                        $alert.text('Resource Name already exists!!');
                        $alert.removeClass('hidden').addClass('alert-danger');

                        setTimeout(function() {
                            $alert.addClass('hidden').removeClass('alert-danger');
                        }, 2000);
                        $(this).addClass('disabled');
                        $name.val('');
                    } else {  //create a new resource
                        if (global.domain.id === '') {
                            global.domain.id = $('.domainName>span').attr('domain_id');
                        }
                        mockService.createResource(global.domain.id, name);
                    }
                })
                .on('focus', '.form-control', function() {   //hide alert when focusing input box
                    var $createModel = $(this).parents('#createModel');
                    var $alert = $createModel.find('.alert');
                    $alert.addClass('hidden').removeClass('alert-danger');
                })
                .on('input', '.form-control', function() {     //adjust btn-right state when input in the inputBox
                    var $this = $(this);
                    var input = $this.val();
                    var $createModel = $this.parents('#createModel');
                    var $btn_right = $createModel.find('.btn-right');

                    if(input == '') {
                        $btn_right.addClass('disabled');
                    } else {
                        $btn_right.removeClass('disabled');
                    }
                });
                /** End of createResourceBtn **/

                /** Begin of mockItemList **/
                $('.mockResourceList')
               .on('click', '.resourceNameBox', function(){     //select a specific resource
                    var $this = $(this);
                    $this.next('.mockItemList').slideToggle(200, function(){
                        //update global data
                        global.resource.name = $this.text();
                        global.resource.id = $this.attr('resource_id');

                        //update page
                        mockService.updateDomainItemDesc();
                        var $icon = $this.children('.icon');
                        if($icon.hasClass('upIcon')){
                            $icon.removeClass('upIcon').addClass('downIcon');
                        }else if($icon.hasClass('downIcon')){
                            $icon.removeClass('downIcon').addClass('upIcon');
                        }
					});
                })
                .on("click", '.mockItemList li',  function(){     //select a specific item
                    var $this = $(this);
                    var $itemName = $this.children('.itemName');
                    var $state = $itemName.parent('li').find('.state');
                    var mockItem_id = $itemName.attr('item_id');

                    //update global data
                    global.item.id = mockItem_id;
                    global.item.name = $itemName.text();

                    //get data by ajax
                    mockService.getSpecificItem(mockItem_id);

                    //update DomainItemDesc
                    var $resource = $this.parents('li').find('.resourceName');
                    global.resource.name = $resource.text();
                    global.resource.id = $resource.attr('resource_id');
                    mockService.updateDomainItemDesc();

                    //update button state
                    var $activeBtn = $('.actionBtn.active');
                    $activeBtn.removeClass('disabled');

                    if($state.hasClass('active')) {
                        $activeBtn.text('Inactive');
                    } else {
                        $activeBtn.text('Active');
                    }
                    $('.actionBtn.add').removeClass('disabled');

                    //update selected state
                    mockService.itemUnSelectedAll();
                    mockService.itemSelected($this);
                })
                .on("click", '.itemIcon.edit',  function(){    //edit item
                    //update global data
                    var $itemName = $(this).siblings('.itemName');
                    var mockItem_id = $itemName.attr('item_id');
                    var $resource = $(this).parents('li').parents('li').find('.resourceName');

                    global.item.id = mockItem_id;
                    global.resource.name = $resource.text();
                    global.resource.id = $resource.attr('resource_id');

                    //initial editModel(clear data in the inputBox)
                    var $editModel = $('#editModel');
                    $editModel.find('input').val('');
                    $editModel.find('textarea').val('');
                    var $rightBtn = $editModel.find('.btn-right');
                    if(!$rightBtn.hasClass('disabled')) {
                        $rightBtn.addClass('disabled');
                    }


                    $editModel.modal({
                        backdrop: 'static'
                    });

                    //get data and update page
                    mockService.getSpecificItem(mockItem_id);
                    mockService.updateDomainItemDesc();
                })
                .on("click", '.itemIcon.delete',  function(){    //delete item
                    var $this = $(this);
                    var $item = $this.parent('li');
                    var $itemName = $this.siblings('.itemName');
                    var mockItem_id = $itemName.attr("item_id");
                    var $resource = $item.parents('li').find('.resourceName');

                    //update global data
                    global.resource.name = $resource.text();
                    global.resource.id = $resource.attr('resource_id');

                    mockService.getSpecificItem(mockItem_id);
                    $('#deleteModel').modal({
                        backdrop: 'static'
                    });
                })
                .on("click", '.state',  function(){    //active/inactive item
                    /*
                    var $this = $(this);
                    var itemId = $this.attr("item_id");
                    var $item = $this.parent('li');
                    var $resource = $item.parents('li').find('.resourceName');

                    //update global data
                    global.resource.name = $resource.text();
                    global.resource.id = $resource.attr('resource_id');


                    mockService.getSpecificItem(itemId);
*/
                    var $this = $(this);

                    var $item = $this.parent('li');
                    var $resource = $item.parents('li').find('.resourceName');
                    var itemId = $item.find('.itemName').attr('item_id');

                    //update global data
                    global.resource.name = $resource.text();
                    global.resource.id = $resource.attr('resource_id');


                    mockService.getSpecificItem(itemId);

                    if($this.hasClass('active')) {
                        mockService.inactiveItemWithId(itemId)
                    } else {
                        mockService.activeItemWithId(itemId);
                    }
                });
                /** End of mockItemList **/


				/** Begin of editModel **/
                $('#editModel').on('click', '.btn-right', function() {    //save change
                    //get data from inputBox
                    var $this = $(this);
                    var $editModel = $this.parents('#editModel');
                    var $name = $editModel.find('input[name=name]');
                    var $description = $editModel.find('textarea[name=description]');
                    var name = $name.val();
                    var description = $description.val();

                    var isExist = false;
                    var $resourceName = $('.mockResourceList').find('.resourceName[resource_id='+ global.resource.id +']');
                    var $itemList = $resourceName.parents('li').find('.mockItemList');
                    var selectedDes = $itemList.find('.itemName[item_id='+ global.item.id +']').parents('li').attr('title');

                    //validate if the itemName is existing.
                    $itemList.each(function() {
                        $(this).find('li .itemName').each(function() {
                            if($(this).text() === name && selectedDes === description){
                                isExist = true;
                                return false;
                            }
                        });
                    });

                    if(isExist) {    //show error notification for 2000ms.
                        var $alert = $editModel.find('.alert');
                        $alert.text('Item Name already exists Or edit nothing!!');
                        $alert.removeClass('hidden').addClass('alert-danger');

                        setTimeout(function() {
                            $alert.addClass('hidden').removeClass('alert-danger');
                        }, 2000);
                        $this.addClass('disabled');
                        $name.val('');
                    } else {
                        mockService.editItem(global.item.id, name, description, global.item.content);
                    }
				})
                .on('input', '.form-control', function() {
                    var $this = $(this);
                    var $editModel = $('#editModel');
                    var $btn_right = $editModel.find('.btn-right');
                    var input = $this.val();

                    //validate if user completes the inputBox
                    var attr = $this.attr('name');
                    if(attr === 'name') {
                        if(input === '') {
                            global.edit.name = false;
                        } else {
                            global.edit.name = true;
                        }
                    } else {
                        if(input === '') {
                            global.edit.description = false;
                        } else {
                            global.edit.description = true;
                        }
                    }

                    if(global.edit.name && global.edit.description) {
                        $btn_right.removeClass('disabled');
                    } else {
                        $btn_right.addClass('disabled');
                    }
                });
				/** End of editModel **/


                /** Begin of deleteModel **/
                $('#deleteModel').on('click', '.btn-right', function() {      //delete item
                    var $deleteModel = $('#deleteModel');
                    if(global.item.active) {    //can not be deleted when the item is active
                        var $alert = $deleteModel.find('.alert');
                        $alert.removeClass('hidden');

                        setTimeout(function() {
                            $alert.addClass('hidden');
                            $deleteModel.modal('hide');
                        }, 2000);
                        $(this).css('color', '#FFF');
                    } else {
                        $deleteModel.modal('hide');
                        var $selector = $('.mockItemList li .itemName[item_id='+ global.item.id + ']').parent('li');
                        mockService.deleteItem(global.item.id, $selector);
                    }
                });
                /** End of deleteModel **/


				/** Begin of actionBtn add**/
				$('.actionBtn.add').on('click', function() {     //add a new item to resource
					var $this = $(this);

                    mockService.verifyInput($this, function(){
                        //initial crateItemModel
                        var $createItemModel = $('#createItemModel');
                        $createItemModel.find('input').val('');
                        $createItemModel.find('textarea').val('');
                        global.create.resource = false;

                        if (global.resource.name !== '') {
                            $createItemModel.find('input[name=resource]').val(global.resource.name);
                            global.create.resource   = true;
                        }

                        var $rightBtn = $createItemModel.find('.btn-right');
                        if(!$rightBtn.hasClass('disabled')) {
                            $rightBtn.addClass('disabled');
                        }
                        $createItemModel.modal({
                            backdrop: 'static'
                        });

                        mockService.getResourceList(global.domain.id);
                    });
				});
                /** End of actionBtn add **/

                /** Begin of CreateItemModel **/
                $('#createItemModel')
                .on('click', '.btn-right', function() {       //create a new item
                    //get data from inputBox
                    var $createItemModel = $('#createItemModel');
                    var resource = $createItemModel.find('input[name=resource]').val();
                    var description = $createItemModel.find('textarea[name=description]').val();
                    var $name = $createItemModel.find('input[name=name]');
                    var name = $name.val();
                    var content = getJSON();

                    var isExist = false;
                    var $resourceName = $('.mockResourceList').find('.resourceName[resource_id='+ global.resource.id +']');
                    var $itemList = $resourceName.parents('li').find('.mockItemList');

                    //validate if the itemName is existing
                    $itemList.each(function() {
                        $(this).find('li .itemName').each(function() {
                            if($(this).text() === name){
                                isExist = true;
                                return false;
                            }
                        });
                    });

                    if(isExist) {
                        var $alert = $createItemModel.find('.alert');
                        $alert.text('Item Name already exists!');
                        $alert.removeClass('hidden').addClass('alert-danger');

                        setTimeout(function() {
                            $alert.addClass('hidden').removeClass('alert-danger');
                        }, 2000);

                        $name.val('');
                        $(this).addClass('disabled');

                    } else {
                        mockService.createNewItem(global.domain.id, name, content, resource, description);
                    }

				})
                .on('input', '.form-control', function() {     //handle user input
                    var $this = $(this);
                    var $createItemModel = $('#createItemModel');
                    var input = $this.val();

                    //validate if user completes the inputBox
                    var attr = $this.attr('name');
                    if(attr === 'name') {
                        if(input === '') {
                            global.create.name = false;
                        } else {
                            global.create.name = true;
                        }
                    } else if(attr === 'description') {
                        if(input === '') {
                            global.create.description = false;
                        } else {
                            global.create.description = true;
                        }
                    }

                    //update btn-right state
                    if(global.create.name && global.create.description && global.create.resource) {
                        $createItemModel.find('.btn-right').removeClass('disabled');
                    } else {
                        $createItemModel.find('.btn-right').addClass('disabled');
                    }
                });

                //toggle domainList
				$('.popup-dropdown>span').bind('click', function(){
					$('.resourceName-selector').slideToggle(200);
				});

                //select a resource
				$('.resourceName-selector').on('click', '>li>a', function(){
					var $this = $(this);
					$('.resourceName-selector').slideToggle(200, function(){
                        var $createItemModel = $('#createItemModel');
						var selectedResourceName = $this.text();

                        //update global data
                        global.resource.name = selectedResourceName;
                        global.resource.id = $this.attr('resource_id');
                        global.create.resource = true;

                        //update page
                        $createItemModel.find('input[name=resource]').val(selectedResourceName);
                        if(global.create.name && global.create.description && global.create.resource) {
                            $createItemModel.find('.btn-right').removeClass('disabled');
                        } else {
                            $createItemModel.find('.btn-right').addClass('disabled');
                        }
					});
				});
				/** End of CreateItemModel**/

                /** Begin of actionBtn save **/
                //save change
                $('.actionBtn.save').on('click', function() {
                    var $this = $(this);

                    mockService.verifyInput($this, function() {
                        mockService.saveItem(global.item.id, getJSON());
                    });
                });
                /** End of actionBtn save **/

                /** Begin of actionBtn active  **/
                //active/Inactive item
                $('.actionBtn.active').on('click', function() {
                    var $this = $(this),
                        command = $this.text();

                    if(command === 'Active' ) {

                        mockService.verifyInput($this, function() {
                            mockService.activeItem(global.item.id);
                        });

                    } else if(command === 'Inactive') {
                        mockService.inactiveItem(global.item.id);
                    }
                });
                /** Begin of actionBtn active  **/

				/** Begin of reset button **/
				$('.actionBtn.reset').on('click', function() {
					editor.set();
                    $('.actionBtn.add').addClass('disabled');
				});
				/** End of reset button **/

			},

            /**
             * operations about page updating.
             */
            updateDomainItemDesc: function updateDomainItemDesc() {
                var $domainItemDesc = $('.domainItemDesc');

                if(global.item.name === '') {
                    if(global.resource.name === '') {
                        $domainItemDesc.text(global.domain.name + ' / ');
                    } else {
                        $domainItemDesc.text(global.domain.name + ' / ' + global.resource.name + ' / ');
                    }
                } else {
                    $domainItemDesc.text(global.domain.name + ' / ' + global.resource.name + ' / ' + global.item.name);
                }
            },
            clearData: function clearData() {
                this.clearDomainData();
                this.clearResourceData();
                this.clearItemData();
            },
            clearDomainData: function clearDomainData() {
                global.domain.id = '';
                global.domain.name = '';
            },
            clearResourceData: function clearResourceData() {
                global.resource.id = '';
                global.resource.name = '';
            },
            clearItemData: function clearItemData() {
                global.item.id = '';
                global.item.active = false;
                global.item.content = '';
                global.item.description = '';
                global.item.name = '';
            },
            disabledBtn: function() {
                $('.actionBtn.add').addClass('disabled');
                $('.actionBtn.active').addClass('disabled');
                $('.actionBtn.save').addClass('disabled');
            },
            itemSelected: function itemSelected($selector) {
                var $itemName = $selector.children('.state');
                var $edit = $selector.children('.edit');
                var $delete = $selector.children('.delete');
                $selector.addClass('selected');
                $itemName.addClass('itemSelected');
                $edit.addClass('editSelected');
                $delete.addClass('deleteSelected');
            },
            itemUnSelectedAll: function itemUnSelectedAll() {
                var $mockItemList = $('.mockItemList');
                var $item = $mockItemList.find('li');
                var $itemName = $mockItemList.find('.state');
                var $edit = $mockItemList.find('.edit');
                var $delete = $mockItemList.find('.delete');
                $item.removeClass('selected');
                $itemName.removeClass('itemSelected');
                $edit.removeClass('editSelected');
                $delete.removeClass('deleteSelected');
            },

            /**
             * operations about ajax.
             */
            //get all domains and update page
            getDomainList: function getDomainList(){
                $.ajax({
                    url: '/mock/domains',
                    type: 'get',
                    dataType : "json",
                    success: function(data) {
                        var length = data.length;
                        var domainList = '';

                        if (length > 0) {
                            for (var i = 0; i < length; i++) {
                                domainList = domainList + '<li><a href="javascript:void(0);" domain_id="' + data[i]._id + '">' + data[i].name + '</a></li>';
                            }
                        }

                        var $selector =  $('.domainName-selector');
                        $selector.html(domainList);
                    }
                });
            },
            //get all items with given domainId and update page
            getMockItemList: function getMockItemList(domainId) {
                $.ajax({
                    url: '/mock/items/domain/' +  domainId,
                    type: 'post',
                    dataType : "json",
                    data: {username: global.user.name},
                    success: function(data) {
                        var innerHtml = '';
                        var length = data.length;

                        for(var i = 0; i < length; i++) {
                            innerHtml += '<li><div class="resourceNameBox"><span class="resourceName" resource_id="' + data[i].resource._id +  '">' +  data[i].resource.name +
                            '</span><span class="icon downIcon" href="javascript:;"></span></div>';

                            var items = data[i].items;
                            var itemLength = items.length;
                            innerHtml += '<ul class="mockItemList collapse">';

                            for(var j = 0; j< itemLength; j++) {
                                var state = 'inactive';
                                if(items[j].active) {
                                    state = 'active';
                                }
                                innerHtml = innerHtml + '<li title="' + items[j].description + '"><span class="state ' + state  + '"></span><span item_id="' + items[j]._id + '" class="itemName">' + items[j].name + '</span>'+
                                    '<span class="itemIcon edit"></span>' +
                                    '<span class="itemIcon delete"></span></li>';
                            }

                            innerHtml += '</ul>';



                            innerHtml += '</li>';
                        }

                        $('.mockResourceList').html(innerHtml);
                    }
                });
            },
            //get all resources with given domainId
            getResourceList: function getResourceList(domainId) {
                $.ajax({
                    url: '/mock/domains/resources',
                    type: 'post',
                    dataType : "json",
                    data: {username: global.user.name, domain: domainId},
                    success: function(data) {
                        var resources = '';
                        var length = data.length;
                        var $createItemModel = $('#createItemModel');
                        var $selector =  $createItemModel.find('.resourceName-selector');

                        if(length > 0) {
                            for(var i = 0; i < length; i++) {
                                resources =  resources + '<li><a href="javascript:void(0);" resource_id="' + data[i]._id  + '">' + data[i].name + '</a>';
                            }
                        }

                        $selector.html(resources);
                    }
                });
            },

            //create a new item
            createResource: function createResource(domainId, name) {
                $.ajax({
                    url: '/mock/resources',
                    type: 'post',
                    dataType : "json",
                    data: {name: name, domain_id: domainId, username: global.user.name},
                    success: function(data) {
                        if(data != null) {
                            var $createModel = $('#createModel');
                            var $alert = $createModel.find('.alert');


                            //update global data
                            global.resource.id = data._id;
                            global.resource.name = data.name;

                            //show success notification
                            $alert.text('Create new resource successfully!!');
                            $alert.removeClass('hidden').addClass('alert-success');
                            setTimeout(function() {
                                $alert.addClass('hidden').removeClass('alert-success');
                                $createModel.modal('hide');
                            }, 500);

                            //update page
                            var $target = $('.mockResourceList');
                            var resource = '<li><div class="resourceNameBox"><span class="resourceName" resource_id="' + data._id +  '">' +  data.name +
                                '</span><span class="icon downIcon" href="javascript:;"></span></div><ul class="mockItemList collapse"></ul>';

                            //update page according to the existing page.
                            if($target.find('p').text() === '') {  //normal page
                                $target.append(resource);
                            } else {                              //after searching and response with 0 item
                                $('.searchBox input').val('');
                                mockService.getMockItemList(global.domain.id);
                            }
                        }
                    }
                });
            },

            //delete item
            deleteItem: function deleteItem(mockItemId, $item) {
                $.ajax({
                    url: '/mock/items/' + mockItemId,
                    type: 'delete',
                    dataType : "json",
                    success: function(data) {
                        if(data.message != null) {
                           $item.detach();
                           mockService.clearItemData();
                           mockService.updateDomainItemDesc();
                           editor.set();
                           mockService.disabledBtn();
                        }
                    }
                });
            },

            getSpecificItem: function getSpecificItem(mockItemId) {
                $.ajax({
                    url: '/mock/items/' + mockItemId,
                    type: 'get',
                    dataType : "json",
                    success: function(data) {
                        global.item.active = data.active;
                        global.item.description = data.description;
                        global.item.name = data.name;
                        global.item.content = data.content;
                        editor.set(JSON.parse(data.content));

                        if(!global.item.active) {
                            $('.actionBtn.save').removeClass('disabled');
                        }
                    }
                });
            },
            activeItem: function activeItem(mockItemId) {
                $.ajax({
                    url: '/mock/items/' + mockItemId,
                    type: 'put',
                    dataType : "json",
                    data: {name: global.item.name, description: global.item.description, active: true, content: getJSON()},
                    success: function() {
                        global.item.active = true;

                        var $selector = $('.mockItemList li .itemName[item_id='+ mockItemId + ']');
                        var $state = $selector.parent('li').find('.state');
                        $state.removeClass('inactive').addClass('active');
                        $('.actionBtn.active').text('Inactive');

                        var $save = $('.actionBtn.save');
                        if(!$save.hasClass('disabled')) {
                            $save.addClass('disabled');
                        }
                    }
                });
            },
            inactiveItem: function inactiveItem(mockItemId) {
                $.ajax({
                    url: '/mock/items/' + mockItemId,
                    type: 'put',
                    dataType : "json",
                    data: {name: global.item.name, description: global.item.description, active: false, content: global.item.content},
                    success: function() {
                        global.item.active = false;

                        var $selector = $('.mockItemList li .itemName[item_id='+ mockItemId + ']');
                        var $state = $selector.parent('li').find('.state');
                        $state.removeClass('active').addClass('inactive');
                        $('.actionBtn.active').text('Active');

                        var $save = $('.actionBtn.save');
                        if($save.hasClass('disabled')) {
                            $save.removeClass('disabled');
                        }
                    }
                });
            },
            activeItemWithId: function activeItemWithId(mockItemId) {
                $.ajax({
                    url: '/mock/items/' + mockItemId,
                    type: 'put',
                    dataType : "json",
                    data: {active: true},
                    success: function() {
                        global.item.active = true;

                        var $selector = $('.mockItemList li .itemName[item_id='+ mockItemId + ']');
                        var $state = $selector.parent('li').find('.state');
                        $state.removeClass('inactive').addClass('active');
                        $('.actionBtn.active').text('Inactive');

                        var $save = $('.actionBtn.save');
                        if(!$save.hasClass('disabled')) {
                            $save.addClass('disabled');
                        }
                    }
                });
            },
            inactiveItemWithId: function inactiveItemWithId(mockItemId) {
                $.ajax({
                    url: '/mock/items/' + mockItemId,
                    type: 'put',
                    dataType : "json",
                    data: {active: false},
                    success: function() {
                        global.item.active = false;

                        var $selector = $('.mockItemList li .itemName[item_id='+ mockItemId + ']');
                        var $state = $selector.parent('li').find('.state');
                        $state.removeClass('active').addClass('inactive');
                        $('.actionBtn.active').text('Active');

                        var $save = $('.actionBtn.save');
                        if($save.hasClass('disabled')) {
                            $save.removeClass('disabled');
                        }
                    }
                });
            },

            saveItem: function saveItem(mockItemId, content) {
                $.ajax({
                    url: '/mock/items/' + mockItemId,
                    type: 'put',
                    dataType : "json",
                    data: {name: global.item.name, description: global.item.description, active: global.item.active, content: content},
                    success: function() {
                        global.item.content = content;

                        var $suc = $('.sucMsg');

                        $suc.removeClass('hidden');
                        $suc.slideDown();

                        setTimeout(function() {
                          $suc.slideUp();
                        }, 3000);

                    }
                });
            },

            editItem: function editItem(mockItemId, name, description, content) {
                $.ajax({
                    url: '/mock/items/' + mockItemId,
                    type: 'put',
                    dataType : "json",
                    data: {name: name, description: description, active: global.item.active,content: content},
                    success: function() {
                        //update global data
                        global.item.name = name;
                        global.item.description = description;

                        var $selector = $('.mockItemList li .itemName[item_id='+ mockItemId + ']');
                        var $editModel = $('#editModel');
                        var $alert = $editModel.find('.alert');

                        //update page
                        $selector.text(name);
                        $selector.parents('li').attr('title', description);
                        $alert.text('edit successfully');
                        $alert.addClass('alert-success').removeClass('hidden');
                        setTimeout(function() {
                            $alert.addClass('hidden').removeClass('alert-success');
                            $editModel.modal('hide');
                        }, 500);
                        mockService.updateDomainItemDesc();
                    }
                });
            },

            createNewItem: function createNewItem(domain, name, content, resource,description) {
                $.ajax({
                    url: '/mock/items',
                    type: 'post',
                    dataType : "json",
                    data: {name: name, domain: domain, resource: resource, description: description, content: content},
                    success: function(data) {
                        if(data != null) {
                            //update global data
                            global.item.active = false;
                            global.item.id = data._id;
                            global.item.name = data.name;
                            global.item.content = data.content;
                            global.item.description = data.description;
                            global.resource.id = data.resource_id;

                            var $createItemModel = $('#createItemModel');
                            var $alert = $createItemModel.find('.alert');

                            //update createItemModel
                            $alert.removeClass('hidden');
                            setTimeout(function() {
                                $alert.addClass('hidden').removeClass('alert-success');
                                $createItemModel.modal('hide');
                            }, 500);

                            //update MockResourceList
                            var $resourceName = $('.mockResourceList').find('.resourceName[resource_id='+ global.resource.id +']');
                            var $itemList = $resourceName.parents('li').find('.mockItemList');

                            var item = '<li title="' + data.description + '"><span class="state inactive"></span><span item_id="' + data._id + '" class="itemName">' + data.name + '</span>'+
                            '<span class="itemIcon edit"></span>' +
                            '<span class="itemIcon delete"></span></li>';

                            $itemList.append(item);

                            var $icon = $resourceName.siblings('.icon');
                            if($icon.hasClass('downIcon')) {
                                $itemList.slideToggle(200, function(){
                                    $icon.removeClass('downIcon').addClass('upIcon');
                                });
                            }

                            mockService.updateDomainItemDesc();
                            editor.set(JSON.parse(global.item.content));

                            //update selected state
                            var $itemName = $itemList.find('li .itemName[item_id='+ data._id + ']');
                            var $selector = $itemName.parents('li');
                            mockService.itemUnSelectedAll();
                            mockService.itemSelected($selector);

                            var $active = $('.actionBtn.active');
                            if($active.hasClass('disabled')) {
                                $active.removeClass('disabled');
                            }
                            var $save = $('.actionBtn.save');
                            if($save.hasClass('disabled')) {
                                $save.removeClass('disabled');
                            }
                        }
                    }
                });
            },

            //validate the dsl
            verifyInput: function verifyInput($selector, callback) {
                $.ajax({
                    url: '/mock/items/verify',
                    type: 'post',
                    dataType : "json",
                    data: {content: getJSON()},
                    success: function(data) {
                        var message = JSON.parse(data).message;
                        var $err = $('.errMsg');

                        if(message === 'success') {
                            if(global.domain !== '') {
                                $selector.removeClass('disabled');
                            }

                            callback();
                        } else {
                            $err.removeClass('hidden');
                            $err.slideDown();
                            $err.text(message);
                            setTimeout(function() {
                                $err.slideUp();
                            }, 3000);

                           $selector.addClass('disabled');
                        }
                    },
                    error: function() {
                        var $err = $('.errMsg');
                        $err.removeClass('hidden');
                        $err.slideDown();
                        $err.text("Not match the syntax!");
                        setTimeout(function() {
                            $err.slideUp();
                        }, 3000);

                        $selector.addClass('disabled');
                    }
                });
            },

            searchItem: function searchItem(domainId, search) {
                $.ajax({
                    url: '/mock/items/domain/' +  domainId + '/search/' + search,
                    type: 'get',
                    dataType : "json",
                    success: function(data) {
                        var innerHtml = '';
                        var length = data.length;

                        if(length > 0) {
                            for(var i = 0; i < length; i++) {
                                innerHtml += '<li><div class="resourceNameBox"><span class="resourceName" resource_id="' + data[i].resource._id +  '">' +  data[i].resource.name +
                                    '</span><span class="icon downIcon" href="javascript:;"></span></div>';

                                var items = data[i].items;
                                var itemLength = items.length;

                                innerHtml += '<ul class="mockItemList">';

                                for(var j = 0; j< itemLength; j++) {
                                    var state = 'inactive';
                                    if(items[j].active) {
                                        state = 'active';
                                    }
                                    innerHtml = innerHtml + '<li title="' + items[j].description + '"><span class="state ' + state  + '"></span><span item_id="' + items[j]._id + '" class="itemName">' + items[j].name + '</span>'+
                                        '<span class="itemIcon edit"></span>' +
                                        '<span class="itemIcon delete"></span></li>';
                                }

                                innerHtml += '</ul>';

                                innerHtml += '</li>';
                            }
                        } else {
                            innerHtml += '<p style="text-align:center;">Orz...no item matches your requirement!</p> ' +
                                '<p style="text-align:center;">Please retry!</p>';
                        }

                        $('.mockResourceList').html(innerHtml);

                        mockService.clearItemData();
                        mockService.clearResourceData();
                        mockService.updateDomainItemDesc();
                        editor.set();
                        mockService.disabledBtn();
                    }
                });
            },

            getSpecificDomain: function getSpecificDomain(domainName) {
                $.ajax({
                    url: '/mock/domains/name/' + domainName,
                    type: 'get',
                    dataType : "json",
                    success: function(data) {
                        if(data !== null) {
                            //update global data
                            global.domain.name = data.name;
                            global.domain.id = data._id;

                            //update page
                            var $domainName = $('.domainName span');
                            $domainName.attr('domainId', global.domain.id).text(global.domain.name);
                            mockService.updateDomainItemDesc();
                            //enable search and createResource
                            $('.createResourceBtn').removeClass('disabled');
                            $('.searchBox input').removeAttr('disabled');
                            //update itemList
                            mockService.getMockItemList(global.domain.id);
                        }
                    }
                });
            },

			init: function(){
                var str1 = getQueryStringV(decodeURI(location.href), "domain");
                if(str1 !== '') {
                    this.getSpecificDomain(str1);
                }

                var username = getUserName(decodeURI(location.href));

                if(username !== '') {
                    global.user.name = username;
                }

                var $domain = $('.domainName>span');
                global.domain.id = $domain.attr('domain_id');
                global.domain.name = $domain.text();

				this.attachEvents();
              //  this.getDomainList();
			},

			start: function(){
			}
		};
		
		mockService.init();
		mockService.start();

}(window || this, jQuery, undefined));
