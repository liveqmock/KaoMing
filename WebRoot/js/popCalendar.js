document.write('<iframe id=\"CalFrame\" name=\"CalFrame\" frameborder=\"0\" src=\"popup/calendar.jsp\" style=\"display:none;position:absolute;z-index:100\"></iframe>');
document.onclick=hideCalendar;

var clientDate =  new Date();

var serverToday = (clientDate.getYear()) + "-" + (clientDate.getMonth() + 1) + "-" + (clientDate.getDate()) ;  //��������������ڣ�Ĭ���ȸ����ͻ�������
var dateAjax = new sack();
var date1=new Array;
var ctlObj;
var startDateElmtId;
var endDateElmtId;
/*
//2007-05-19 sunhj ������ʱ�����ε����AJAXȡ������ʱ��ĳ���
//��Ϊÿ��ҳ��load��ʱ�򶼻�ʹ��һ�����ajax����Է������������нϴ�ѹ��
dateAjax.setVar("method", "getServerDate");
dateAjax.requestFile = "indexAction.do";
dateAjax.method = "GET";
dateAjax.onCompletion =setServerToday;
dateAjax.runAJAX();

function setServerToday(){
	var returnXml = dateAjax.responseXML;
	var xmlObj = returnXml.getElementsByTagName("xml")[0];
	var flag = returnXml.getElementsByTagName("flag");
	if(eval(flag)){
		serverToday = returnXml.getElementsByTagName("date")[0].firstChild.nodeValue;
	}else{ //070227 sunhj �������������ʱ�����⣬���ڵ���ʱ����������Ƿ���һ���ͻ���ʱ��
		serverToday = new Date();
	}
}*/

function showCalendar(sImg,bOpenBound,sFld1,disableFutureDate){
	//�����ֱ�Ϊ���������ͼƬ���Ƿ��ȥ�������Ƿ��ѡ����Ҫ���ص�����򣬽����������Ƿ��ѡ
	//disableFutureDate����Ļ�Ĭ��Ϊfalse������
	var fld1,fld2;
	var cf=document.getElementById("CalFrame");
	var wcf=window.frames.CalFrame;
	var oImg=document.getElementById(sImg);
	if(!oImg){
		alert("���ƶ��󲻴��ڣ�");
		return;
	}
	if(!sFld1){
		alert("����ؼ�δָ����");
		return;
	}
	fld1=document.getElementById(sFld1);
	if(!fld1){
		alert("����ؼ������ڣ�");
		return;
	}
	if(fld1.tagName!="INPUT"||fld1.type!="text"){
		alert("����ؼ����ʹ���");
		return;
	}
	if(cf.style.display=="block"){
		cf.style.display="none";
		return;
	}
	var eT=0,eL=0,p=oImg;
	var sT=document.body.scrollTop,sL=document.body.scrollLeft;
	var eH=oImg.height,eW=oImg.width;
	while(p&&p.tagName!="BODY"){
		eT+=p.offsetTop;
		eL+=p.offsetLeft;
		p=p.offsetParent;
	}
	cf.style.top=(document.body.clientHeight-(eT-sT)-eH>=cf.height)?eT+eH:eT-cf.height;
	cf.style.left=(document.body.clientWidth-(eL-sL)>=cf.width)?eL:eL+eW-cf.width;
	cf.style.display="block";
	
	wcf.openbound=bOpenBound;
	wcf.fld1=fld1;
	wcf.fld2=fld2;
	wcf.disableFutureDate=!disableFutureDate;
	//wcf.callback=sCallback;
	wcf.setCurrentDate();
	wcf.initCalendar();
}

//add by hao 080103 ָ����������Ϊ��ǰ���� ������ year,month,day
function showSpeCalendar(sImg,bOpenBound,sFld1,disableFutureDate,year,month,day){
	//�����ֱ�Ϊ���������ͼƬ���Ƿ��ȥ�������Ƿ��ѡ����Ҫ���ص�����򣬽����������Ƿ��ѡ
	//disableFutureDate����Ļ�Ĭ��Ϊfalse������
	var fld1,fld2;
	var cf=document.getElementById("CalFrame");
	var wcf=window.frames.CalFrame;
	var oImg=document.getElementById(sImg);
	if(!oImg){
		alert("���ƶ��󲻴��ڣ�");
		return;
	}
	if(!sFld1){
		alert("����ؼ�δָ����");
		return;
	}
	fld1=document.getElementById(sFld1);
	if(!fld1){
		alert("����ؼ������ڣ�");
		return;
	}
	if(fld1.tagName!="INPUT"||fld1.type!="text"){
		alert("����ؼ����ʹ���");
		return;
	}
	if(cf.style.display=="block"){
		cf.style.display="none";
		return;
	}
	var eT=0,eL=0,p=oImg;
	var sT=document.body.scrollTop,sL=document.body.scrollLeft;
	var eH=oImg.height,eW=oImg.width;
	while(p&&p.tagName!="BODY"){
		eT+=p.offsetTop;
		eL+=p.offsetLeft;
		p=p.offsetParent;
	}
	cf.style.top=(document.body.clientHeight-(eT-sT)-eH>=cf.height)?eT+eH:eT-cf.height;
	cf.style.left=(document.body.clientWidth-(eL-sL)>=cf.width)?eL:eL+eW-cf.width;
	cf.style.display="block";
	
	wcf.openbound=bOpenBound;
	wcf.fld1=fld1;
	wcf.fld2=fld2;
	wcf.disableFutureDate=!disableFutureDate;
	//wcf.callback=sCallback;
	wcf.setSpeDate(year,month,day);
	wcf.initCalendar();
}

function showCalendarLimit(sImg,bOpenBound,sFld1,limitDate){
	//�����ֱ�Ϊ���������ͼƬ���Ƿ��ȥ�������Ƿ��ѡ����Ҫ���ص�����򣬽����������Ƿ��ѡ
	//disableFutureDate����Ļ�Ĭ��Ϊfalse������
	var fld1,fld2;
	var cf=document.getElementById("CalFrame");
	var wcf=window.frames.CalFrame;
	var oImg=document.getElementById(sImg);
	if(!oImg){
		alert("���ƶ��󲻴��ڣ�");
		return;
	}
	if(!sFld1){
		alert("����ؼ�δָ����");
		return;
	}
	fld1=document.getElementById(sFld1);
	if(!fld1){
		alert("����ؼ������ڣ�");
		return;
	}
	if(fld1.tagName!="INPUT"||fld1.type!="text"){
		alert("����ؼ����ʹ���");
		return;
	}
	if(cf.style.display=="block"){
		cf.style.display="none";
		return;
	}
	
	var eT=0,eL=0,p=oImg;
	var sT=document.body.scrollTop,sL=document.body.scrollLeft;
	var eH=oImg.height,eW=oImg.width;
	while(p&&p.tagName!="BODY"){
		eT+=p.offsetTop;
		eL+=p.offsetLeft;
		p=p.offsetParent;
	}
	cf.style.top=(document.body.clientHeight-(eT-sT)-eH>=cf.height)?eT+eH:eT-cf.height;
	cf.style.left=(document.body.clientWidth-(eL-sL)>=cf.width)?eL:eL+eW-cf.width;
	cf.style.display="block";
	
	wcf.openbound=bOpenBound;
	wcf.fld1=fld1;
	wcf.fld2=fld2;
	wcf.limitDate=!limitDate;
	//wcf.callback=sCallback;
	wcf.setCurrentDate();
	wcf.initCalendar();
}
function hideCalendar(){
	var cf=document.getElementById("CalFrame");
	cf.style.display="none";
}

/*
ǰ�����������ԭ�� 07-02-12 sunhj
���ݿ����е�������ʱ���룬�е�����û��ʱ���롣
1���������ʱ����Ĳ�ѯ��ʱ��Hibernate�������ݿ���Date���͵������ǲ���ʱ��������ѯ�ģ�param.setHbType(Hibernate.DATE)��������������ݿ��е�������ʱ�����ǲ鲻�����ġ����磺���ݿ��е�ֵΪ2007-02-12 12:30:59������Ĳ���Ϊ2007-02-12 00:00:00��2007-02-12 23:59:59
2���������2007-02-12 00:00:00��2007-02-13 00:00:00����querybean������<=������2007-02-13����ʱ��������ݴ�����
*/
function setDate(){	
	var date = serverToday;
	date1=date.split("-");			
	var d=new Date(date1[0],date1[1]-1,date1[2]);
	var year=d.getYear();
	var month=d.getMonth();
	month = month+1;
	var day=d.getDate();
	var scope = ctlObj.value;
	if (scope == 0) {// current day
		var tomorrow = new Date(year,month-1,day+1);
		var tomorrowYear  = tomorrow.getYear();
		var tomorrowMonth = tomorrow.getMonth()+1;
		var tomorrowDay   = tomorrow.getDate();
		//document.getElementById(startDateElmtId).value=year+"-"+month+"-"+day+" 00:00:00";
		document.getElementById(startDateElmtId).value=year+"-"+month+"-"+day;
		//alert(document.getElementById(startDateElmtId).value);
		//document.getElementById(endDateElmtId).value=tomorrowYear+"-"+tomorrowMonth+"-"+tomorrowDay+" 00:00:00";
		//document.getElementById(endDateElmtId).value=year+"-"+month+"-"+day+" 23:59:59";  // �ں�̨������endDate������Ҫ��ǰ̨������
		document.getElementById(endDateElmtId).value=year+"-"+month+"-"+day;
		//alert(document.getElementById(endDateElmtId).value);
		changeDateAreaVisible(ctlObj,false);
	} else if (scope == 1) {// current month
		var nextMonthThisDay = new Date(year,month,day);
		var nextMonthYear  = nextMonthThisDay.getYear();
		var nextMonthMonth = nextMonthThisDay.getMonth()+1;

		//document.getElementById(startDateElmtId).value=year+"-"+month+"-01"+" 00:00:00";
		document.getElementById(startDateElmtId).value=year+"-"+month+"-01";
		var day4=31;
		if(month=='1'||month=='3'||month=='5'||month=='7'||month=='8'||month=='10'||month=='12'){
		day4 = 31;
		}else if(month=='4'||month=='6'||month=='9'||month=='11'){
		day4=30;
		}else if(year%4==0 && year%100>0 && year%400==0){
		day4=29;
		}else if(year%4!=0){
		day4=28;
		}
		//document.getElementById(endDateElmtId).value=year+"-"+month+"-"+day4+" 23:59:59";
		document.getElementById(endDateElmtId).value=year+"-"+month+"-"+day4;
		//document.getElementById(endDateElmtId).value=nextMonthYear+"-"+nextMonthMonth+"-01 00:00:00";
		changeDateAreaVisible(ctlObj,false);
	} else if (scope == 2) {// current year
		document.getElementById(startDateElmtId).value=year+"-01-01";
		document.getElementById(endDateElmtId).value=year+"-12-31";
		//document.getElementById(startDateElmtId).value=year+"-01-01 00:00:00";
		//document.getElementById(endDateElmtId).value=year+"-12-31 23:59:59";
		//document.getElementById(endDateElmtId).value=(year+1)+"-01-01 00:00:00";
		changeDateAreaVisible(ctlObj,false);
	}else if(scope==3){//date range
		if(document.getElementById(startDateElmtId).value == null || document.getElementById(startDateElmtId).value==""){
			document.getElementById(startDateElmtId).value="";
		}
		if(document.getElementById(endDateElmtId).value == null || document.getElementById(endDateElmtId).value==""){
			document.getElementById(endDateElmtId).value="";
		}
		changeDateAreaVisible(ctlObj,true);
	}else if(scope==4){//all
		document.getElementById(startDateElmtId).value="";
		document.getElementById(endDateElmtId).value="";
		changeDateAreaVisible(ctlObj,false);
	}		
}

/*
 *
 */
function setDate(){	
	var date = serverToday;
	date1=date.split("-");			
	var d=new Date(date1[0],date1[1]-1,date1[2]);
	var year=d.getYear();
	var month=d.getMonth();
	month = month+1;
	var day=d.getDate();
	var scope = ctlObj.value;
	if (scope == 0) {// current day
		var tomorrow = new Date(year,month-1,day+1);
		var tomorrowYear  = tomorrow.getYear();
		var tomorrowMonth = tomorrow.getMonth()+1;
		var tomorrowDay   = tomorrow.getDate();
		//document.getElementById(startDateElmtId).value=year+"-"+month+"-"+day+" 00:00:00";
		document.getElementById(startDateElmtId).value=year+"-"+month+"-"+day;
		//alert(document.getElementById(startDateElmtId).value);
		//document.getElementById(endDateElmtId).value=tomorrowYear+"-"+tomorrowMonth+"-"+tomorrowDay+" 00:00:00";
		//document.getElementById(endDateElmtId).value=year+"-"+month+"-"+day+" 23:59:59";  // �ں�̨������endDate������Ҫ��ǰ̨������
		document.getElementById(endDateElmtId).value=year+"-"+month+"-"+day;
		//alert(document.getElementById(endDateElmtId).value);
		changeDateAreaVisible(ctlObj,false);
	} else if (scope == 1) {// current month
		var nextMonthThisDay = new Date(year,month,day);
		var nextMonthYear  = nextMonthThisDay.getYear();
		var nextMonthMonth = nextMonthThisDay.getMonth()+1;

		//document.getElementById(startDateElmtId).value=year+"-"+month+"-01"+" 00:00:00";
		document.getElementById(startDateElmtId).value=year+"-"+month+"-01";
		var day4=31;
		if(month=='1'||month=='3'||month=='5'||month=='7'||month=='8'||month=='10'||month=='12'){
		day4 = 31;
		}else if(month=='4'||month=='6'||month=='9'||month=='11'){
		day4=30;
		}else if(year%4==0 && year%100>0 && year%400==0){
		day4=29;
		}else if(year%4!=0){
		day4=28;
		}
		//document.getElementById(endDateElmtId).value=year+"-"+month+"-"+day4+" 23:59:59";
		document.getElementById(endDateElmtId).value=year+"-"+month+"-"+day4;
		//document.getElementById(endDateElmtId).value=nextMonthYear+"-"+nextMonthMonth+"-01 00:00:00";
		changeDateAreaVisible(ctlObj,false);
	} else if (scope == 2) {// current year
		document.getElementById(startDateElmtId).value=year+"-01-01";
		document.getElementById(endDateElmtId).value=year+"-12-31";
		//document.getElementById(startDateElmtId).value=year+"-01-01 00:00:00";
		//document.getElementById(endDateElmtId).value=year+"-12-31 23:59:59";
		//document.getElementById(endDateElmtId).value=(year+1)+"-01-01 00:00:00";
		changeDateAreaVisible(ctlObj,false);
	}else if(scope==3){//date range
		if(document.getElementById(startDateElmtId).value == null || document.getElementById(startDateElmtId).value==""){
			document.getElementById(startDateElmtId).value="";
		}
		if(document.getElementById(endDateElmtId).value == null || document.getElementById(endDateElmtId).value==""){
			document.getElementById(endDateElmtId).value="";
		}
		changeDateAreaVisible(ctlObj,true);
	}else if(scope==4){//all
		document.getElementById(startDateElmtId).value="";
		document.getElementById(endDateElmtId).value="";
		changeDateAreaVisible(ctlObj,false);
	}		
}
function changeDateDisplay(ctlObj1,startDateElmtId1,endDateElmtId1){
	 ctlObj=ctlObj1;
	 startDateElmtId=startDateElmtId1;
	 endDateElmtId=endDateElmtId1;
	 setDate();
}
function changeDateAreaVisible(ctlObj,display){
	var objTRElmt=getParentByTagName(ctlObj,"TR");
	if(display == true){
		getChildByTagNameTimes(objTRElmt,"TD",3).style.display = "block";
		getChildByTagNameTimes(objTRElmt,"TD",4).style.display = "block";
		getChildByTagNameTimes(objTRElmt,"TD",5).style.display = "block";
		getChildByTagNameTimes(objTRElmt,"TD",6).style.display = "block";
	}else{
		getChildByTagNameTimes(objTRElmt,"TD",3).style.display = "none";
		getChildByTagNameTimes(objTRElmt,"TD",4).style.display = "none";
		getChildByTagNameTimes(objTRElmt,"TD",5).style.display = "none";
		getChildByTagNameTimes(objTRElmt,"TD",6).style.display = "none";
	}
}
/*
sunhj 070108
���ڵ����������ڷ�Χ����ʼ��ֹ������TDĬ��Ϊdisplay:none
�����loadҳ���ʱ�����Ҫ��ʾ���������������Ҫ��body.onload��showQueryDateTR����
*/
function showQueryDateTR(scopeName,scopeValue){
	var scopeObj = eval("document.forms[0]."+scopeName);
	//changeDateDisplay(scopeObj,'startDate','endDate');
	changeDateDisplay(scopeObj,getStartDateInputName(scopeObj),getEndDateInputName(scopeObj));
	if(scopeObj.value == scopeValue){
		changeDateAreaVisible(scopeObj,true);
	}else{
		changeDateAreaVisible(scopeObj,false);
	}
}

/*
sunhj 070108
�������������Ϊ��ȡ��startDate��input��name
������Ϊ���������˵�startDate��input��name=startDate������showQueryDateTR������ǰһ�汾д����startDate���µ�
��Ҫע��˷���ȡstartDate��input��nameʹ�õ������input�����ڵ�TD��������Ҫҳ���ϱ�֤�������и�ʽ���Ű�
����Ĳ����������е�������name
*/
function getStartDateInputName(scopeObj){
	var objTRElmt=getParentByTagName(scopeObj,"TR");
	var startDateTDObj = getChildByTagNameTimes(objTRElmt,"TD",4);
	var startDateInputObj = getChildByTagNameTimes(startDateTDObj,"INPUT",1);
	return startDateInputObj.name;
}
/*
sunhj 070108
�������������Ϊ��ȡ��endDate��input��name
������Ϊ���������˵�endDate��input��name=endDate������showQueryDateTR������ǰһ�汾д����startDate���µ�
��Ҫע��˷���ȡendDate��input��nameʹ�õ������input�����ڵ�TD��������Ҫҳ���ϱ�֤�������и�ʽ���Ű�
����Ĳ����������е�������name
*/
function getEndDateInputName(scopeObj){
	var objTRElmt=getParentByTagName(scopeObj,"TR");
	var endDateTDObj = getChildByTagNameTimes(objTRElmt,"TD",6);
	var endDateInputObj = getChildByTagNameTimes(endDateTDObj,"INPUT",1);
	return endDateInputObj.name;
}

//AJAX
function sack(file) {
	this.xmlhttp = null;

	this.resetData = function() {
		this.method = "POST";
  		this.queryStringSeparator = "?";
		this.argumentSeparator = "&";
		this.URLString = "";
		this.encodeURIString = true;
  		this.execute = false;
  		this.element = null;
		this.elementObj = null;
		this.requestFile = file;
		this.vars = new Object();
		this.responseStatus = new Array(2);
  	};

	this.resetFunctions = function() {
  		this.onLoading = function() { };
  		this.onLoaded = function() { };
  		this.onInteractive = function() { };
  		this.onCompletion = function() { };
  		this.onError = function() { };
		this.onFail = function() { };
	};

	this.reset = function() {
		this.resetFunctions();
		this.resetData();
	};

	this.createAJAX = function() {
		try {
			this.xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e1) {
			try {
				this.xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e2) {
				this.xmlhttp = null;
			}
		}

		if (! this.xmlhttp) {
			if (typeof XMLHttpRequest != "undefined") {
				this.xmlhttp = new XMLHttpRequest();
			} else {
				this.failed = true;
			}
		}
	};

	this.setVar = function(name, value){
		this.vars[name] = Array(value, false);
	};

	this.encVar = function(name, value, returnvars) {
		if (true == returnvars) {
			return Array(encodeURIComponent(name), encodeURIComponent(value));
		} else {
			this.vars[encodeURIComponent(name)] = Array(encodeURIComponent(value), true);
		}
	}

	this.processURLString = function(string, encode) {
		encoded = encodeURIComponent(this.argumentSeparator);
		regexp = new RegExp(this.argumentSeparator + "|" + encoded);
		varArray = string.split(regexp);
		for (i = 0; i < varArray.length; i++){
			urlVars = varArray[i].split("=");
			if (true == encode){
				this.encVar(urlVars[0], urlVars[1]);
			} else {
				this.setVar(urlVars[0], urlVars[1]);
			}
		}
	}

	this.createURLString = function(urlstring) {
		if (this.encodeURIString && this.URLString.length) {
			this.processURLString(this.URLString, true);
		}

		if (urlstring) {
			if (this.URLString.length) {
				this.URLString += this.argumentSeparator + urlstring;
			} else {
				this.URLString = urlstring;
			}
		}

		// prevents caching of URLString
		this.setVar("rndval", new Date().getTime());

		urlstringtemp = new Array();
		for (key in this.vars) {
			if (false == this.vars[key][1] && true == this.encodeURIString) {
				encoded = this.encVar(key, this.vars[key][0], true);
				delete this.vars[key];
				this.vars[encoded[0]] = Array(encoded[1], true);
				key = encoded[0];
			}

			urlstringtemp[urlstringtemp.length] = key + "=" + this.vars[key][0];
		}
		if (urlstring){
			this.URLString += this.argumentSeparator + urlstringtemp.join(this.argumentSeparator);
		} else {
			this.URLString += urlstringtemp.join(this.argumentSeparator);
		}
	}

	this.runResponse = function() {
		eval(this.response);
	}

	this.runAJAX = function(urlstring) {
		if (this.failed) {
			this.onFail();
		} else {
			this.createURLString(urlstring);
			if (this.element) {
				this.elementObj = document.getElementById(this.element);
			}
			if (this.xmlhttp) {
				var self = this;
				if (this.method == "GET") {
					totalurlstring = this.requestFile + this.queryStringSeparator + this.URLString;
					this.xmlhttp.open(this.method, totalurlstring, true);
				}else if(this.method == "FILE") {
					this.xmlhttp.open(this.method, this.requestFile, true);
					try {
						this.xmlhttp.setRequestHeader("Content-Type", "multipart/form-data")
					} catch (e) { }
				} else {
					this.xmlhttp.open(this.method, this.requestFile, true);
					try {
						this.xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
					} catch (e) { }
				}

				this.xmlhttp.onreadystatechange = function() {
					switch (self.xmlhttp.readyState) {
						case 1:
							self.onLoading();
							break;
						case 2:
							self.onLoaded();
							break;
						case 3:
							self.onInteractive();
							break;
						case 4:
							self.response = self.xmlhttp.responseText;
							self.responseXML = self.xmlhttp.responseXML;
							self.responseStatus[0] = self.xmlhttp.status;
							self.responseStatus[1] = self.xmlhttp.statusText;

							if (self.execute) {
								self.runResponse();
							}

							if (self.elementObj) {
								elemNodeName = self.elementObj.nodeName;
								elemNodeName.toLowerCase();
								if (elemNodeName == "input"
								|| elemNodeName == "select"
								|| elemNodeName == "option"
								|| elemNodeName == "textarea") {
									self.elementObj.value = self.response;
								} else {
									self.elementObj.innerHTML = self.response;
								}
							}
							if (self.responseStatus[0] == "200") {
								self.onCompletion();
							} else {
								self.onError();
							}

							self.URLString = "";
							break;
					}
				};

				this.xmlhttp.send(this.URLString);
			}
		}
	};

	this.reset();
	this.createAJAX();
}
