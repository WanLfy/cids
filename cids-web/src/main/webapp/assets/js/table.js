/* json构建table */
var table = {
    /**
     * 构建bootstrapTable
     * @param elementId table元素ID
     * @param url 分页请求ajax url
     * @param columns 列描述
     * @param searchFormId 搜索表单元素ID
     * @param singleSelect 是否单选
     */
    showDataTable:function (elementId, url, columns, searchFormId,singleSelect) {
        $('#' + elementId).bootstrapTable({
            columns: columns, //需要显示的列对象
            method: 'post',
            url: contextPath + url,
            contentType: 'application/x-www-form-urlencoded',
            cache:false,
            dataType: 'json',
            pagination: true, //是否显示分页
            sortOrder: '',
            striped: true,
            showColumns: true,
            pageList: [5, 10, 25, 30, 50, 100, 'all'],
            queryParamsType: "",
            sidePagination: 'server',
            clickToSelect: true,
            singleSelect: singleSelect, //单选/多选
            showSelectAll: !singleSelect,
            showRefresh: true,
            responseHandler: function (res) {
                var data = {};
                data.rows = res.pageItems;
                data.total = res.totalItem;
                data.pageErrorInfo = res.pageErrorInfo;
                return data;
            },
            queryParams: function (params) {
                var temp = $("#" + searchFormId).serializeObject();
                temp.pageSize = params.pageSize;
                temp.currentPage = params.pageNumber;
                temp.sort = params.sortName;
                temp.order = params.sortOrder;
                return $.param(temp,true);
            },
            onLoadSuccess: function (data) {
                window.parent.home.iFrameHeight();
                return true;
            }
        });

        //response error
        var BootstrapTable = $.fn.bootstrapTable.Constructor,
            _load = BootstrapTable.prototype.load;
        BootstrapTable.prototype.load = function (data) {
            if(data.pageErrorInfo!=null && data.rows==null){
                bootbox.alert($.fn.bootstrapTable.defaults.serverResponseErrr+':'+data.pageErrorInfo);
            }
            _load.apply(this, Array.prototype.slice.apply(arguments));
        }
    },

    /**
     * 查询
     * @param elementId
     */
    doSearch:function (elementId) {
        $('#' + elementId).bootstrapTable('refresh');
    },

    /**
     * 清空条件
     * @param elementId
     */
    doClear:function (elementId) {
        $('#' + elementId)[0].reset();
        //单选select的清空
        $('#' + elementId).find('select option:selected').attr('selected',false);
    },

    //按照操作抽象方法
    //1.跳转url
    //2.跳转页面带数据
    /**
     * 跳转url
     * @param url
     */
    doJumpUrl:function (url) {
        location.href = contextPath+url;
    },

    /**
     * 跳转页面带数据
     * @param url
     */
    doJumpWithData:function (elementId,url,validateDataFn) {
        var records = $('#' + elementId).bootstrapTable('getSelections');
        //验证是否选中
        if(records.length==0){
            bootbox.alert("请先选择记录");
            return;
        }
        //自定义数据验证回调
        if($.isFunction(validateDataFn)){
            var v = validateDataFn.call(window,records);
            if(v===false){
                return;
            }
        }

        //form
        var $form = $("<form action='"+(contextPath+url)+"' method='POST'></form>");
        //data
        var tableConfig = $('#' + elementId).bootstrapTable('getOptions');
        //singleSelect传输字段信息
        if(tableConfig.singleSelect){
            var data = records[0];
            for(var key in data){
                if(data[key]==null){
                    continue;
                }
                $form.append("<input type='hidden' name='"+key+"' value='"+(data[key]!=null?data[key]:'')+"'/>");
            }
        }
        //多选数据jsonStr
        else{
            $form.append("<input type='hidden' name='dataStr' value='"+JSON.stringify(records)+"'/>");
        }

        $("body").append($form);
        $form.submit();
    },


    /**
     * 选中行或者取消选中事件
     *
     * @param eventFn
     */
    onCheckRowEvent:function(elementId,eventFn){
        $('#' + elementId).on('check.bs.table', function (e, row, $element) {
            var records = $('#' + elementId).bootstrapTable('getSelections');
            eventFn.call(e.target,records);
        });
        $('#' + elementId).on('uncheck.bs.table', function (e, row, $element) {
            var records = $('#' + elementId).bootstrapTable('getSelections');
            eventFn.call(e.target,records);
        });
        $('#' + elementId).on('check-all.bs.table', function (e, row, $element) {
            var records = $('#' + elementId).bootstrapTable('getSelections');
            eventFn.call(e.target,records);
        });
        $('#' + elementId).on('uncheck-all.bs.table', function (e, row, $element) {
            var records = $('#' + elementId).bootstrapTable('getSelections');
            eventFn.call(e.target,records);
        });
    },
    initCheckForm:function (formId) {
        $("#"+formId+" input").attr("readOnly","true");
        $("#"+formId+" select").attr("disabled","true");
        $("#"+formId+" textarea").attr("disabled","true");
    },isOrNo:function(value){
        var s = "";
        switch(value){
            case '0': s="否"; break;
            case '1': s="是"; break;
            default: s=value;
        }
        return s;
    },getOperType:function (value) {
        var returnStr;
        switch (value){
            case '1': returnStr = '增加'; break;
            case '2': returnStr = '修改'; break;
            case '4': returnStr = '删除'; break;
            default :returnStr = value;
        }
        return returnStr;
    },getCheckStat:function (value) {
        var returnStr;
        switch (value){
            case '1': returnStr = '未复核'; break;
            case '2': returnStr = '已通过复核'; break;
            case '3': returnStr = '复核不通过'; break;
            default :returnStr = value;
        }
        return returnStr;
    }
};