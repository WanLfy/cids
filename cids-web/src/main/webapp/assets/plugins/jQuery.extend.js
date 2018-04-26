$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

$.extend({
    initSelectPick:function(elementId,url,valueStr,keyStr,nowValue,queryParameter,width,doSelect){
        var elementObject = $('#'+elementId);
        $.ajax({
            url:  contextPath + url,
            type: "post",
            dataType: "json",
            data: queryParameter,
            async:false,
            success: function (data) {
                elementObject.html('');
                // elementObject.append("<option value=''>请选择</option>");
                $.each(data, function (i,value) {
                    var str = JSON.stringify(value);
                    elementObject.append("<option value='" + value[keyStr] + "' elementObj='"+str+"'>" + value[valueStr] + "</option>");
                });
                var option = {noneSelectedText:"请选择",selectOnTab:true,showTick:true};
                if(width){
                    option.width = width;
                }
                elementObject.selectpicker(option);
                elementObject.selectpicker('refresh');
                elementObject.selectpicker('val',nowValue);
                if(typeof doSelect != 'undefined' && typeof doSelect == 'function'){
                    elementObject.on('changed.bs.select',function (e) {
                        doSelect.call(this,e);
                    });
                }
            },
            error: function (data) {
                alert("获取数据失败" + data);
            }
        });
    },
    dateFormat:function (date, format) {
        if (!date)
            return;
        if (!format)
            format = "yyyy-MM-dd";
        switch (typeof date) {
            case "string":
                date = this.stringToDate(date);
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date)
            return;
        var dict = {
            "yyyy" : date.getFullYear(),
            "M" : date.getMonth() + 1,
            "d" : date.getDate(),
            "H" : date.getHours(),
            "m" : date.getMinutes(),
            "s" : date.getSeconds(),
            "MM" : ("" + (date.getMonth() + 101)).substr(1),
            "dd" : ("" + (date.getDate() + 100)).substr(1),
            "HH" : ("" + (date.getHours() + 100)).substr(1),
            "mm" : ("" + (date.getMinutes() + 100)).substr(1),
            "ss" : ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
            return dict[arguments[0]];
        });
    },
    /**
     * 字符串转为Date对象
     * @param dateStr
     * @returns {Date}
     */
    stringToDate:function (dateStr){
        var yyyyMMdd_reg = /^[0-9]{4}[0-9]{2}[0-3]{1}[0-9]{1}$/;
        var yyyy_MM_dd_reg = /^[0-9]{4}-[0-9]{2}-[0-3]{1}[0-9]{1}$/;
        var yyyyMMddHHmmss_reg = /^[0-9]{4}[0-9]{2}[0-3]{1}[0-9]{1}[0-2]{1}[0-9]{1}[0-5]{1}[0-9]{1}[0-5]{1}[0-9]{1}$/;
        var yyyy_MM_dd_HH_mm_ss_reg = /^[0-9]{4}-[0-9]{2}-[0-3]{1}[0-9]{1} [0-2]{1}[0-9]{1}:[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}$/;
        var tempStr = "";
        if(yyyy_MM_dd_HH_mm_ss_reg.test(dateStr) ){
            tempStr = dateStr;
        }else if(yyyy_MM_dd_reg.test(dateStr) ){
            tempStr = dateStr +" 00:00:00";
        }else if(yyyyMMdd_reg.test(dateStr)){
            tempStr = dateStr.substring(0,4) +"-"+dateStr.substring(4,6)+"-"+ dateStr.substring(6,8) +" 00:00:00";
        }else if(yyyyMMddHHmmss_reg.test(dateStr)){
            tempStr = dateStr.substring(0,4) +"-"+dateStr.substring(4,6)+"-"+ dateStr.substring(6,8) +" "+dateStr.substring(8,10)
                +":"+dateStr.substring(10,12)+":"+dateStr.substring(12,14);
        }else{
            tempStr = dateStr;
        }
        return new Date(tempStr);
    },

    /**
     * 数值格式化,copy:http://www.asual.com/jquery/format/ 1.3.js
     * 使用示例:
     *  $.dateFormat(0.123, '#0.0000')  :  '0.1230'
     *  $.dateFormat(7456.2, '#,##0.0#')  :  '7,456.2'
     *  $.dateFormat(7456.351, '#,##0.#')  :  '7,456.4'
     *  $.dateFormat(123.4, '#,##0.00#')  :  '123.40'
     *  $.dateFormat(12.32410, '#,##0.0000#')  :  '12.3241'
     *  $.dateFormat(0.123213, '#,##0.00###')  :  '0.12321'
     *  $.dateFormat(2101.234, '#,###')  :  '2,101'
     *
     * @param value
     * @param format
     * @returns {*}
     */
    dataFormat: function(value, format) {
        var _locale = {
            number: {
                format: '#,##0.0#',
                groupingSeparator: ',',
                decimalSeparator: '.'
            }
        };

        var groupingSeparator,
            groupingIndex,
            decimalSeparator,
            decimalIndex,
            roundFactor,
            result,
            i;
        if (typeof value == 'string') {
            groupingSeparator = _locale.number.groupingSeparator;
            decimalSeparator = _locale.number.decimalSeparator;
            decimalIndex = value.indexOf(decimalSeparator);

            roundFactor = 1;
            if (decimalIndex != -1) {
                roundFactor = Math.pow(10, value.length - decimalIndex - 1);
            }
            value = value.replace(new RegExp('[' + groupingSeparator + ']', 'g'), '');
            value = value.replace(new RegExp('[' + decimalSeparator + ']'), '.');
            return Math.round(value * roundFactor) / roundFactor;
        } else {
            if (typeof format == 'undefined' || format.length < 1) {
                format = _locale.number.format;
            }
            groupingSeparator = ',';
            groupingIndex = format.lastIndexOf(groupingSeparator);
            decimalSeparator = '.';
            decimalIndex = format.indexOf(decimalSeparator);
            var integer = '',
                fraction = '',
                negative = value < 0,
                minFraction = format.substr(decimalIndex + 1).replace(/#/g, '').length,
                maxFraction = format.substr(decimalIndex + 1).length,
                powFraction = 10;
            value = Math.abs(value);
            if (decimalIndex != -1) {
                fraction = _locale.number.decimalSeparator;
                if (maxFraction > 0) {
                    roundFactor = 1000;
                    powFraction = Math.pow(powFraction, maxFraction);
                    var tempRound = Math.round(parseInt(value * powFraction * roundFactor -
                                Math.round(value) * powFraction * roundFactor, 10) / roundFactor),
                        tempFraction = String(tempRound < 0 ? Math.round(parseInt(value * powFraction * roundFactor -
                                parseInt(value, 10) * powFraction * roundFactor, 10) / roundFactor) : tempRound),
                        parts = value.toString().split('.');
                    if (typeof parts[1] != 'undefined') {
                        for (i = 0; i < maxFraction; i++) {
                            if (parts[1].substr(i, 1) == '0' && i < maxFraction - 1 &&
                                tempFraction.length != maxFraction) {
                                tempFraction = '0' + tempFraction;
                            } else {
                                break;
                            }
                        }
                    }
                    for (i = 0; i < (maxFraction - fraction.length); i++) {
                        tempFraction += '0';
                    }
                    var symbol,
                        formattedFraction = '';
                    for (i = 0; i < tempFraction.length; i++) {
                        symbol = tempFraction.substr(i, 1);
                        if (i >= minFraction && symbol == '0' && /^0*$/.test(tempFraction.substr(i + 1))) {
                            break;
                        }
                        formattedFraction += symbol;
                    }
                    fraction += formattedFraction;
                }
                if (fraction == _locale.number.decimalSeparator) {
                    fraction = '';
                }
            }

            if (decimalIndex !== 0) {
                if (fraction != '') {
                    integer = String(parseInt(Math.round(value * powFraction) / powFraction, 10));
                } else {
                    integer = String(Math.round(value));
                }
                var grouping = _locale.number.groupingSeparator,
                    groupingSize = 0;
                if (groupingIndex != -1) {
                    if (decimalIndex != -1) {
                        groupingSize = decimalIndex - groupingIndex;
                    } else {
                        groupingSize = format.length - groupingIndex;
                    }
                    groupingSize--;
                }
                if (groupingSize > 0) {
                    var count = 0,
                        formattedInteger = '';
                    i = integer.length;
                    while (i--) {
                        if (count !== 0 && count % groupingSize === 0) {
                            formattedInteger = grouping + formattedInteger;
                        }
                        formattedInteger = integer.substr(i, 1) + formattedInteger;
                        count++;
                    }
                    integer = formattedInteger;
                }
                var maxInteger, maxRegExp = /#|,/g;
                if (decimalIndex != -1) {
                    maxInteger = format.substr(0, decimalIndex).replace(maxRegExp, '').length;
                } else {
                    maxInteger = format.replace(maxRegExp, '').length;
                }
                var tempInteger = integer.length;
                for (i = tempInteger; i < maxInteger; i++) {
                    integer = '0' + integer;
                }
            }
            result = integer + fraction;
            return (negative ? '-' : '') + result;
        }
    }
});