/**
 * Bootstrap Table Chinese translation
 * Author: Zhixin Wen<wenzhixin2010@gmail.com>
 */
(function ($) {
    'use strict';

    function zh(){
        //bootstrap table
        $.extend($.fn.bootstrapTable.defaults, {
            formatLoadingMessage: function() {
                return '正在努力地加载数据中，请稍候……';
            },
            formatRecordsPerPage: function(pageNumber) {
                return '每页显示 ' + pageNumber + ' 条记录';
            },
            formatShowingRows: function(pageFrom, pageTo, totalRows) {
                return '显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
            },
            formatSearch: function() {
                return '搜索';
            },
            formatNoMatches: function() {
                return '没有找到匹配的记录';
            },
            formatPaginationSwitch: function () {
                return '隐藏/显示分页';
            },
            formatRefresh: function () {
                return '刷新';
            },
            formatToggle: function () {
                return '切换';
            },
            formatColumns: function () {
                return '列';
            },
            formatExport: function () {
                return '导出数据';
            },
            formatClearFilters: function () {
                return '清空过滤';
            },
            paginationPreText: '上一页',
            paginationFirstText: '首页',
            paginationNextText: '下一页',
            paginationLastText: '尾页',
            serverNetworkErr: '网络异常',
            serverResponseErrr: '响应异常'
        });
        //jquery validate
        $.extend($.validator.messages, {
            required: "必选字段",
            remote: "请修正该字段",
            email: "请输入正确格式的电子邮件",
            url: "请输入合法的网址",
            date: "请输入合法的日期",
            dateISO: "请输入合法的日期 (ISO).",
            number: "请输入合法的数字",
            digits: "只能输入整数",
            creditcard: "请输入合法的信用卡号",
            equalTo: "请再次输入相同的值",
            accept: "请输入拥有合法后缀名的字符串",
            maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串"),
            minlength: $.validator.format("请输入一个长度最少是 {0} 的字符串"),
            rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
            range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
            max: $.validator.format("请输入一个最大为 {0} 的值"),
            min: $.validator.format("请输入一个最小为 {0} 的值")
        });
    }

    function en() {
        //bootstrap table
        $.extend($.fn.bootstrapTable.defaults, {
            formatLoadingMessage: function() {
                return 'trying to load the data, please wait a moment……';
            },
            formatRecordsPerPage: function(pageNumber) {
                return 'per page display ' + pageNumber + ' record';
            },
            formatShowingRows: function(pageFrom, pageTo, totalRows) {
                return 'display ' + pageFrom + ' to ' + pageTo + ' records，a total of ' + totalRows + ' records.';
            },
            formatSearch: function() {
                return 'search';
            },
            formatNoMatches: function() {
                return 'no matching records were found';
            },
            formatPaginationSwitch: function () {
                return 'hide / display paging';
            },
            formatRefresh: function () {
                return 'refresh';
            },
            formatToggle: function () {
                return 'switch';
            },
            formatColumns: function () {
                return 'column';
            },
            formatExport: function () {
                return 'export data';
            },
            formatClearFilters: function () {
                return 'reset';
            },
            paginationPreText: 'pre',
            paginationFirstText: 'first',
            paginationNextText: 'next',
            paginationLastText: 'last',
            serverNetworkErr: 'network error',
            serverResponseErrr: 'response error'
        });
        //jquery validate
        $.extend($.validator.messages, {
            required: "This field is required.",
            remote: "Please fix this field.",
            email: "Please enter a valid email address.",
            url: "Please enter a valid URL.",
            date: "Please enter a valid date.",
            dateISO: "Please enter a valid date ( ISO ).",
            number: "Please enter a valid number.",
            digits: "Please enter only digits.",
            creditcard: "Please enter a valid credit card number.",
            equalTo: "Please enter the same value again.",
            maxlength: $.validator.format( "Please enter no more than {0} characters." ),
            minlength: $.validator.format( "Please enter at least {0} characters." ),
            rangelength: $.validator.format( "Please enter a value between {0} and {1} characters long." ),
            range: $.validator.format( "Please enter a value between {0} and {1}." ),
            max: $.validator.format( "Please enter a value less than or equal to {0}." ),
            min: $.validator.format( "Please enter a value greater than or equal to {0}." )
        });
    }

    var language = $.cookie('language') || 'zh';
    switch (language){
        case 'zh':
            zh();
            break;
        case 'en':
            en()
            break;
        default:
            zh();
    }
})(jQuery);