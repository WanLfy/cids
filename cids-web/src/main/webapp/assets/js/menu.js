/* json拼接树状列表 */
var menu = {
    setTree: function () {
        $.ajax({
            type: 'post',
            url: contextPath + '/menu/getMenuTree.json',
            dataType: 'json',
            success: function (tree) {
                $('#tree').treeview({
                    data: tree,
                    enableLinks: true,
                    isNoUnSelect: true,
                    levels: 1,
                    expandIcon: 'glyphicon glyphicon-chevron-right',
                    collapseIcon: 'glyphicon glyphicon-chevron-down',
                    onhoverColor: '#fbe8cc',
                    selectedBackColor: '#daa353',
                    backColor: '#fafafa',
                    onNodeSelected: function (event, node) {
                        if (node.href) {
                            $('html').scrollTop(0);
                            $("#mainFrame").attr('src', contextPath + node.hrefInfo);
                        }
                    }
                });
                /*绑定点击选中事件*/
                $('#tree').on('click', 'ul', function (e) {
                    var nodeId = parseInt($(e.target).attr('data-nodeid'));
                    if (nodeId) {
                        var node = $('#tree').treeview('getNode', nodeId);
                        if (node.href) {
                            $('#tree').treeview('selectNode', [nodeId]);
                        }
                    }
                });
            },
            error: function (err) {
                alert('不好意思，数据忘记带上了。。。');
            }
        });
        // },
        // //选中对应的菜单
        // selectNodeByName:function (name) {
        //     var tree = $('#tree').treeview(true);
        //     var nodes = tree.search(name,{revealResults:false});
        //     if(nodes.length>0){
        //         tree.clearSearch();
        //         tree.collapseAll();
        //
        //         var node = nodes[nodes.length-1];
        //         tree.selectNode(node,{silent:true});
        //         home.showNav(node);
        //
        //         var tempParent = tree.getNode(node.parentId);
        //         while(tempParent!=null){
        //             tree.expandNode(tempParent);
        //             tempParent=tree.getNode(tempParent.parentId);
        //         }
        //
        //
        //     }
        // },
        // //设置左侧边栏宽度
        // collapse: function () {
        //     var icon = $('.toggle-icon');
        //     var arrow = $('.dir-icon');
        //     var lw = $('.left-sidebar').width();
        //     var rw = $('.right-container').width();
        //     var ml = $('.right-container').css('margin-left');
        //     //折叠侧边栏
        //     icon.on('click', function () {
        //         var lw = $('.left-sidebar').width();
        //         if (icon.hasClass('on')) {
        //             ml = $('.right-container').css('margin-left');
        //             $('.left-sidebar').css('left', -lw + 54 + 'px');
        //             $('.right-container').css({
        //                 'width': rw + lw - 60 + 'px',
        //                 'margin-left': '95px'
        //             });
        //             $('.leftMenu').hide();
        //             arrow.hide();
        //             icon.removeClass('on');
        //         } else {
        //             $('.left-sidebar').css('left', 0);
        //             $('.right-container').css({
        //                 'width': rw,
        //                 'margin-left': ml
        //             });
        //             $('.leftMenu').show();
        //             arrow.show();
        //             icon.addClass('on');
        //         }
        //     });
        //     //收缩侧边栏
        //     arrow.on('click', function () {
        //         var self = $(this);
        //         if (arrow.hasClass('left')) {
        //             self.addClass('right').removeClass('left');
        //             $('.sidebar-wrapper').css('width', lw + 30 + 'px');
        //             $('.left-sidebar').css('width', lw + 30 + 'px');
        //             $('.right-container').css({
        //                 'width': rw -30 + 'px',
        //                 'margin-left': '20vw'
        //             });
        //         } else {
        //             self.addClass('left').removeClass('right');
        //             $('.sidebar-wrapper').css('width', lw);
        //             $('.left-sidebar').css('width', lw);
        //             $('.right-container').css({
        //                 'width': rw,
        //                 'margin-left': ml
        //             });
        //         }
        //     });
        // }
    }
};