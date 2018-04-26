//用对象关键字将各个方法包裹住，避免命名冲突
var home = {
    //初始化时间插件
    initDate: function () {
        $('.date-picker').datepicker({
            format: "yyyymmdd",
            autoclose: true
        });
    },
    // 初始化多选插件
    initSelect: function () {
        $('#warehouseID,#entityVariety').selectpicker({
            'selectedText': 'cat'
        });
    },
    // 条件筛选框折叠
    toggleChevron: function () {
        $('.portlet-title span').on('click', function () {
            var self = $(this);
            if (self.hasClass('glyphicon-chevron-down')) {
                self.addClass('glyphicon-chevron-up').removeClass('glyphicon-chevron-down');
            } else {
                self.addClass('glyphicon-chevron-down').removeClass('glyphicon-chevron-up');
            }
        });
    },
    //查询弹框
    query: function () {
        $('#query').on('click', function () {
            bootbox.alert("查询条件有误!");
        });
    },
    // 返回顶部
    goTop: function () {
        $(".footer-tools").click(function () {
            $('body,html').animate({ scrollTop: 0 }, 500);
            return false;
        });
    },
    iFrameHeight:function (){
        var ifm = $("#mainFrame");
        ifm.height(300);
        try{
            var windowHeight = $(window).height();
            var ifmHeight = ifm.contents().height();
            var heightW = ifmHeight > windowHeight ? ifmHeight : windowHeight;
            ifm.height(heightW);
        } catch(e){
            ;
        }
    },

    goHome:function(){
        // 折叠所有节点
        $('#tree').treeview('collapseAll', { silent: true });
        home.showNav();
        home.refreshIfream(contextPath+'/url/commonUrls.htm');
    },
    refreshIfream:function(url){
        $("#mainFrame").attr('src', url);
    },
    showNav:function (node){
        // 显示导航added by fick song at 2014.09.18
        var navStr = '<li><i class="glyphicon glyphicon-home"></i>\n' +
            '                                <a href="#" onclick="javascript:home.goHome()"\n' +
            '                                > 首页</a></li>';
        if(node){
            var url = node.hrefInfo;
            if (url == "javascript:;" || (contextPath&&url.match(new RegExp(contextPath+'/?$')) ) ) {
                return false;
            }
            navStr += home.getNavInfo(node);
            //增加收藏夹功能added by lyt at 2016年8月25日10:38:22 start
            var exp = new RegExp("/","g");
            var cookieKey = url.replace(exp,"_")+"_userid"+$('.username').last().attr("id");
            if($.cookie(cookieKey) == $('.username').last().html()){
                navStr += "&nbsp;&nbsp;<span class='fa fa-star' id='"+cookieKey+"' style='color: rgb(255, 128, 0);width: auto;cursor:pointer;' onclick='home.menuToFav(this)'>已收藏</span>";
            }else{
                navStr += "&nbsp;&nbsp;<span class='fa fa-star-o'  id='"+cookieKey+"'  style='color: rgb(104, 104, 104);width: auto;cursor:pointer;' onclick='home.menuToFav(this)'>点击收藏</span>";
            }
            $('#navTitle').html(node.text);
        }else{
            $('#navTitle').html('首页');
        }
        $('#navBody').html(navStr);
    },
    getNavInfo:function (node){
        var parentNav = "";
        var navStr = "";
        if(node.parentId != null){
            var parentNode = $('#tree').treeview('getParent', node);
            parentNav = home.getNavInfo(parentNode);
        }
        var onclickStr = "";
        if(node.hrefInfo){
            onclickStr = "onclick=\"javascript:home.refreshIfream('"+contextPath+node.hrefInfo+"')\""
        }
        navStr += "<li><i class='glyphicon'></i><a href='#' "+onclickStr+">";
        navStr += node.text;
        navStr += "</a></li>";

        return parentNav + navStr;
    },

    //收藏常用菜单至cookie
    menuToFav:function (obj){
        var cookieKey = $(obj).attr("id");
        if($.cookie(cookieKey) == $('.username').last().html()){
            $(obj).attr("class","fa fa-star-o");
            $(obj).attr("style","color: rgb(104, 104, 104);width: auto;cursor:pointer;");
            $(obj).html("点击收藏");
            $.cookie(cookieKey, null,{ expires: 0 , path: '/' });
        }else{
            if(home.checkFav())return false;
            $(obj).attr("class","fa fa-star");
            $(obj).attr("style","color: rgb(255, 128, 0);width: auto;cursor:pointer;");
            $(obj).html("已收藏");
            $.cookie(cookieKey, $('.username').last().html(), { expires: 365 , path: '/' });
        }
    },

    //检查收藏是否达上限
    checkFav:function (){
        var maxFav = 10;//定义收藏上限值
        var cookies = $.cookie();//获取所有cookie
        var i=0;//定义收藏计数器
        //遍历cookie对菜单收藏个数进行统计
        for(var key in cookies){
            if(cookies[key] == $('.username').last().html()){
                i++;
            }
        }
        if(i>=maxFav){
            alert("收藏已达上限！");
            return true;
        }
        return false;
    },

    delFav:function(obj){
        var url = $(obj).attr("id");
        var exp = new RegExp("/","g");
        var cookieKey = url.replace(exp,"_")+"_userid"+window.parent.$('.username').last().attr("id");
        if($.cookie(cookieKey) == window.parent.$('.username').last().html()){
            $.cookie(cookieKey, null,{ expires: 0 , path: '/' });
        }
        $(obj).parent().parent().remove();
    },

    blockUI : function(options) {
        var options = $.extend(true, {}, options);
        var html = '';
        if (options.iconOnly) {
            html = '<div class="loading-message '
                + (options.boxed ? 'loading-message-boxed' : '')
                + '"><img style="" src="'
                + contextPath
                + '/assets/img/loding-edit.gif" align=""></div>';
        } else if (options.textOnly) {
            html = '<div class="loading-message '
                + (options.boxed ? 'loading-message-boxed' : '')
                + '"><span>&nbsp;&nbsp;'
                + (options.message ? options.message : 'LOADING...')
                + '</span></div>';
        } else {
            html = '<div class="loading-message '
                + (options.boxed ? 'loading-message-boxed' : '')
                + '"><img style="" src="'
                + contextPath
                + '/assets/img/loding-edit.gif" align=""><span>&nbsp;&nbsp;'
                + (options.message ? options.message : 'LOADING...')
                + '</span></div>';
        }

        if (options.target) { // element blocking
            var el = jQuery(options.target);
            if (el.height() <= ($(window).height())) {
                options.cenrerY = true;
            }
            el
                .block({
                    message : html,
                    baseZ : options.zIndex ? options.zIndex : 1000,
                    centerY : options.cenrerY != undefined ? options.cenrerY
                        : false,
                    css : {
                        top : '10%',
                        border : '0',
                        padding : '0',
                        backgroundColor : 'none'
                    },
                    overlayCSS : {
                        backgroundColor : options.overlayColor ? options.overlayColor
                            : '#000',
                        opacity : options.boxed ? 0.05 : 0.1,
                        cursor : 'wait'
                    }
                });
        } else { // page blocking
            $.blockUI({
                    message : html,
                    baseZ : options.zIndex ? options.zIndex : 1000,
                    css : {
                        border : '0',
                        padding : '0',
                        backgroundColor : 'none'
                    },
                    overlayCSS : {
                        backgroundColor : options.overlayColor ? options.overlayColor
                            : '#000',
                        opacity : options.boxed ? 0.05 : 0.1,
                        cursor : 'wait'
                    }
                });
        }
    },

    // wrapper function to un-block element(finish loading)
    unblockUI : function(target) {
        if (target) {
            jQuery(target).unblock({
                onUnblock : function() {
                    jQuery(target).css('position', '');
                    jQuery(target).css('zoom', '');
                }
            });
        } else {
            $.unblockUI();
        }
    }
};
