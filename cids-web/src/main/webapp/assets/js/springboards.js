//加载单点登录系统列表
var springboard = {
    $div: '<div id="springboard"></div>',
    $icon: '<div class="icon transition glyphicon glyphicon-indent-right"></div>',
    $board: '<div class="board transition"></div>',

    //请求数据
    post: function (api_url,login_name) {
        var _this = this;
        $.ajax({
            url: api_url, //请求地址
            type: 'POST',
            data: {userName:login_name}, //用户信息
            contentType: 'application/json;charset=utf-8',
            dataType: 'jsonp', //返回的数据格式：json/xml/html/script/jsonp/text
            success: function (data, textStatus, jqXHR) {
                _this.build(data.result);
            },
            error: function (xhr, textStatus) {
                alert('请求失败');
            }
        });
    },

    // 拼接dom
    build: function (elements) {
        /* 返回data中应包含以下三项：
         url:对应平台地址,
         name:对应平台名称,
         id:对应平台id值
         [{url:'',name:'',id:''},{url:'',name:'',id:''}]
         */
        var string = '';
        for (var i = 0, length = elements.length; i < length; i++) {
            string += '<span><a href="' + elements[i].url + '" title="' + elements[i].name + '" data-id="' + elements[i].id + '">' + elements[i].name + '</a></span>';
        }
        var item = $(this.$board).html(string);
        this.render(item);
    },

    // 页面渲染
    render: function (item) {
        var div = $(this.$div);
        var icon = $(this.$icon);
        div.append(icon).append(item);
        $('body').append(div);
        this.action();
    },

    //收缩动作
    action: function () {
        $('#springboard').on('click', function () {
            var icon = $(this).find('.icon');
            var board = $(this).find('.board');
            var width = board.css('width');
            if (icon.hasClass('glyphicon-indent-right')) {
                icon.addClass('glyphicon-indent-left').removeClass('glyphicon-indent-right');
                board.css('left', '-' + width);
                board.css('height',board.find('> span').length*40+'px');
            } else {
                icon.addClass('glyphicon-indent-right').removeClass('glyphicon-indent-left');
                board.css('left', 0);
                board.css('height','40px');
            }
        });
        //鼠标离开收起
        $("#springboard").on('mouseleave',function (e) {
            var icon = $(this).find('.icon');
            var board = $(this).find('.board');
            if (!icon.hasClass('glyphicon-indent-right')) {
                icon.addClass('glyphicon-indent-right').removeClass('glyphicon-indent-left');
                board.css('left', 0);
                board.css('height','40px');
            }
        });
    }
}