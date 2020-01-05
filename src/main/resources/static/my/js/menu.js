function Menu(eleId, userId) {
    this.eleId = eleId || "nav";
    this.userId = userId || "0";
    this.permission = [];
    this.menuItemRight = '<i class="iconfont nav_right">&#xe697;</i>';
    this.menuItemTemplate = ""
        +'<li>'
        + '	<a {{menu_level}}href="{{menu_href}}">'
        + '		<i class="iconfont">{{men_icon}}</i>'
        + '		<cite>{{menu_name}}</cite>'
        + '		{{menu_right}}'
        + '	</a>'
        + '	{{sub_menu}}'
        +'</li>';
    this.subMenu = '<ul class="sub-menu">'
        + '	{{sub_menu_list}}'
        + '</ul>';
}

Menu.prototype = {
    init: function (eleId, userId) {
        eleId = eleId || this.eleId;
        userId = userId || this.userId;
        var _this = this;
        _this.getMenuData(userId).then(function (res) {
            var htmlStr = _this.generateMenu('',_this.permission);
            $("#" + eleId).html(htmlStr);
        });
        // _this.menuListen();
    },
    getMenuData: function (userId) {
        var _this = this;
        return new Promise(function (resolve, reject) {
                $.ajax({
                    url: '/permission/menu',
                    type: 'GET',
                    // dataType: 'jsonp',
                    data: {'userId': 1}
                }).always(function (res) {//complete()
                }).done(function (res) {//Success()
                    if (res.code != '200') {
                        return;
                    }

                    if(res.data.length == 0){
                        $('.left-nav').animate({left: '-221px'}, 100);
                        $('.page-content').animate({left: '0px'}, 100);
                        $('.page-content-bg').hide();
                        $('.container .left_open i').hide()
                        return ;
                    }
                    _this.permission = res.data;

                    if (resolve && typeof resolve == "function") {
                        resolve(res);
                    }
                }).fail(function (res) {
                    if (reject && typeof reject == "function") {
                        reject(res);
                    }
                });
            }
        );
    },
    generateMenu: function (eleId,data) {
        var htmlStr = "";
        var _this = this;
        if(data.length == 0){
            return ;
        }
        //var userPermission = localStorage.permission;
        var parentList = [];
        data.map(function (item, index) {
            var isRoot = false;
            if (item.parentId == 0) {
                isRoot = true;
            }
            var menuItemStr = _this.menuItemTemplate;
            menuItemStr = menuItemStr.replace(/{{menu_name}}/, item.name);
            if (isRoot) {
                menuItemStr = menuItemStr.replace(/{{menu_right}}/, _this.menuItemRight);
                menuItemStr = menuItemStr.replace(/{{men_icon}}/, "&#xe6b4;");
            } else {
                menuItemStr = menuItemStr.replace(/{{menu_right}}/, "");
                menuItemStr = menuItemStr.replace(/{{men_icon}}/, "&#xe6a7;");
            }
            var child = "";
            if (item.child) {
                var childList = _this.generateMenu(eleId,item.child);
                if (childList) {
                    child = _this.subMenu;
                    child = child.replace(/{{sub_menu_list}}/, childList);
                    menuItemStr = menuItemStr.replace(/{{menu_level}}/, "");
                    menuItemStr = menuItemStr.replace(/{{menu_href}}/, "javascript:;");
                } else {
                    menuItemStr = menuItemStr.replace(/{{menu_level}}/, "_");
                    menuItemStr = menuItemStr.replace(/{{menu_href}}/, item.href);
                }
            } else {
                menuItemStr = menuItemStr.replace(/{{menu_level}}/, "_");
                menuItemStr = menuItemStr.replace(/{{menu_href}}/, item.href);
            }
            menuItemStr = menuItemStr.replace(/{{sub_menu}}/, child);
            htmlStr += menuItemStr;
        });
        return htmlStr;
    },
    menuListen: function() {
        $('.left-nav #nav').unbind();
        $('.left-nav #nav').on('click', 'li', function(event) {
            var index = $('.left-nav #nav li').index($(this));

            if($(this).children('.sub-menu').length){
                if($(this).hasClass('open')){

                    if($(this).parent().hasClass('sub-menu')){
                        deleteCookie('left_menu_son');
                    }else{
                        deleteCookie('left_menu_father');
                    }

                    $(this).removeClass('open');
                    $(this).find('.nav_right').html('&#xe697;');
                    $(this).children('.sub-menu').stop().slideUp();
                    $(this).siblings().children('.sub-menu').slideUp();
                }else{


                    if($(this).parent().hasClass('sub-menu')){
                        setCookie('left_menu_son',index);
                    }else{
                        setCookie('left_menu_father',index);
                    }

                    $(this).addClass('open');
                    $(this).children('a').find('.nav_right').html('&#xe6a6;');
                    $(this).children('.sub-menu').stop().slideDown();
                    $(this).siblings().children('.sub-menu').stop().slideUp();
                    $(this).siblings().find('.nav_right').html('&#xe697;');
                    $(this).siblings().removeClass('open');
                }
            }else{

                var url = $(this).children('a').attr('_href');
                var title = $(this).find('cite').html();
                // var index  = $('.left-nav #nav li').index($(this));

                var is_refresh = $(this).attr('date-refresh')?true:false;

                for (var i = 0; i <$('.x-iframe').length; i++) {
                    if($('.x-iframe').eq(i).attr('tab-id')==index+1){
                        tab.tabChange(index+1);
                        event.stopPropagation();

                        if(is_refresh)
                            $('.x-iframe').eq(i).attr("src",$('.x-iframe').eq(i).attr('src'));

                        return;
                    }
                };

                if(getCookie('tab_list')){
                    tab_list = getCookie('tab_list').split(',');
                }else{
                    tab_list = [];
                }

                var is_exist = false;

                for (var i in tab_list) {
                    if(tab_list[i]==index)
                        is_exist = true;
                }

                if(!is_exist){
                    tab_list.push(index);
                }

                setCookie('tab_list',tab_list);

                tab.tabAdd(title,url,index+1);
                tab.tabChange(index+1);
            }

            event.stopPropagation();

        })

        // 左侧菜单记忆功能
        if(getCookie('left_menu_father')!=null){
            $('.left-nav #nav li').eq(getCookie('left_menu_father')).click();
        }

        if(getCookie('left_menu_son')!=null){
            $('.left-nav #nav li').eq(getCookie('left_menu_son')).click();
        }
    }

}