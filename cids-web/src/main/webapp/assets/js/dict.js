(function () {
    /**
     * 字典常量
     */
    window._DICT_VAL = {};


    //获取字典常量
    var _getDictVal = function () {
        return (window.parent)?window.parent._DICT_VAL:window._DICT_VAL;
    }
    //根据key找到json对象值
    var _loopJsonObjByKey = function(json,key){
        for(var jk in json){
            if(jk==key){
                return json[jk];
            }
        }
        return null;
    }

    /**
     * ajax加载后台常量
     * 在home.html加载一次,iframe里可使用
     */
    var loadDict = function () {
        $.ajax({
            url: contextPath+'/common/dict/queryAll.json',
            type: 'post',
            dataType: 'json',
            success: function (data) {
                _DICT_VAL = data;
            }
        });
        //window._DICT_VAL = {'flowType': {'0': '测试11', '1': '测试22'}};
    }

    /**
     * 通过dictKey获取字典
     * @param dictKey
     * @returns {{}}
     */
    var getDict = function (dictKey) {
        var dict = _getDictVal();

        return _loopJsonObjByKey(dict,dictKey) || {};
    }

    /**
     * 通过dictKey获取字典,然后更具具体值获取翻译(描述)
     * @param dictKey
     * @param val
     * @returns {string}
     */
    var getDictLabel = function (dictKey,val) {
        var d = getDict(dictKey);
        return _loopJsonObjByKey(d,val) || '-';
    }


    window.dict = {
        loadDict:loadDict,
        getDict:getDict,
        getDictLabel:getDictLabel
    };
})();
