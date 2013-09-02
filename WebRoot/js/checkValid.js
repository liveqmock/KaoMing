//******************************************************************
//**     功能：判断对象的值是否为' " \ < >
//**     结果：true不包含该类字符，false包含该类字符
//**     说明：fieldName为要检查的文本框对象
//******************************************************************
function f_verifyString(fieldName){
	var flag=true;
  	var sourcestr=new String(fieldName.value);
  	var tempstr;
  	for (var strposition = 0; strposition < sourcestr.length; strposition ++) {
    	if (sourcestr.charAt(strposition)=='\'' || sourcestr.charAt(strposition)=='\"' || sourcestr.charAt(strposition)=='\\' || sourcestr.charAt(strposition)=='<' || sourcestr.charAt(strposition)=='>') {
      		flag = false;
      		break;
    	}
  	}
  	return flag;
}

//******************************************************************
//**     功能：判断对象的值是否符合email的格式
//**     结果：true符合，false不符合
//**     说明：fieldName为要检查的文本框对象
//******************************************************************
function verifyEmail(fieldName)
{
	var flag=true;
	var email = fieldName.value;
	if(email!=null&&email!=''){
	　　var pattern = /^([.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	　　flag = pattern.test(email);
		if(!flag){
			alert("email格式非法");
		}
	}
　　return flag;
}


//******************************************************************
//**     功能：判断对象的值是否超出了指定的长度，包含了汉字的判断，一个汉字算2个长度，一个普通字符算1个长度
//**     结果：true超出了指定的长度，false没有超过指定的长度
//**     说明：fieldName为要检查的文本框对象，len为限定的最大长度，如结果大于len则返回true
//******************************************************************
function f_overLength(fieldName,len){
	var endvalue=0;
  	var sourcestr=new String(fieldName.value);
  	var tempstr;
  	for (var strposition = 0; strposition < sourcestr.length; strposition ++) {
    	tempstr=sourcestr.charAt(strposition);
    	if (tempstr.charCodeAt(0)>255 || tempstr.charCodeAt(0)<0) {
      		endvalue=endvalue+2;
    	}else{
    		endvalue=endvalue+1;
    	}
  	}
  	if(endvalue >len){
  		return true;
  	}else{
  		return false;
  	}
}


//******************************************************************
//**     功能：判断对象的值是否为空，判断了空对象，长度为零，为全空格也视为空！
//**     结果：true表示为空，false表示不为空
//**     说明：参数为对应的文本框对象
//******************************************************************
function f_trimIsNull(fieldName){
	var str = fieldName.value;
  	if(str==null){
  		return true;
  	}
  	if(str.length==0){
  		return true;
  	}
  	var i=0,j=str.length-1,c;
  	for(;i<str.length;i++){
  	  	c=str.charAt(i);
  	  	if((c!=' ') && (c!=' ')){
  	  		return false;
  	  	}
  	  	fieldName.value ="";
		return true;
  	}
}

//******************************************************************
//**     功能：判断对象的值是否为数字类型
//**     结果：
//**     说明：
//******************************************************************
function f_isNumFormat(fieldName,fieldDesc){	
	var flag = true;
	var etext=fieldName.value;
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa<'0'||aa>'9'){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  alert(fieldDesc+"  请输入数字！");//请输入数字！
	  fieldName.focus();	  
    }				
	return flag;
}

//******************************************************************
//**     功能：判断对象的值是否为数字类型
//**     结果：
//**     说明：
//******************************************************************
function f_isNumFormat2(fieldName){	
	var flag = true;
	var etext=fieldName.value;
	if(etext == null){ // 20060825 sunhj added
		return false;
	}else{
		var elen=fieldName.value.length;
		var aa;
		for (i=0;i<=elen-1;i++){
			aa=etext.charAt(i);
			if (aa<'0'||aa>'9'){
				flag=false;
				break;
			}
		}				
		return flag;
	}
}

//******************************************************************
//**     功能：判断对象的值是否为数字类型
//**     结果：
//**     说明：
//******************************************************************
function f_isNumFormat3(fieldName){	
	var flag = true;
	var etext=fieldName.value;
	if(etext == null){ // 20060825 sunhj added
		return false;
	}else{
		var elen=fieldName.value.length;
		var aa;
		for (i=0;i<=elen-1;i++){
			aa=etext.charAt(i);
			if(i==0){
				if(aa!='-'){
					if (aa<'0'||aa>'9'){
						flag=false;
						break;
					}
				}
			}else{
				if (aa<'0'||aa>'9'){
					flag=false;
					break;
				}
			}
		}				
		return flag;
	}
}

//******************************************************************
//**     功能：判断对象的值是否为浮点类型
//**     结果：
//**     说明：看是否在０～９之间，或"."
//******************************************************************
function f_isFloatFormat(fieldName){	
	var flag = true;
	var etext=fieldName.value;
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if ((aa<'0'||aa>'9')&&(aa!='.')){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  fieldName.focus();	  
    }				
	return flag;
}


//******************************************************************
//**     功能：判断对象的值是否为字母和数字
//**     结果：
//**     说明：看是否在０～９之间，或a~z,A~Z之间
//******************************************************************
function f_isNumChar(fieldName,fieldDesc){	
    var flag = true;
    var etext=fieldName.value;

    if(etext!=null&&etext!=''){
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charCodeAt(i);

		if ((aa<48)||(aa>57&&aa<65)||(aa>90&&aa<97)||(aa>122)){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  	alert(fieldDesc+"  只能输入字母和数字！");
	  	fieldName.focus();	  
    	}
    }				
    return flag;
}


//******************************************************************
//**     功能：判断对象的值是否为字母、数字和下划线
//**     结果：
//**     说明：看是否在０～９之间，或a~z,A~Z之间
//******************************************************************
function f_isNumChar2(fieldName,fieldDesc){	
     var flag = true;
    var etext=fieldName.value;

    if(etext!=null&&etext!=''){
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charCodeAt(i);

		if (((aa<48)||(aa>57&&aa<65)||(aa>90&&aa<97)||(aa>122))&&aa!=95){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  	alert(fieldDesc+"  只能输入字母、数字和下划线！");
	  	fieldName.focus();	  
    	}
    }				
    return flag;
}

//******************************************************************
//**     功能：判断对象的值是否为字母、数字
//**     结果：
//**     说明：看是否在０～９之间，或a~z,A~Z之间 Add by hao for repairBRDetail
//******************************************************************
function f_isNumChar3(fieldName){	
    var flag = true;
    var etext=fieldName.value;

    if(etext!=null&&etext!=''){
		var elen=fieldName.value.length;
		var aa;
		for (i=0;i<=elen-1;i++){
			aa=etext.charCodeAt(i);
	
			if (((aa<48)||(aa>57&&aa<65)||(aa>90&&aa<97)||(aa>122))){
				flag=false;
				break;
			}
		}	
	}			
    return flag;
}


//******************************************************************
//**     功能：判断值是否在8~222之间，即为合法的键盘输入（不许输入中文或日文等其它）
//**     结果：
//**     说明：
//******************************************************************
function f_isASCIICode(fieldName){	
	var flag = true;
	var etext=fieldName.value;
	var elen=fieldName.value.length;
	var aa;
	for (i=0;i<=elen-1;i++){
		aa=etext.charCodeAt(i);
		if ((aa>222)||(aa<8)){
			flag=false;
			break;
		}
	}
	if(!flag) {
	  fieldName.focus();	  
    }				
	return flag;
}
//******************************************************************
//**     功能：判断对象的值是否为有效整数
//**     结果：
//**     说明：
//******************************************************************
function f_isValidInt(fieldName,fieldDesc) 
{
  var flag=true;
  var Obj=fieldName;
  var elen=fieldName.value.length;
  var aa;
  var first;
  if(isNaN(Obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : 请输入数字 ！"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  else {
  	first=new Number(fieldName.value.charAt(0));
  	if(first>=1&&first<=9) {
      for (i=1;i<=elen-1;i++){
		aa=fieldName.value.charAt(i);
		if (aa<'0'||aa>'9')
		{
			flag=false;
			alert(fieldDesc +" : 请输入有效整数 ！"); 
	        fieldName.value="";
	        fieldName.focus();
			break;
		}
	  }
	}
	else {
	   flag=false;
		alert(fieldDesc +" : 请输入有效整数 ！"); 
	    fieldName.value="";
	    fieldName.focus();
	}	  
  }		
  return flag;
}
//******************************************************************
//**     功能：判断是否为有效正数
//**     传入参数：
//**           fieldName ：对象名称 eg: document.forms[0].textboxname
//**           fieldDesc : 对象描述 eg: 投资金额
//**           decNum    : 小数点数 eg: 2   
//**     结果：不合法：给出相应的提示信息，返回 false
//**           合法：  返回true
//**     说明：
//******************************************************************
function f_isValidNum(fieldName,fieldDesc,decNum) 
{
  var flag=true;
  var obj=fieldName;
  var vDecIndex;
  vDecIndex=obj.value.indexOf(".");
  if(isNaN(obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : 请输入数字 ！"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  else if(obj.value<0) {
     alert(fieldDesc +" :不能为负！");
     flag=false;
	 fieldName.focus();
  }
  else if(obj.value.charAt(0)=='0'&&obj.value.charAt(1)!='.'||obj.value.charAt(obj.value.length-1)<'0'||obj.value.charAt(obj.value.length-1)>'9')
  {
    flag=false; 
    alert(fieldDesc+" : 请输入有效数字 ！");
	fieldName.value="";
	fieldName.focus();
  }
  if(vDecIndex!=-1) {
  	if(vDecIndex>9) {
  		flag=false; 
    	alert(fieldDesc+" : 您输入的数据过大，请按照要求输入！"); 
		fieldName.focus();
	}
	else if(obj.value.length-vDecIndex>decNum+1) {
  		flag=false; 
    	alert(fieldDesc+" : 请按照要求保留"+decNum+"位小数！"); 
		fieldName.focus();
	}		
  }	
  return flag;
}
//******************************************************************
//**     功能：判断是否为有效正数
//**     传入参数：
//**           fieldName ：对象名称 eg: document.forms[0].textboxname
//**           fieldDesc : 对象描述 eg: 投资金额
//**           decNum    : 小数点数 eg: 2   
//**     结果：不合法：给出相应的提示信息，返回 false
//**           合法：  返回true
//**     说明：
//******************************************************************
function f_isValidNum4(fieldName,fieldDesc,decNum) 
{
  var flag=true;
  var Obj=fieldName;
  var first;
  if(isNaN(Obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : 请输入数字 ！"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  else if(Obj.value<0) {
     alert(fieldDesc +" :不能为负！");
     flag=false;
	 fieldName.focus();
  }
  else if(Obj.value.charAt(0)=='0'&&Obj.value.charAt(1)!='.'||Obj.value.charAt(Obj.value.length-1)<'0'||Obj.value.charAt(Obj.value.length-1)>'9')
  {
    flag=false; 
    alert(fieldDesc+" : 请输入有效数字 ！");
	fieldName.value="";
	fieldName.focus();
  }
  return flag;
}


//******************************************************************
//**     功能：判断是否为有效浮点数
//**     传入参数：
//**           fieldName ：对象名称 eg: document.forms[0].textboxname
//**           fieldDesc : 对象描述	eg: 投资金额
//**			 intNum     :  整数有效位数	eg:10
//**           decNum    : 小数点有效位数	 eg: 2   
//**     结果：不合法：给出相应的提示信息，返回 false
//**           合法：  返回true
//**     说明：
//******************************************************************
function f_isValidFloat(fieldName,fieldDesc,intNum,decNum) 
{
  var flag=true;
  var obj=fieldName;
  var vDecIndex;
  vDecIndex=obj.value.indexOf(".");
  if(isNaN(obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : 请输入数字 ！"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  else if(obj.value<0) {
     alert(fieldDesc +" :不能为负！");
     flag=false;
	 fieldName.focus();
  }
  else if(obj.value!=0&&obj.value.charAt(0)=='0'&&obj.value.charAt(1)!='.'||obj.value.charAt(obj.value.length-1)<'0'||obj.value.charAt(obj.value.length-1)>'9')
  {
    flag=false; 
    alert(fieldDesc+" : 请输入有效数字 ！");
	fieldName.value="";
	fieldName.focus();
  }
  else if(vDecIndex!=-1) {
  	if(vDecIndex>intNum) {
  		flag=false; 
    	alert("您输入的"+fieldDesc+"整数部分大于"+intNum+"位，请重新输入！"); 
		fieldName.focus();
	}
	else if(obj.value.length-vDecIndex>decNum+1) {
  		flag=false; 
    	alert(fieldDesc+" : 请按照要求保留"+decNum+"位小数！"); 
		fieldName.focus();
	}		
  }	else{
  	if(obj.value.length>intNum) {
  		flag=false; 
    	alert("您输入的"+fieldDesc+"整数部分大于"+intNum+"位，请重新输入！"); 
		fieldName.focus();
	}
  }
  return flag;
}

//******************************************************************
//**     功能：判断栏位值是否为空
//**     结果：
//**     说明：
//******************************************************************
function f_isNull(fieldName,fieldDesc)
{
	if(fieldName==null) return true;
   var flag=true;
   if(fieldName.value==null||fieldName.value=="") {
      flag=false;
	  alert("请输入 :"+ fieldDesc);
	  if(fieldName.type!='hidden'){
	  	fieldName.focus();
	  }
   }
   return flag;
}


//******************************************************************
//**     功能：校验长度
//**     结果：
//**     说明：
//******************************************************************
function f_maxLength(fieldName,fieldDesc,len){
		var flag=true;
		
   	var endvalue=0;
  	var sourcestr=new String(fieldName.value);
  	var tempstr;
  	if(sourcestr!=''){
	  	for (var strposition = 0; strposition < sourcestr.length; strposition ++) {
	    	tempstr=sourcestr.charAt(strposition);
	    	if (tempstr.charCodeAt(0)>255 || tempstr.charCodeAt(0)<0) {
	      		endvalue=endvalue+2;
	    	}else{
	    		endvalue=endvalue+1;
	    	}
	  	}
	  }
		
  	if(endvalue >len){
  		flag=false;
  		alert("字段长度超出最大限制("+len+") :"+ fieldDesc);
  	}
  	return flag;
}

//******************************************************************
//**     功能：判断栏位值是否为小数
//**     结果：
//**     说明：
//******************************************************************
function f_isDecemal(fieldName,fieldDesc)
{
  var flag=true;
  var Obj=fieldName;
  if(isNaN(Obj.value)) {
     flag=false; 
	 alert(fieldDesc +" : 请输入小数 ！"); 
	 fieldName.value="";
	 fieldName.focus();
  }	
  if(Obj.value.charAt(0)=='0'&&Obj.value.charAt(1)!='.'||Obj.value.charAt(0)!='0')
  {
    flag=false; 
    alert(fieldDesc+" : 请输入'0'到'1'之间的有效数字 ！");
	fieldName.value="";
	fieldName.focus();
  }
  return flag;
}
//******************************************************************
//**     功能：判断对象(栏位)值是否为空，如果不为空的话，是否为有效数字
//**     结果：
//**     说明：
//******************************************************************
function f_isNumCondValid(fieldName,fieldDecs){	 
	var flag = true; 
    if(fieldName.value!=""&&fieldName.value!=null) {
       flag=f_isValidNum(fieldName,fieldDecs);
    }				
	return flag;
}


//排除非数字字符输入,排除+-.，可以0开头
function checkIsNumPlus(item,item2){
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa=='+'){
			getError("invalidPlus",item2);//不能输入'+'号！
			item.focus();
		}
	}
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa=='-'){
			getError("invalidMinus",item2);//不能输入'-'号！
			item.focus();
		}
	}	
	if (etext.charAt(0)=='0'){
		getError("firstNotDot",item2);//首位不能是'0'！
		item.focus();
	}	
	if (isNaN(item.value))
	{
		getError("checkIsNum",item2);
		item.focus();
	}
	return !errfound;
}


//排除非数字字符输入与空,没有判断+-.全角，可以0开头
function checkIsNum(item,item2){
	errfound = false;

	if (isNaN(item.value))
	{
		getError("checkIsNum",item2);
		item.focus();
	}
	if (item.value.charAt(0)=='.')
	{
		item.value='0'+item.value;
	}
	return !errfound;
}


//排除非数字字符输入与空,排除第一个输入字符是'0'
function firstNotDot(item,item2){
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	if (elen == 0){
		getError("invalidNotNull",item2);//输入框不能为空！
		item.focus();
	}
	if (isNaN(item.value)){
		getError("checkIsNum",item2);
		item.focus();
	}
	if (etext.charAt(0)=='0'){
		getError("firstNotDot",item2);//首位不能是'0'！
		item.focus();
	}
	return !errfound;
}


//排除非数字字符输入与空,排除第一个输入字符是'0',只能整数
function firstNotDot2(item,item2){
    
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	
	if (elen == 0){
		getError("invalidNotNull",item2);//输入框不能为空！
		item.focus();
	}
	if (isNaN(item.value)){
		getError("checkIsNum",item2);
		item.focus();
	}
	if (etext.indexOf('.')!=-1){
		getError("invalidInt",item2);
		item.focus();
	}
	if (etext.charAt(0)=='0'){
		getError("firstNotDot",item2);//首位不能是'0'！
		item.focus();
	}
	return !errfound;
}


//判断文本框是否为空
//调用举例f_timeCheck(receive_date,'来信日期');
function invalidNotNull(item,item2){
	errfound = false;
	etext=item.value;
	elen=item.value.length;
		if (elen == 0){
			getError("invalidNotNull",item2);//输入框不能为空！
			item.focus();
		}
	return !errfound;
}



//判断用户的输入中是否含有"+"
function invalidPlus(item,item2){	
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa=='+'){
			getError("invalidPlus",item2);//不能输入'+'号！
			item.focus();
		}
	}
	return !errfound;
}


//判断用户的输入中是否含有"-"
function invalidMinus(item,item2){	
	errfound = false;
	etext=item.value;
	elen=item.value.length;
	for (i=0;i<=elen-1;i++){
		aa=etext.charAt(i);
		if (aa=='-'){
			getError("invalidMinus",item2);//不能输入'-'号！
			item.focus();
		}
	}
	return !errfound;
}
//根据错误类型，得到错误信息
function getError(text,item2){
	if(text == "invalidInt"){
		text="请输入一个整数！";
	}
	if(text == "invalidNotNull"){
		text="输入框不能为空！";
	}
	if(text == "tooLongNum"){
		text="您输入的数字太大！";
	}
	if(text == "tooLongStr"){
		text="您输入的字符串太长！";
	}
	if(text == "invalidDateFormat"){
		text="请输入'yyyy-mm-dd'格式日期";
	}
	if(text == "invalidStr"){
		text="您输入的字符串非法！";
	}
	if(text == "invalidEmail"){
		text="您的Email格式无效！";
	}
	if(text == "invalidNumFormat"){
		text="请输入半角数字！";
	}
	if(text == "invalidDot"){
		text="不能输入'.'号！";
	}
	if(text == "invalidPlus"){
		text="不能输入'+'号！";
	}
	if(text == "invalidMinus"){
		text="不能输入'-'号！";
	}
	if(text == "checkIsNum"){
		text="请输入数字！";
	}
	if(text == "checkIsLong"){
		text="请输入合法数值！";
	}
	if(text == "firstNotDot"){
		text="首位不能是'0'！";
	}
	if(text == "dateInvalid"){
		text="日期格式不合法(注:标准日期为yyyy-mm-dd格式)！";
	}
	if(text == "numberInvalid"){
		text="输入的数字类型不合法！";
	}
	if(text == "noInvalid"){
		text="输入的编号不合法(注:标准编号为yyyymmdd####格式)！";
	}
	if(text == "invalidPlusInt"){
		text="请输入一个正整数！";
	}
	if(errfound) return;
		window.alert(item2+text);
	errfound=true;
}




function chkPercent(data0){
    var varRet=false;
    
    if(data0!=null&&data0!=''){
	var data1=data0.substring(data0.length-1);
	if(data1=='%'){
		var data2=data0.substring(0,data0.length-1);
		  if(isNaN(data2)) {
			 alert("请输入数字 ！"); 
		  }else if(data2<0) {
		     	alert("不能为负！");
		  }else{
		  	varRet=true;
		  }
		
	}
    }else{
    	alert("折扣不能为空");
    }
	return varRet;
	
}



//******************************************************************
//**     功能：判断对象的值是否为价钱(浮点类型)
//**     结果：
//**     说明：看是否在０～９之间，或"."
//******************************************************************
function f_isMoney(data0){	
  var flag=true;
  var vDecIndex=data0.indexOf(".");
  if(data0==null||data0==''){
  	alert("价格不能为空");
  	flag=false;
  }else if(isNaN(data0)) {
     	 flag=false; 
	 alert("请输入数字 ！"); 
  }	
  else if(data0<0) {
     	alert("不能为负！");
     	flag=false;
  }

  else if(vDecIndex!=-1) {
  	if(data0.length-vDecIndex>3) {
	  	flag=false; 
	    	alert("请按照要求保留2位小数！"); 
	}		
  }	
  return flag;
}



//check Date format
function checkInputDate(obj){
	if(obj==null) return true;
    var   strDate=obj.value;
    var  re =/^(\d{4})-(\d{2})-(\d{2})$/;
    if(re.test(strDate))//判断日期格式符合YYYY-MM-DD标准
    {
     var   dateElement=new   Date(RegExp.$1,parseInt(RegExp.$2,10)-1,RegExp.$3);
      if(!((dateElement.getFullYear()==parseInt(RegExp.$1))&&((dateElement.getMonth()+1)==parseInt(RegExp.$2,10))&&(dateElement.getDate()==parseInt(RegExp.$3))))//判断日期逻辑
      {
       //alert("xx日期格式错误，应为：(YYYY-MM-DD)");
       //return false;
       }
    }else{
    	alert("日期格式错误，应为：(YYYY-MM-DD)");
    	return false;
    }
    return true;
  }
 