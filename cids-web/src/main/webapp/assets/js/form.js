/** jquery validate 设置错误显示方式 */
$.validator.setDefaults({
    /**错误元素父form-group添加error样式*/
    highlight : function(element) {
        $(element).closest('.input-wrapper').addClass('has-error');
    },
    /**错误元素父input-wrapper移除error样式*/
    unhighlight : function(element) {
        $(element).closest('.input-wrapper').removeClass('has-error');
        //隐藏弹出框
        var _popover = $(element).data('_popover');
        _popover && _popover.popover("hide");
    },
    errorElement:'div',
    errorPlacement : function(error, element) {
        //错误弹出框显示
        var _popover;
        _popover = $(element).popover('destroy').popover({
                trigger: "manual",
                placement: "right",
                content: error.text(),
                template: "<div class=\"popover\"><div class=\"arrow\"></div><div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
        });
        _popover.popover("show");
        $(element).data('_popover',_popover);
    },
    /*
    之前的错误显示方式
    showErrors:function(errorMap,errorList){
        var firstErr = errorList[0];
        showErrMessage(firstErr.message);
    },*/
    //提交处理
    /*submitHandler: function (form) {
        //
        var html = '<div class="loading-message "><img style="" src="'
                    + contextPath
                    + '/assets/img/loading-spinner-grey.gif" align=""><span>&nbsp;&nbsp;LOADING...</span></div>';
        $.blockUI({
                message : html,
                baseZ : 1000,
                css : {border : '0', padding : '0',backgroundColor : 'none'},
                overlayCSS : {backgroundColor :'#000',opacity : 0.1,cursor : 'wait' }
            });
        form.submit();
    }*/
});

/*** jquery validate 自定义校验规则start ****************************************************************************/
/*
 字节校验
 一个中文字符算两个字节
 */
$.validator.addMethod(
    "byteMaxLength",
    function(value, element, param) {
        var length = value.length;
        for(var i = 0; i < value.length; i++){
            if(value.charCodeAt(i) > 127){
                length++;
            }
        }
        return this.optional(element) || length <= param;
    },
    $.validator.format("请确保输入的值不大于{0}个字节(一个中文字算2个字节)")
);


/*
 邮政编码验证
 6位数字
 */
jQuery.validator.addMethod("isZipCode", function(value, element) {
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");

/*
 地址格式校验
 不能包含空格/不能有特殊字符/5-40位之间
 */
$.validator.addMethod(
    "checkAddress",
    function(value, element, param) {
        var callName = '地址';
        if(value.indexOf(' ')>=0){
            $(element).data('error-msg',callName+"中不能包含空格符，请重新输入！");
            return false;
        }
        if(/^\d+$/.test(value)){
            $(element).data('error-msg',callName+"中不能全部为数字，请重新输入！");
            return false;
        }
        /*var badChar = "><,[]{}?/+=|\\'\":;~!@#$%^&`";
        for(var i=0;i<badChar.length;i++){
            var c1 = badChar.charAt(i);
            if(value.indexOf(c1)>=0){
                $(element).data('error-msg',callName+"中不能含有字符 "+c1 + " !");
                return false;
            }
        }*/
        /*var len = value.length;
        if ((len>40)||(len<5))
        {
            $(element).data('error-msg',callName+"不能超过40个汉字也不能少于五个汉字！");
            return false;
        }*/

        return  true;
    },
    function(params, element) {
        return $.validator.format( $(element).data('error-msg') );
    }
);

/*
 英文校验
 英文或者空格
 */
$.validator.addMethod(
    "english",
    function (value, element, param) {
        var reg = /^[a-zA-Z ]*$/;
        return this.optional(element) || reg.test(value);
    },
    $.validator.format("请输入英文字母或者空格")
);
/*
 特殊字符校验
 不能包含:[]{}?+=\'\"~!@$%^&`
 */
$.validator.addMethod(
    "spectailChar",
    function (value, element, param) {
        var badChar = "[]{}?+=\'\"~!@$%^&`";
        for(var i=0;i<badChar.length;i++){
            var c1 = badChar.charAt(i);
            if(value.indexOf(c1)>=0){
                $(element).data('error-msg',"第"+(value.indexOf(c1)+1)+"位不能含有字符 "+c1 + " ！");
                return false;
            }
        }
        return true;
    },
    function(params, element) {
        return $(element).data('error-msg');
    }
);

/*
 身份证校验
 15位身份证号或者18位
 */
$.validator.addMethod(
    "IDCard",
    function (value, element, param) {
        return this.optional(element) || /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
    },
    $.validator.format("身份证格式不正确")
);

/*
 电话校验
 手机/固话/传真
 (4位区号-)(3-5位数字-)(4-12位数字)(-1-6位分机)
 */
$.validator.addMethod(
    "phone",
    function (value, element, param) {
        return this.optional(element) || /^(([0-9]{4}-)?[0-9]{3,5}-)?[0-9]{4,12}(-[0-9]{1,6})?$/.test(value);
    },
    $.validator.format("不是有效电话")
);

/*
 需求的电话校验规则:不允许除数字 +（）- 以外的任何字符
 */
$.validator.addMethod(
    "phoneNumber",
    function (value, element, param) {
        return this.optional(element) || /^[0-9\+\-\(\)]+$/.test(value);
    },
    $.validator.format("不允许除数字 +（）- 以外的任何字符")
);

/*
 只允许输入数字、字母
 */
$.validator.addMethod(
    "numberLetter",
    function (value, element, param) {
        return this.optional(element) || /^[\d\w]+$/.test(value);
    },
    $.validator.format("不允许输入除数字、字母以外的字符")
);

/**
 * 金额
 * 16位整数2位小数
 */
$.validator.addMethod(
    "amount",
    function (value, element, param) {
        return this.optional(element) || /^[0-9]{1,16}(\.[0-9]{1,2})?$/.test(value);
    },
    $.validator.format("不是有效金额")
);

/**
 * 金额
 * 16位整数2位小数
 */
$.validator.addMethod(
    "checkFileType",
    function (value, element, param) {
        var filepath=value;
        //获得上传文件名
        var fileTArr=filepath.toLowerCase().split(".");
        //切割出后缀文件名
        var filetype=fileTArr[fileTArr.length-1];
        return this.optional(element) || param==filetype;
    },
    $.validator.format("文件格式不正确")
);

/**
 * 大于值
 */
$.validator.addMethod(
    "greatThan",
    function (value, element, param) {
        var target = $( param );
        if ( this.settings.onfocusout && target.not( ".validate-greatThan-blur" ).length ) {
            target.addClass( "validate-greatThan-blur" ).on( "blur.validate-greatThan", function() {
                $( element ).valid();
            } );
        }
        if(value <= target.val()){
            $(element).data('error-msg',"请输入大于"+target.val()+"的值！");
            return this.optional(element) || false;
        }
        return true;
    },
    function(params, element) {
        return $.validator.format( $(element).data('error-msg') );
    }
);

/**
 * 小于值
 */
$.validator.addMethod(
    "lessThan",
    function (value, element, param) {
        var target = $( param );
        if ( this.settings.onfocusout && target.not( ".validate-lessThan-blur" ).length ) {
            target.addClass( "validate-lessThan-blur" ).on( "blur.validate-lessThan", function() {
                $( element ).valid();
            } );
        }
        if(value > target.val()){
            $(element).data('error-msg',"请输入小于"+target.val()+"的值！");
            return this.optional(element) || false;
        }
        return true;
    },
    function(params, element) {
        return $.validator.format( $(element).data('error-msg') );
    }
);


/**
 *  校验客户代码有效性
 *  validClientID:{bindName:'#clientName'}
 */
$.validator.addMethod(
    "validClientID",
    function (value,element,param) {
        param = $.extend({
            url:contextPath +'/common/queryClientInfo.json',
            bindName:null
        },param)

        var validator = this;
        return $.validator.methods.remote.apply(this,[value,element,{
            url:param.url,
            success:function(response){
                var previous = validator.previousValue(element);
                validator.settings.messages[element.name].validClientID = previous.originalMessage;
                var valid = response!=null && response.success==true;

                if(param.bindName){
                    $(param.bindName).val(valid?response.result.name:'');
                }
                if ( valid ) {
                    var submitted = validator.formSubmitted;
                    validator.prepareElement(element);
                    validator.formSubmitted = submitted;
                    validator.successList.push(element);
                    delete validator.invalid[element.name];
                    validator.showErrors();
                } else {
                    var errors = {};
                    var message = response.retMsg || validator.defaultMessage( element, "validClientID" );
                    errors[element.name] = previous.message = $.isFunction(message) ? message(value) : message;
                    validator.invalid[element.name] = true;
                    validator.showErrors(errors);
                }
                previous.valid = valid;
                validator.stopRequest(element, valid);
            }
        }]);
    },
    "无效的客户代码"
);

/**
 *  校验会员代码
 *  validMemberID:{bindName:'#clientName'}
 */
$.validator.addMethod(
    "validMemberID",
    function (value,element,param) {
        param = $.extend({
            url:contextPath +'/common/queryMemberInfo.json',
            bindName:null
        },param)

        var validator = this;
        return $.validator.methods.remote.apply(this,[value,element,{
            url:param.url,
            success:function(response){
                var previous = validator.previousValue(element);
                validator.settings.messages[element.name].validMemberID = previous.originalMessage;
                var valid = response!=null && response.success==true;

                if(param.bindName){
                    $(param.bindName).val(valid?response.result.name:'');
                }
                if ( valid ) {
                    var submitted = validator.formSubmitted;
                    validator.prepareElement(element);
                    validator.formSubmitted = submitted;
                    validator.successList.push(element);
                    delete validator.invalid[element.name];
                    validator.showErrors();
                } else {
                    var errors = {};
                    var message = response.retMsg || validator.defaultMessage( element, "validMemberID" );
                    errors[element.name] = previous.message = $.isFunction(message) ? message(value) : message;
                    validator.invalid[element.name] = true;
                    validator.showErrors(errors);
                }
                previous.valid = valid;
                validator.stopRequest(element, valid);
            }
        }]);
    },
    "无效会员代码"
);

/**
 *  校验席位代码
 *  validMemberID:{bindName:'#clientName'}
 */
$.validator.addMethod(
    "validSeatID",
    function (value,element,param) {
        param = $.extend({
            url:contextPath +'/common/querySeatsBySeatId.json',
            bindName:null
        },param)

        var validator = this;
        return $.validator.methods.remote.apply(this,[value,element,{
            url:param.url,
            success:function(response){
                var previous = validator.previousValue(element);
                validator.settings.messages[element.name].validSeatID = previous.originalMessage;
                var valid = response!=null && response.success==true;

                if(param.bindName){
                    $(param.bindName).val(valid?response.result.seatName:'');
                }
                if ( valid ) {
                    var submitted = validator.formSubmitted;
                    validator.prepareElement(element);
                    validator.formSubmitted = submitted;
                    validator.successList.push(element);
                    delete validator.invalid[element.name];
                    validator.showErrors();
                } else {
                    var errors = {};
                    var message = response.retMsg || validator.defaultMessage( element, "validSeatID" );
                    errors[element.name] = previous.message = $.isFunction(message) ? message(value) : message;
                    validator.invalid[element.name] = true;
                    validator.showErrors(errors);
                }
                previous.valid = valid;
                validator.stopRequest(element, valid);
            }
        }]);
    },
    "无效席位代码"
);

/**
 *  校验客户交易16代码
 */
$.validator.addMethod(
    "validTradeID",
    function (value,element,param) {
        param = $.extend({
            url:contextPath +'/common/queryClientSeatByPK.json',
        },param)

        var validator = this;
        return $.validator.methods.remote.apply(this,[value,element,{
            url:param.url,
            success:function(response){
                var previous = validator.previousValue(element);
                validator.settings.messages[element.name].validTradeID = previous.originalMessage;
                var valid = response!=null && response.success==true;

                if ( valid ) {
                    var submitted = validator.formSubmitted;
                    validator.prepareElement(element);
                    validator.formSubmitted = submitted;
                    validator.successList.push(element);
                    delete validator.invalid[element.name];
                    validator.showErrors();
                } else {
                    var errors = {};
                    var message = response.retMsg || validator.defaultMessage( element, "validTradeID" );
                    errors[element.name] = previous.message = $.isFunction(message) ? message(value) : message;
                    validator.invalid[element.name] = true;
                    validator.showErrors(errors);
                }
                previous.valid = valid;
                validator.stopRequest(element, valid);
            }
        }]);
    },
    "无效交易代码"
);


/**
 * 元素中添加错误,直接不通过,返回错误信息
 */
$.validator.addMethod(
    "addError",
    function(value, element, param) {
        $(element).data('error-msg',param);
        return false;
    },
    function(params, element) {
        return $.validator.format( $(element).data('error-msg') );
    }
);
/*** jquery validate 自定义校验规则end ****************************************************************************/