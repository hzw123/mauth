/**  
* js时间对象的格式化; 
* eg:format="yyyy-MM-dd hh:mm:ss";   
* var start = new Date();  
start.add("d", -1); //昨天  
start.format('yyyy/MM/dd w'); //格式化  
start.add("m", -1); //上月  
*/  
Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1,  //month   
        "d+": this.getDate(),     //day   
        "h+": this.getHours(),    //hour   
        "m+": this.getMinutes(),  //minute   
        "s+": this.getSeconds(), //second   
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter   
        "S": this.getMilliseconds() //millisecond   
    }  
    var week=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    }  
    if (/(w+)/.test(format)){  
    	format = format.replace(RegExp.$1, week[this.getDay()]);  
    }  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  
   
/** 
*js中更改日期  
* y年， m月， d日， h小时， n分钟，s秒  
*/  
Date.prototype.add = function (part, value) {  
    value *= 1;  
    if (isNaN(value)) {  
        value = 0;  
    }  
    switch (part) {  
        case "y":  
            this.setFullYear(this.getFullYear() + value);  
            break;  
        case "m":  
            this.setMonth(this.getMonth() + value);  
            break;  
        case "d":  
            this.setDate(this.getDate() + value);  
            break;  
        case "h":  
            this.setHours(this.getHours() + value);  
            break;  
        case "n":  
            this.setMinutes(this.getMinutes() + value);  
            break;  
        case "s":  
            this.setSeconds(this.getSeconds() + value);  
            break;  
        default:  
    }  
}  

//js实现日期的比较 
function dateCompare(d1, d2, Operator,type) {
	var day1, day2;
	if(type=='date'){
		day1=d1.format("yyyy-MM-dd");
		day2=d2.format("yyyy-MM-dd");
	}
	if(type=='minite'){
		day1=d1.format("yyyy-MM-dd hh:mm");
		day2=d2.format("yyyy-MM-dd hh:mm");
	}

	var D1 = new Date(day1.replace(/-/g, "/"));
	var D2 = new Date(day2.replace(/-/g, "/"));
	Operator=Operator.replace("/");
	if (Operator == ">") {
		return d1 > d2;
	}
	if (Operator == "<") {
		return d1 < d2;
	}
	if (Operator == "==") {
		return d1 == d2;
	}
	if (Operator == "!=") {
		return d1 != d2;
	}
	if (Operator == ">=") {
		return d1 >= d2;
	}
	if (Operator == "<=") {
		return d1 <= d2;
	}
}
/** 
*jsString 转换日期 yyyy-MM-dd 格式 
*/  
function  stringToYYYYMMDD(dateStr) {
	var mr = /^(\d{4})-(\d{1,2})-(\d{1,2})$/.exec(dateStr);
	if (mr) {
	    var d = new Date(
	    	parseInt(mr[1], 10),
	        parseInt(mr[2], 10)-1,
	        parseInt(mr[3], 10));
	     return d;
	} else {
	    alert("格式不正确!");
	}
}
/** 
 *jsString 转换日期 yyyy-MM-dd HH:mm 格式 
 */  
function stringToYYYYMMDDHHMM(dateStr) {
	var mr = /^(\d{4})-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2})$/.exec(dateStr);
	if (mr) {
		var d = new Date(
				parseInt(mr[1], 10),
				parseInt(mr[2], 10)-1,
				parseInt(mr[3], 10),
				parseInt(mr[4], 10),
				parseInt(mr[5], 10));
		return d;
	} else {
		alert("格式不正确!");
	}
}
/** 
 *jsString 转换日期 yyyy-MM-dd hh:mm:ss 格式 
 */  
function stringToYYYYMMDDHHMMSS(dateStr) {
	var mr = /^(\d{4})-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/.exec(dateStr);
	if (mr) {
		var d = new Date(
				parseInt(mr[1], 10),
				parseInt(mr[2], 10)-1,
				parseInt(mr[3], 10),
				parseInt(mr[4], 10),
				parseInt(mr[5], 10),
				parseInt(mr[6], 10));
		return d;
	} else {
		alert("格式不正确!");
	}
}
