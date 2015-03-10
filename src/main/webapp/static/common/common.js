// 显示和隐藏窗口的速度
SPEED = 'slow';

//验证是否有非常字符
function checkValidity(obj){
	var checkString="`~!@#$%^&*()+=[]{}\\|;':\",/<>?";
	for (var j=0;j<checkString.length;j++)
	{
		if(obj.indexOf(checkString.substring(j,j+1))!=-1)
		{
			return true;
		}
	}
	return false;

}

// 验证金额格式满足 (xxx || xx.xx)
function moneyCheck(v,msg){
	var patrn=/^\-?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
	if(!patrn.test(v)){
		if(msg){
			alert('金额格式不正确，请输入正确的金钱,如：10.00 、 10、 10.0');
		}
		return false;
	} 
	return true;
}

// 去掉字符串前后空格
function trim(str){
  var strReturn;
  strReturn = leftTrim(str);
  strReturn = rightTrim(strReturn);
  return strReturn;
}

// 去掉字符串首空格
function leftTrim(strValue){
var re =/^\s*/;
if(strValue==null)
  return null;
strValue= "" + strValue;
var strReturn = strValue.replace(re,"");
return strReturn;
}


// 去掉字符串尾空格
function rightTrim(strValue){
var re =/\s*$/;
if(strValue==null)
  return null;
var strReturn = strValue.replace(re,"");
return strReturn;
}




// 得到字符串的字符长度（一个汉字占两个字符长）
function getBytesLength(str) {   
   return str.replace(/[^\x00-\xff]/g, 'xx').length;   
}


// 对输入域是否是整数的校验,即只包含字符0123456789 luzhonghua
function isInteger(strValue)
{
  var result = regExpTest(strValue,/\d+/g);
  return result;
}


// 正则验证 Test luzhonghua
function regExpTest(source,re)
{
  var result = false;

  if(source==null || source=="")
    return false;

  if(source==re.exec(source))
    result = true;

  return result;
}


// 对输入域是否是数字的校验 luzhonghua
function isNumeric(strValue)
{
	var result = regExpTest(strValue,/\d*[.]?\d*/g);
	return result;
}



// 给所有INPUT:TEST添加取出头尾空字符的方法
$(function($) {
	$("input:text").blur(function() {
		var trV = trim($(this).val());
		$(this).val(trV);
	});
});

// --身份证号码验证-支持新的带x身份证
function isValidateIdCardNo(num) 
{
     var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
     var error;
     var varArray = new Array();
     var lngProduct = 0;
     var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;
    // initialize
    if (intStrLen != 18) {
        error = "输入身份证号码长度必须为18位！当前长度为"+intStrLen+"位";
        alert(error);
        return false;
    }    
    // check and set value
    for(var i=0;i<intStrLen;i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            error = "错误的身份证号码！";
            alert(error);
            return false;
        } else if (i < 17) {
            varArray[i] = varArray[i]*factorArr[i];
        }
    }
    if (intStrLen == 18) {
        // check date
        var date8 = idNumber.substring(6,14);
        date8 = date8.substring(0,4)+"-"+date8.substring(4,6)+"-"+date8.substring(6,8);
        if (isDatePart(date8) == false) {
            error = "身份证中日期信息不正确！";
            alert(error);
            return false;
        }       
        // calculate the sum of the products
        for(var i=0;i<17;i++) {
            lngProduct = lngProduct + varArray[i];
        }        
        // calculate the check digit
        intCheckDigit = 12 - lngProduct % 11;
        switch (intCheckDigit) {
            case 10:
                intCheckDigit = 'X';
                break;
            case 11:
                intCheckDigit = 0;
                break;
            case 12:
                intCheckDigit = 1;
                break;
        }    
   
        // check last digit
        if (varArray[17].toUpperCase() != intCheckDigit) {
            alert("错误的身份证号码！");
            return false;
        }
    } 
    else{        // length is 15
        // check date
         var date6 = "19" +idNumber.substring(6, 8)
				+ "-" + idNumber.substring(8, 10) + "-"
				+ idNumber.substring(10, 12);
        
		if (isDatePart(date6) == false) {
            alert("身份证日期信息有误！");
            return false;
        }
    }
   
    return true;
}
/**
 * 判断一个字符串是否为合法的日期格式：YYYY-MM-DD
 * 
 * @param dateStr
 * @return
 */
function isDatePart(dateStr) {
	var parts;

	if (dateStr.indexOf("-") > -1) {
		parts = dateStr.split('-');
	} else {
		return false;
	}

	if (parts.length < 3) {
		// 日期部分不允许缺少年、月、日中的任何一项
		return false;
	}

	for (i = 0; i < 3; i++) {
		// 如果构成日期的某个部分不是数字，则返回false
		if (isNaN(parts[i])) {
			return false;
		}
	}

	y = parts[0];// 年
	m = parts[1];// 月
	d = parts[2];// 日

	if (m.length != 2 || d.length != 2 || y.length != 4) {
		return false;
	}
	if (y > 9999 && y < 1900) {
		return false;
	}

	if (m < 1 || m > 12) {
		return false;
	}

	switch (d) {
	case 29:
		if (m == 2) {
			// 如果是2月份
			if ((y / 100) * 100 == y && (y / 400) * 400 != y) {
				// 如果年份能被100整除但不能被400整除 (即闰年)
			} else {
				return false;
			}
		}
		break;
	case 30:
		if (m == 2) {
			// 2月没有30日
			return false;
		}
		break;
	case 31:
		if (m == 2 || m == 4 || m == 6 || m == 9 || m == 11) {
			// 2、4、6、9、11月没有31日
			return false;
		}
		break;
	default:

	}
	return true;
}

// 去掉表单中所有输入框前后空格
function trimAllInput() {
	$(":text").each(function() {
		$(this).val($.trim($(this).val()));
	});
}


// 特殊字符校验
function hasSpecailChar(strValue){
	return ! (/^[\w\s\u4e00-\u9fa5\.]*$/.test(strValue));
}


// 验证手机号
function isMobile(val){
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	return (myreg.test(val));
}

// 百分率格式验证 例如： 90.00%
function isPercent(val){
// var myreg=/^\d+(\.\d)?\d{0,3}%$/;
	var myreg=/^(([1-9]\d(\.\d{1,2})?)|(0\.(?!0+%$)\d{1,2}))%$/;
	return (myreg.test(val));
}

// 验证电话号码
function isTel(val){
	var myreg  =/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	return (myreg.test(val));
}

// 验证EMAIL
function isEmail(val){
	var pass=0; 
	if (window.RegExp){
	  var tempS="a"; 
	  var tempReg=new RegExp(tempS);
	  if (tempReg.test(tempS)) pass=1;
	}
	if(!pass)return(val.indexOf(".")>2) && (val.indexOf("@")>0);
	var r1=new RegExp("(@.*@)|(\\.\\.)|(@\\.)|(^\\.)");
	var r2=new RegExp("^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&\\??\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\}\\~]*[a-zA-Z0-9\\!\\#\\$\\%\\&\\??\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\}\\~]\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$");
	return (!r1.test(val) && r2.test(val)); 
}

// 校验数字与字母
function isNumAndChar(strValue){
	return /^[A-Za-z0-9]*$/.test(strValue);
}


// 邮政编码验证
function isPostcode(val){
	var postcodereg  =/^[0-9]\d{5}$/;
	return (postcodereg.test(val));
}

// 置空
function setEmpty(id) {
	$("#"+id).val("");
}
  
// 取url参数
function XgetPara(obj,def){
	var url = location + "";
	if(url.indexOf("?") <= 0)
		return (typeof(def) == "undefined") ? "" : def;
	url = url.substring(url.indexOf("?")+1);

    // xg.fang 2007-11-21
	var isflag = url.indexOf("forward=");
	if(isflag >= 0)
	{
		return url.substring(isflag+8);
	}
    else
	{
		var allParas = url.split("&");
		var paras = new Array();
		for(var i=0; i<allParas.length;i++)
			if(allParas[i].indexOf(obj+"=")==0)
				paras[paras.length]=allParas[i].substring(allParas[i].indexOf("=")+1);
		var para;
		if(paras.length==0)
			para = (typeof(def) == "undefined") ? "" : def;
		else if(paras.length==1)
			para =  paras[0];
		else
			para =  paras;

		if(window.HTMLElement && para!=null && para.indexOf("%u")<0){	para = decodeURI(para);}

		return para;
	}
}

/**
 * 是否为此刻后一个小时
 * 
 * @param leasetime
 */
function afterOneHour(leasetime) {
	var tmpArray1 = leasetime.split(" ");
	if (tmpArray1.length < 2) {
		tmpArray1 = leasetime.split(" ");

		var YearMonthDate1 = tmpArray1[0].split("-");
		var time = new Date(YearMonthDate1[0], YearMonthDate1[1] - 1,
				YearMonthDate1[2]);

		var vl = 1000*60*60;
		var now = new Date();
		now.setTime(now.getTime() + vl);

		if (time > now) {
			return true;
		} else {
			return false;
		}
	} else {
		var YearMonthDate1 = tmpArray1[0].split("-");
		var HourMinSec1 = tmpArray1[1].split(":");
		var time = new Date(YearMonthDate1[0], YearMonthDate1[1] - 1,
				YearMonthDate1[2], HourMinSec1[0], HourMinSec1[1]);

		var vl = 1000*60*60;
		var now = new Date();
		now.setTime(now.getTime() + vl);

		if (time > now) {
			return true;
		} else {
			return false;
		}
	}
}

/**
 * 是否大于当前时间
 * 
 * @param leasetime
 */
function afterNow(leasetime) {
	var tmpArray1 = leasetime.split(" ");
		var YearMonthDate1 = tmpArray1[0].split("-");
		var HourMinSec1 = tmpArray1[1].split(":");
		var time = new Date(YearMonthDate1[0], YearMonthDate1[1] - 1,
				YearMonthDate1[2], HourMinSec1[0], HourMinSec1[1]);

		var now = new Date();
		now.setTime(now.getTime());

		if (time > now) {
			return true;
		} else {
			return false;
		}
}

/**
 * 时间比较
 * 
 * @param leasetime
 * @param returntime(为空时，默认当前系统时间)
 * @returns {Boolean}
 */
function compareTime(leasetime, returntime) {
	var tmpArray1 = leasetime.split(" ");
	var tmpArray2 = returntime == null ? null : returntime.split(" ");
	if (tmpArray1.length < 2) {
		var YearMonthDate1 = tmpArray1[0].split("-");
		var now1 = new Date(YearMonthDate1[0], YearMonthDate1[1] - 1,
				YearMonthDate1[2]);
		
		var now2 = new Date();
		if(tmpArray2 != null) {
			var YearMonthDate2 = tmpArray2[0].split("-");
			now2 = new Date(YearMonthDate2[0], YearMonthDate2[1] - 1,
					YearMonthDate2[2]);
		}
		if (now1 < now2) {
			return false;
		} else {
			return true;
		}
	} else {
		var YearMonthDate1 = tmpArray1[0].split("-");
		var HourMinSec1 = tmpArray1[1].split(":");
		var now1 = new Date(YearMonthDate1[0], YearMonthDate1[1] - 1,
				YearMonthDate1[2], HourMinSec1[0], HourMinSec1[1]);

		var now2 = new Date();
		if(tmpArray2 != null) {
			var YearMonthDate2 = tmpArray2[0].split("-");
			var HourMinSec2 = tmpArray2[1].split(":");
			now2 = new Date(YearMonthDate2[0], YearMonthDate2[1] - 1,
					YearMonthDate2[2], HourMinSec2[0], HourMinSec2[1]);

		}
		
		if (now1 < now2) {
			return false;
		} else {
			return true;
		}
	}
}

/**
 * 将Date对象按规定格式转为字符串
 * 
 * @author wyh
 * 
 */
 Date.prototype.format = function(format){
	var o = {
            "M+" : this.getMonth()+1, // month
            "d+" : this.getDate(), // day
            "h+" : this.getHours(), // hour
            "m+" : this.getMinutes(), // minute
            "s+" : this.getSeconds(), // second
            "q+" : Math.floor((this.getMonth()+3)/3), // quarter
            "S" : this.getMilliseconds() // millisecond
        };
    if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
};

/**
 * 判定某值是否为空
 * 
 * @param value
 * @returns {Boolean}
 */
function isNull(value) {
	if(value == null || $.trim(value) == '' || typeof(value) == 'undefined') {
		return true;
	} else {
		return false;
	}
}

// 去两位小数
function round(number, digit) { 
    var multiple  = 1; 
    for( var i = 0; i < digit; i++ ) 
    { 
        multiple  *= 10; 
    }
    if(number >= 0) 
    	return parseInt(number * multiple  + 0.5) / multiple; 
    else
    	return parseInt(number * multiple  - 0.5) / multiple; 
} 

/**
 * 获取当前系统时间 格式：年月日时分秒+毫秒（1－3位）
 * 
 * @returns
 */
function getdate() {   
	 var now=new Date();
	  y=now.getFullYear();
	  m=now.getMonth()+1;
	  d=now.getDate();
	  m=m<10?"0"+m:m;
	  d=d<10?"0"+d:d;

	  h = now.getHours() ;// + ":"; //取小时
	  h=h<10?"0"+h:h;
	  M = now.getMinutes() ;// + ":"; //取分
	  M=M<10?"0"+M:M;
	  s = now.getSeconds();         // 取秒
	  s=s<10?"0"+s:s;

	  return y+m+d+h+M+s + now.getMilliseconds() ;
}

//验证是否有非常字符
function checkStrT(obj){
	var checkString="`~!@#$%^&*()+=[]{}\\|;':\",/<>?";
	for (var j=0;j<checkString.length;j++)
	{
		if(obj.indexOf(checkString.substring(j,j+1))!=-1)
		{
			alert("上传文件名中不能有非常字符:\""+checkString.substring(j,j+1)+"\",请重新命名!");
			return true;
		}
	}
}
