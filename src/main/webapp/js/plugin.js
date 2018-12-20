/**
 * 缓存自定义下拉框回显所需要的值
 */
var cacheselect = {};

/**
 * 替换全部文本
 */
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
    if(!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")), replaceWith);
    } else {
        return this.replace(reallyDo, replaceWith);
    }
}

/**
 * 判断是否是某个字符串结尾
 * @param endStr
 * @returns {boolean}
 */
String.prototype.endWith=function(endStr){
    var d=this.length-endStr.length;
    return (d>=0&&this.lastIndexOf(endStr)==d)
}

/**
 * 将form表单中的元素转换成json字符串
 * @param formId
 */
function getFormJson(formId) {
    // 返回的是json数组[{xxx:xxx},{yyy:yyy}]
    var formData = $("#"+formId).serializeArray();
    // 将json数组转换成json
    var obj={}
    for (var i in formData) {
        //下标为的i的name做为json对象的key，下标为的i的value做为json对象的value
        obj[formData[i].name]=formData[i]['value'];
    }
    return obj;
}

/**
 * 提交form表单到后台，post请求，ajax提交，数据为json
 * @param url 请求url
 * @param formId 需要提交的表单ID
 * @param callback 成功回调函数
 */
function submitForm(url, formId, callback) {
    var json = getFormJson(formId);
    $.post(url, getFormJson(formId), callback);
}

/**
 * 跳转到指定页面
 * @param page
 */
function gotoPage(page) {
    window.href = page;
}

/**
 * 监听下拉框initSelect的class，动态设置值
 */
function initSelectClassListener() {
    $(".initSelect").each(function () {
        var thisSelect = $(this);
        var templet = thisSelect.html().toString();
        var params;
        // 判读是否有typeId属性，如果有，全部取默认配置
        var typeId = $(this).attr("typeId");
        if(typeId) {
            params = {
                "tableName" : 'DATA_CONFIG',// 配置表名
                "filterName" : 'TYPE_ID', // 过滤条件字段名称
                "filterValue" : typeId, // 过滤条件字段值
                "displayName" : 'NAME',// 显示值
                "displayValue" : 'VALUE'// 实际值
            };
        } else {
            //获取下拉框配置的表名称,默认表名defaltTableName
            var tableName = $(this).attr("tableName");
            // 设置默认值,DATA_CONFIG表
            if(!tableName) {
                tableName = "DATA_CONFIG";
            }
            // 获取下拉框查询条件
            var filterName = $(this).attr("filterName");
            var filterValue = $(this).attr("filterValue");
            // 设置默认值，不配置则查询所有
            if(!filterName) {
                filterName = "";
                filterValue = "";
            }
            // 获取下拉框的name和value取值
            var displayName = $(this).attr("displayName");
            var displayValue = $(this).attr("displayValue");
            // 设置默认值,取dataConfig的name和vlaue字段
            if(!displayName) {
                displayName = "NAME";
                displayValue = "VALUE";
            }
            // 拼凑请求后台所需要的参数（json）
            params = {
                "tableName" : tableName,// 配置表名
                "filterName" : filterName,// 过滤条件字段名称
                "filterValue" : filterValue,// 过滤条件字段值
                "displayName" : displayName,// 显示值
                "displayValue" : displayValue// 实际值
            };
        }
        // 请求后台获取对应的数据
        $.post("/dataConfig/getData", params, function (data) {
            var jsonResultList = data.data;
            jsonResultList = eval('('+jsonResultList+')');
            var result = "";
            // 将返回的数据设置到下拉框中
            for(var i in jsonResultList) {
                var jsonResult = jsonResultList[i];
                // name为option的值，value为option的value
                result += templet.replaceAll("#name", jsonResult['name']).replaceAll("#value", jsonResult['value']);
            }
            thisSelect.html(result);
            // 回显缓存的回显值
            if(cacheselect[thisSelect.attr("name")]) {
                thisSelect.val(cacheselect[thisSelect.attr("name")]);
            }
        });
    });
}

/**
 * 监听所有form表单，根据data属性回显
 */
function formShowDataListener() {
    $("form").each(function () {
        var data = $(this).attr("data");
        if(data) {
            var json = eval("("+data+")");
            // 遍历form下的元素，如果name相同，则将数据中的值放入表单元素中
            var nodeList = $(this).children();
            $.each(nodeList, function () {
               var nameAttr = $(this).attr("name");
               console.log(nameAttr);
               if(nameAttr) {
                   // 如果是下拉框，页面加载到此处时，还没有走完initSelectClassListener的回调函数
                   // 直接设置值不会生效，需要先记录值，在initSelectClassListener的回调函数执行完之后放入
                   var inputClass = $(this).attr("class");
                   // 如果class中包含initSelect说明是自定义的可配置的下拉框
                   if(inputClass&&inputClass.indexOf("initSelect")!=-1) {
                       cacheselect[nameAttr] = json[nameAttr];
                   }
               }
            });
        }
    });
}

$(function () {
    /**
     * 监听下拉框initSelect的class，动态设置值
     */
    initSelectClassListener();
    /**
     * 监听所有form表单，根据data属性回显
     */
    formShowDataListener();
});