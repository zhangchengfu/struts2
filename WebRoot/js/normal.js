//select/unselect all checkbox in datagrid
function trim(str){ 
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

function checkIsNotEmpty(val) {
	if(val != null && trim(val) != ''){
		return true;
	}
	return false;
}



function check(field, key) {
	var t = document.getElementsByName(key)[0];
	if (!field.length) {
		if (t.checked) {
			field[0].checked = true;
		} else {
			field[0].checked = false;
		}
	}
	if (t.checked) {
		for (i = 0; i < field.length; i++) {
			field[i].checked = true;
		}
	} else {
		for (i = 0; i < field.length; i++) {
			field[i].checked = false;
		}
	}
}
function checkId(id, key) {
	var t = document.getElementsByName(key)[0];
	var field = document.getElementsByName(id);
	if (!field.length) {
		if (t.checked) {
			t.checked = true;
		} else {
			t.checked = false;
		}
	}
	if (t.checked) {
		for (i = 0; i < field.length; i++) {
			field[i].checked = true;
		}
	} else {
		for (i = 0; i < field.length; i++) {
			field[i].checked = false;
		}
	}
}
function dealWithCheckbox(id, key)
{
	var t = document.getElementsByName(key)[0];
	var field = document.getElementsByName(id);
	var flag=true;
	for (i = 0; i < field.length; i++) 
	{
		if(!field[i].checked)
		{
			flag=false;
			break;
		}
	}
	if(flag)
	{
		t.checked=true;
	}
	else
	{
		t.checked=false;
	}
}

function openModalDialog(endtarget, winname, w, h) {
	var _left = (window.screen.width - w) / 2;
	var _top = (window.screen.height - h) / 2;
	var features = "dialogWidth:" + w + "px;" + "dialogHeight:" + h + "px;" + "dialogLeft:" + _left + "px;" + "dialogTop:" + _top + "px;" + "directories:no; localtion:no; menubar:no; status=no; toolbar=no;scrollbars:auto;resizable=no";
	return window.showModalDialog(endtarget, winname, features);
    //using this to receive you parmeters in your endtaget page
    //var win=window.parent.dialogArguments;
}

function checkAmount(e, obj) {
	var key = window.event ? e.keyCode : e.which;
	var keycode = String.fromCharCode(key);
	
	if(key == 48){
		if($(obj).val().length == 0 ){
			return false;
		}else if($(obj).val().split(".").length > 1 && $(obj).val().split(".")[1].length>1 && getCaretPos(obj,$(obj).val().split(".")[0].length)){
			return false;
		}
	}else if (key == 8 || key == 46) {
		if (key == 46) {//小数点
			if ($(obj).val().split(".").length > 1 || $(obj).val().split(".")[0] == "") {//只能有一个小数点,并且第一位不是小数点
				return false;
			}
		}
		return true;
	}else{
		if($(obj).val().split(".").length > 1 && $(obj).val().split(".")[1].length>1 && getCaretPos(obj,$(obj).val().split(".")[0].length)){
			return false;
		}
	}
	reg = /\d|\./;
	return reg.test(keycode);
}

function checkAmount2(e, obj) {
	var key = window.event ? e.keyCode : e.which;
	var keycode = String.fromCharCode(key);
	
	if(key == 48){
		if($(obj).val().length == 0 ){
			/*return false;*/
		}else if($(obj).val().split(".").length > 1 && $(obj).val().split(".")[1].length>1 && getCaretPos(obj,$(obj).val().split(".")[0].length)){
			return false;
		}
	}else if (key == 8 || key == 46) {
		if (key == 46) {//小数点
			if ($(obj).val().split(".").length > 1 || $(obj).val().split(".")[0] == "") {//只能有一个小数点,并且第一位不是小数点
				return false;
			}
		}
		return true;
	}else{
		if($(obj).val().split(".").length > 1 && $(obj).val().split(".")[1].length>1 && getCaretPos(obj,$(obj).val().split(".")[0].length)){
			return false;
		}
	}
	reg = /\d|\./;
	return reg.test(keycode);
}

function  getCaretPos(obj,num){  
	 obj.focus();  
	 var  workRange=document.selection.createRange();  
	 var  allRange=obj.createTextRange();  
	 workRange.setEndPoint("StartToStart",allRange);  
	 len=workRange.text.length;  
	 if  (len  >  num){
		 return true;
	 }   
}  
function checkSize(e, obj,num) {
	if($(obj).val().length > num){
		return false;
	}
	return true;
}


function checkNum(e) {
	var key = window.event ? e.keyCode : e.which;
	var keycode = String.fromCharCode(key);
	if (key == 8) {
		return true;
	}
	reg = /[0-9]/;
	//alert(reg.test(keycode));
	return reg.test(keycode);
}


function format(num,pattern){
 var iSnegative=false;
 if(num)
 {
	  var firstChar=num.toString().substr(0,1); 
	  if(firstChar=="-")
	  {
	    num = num.toString().substr(1,num.toString().length-1);
	    iSnegative=true;
	  } 
  }
  var strarr = num?num.toString().split('.'):['0'];   
  var fmtarr = pattern?pattern.split('.'):[''];   
  var retstr='';    
  var str = strarr[0];
  var fmt = fmtarr[0];   
  var i = str.length-1;     
  var comma = false;   
  for(var f=fmt.length-1;f>=0;f--){   
    switch(fmt.substr(f,1)){   
      case '#':   
        if(i>=0 ) retstr = str.substr(i--,1) + retstr;   
        break;   
      case '0':   
        if(i>=0) retstr = str.substr(i--,1) + retstr;   
        else retstr = '0' + retstr;   
        break;   
      case ',': 
        comma = true;   
        retstr=','+retstr;   
        break;   
    }   
  }   
  if(i>=0){   
    if(comma){   
      var l = str.length;   
      for(;i>=0;i--){   
        retstr = str.substr(i,1) + retstr;   
        if(i>0 && ((l-i)%3)==0) retstr = ',' + retstr;    
      }   
    }   
    else retstr = str.substr(0,i+1) + retstr;   
  }   
  
  retstr = retstr+'.';    
  str=strarr.length>1?strarr[1]:'';   
  fmt=fmtarr.length>1?fmtarr[1]:'';   
  i=0;   
  for(var f=0;f<fmt.length;f++){   
    switch(fmt.substr(f,1)){   
      case '#':   
        if(i<str.length) retstr+=str.substr(i++,1);   
        break;   
      case '0':   
        if(i<str.length) retstr+= str.substr(i++,1);   
        else retstr+='0';   
        break;   
    }   
  }   
  retstr= retstr.replace(/^,+/,'').replace(/\.$/,'');
  if(iSnegative)
  {
   retstr="-"+retstr;
  }
  return retstr;
}

function getInputLength(value) 
{
	var tmp = value;
    var length = 0;
    for (var i = 0; i < tmp.length; i++) 
    {
    	if (tmp.charCodeAt(i) > 255)
    	{
        	length = length + 2;
        }
        else 
        {
        	length++;
        }
	}
    return length;
}




function checkIfHaveChinese(value) 
{
	var tmp = value;
	var flag=false;
    for (var i = 0; i < tmp.length; i++) 
    {
    	if (tmp.charCodeAt(i) > 255)
    	{
        	flag=true;
        	break;
        }
	}
    return flag;
}

function onlyAccceptNum(str)
{
	var reg = /^\d+$/;
	if( !reg.test(str) )
	{
		return false;
	}
	return true;
}

function checkPhoneNum(val)
{
	var reg = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/; //支持格式 3至4位区号-7或8位电话号-1至4位分机号
	var reg1= /^1[3|4|5|8][0-9]\d{8}$/;
	if( reg1.test(val) || reg.test(val) )
	{
		return true;
	}
	return false;
}


function checkEndDateGreaterThanStartDate(startDate,endDate){
	var falg = true;
	if(startDate != '' && endDate != ''){
		var formDate = new Date(startDate.replace("-", "/"));
		var toDate = new Date(endDate.replace("-", "/"));
		if(Date.parse(formDate) - Date.parse(toDate) > 0){
			falg = false;
		} 
	}
	return falg;
}

function compareInputDateAndToday(inputDate){//  >0 过去   =0 今天   <0 将来
	var dd = new Date(); 
	var y = dd.getFullYear(); 
	var m = dd.getMonth()+1;
	var d = dd.getDate();     
	var today = new Date((y+"-"+m+"-"+d).replace("-", "/")); 
	var idate = new Date(inputDate.replace("-", "/"));
	return (Date.parse(today) - Date.parse(idate));
}

function ShowCalender(obj) {
	new Calendar(null, null, 0, 0).show(document.getElementsByName(obj)[0]);
}

function ShowCalender(startYear,endYear,obj) {
	new Calendar(startYear, endYear, 0, 0).show(document.getElementsByName(obj)[0]);
}

function atoc(numberValue){  
	var numberValue=new String(Math.round(numberValue*100)); // 数字金额  
	var chineseValue=""; // 转换后的汉字金额  
	var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字  
	var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位  
	var len=numberValue.length; // numberValue 的字符串长度  
	var Ch1; // 数字的汉语读法  
	var Ch2; // 数字位的汉字读法  
	var nZero=0; // 用来计算连续的零值的个数  
	var String3; // 指定位置的数值  
	if(len>15){  
	alert("超出计算范围");  
	return "";  
	}  
	if (numberValue==0){  
	chineseValue = "零元整";  
	return chineseValue;  
	}  
	String2 = String2.substr(String2.length-len, len); // 取出对应位数的STRING2的值  
	for(var i=0; i<len; i++){  
	String3 = parseInt(numberValue.substr(i, 1),10); // 取出需转换的某一位的值  
	if ( i != (len - 3) && i != (len - 7) && i != (len - 11) && i !=(len - 15) ){  
	if ( String3 == 0 ){  
	Ch1 = "";  
	Ch2 = "";  
	nZero = nZero + 1;  
	}  
	else if ( String3 != 0 && nZero != 0 ){  
	Ch1 = "零" + String1.substr(String3, 1);  
	Ch2 = String2.substr(i, 1);  
	nZero = 0;  
	}  
	else{  
	Ch1 = String1.substr(String3, 1);  
	Ch2 = String2.substr(i, 1);  
	nZero = 0;  
	}  
	}  
	else{ // 该位是万亿，亿，万，元位等关键位  
	if( String3 != 0 && nZero != 0 ){  
	Ch1 = "零" + String1.substr(String3, 1);  
	Ch2 = String2.substr(i, 1);  
	nZero = 0;  
	}  
	else if ( String3 != 0 && nZero == 0 ){  
	Ch1 = String1.substr(String3, 1);  
	Ch2 = String2.substr(i, 1);  
	nZero = 0;  
	}  
	else if( String3 == 0 && nZero >= 3 ){  
	Ch1 = "";  
	Ch2 = "";  
	nZero = nZero + 1;  
	}  
	else{  
	Ch1 = "";  
	Ch2 = String2.substr(i, 1);  
	nZero = nZero + 1;  
	}  
	if( i == (len - 11) || i == (len - 3)){ // 如果该位是亿位或元位，则必须写上  
	Ch2 = String2.substr(i, 1);  
	}  
	}  
	chineseValue = chineseValue + Ch1 + Ch2;  
	}  
	if ( String3 == 0 ){ // 最后一位（分）为0时，加上“整”  
	//chineseValue = chineseValue + "整";  
	}  
	return chineseValue;  
	} 

function checkMail(val)
{
	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if( reg.test(val))
	{
		return true;
	}
	return false;
}

function checkDoubleNum(e, obj) {
	var key = window.event ? e.keyCode : e.which;
	var keycode = String.fromCharCode(key);
	
	if(key == 48){
		if($(obj).val().split(".").length > 1 && $(obj).val().split(".")[1].length>1){
			return false;
		}
	}else if (key == 8 || key == 46) {
		if (key == 46) {//小数点
			if ($(obj).val().split(".").length > 1 || $(obj).val().split(".")[0] == "") {//只能有一个小数点,并且第一位不是小数点
				return false;
			}
		}
		return true;
	}else{
		if($(obj).val().split(".").length > 1 && $(obj).val().split(".")[1].length>1 && getCaretPos(obj,$(obj).val().split(".")[0].length)){
			return false;
		}
	}
	reg = /\d|\./;
	return reg.test(keycode);
}

//验证时间
function checkTime(e){
	var key = window.event ? e.keyCode : e.which;
	var keycode = String.fromCharCode(key);
	if (key == 8) {
		return true;
	}
	reg = /[0-9]|\:/;
	return reg.test(keycode);
}

// 验证字母和数字
function checkNumAndLetter(e) {
	var key = window.event ? e.keyCode : e.which;
	var keycode = String.fromCharCode(key);
	if (key == 8) {
		return true;
	}
	reg = /[A-Za-z0-9]/;
	return reg.test(keycode);
}

function validateAmt(sDouble)
{
  var re = /^\d+(?=\.{0,1}\d+$|$)/;
  return re.test(sDouble);
}

function validateTime(sTime){
	var re = /^([0-9]|[01][0-9]|2[0-3])\:([0-9]|[0-5][0-9])\:([0-9]|[0-5][0-9])$/;
	return re.test(sTime);
}

function validateNum(sNum){
	var re = /^\d+(\.\d{1,2})?$/;
	return re.test(sNum);
}
