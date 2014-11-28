(function(window, $, undefined){
	var mockService = {
        attachEvent: function attachEvent() {
            $('.btn-create').on('click', function() {
                window.location.href = 'main.html?domain=';
            });

            $('.domains-main').on('click', '.domain-item', function() {
                window.location.href = encodeURI('main.html?domain=' + $(this).text());
            });

            $('.domains-more').on('click', function() {
                var $this = $(this),
                    $show = $this.children('p:first-child'),
                    $icon = $this.children('p.icon');
                if ($show.text() === 'SHOW MORE') {
                    mockService.getMoreDomain($show, $icon);
                } else {
                    mockService.getDomainList();
                    $show.text('SHOW MORE');
                    $icon.removeClass('upIcon').addClass('downIcon');
                }

            });
        },
        getDomainList: function getDomainList() {
            $.ajax({
                url: '/mock/domains/skip/0/limit/6',
                type: 'get',
                dataType : "json",
                success: function(data) {
                    var domains = [];
                    var length = data.length;

                    for(var i = 0; i < length; i++) {
                        domains[i] = data[i].name;
                    }

                    var $selector =  $('.domains-main');

                    $selector.html('<a class="domain-item" href="javascript:void(0);">' + domains.join('</a><a class="domain-item" href="javascript:void(0);">') + '</a>');
                }
            });
        },
        getMoreDomain: function getMoreDomain($show, $icon) {
            $.ajax({
                url: '/mock/domains/skip/6',
                type: 'get',
                dataType : "json",
                success: function(data) {
                    var domains = [];
                    var length = data.length;

                    for(var i = 0; i < length; i++) {
                        domains[i] = data[i].name;
                    }

                    var $selector =  $('.domains-main');

                    $selector.append('<a class="domain-item" href="javascript:void(0);">' + domains.join('</a><a class="domain-item" href="javascript:void(0);">') + '</a>');

                    $show.text('COLLAPSE');
                    $icon.removeClass('downIcon').addClass('upIcon');
                }
            });
        },
        init: function init() {
            this.attachEvent();
            this.getDomainList();
        }
    }

    mockService.init();

}(window || this, jQuery, undefined));