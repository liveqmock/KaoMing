
/***
 * 输入框键盘输入校验函数.
 * 用于在输入框输入时校验键盘按键. 
 * 如只能输入数字则敲击字母或其他字符的按键均会被忽略.
 * 一般的如果没有特殊说明,函数的调用都应是在onkeydown事件被触发.
 * 拿<strong>出生日期</strong>输入框为例:
 * <p><blockquote><pre>
 * &lt;script language='javascript' src='inputValidation.js'&gt;&lt/script&gt;
 * ...
 * //出生日期 输入格式为 YYYY-MM-DD
 * &lt;input type='text' name='birthday' size='12' maxlength='10'
 * <strong>onkeydown='javascript:input_date("yyyy-mm-dd");'</strong>&gt;
 * </pre></blockquote></p>
 * 附:标准104键盘按键代码图(对应event.keyCode)
 * <p><blockquote><pre>
 * backspace = 8
 * tabspace = 9
 * return = 13
 * shift(both) = 16
 * control(both) = 17
 * alternative(both) = 18
 * break = 19
 * caps lock = 20
 * escape = 27
 * space = 32
 * page up = 33
 * page down = 34
 * end = 35
 * home = 36
 * [方向键]
 * left = 37
 * up = 38
 * right = 39
 * down = 40
 * insert = 45
 * delete = 46
 * 0~9[)~!] = 48~57
 * A~Z = 65~90
 * a~z = 97~122
 * windows key(left) = 91
 * windows key(right) = 92
 * menu key = 93
 * [小键盘]
 * (num lock 打开)
 * 0~9 = 96~105
 * * = 106
 * + = 107
 * - = 109
 * . = 110
 * / = 111
 * num lock = 144
 * (num lock 关闭)
 * 5 = 12
 * page up = 33
 * page down = 34
 * end = 35
 * home = 36
 * left = 37
 * up = 38
 * right = 39
 * down = 40
 * insert = 45
 * delete = 46
 * [功能键]
 * F1~F12 = 112~123
 * scroll lock = 145
 * [符号键]
 * ;[:] = 186
 * +[=] = 187
 * ,[<] = 188
 * -[_] = 189
 * .[>] = 190
 * /[?] = 191
 * `[~] = 192
 * [[{] = 219
 * \[|] = 220
 * ][}] = 221
 * '["] = 222
 * </pre></blockquote></p>
 * @author 
 * @version 1.0.2003.0815
 * 其他小组可以根据不同需要扩展该文件.
 **/
//--------------------------------------------------------------------------------

/**
 * 键 Backspace 退格键
 */
var kBackspace = 8;

/**
 * 键 Tabspace 制表符
 */
var kTabspace = 9;

/**
 * 键 Return 回车键
 */
var kReturn = 13;

/**
 * 键 Shift 上档键
 */
var kShift = 16;

/**
 * 键 Control 控制键
 */
var kCtrl = 17;

/**
 * 键 Alternative 附功能键
 */
var kAlt = 18;

/**
 * 键 Break 暂停键
 */
var kBreak = 19;

/**
 * 键 Caps Lock 大写锁定键
 */
var kCapsLock = 20;

/**
 * 键 Escape 退出键
 */
var kEscape = 27;

/**
 * 键 Space 空格键
 */
var kSpace = 32;

/**
 * 键 Page Up 向上翻页键
 */
var kPageUp = 33;

/**
 * 键 Page Down 向下翻页键
 */
var kPageDown = 34;

/**
 * 键 End 行末键
 */
var kEnd = 35;

/**
 * 键 Home 行首键
 */
var kHome = 36;

/**
 * 键 Left 向左键
 */
var kLeft = 37;

/**
 * 键 Up 向上键
 */
var kUp = 38;

/**
 * 键 Right 向右键
 */
var kRight = 39;

/**
 * 键 Down 向下键
 */
var kDown = 40;

/**
 * 键 Insert 插入键
 */
var kInsert = 45;

/**
 * 键 Delete 删除键
 */
var kDelete = 46;

/**
 * 键 0[)]
 */
var k0 = 48;

/**
 * 键 1[!]
 */
var k1 = 49;

/**
 * 键 2[@]
 */
var k2 = 50;

/**
 * 键 3[#]
 */
var k3 = 51;

/**
 * 键 4[$]
 */
var k4 = 52;

/**
 * 键 5[%]
 */
var k5 = 53;

/**
 * 键 6[^]
 */
var k6 = 54;

/**
 * 键 7[&]
 */
var k7 = 55;

/**
 * 键 8[*]
 */
var k8 = 56;

/**
 * 键 9[(]
 */
var k9 = 57;

/**
 * 键 A
 */
var kA = 65;

/**
 * 键 B
 */
var kB = 66;

/**
 * 键 C
 */
var kC = 67;

/**
 * 键 D
 */
var kD = 68;

/**
 * 键 E
 */
var kE = 69;

/**
 * 键 F
 */
var kF = 70;

/**
 * 键 G
 */
var kG = 71;

/**
 * 键 H
 */
var kH = 72;

/**
 * 键 I
 */
var kI = 73;

/**
 * 键 J
 */
var kJ = 74;

/**
 * 键 K
 */
var kK = 75;

/**
 * 键 L
 */
var kL = 76;

/**
 * 键 M
 */
var kM = 77;

/**
 * 键 N
 */
var kN = 78;

/**
 * 键 O
 */
var kO = 79;

/**
 * 键 P
 */
var kP = 80;

/**
 * 键 Q
 */
var kQ = 81;

/**
 * 键 R
 */
var kR = 82;

/**
 * 键 S
 */
var kS = 83;

/**
 * 键 T
 */
var kT = 84;

/**
 * 键 U
 */
var kU = 85;

/**
 * 键 V
 */
var kV = 86;

/**
 * 键 W
 */
var kW = 87;

/**
 * 键 X
 */
var kX = 88;

/**
 * 键 Y
 */
var kY = 89;

/**
 * 键 Z
 */
var kZ = 90;

/**
 * 键 Windows (左)
 */
var kWinLeft = 91;

/**
 * 键 Windows (右)
 */
var kWinRight = 92;

/**
 * 键 Menu
 */
var kMenu = 93;

/**
 * 数字键 0 小键盘(Num Lock 打开)
 */
var kNumPad0 = 96;

/**
 * 数字键 1 小键盘(Num Lock 打开)
 */
var kNumPad1 = 97;

/**
 * 数字键 2 小键盘(Num Lock 打开)
 */
var kNumPad2 = 98;

/**
 * 数字键 3 小键盘(Num Lock 打开)
 */
var kNumPad3 = 99;

/**
 * 数字键 4 小键盘(Num Lock 打开)
 */
var kNumPad4 = 100;

/**
 * 数字键 5 小键盘(Num Lock 打开)
 */
var kNumPad5 = 101;

/**
 * 数字键 6 小键盘(Num Lock 打开)
 */
var kNumPad6 = 102;

/**
 * 数字键 7 小键盘(Num Lock 打开)
 */
var kNumPad7 = 103;

/**
 * 数字键 8 小键盘(Num Lock 打开)
 */
var kNumPad8 = 104;

/**
 * 数字键 9 小键盘(Num Lock 打开)
 */
var kNumPad9 = 105;

/**
 * 键 Multipy 小键盘 *
 */
var kMultipy = 106;

/**
 * 键 Plus 小键盘 +
 */
var kPlus = 107;

/**
 * 键 Minus 小键盘 -
 */
var kMinus = 109;

/**
 * 键 Dot 小键盘 .
 */
var kDot = 110;

/**
 * 键 Divide 小键盘 /
 */
var kDivide = 111;

/**
 * 键 Num Lock 小键盘
 */
var kNumLock = 144;

/**
 * 键 5 小键盘 (Num Lock 关闭)
 */
var kNumPad5 = 12;

/**
 * 功能键 F1
 */
var kF1 = 112; 

/**
 * 功能键 F2
 */
var kF2 = 113; 

/**
 * 功能键 F3
 */
var kF3 = 114; 

/**
 * 功能键 F4
 */
var kF4 = 115; 

/**
 * 功能键 F5
 */
var kF5 = 116; 

/**
 * 功能键 F6
 */
var kF6 = 117; 

/**
 * 功能键 F7
 */
var kF7 = 118; 

/**
 * 功能键 F8
 */
var kF8 = 119; 

/**
 * 功能键 F9
 */
var kF9 = 120; 

/**
 * 功能键 F10
 */
var kF10 = 121; 

/**
 * 功能键 F11
 */
var kF11 = 122; 

/**
 * 功能键 F12
 */
var kF12 = 123; 

/**
 * 键 Scroll Lock
 */
var kScrollLock = 145;

/**
 * 键 ;[:]
 */
var kSemicolon = 186;

/**
 * 键 =[+]
 */
var kEqual = 187;

/**
 * 键 ,[<]
 */
var kComma = 188;

/**
 * 键 -[_]
 */
var kDash= 189;

/**
 * 键 .[>]
 */
var kFullStop = 190;

/**
 * 键 /[?]
 */
var kSlash = 191;

/**
 * 键 `[~]
 */
var kBackQuote = 192;

/**
 * 键 [[{]
 */
var kSquareBraketLeft = 219;

/**
 * 键 \[|]
 */
var kBacklash = 220;

/**
 * 键 ][}]
 */
var kSquareBraketRight = 221;

/**
 * 键 '["]
 */
var kQuote = 222;

/**
 * 控制键盘按键不能输入字符. 只能按没有字符的键.
 * 如 退格键, 回车键, 方向键等.
 * @author 
 * @version 1.0.2003.0815
 */
function f_nochar() {
	var key = event.keyCode;
	//不接收数字
	if (key >= k0 && key <= k9) {
		event.returnValue = false;
	}
	//不接收字符和小键盘数字
	if (key == kSpace || key >= kA && key <=kZ || key >= 96 && key <= kDivide || 
		key >=kSemicolon && key <=kQuote) {
		event.returnValue = false;
	}	
}

/**
 * 判断键盘事件,只能接收数字按键的函数.
 * 如果是数字键以外的按键都会被忽略.
 * 在input 控件onkeydown事件时触发.
 * @author 
 * @version 1.0.2003.0815
 */
function f_onlynumber() {
	var src = event.srcElement;
	var key = event.keyCode;

	//不能输入字符
	if (key == kSpace || key >= kA && key <=kZ || 
		key >= kMultipy && key <= kDivide || key >=kSemicolon && key <=kQuote) {
		//alert(false);
		event.returnValue = false;
	}
	//不能在按住shift键同时按数字键
	if ((key >= k0 && key <= k9) && event.shiftKey) {
	//alert(false);
		event.returnValue = false;
	}
}

/**
 * 判断键盘事件,只能接收数字按键和小数点的函数.
 * 如果是数字键以外的按键都会被忽略.
 * 在input 控件onkeydown事件时触发.
 */
function f_onlymoney(){	
  	var src = event.srcElement;
	var key = event.keyCode;

	if (key!=kDot&&key!=kFullStop && (key == kSpace || key >= kA && key <=kZ || 
		key >= kMultipy && key <= kDivide || key >=kSemicolon && key <=kQuote)) {
		event.returnValue = false;
	}
	if ((key >= k0 && key <= k9) && event.shiftKey) {
		event.returnValue = false;
	}
}


/**
 * 判断键盘事件,只能接收字母、数字、下划线和退格键.
 */
function f_pwck(){
	var src = event.srcElement;
	var key = event.keyCode;

	//不能输入字符
	if (key<8||(key>9&&key<46)||(key>46&&key<48)||(key>57&&key<65)||(key>90&&key<96)||(key>122)){
		event.returnValue = false;
	}
	//下划线
	if ( event.shiftKey && key==189) {
		event.returnValue = true;
	}else if(event.shiftKey && key!=189){
		event.returnValue = false;
	}
	//ctrl+c,ctrl+v
	if ((key==67||key==86) && event.ctrlKey) {
		event.returnValue = false;
	}
	
}

/**
 * 控制日期输入框的输入方式.
 * 根据格式控制日期的输入. 
 * 调用方法: onkeydown='javascript:input_date([format]);'
 * 有效的格式有: yyyy-mm-dd, yyyy:mm:dd, yyyy/mm/dd, yyyy.mm.dd, yyyymmdd
 * 格式不区分大小写.
 * @param format 日期格式(可选)
 * @author 
 * @version 1.0.2003.0815
 */
function input_date() {
	var length = event.srcElement.value.length;
	var format;
	var key = event.keyCode;
	if (arguments[0] != null && arguments[0] == true) {
		format = arguments[0].toLowerCase();
	} else {
		format = "yyyy-mm-dd";
	}
	switch (format) {
	case "yyyy:mm:dd":
		//除了":"外, 不能输入其他字符
		if (key == kSpace || key >= kA && key <=kZ || 
			key >= kMultipy && key <= kDivide || 
			key > kSemicolon && key <=kQuote || 
			key == kSemicolon && !event.shiftKey) {
			event.returnValue = false;
		}
		//不能在按住shift键同时按数字键
		if ((key >= k0 && key <= k9) && event.shiftKey) {
			event.returnValue = false;
		}
		if (length > 9) {
			f_nochar();
		}
		break;
	case "yyyy/mm/dd":
		//除了"/"外, 不能输入其他字符
		if (key == kSpace || key >= kA && key <=kZ || 
			key >= kMultipy && key < kDivide || key >=kSemicolon && key < kSlash || 
			key > kSlash && key <=kQuote || key == kSlash && event.shiftKey || 
			key == kDivide && event.shiftKey) {
			event.returnValue = false;
		}
		//不能在按住shift键同时按数字键
		if ((key >= k0 && key <= k9) && event.shiftKey) {
			event.returnValue = false;
		}
		if (length > 9) {
			f_nochar();
		}
		break;
	case "yyyy.mm.dd":
		//除了"."外, 不能输入其他字符
		if (key == kSpace || key >= kA && key <=kZ || 
			key >= kMultipy && key < kDot || key == kDivide || 
			key >=kSemicolon && key <kFullStop || 
			key > kFullStop && key <=kQuote || 
			key == kFullStop && event.shiftKey || key == kDot && event.shiftKey) {
			event.returnValue = false;
		}
		//不能在按住shift键同时按数字键
		if ((key >= k0 && key <= k9) && event.shiftKey) {
			event.returnValue = false;
		}
		if (length > 10) {
			f_nochar();
		}
		break;
	case "yyyymmdd":
		if (length < 8) {
			f_onlynumber();
		} 
		if (length > 7) {
			f_nochar();
		}
		break;
	default://yyyy-mm-dd
		//除了"-"外, 不能输入其他字符
		if (key == kSpace || key >= kA && key <=kZ || 
			key >= kMultipy && key < kMinus || key > kMinus && key <= kDivide || 
			key >=kSemicolon && key <kDash || key > kDash && key <=kQuote || 
			key == kDash && event.shiftKey || key == kMinus && event.shiftKey) {
			event.returnValue = false;
		}
		//不能在按住shift键同时按数字键
		if ((key >= k0 && key <= k9) && event.shiftKey) {
			event.returnValue = false;
		}
		if (length > 9) {
			f_nochar();
		}
		break;
	}
}

/**
 * 检查身份证输入框接收的键盘按键是否在正确的按键内.
 * 身份证只能有15位(只有数字)或18位(最后一位可以为字符).
 * 在控件onkeydown事件时触发.
 * 注意要控制输入框的最大字符长度为18(maxlength=18).
 * @author 
 * @version 1.0.2003.0815
 */
function input_idno() {
	var length = event.srcElement.value.length;
	if (length < 17) {//第17位时可以输入字符
		f_onlynumber();
	}
	if (length == 18) {//增加对超过18位字符的判断, 相当于maxlength=18
		f_nochar();
	}
}

/**
 * 检查邮政编码输入框.
 * 中国邮政编码只有6位(默认).
 * 可以输入地区编码(与互联网国家后缀相同). 不同国家有不同的邮政编码规则.
 * 使用方法: onkeydown='javascript:input_postcode([country_code]);'
 * <p><blockquote><pre>
 * 可以使用的国家编码有:
 * CN = 中国
 * US = 美国
 * MY = 马来西亚
 * </pre></blockquote></p>
 * @param country 国家编码(可选)
 * @author 
 * @version 1.0.2003.0819
 */
function input_postcode() {
	var length = event.srcElement.value.length;
	var country = "CN";
	if (arguments[0] != null && arguments[0] == true) {
		country = arguments[0].toUpperCase();
	} else {
		country = "CN";
	}
	switch (country) {
	case "US","MY":
		if (length < 5) {
			f_onlynumber();
		}
		if (length = 5) {//邮政编码最多5位
			f_nochar();
		}
	break;
	default://"CN"
		if (length < 6) {
			f_onlynumber();
		}
		if (length = 6) {//邮政编码最多6位
			f_nochar();
		}
	break;
	}
}

/**
 * 校验整型输入框. 如 10, 123, 65535等.
 * @author 
 * @version 1.0.2003.0819
 */
function input_int() {
	f_onlynumber();
}

/**
 * 校验实数输入框. 如 10, 13.5, 0.228等.
 * @author 
 * @version 1.0.2003.0819
 */
function input_real() {
	//除了"."外, 不能输入其他字符
	if (key == kSpace || key >= kA && key <=kZ || key >= kMultipy && key < kDot || 
		key == kDivide || key >=kSemicolon && key <kFullStop || 
		key > kFullStop && key <=kQuote || key == kFullStop && event.shiftKey || 
		key == kDot && event.shiftKey) {
		event.returnValue = false;
	}
	//不能在按住shift键同时按数字键
	if ((key >= k0 && key <= k9) && event.shiftKey) {
		event.returnValue = false;
	}
}

/**
 * 校验科学计数法输入框. 如 10, 3e+2, 45E-12等.
 * @author 
 * @version 1.0.2003.0819
 */
function input_scientific() {
	//除了"e, +, -"外, 不能输入其他字符
	if (key == kSpace || key >= kA && key < kE || key > kE && key <=kZ || 
		key == kMultipy || key == 108 || key > kMinus && key <= kDivide || 
		key == kSemicolon || key == kComma || k > kDash && k <=kQuote || 
		key == kDash && event.shiftKey || !(key == kDash && event.shiftKey)) {
		event.returnValue = false;
	}
	//不能在按住shift键同时按数字键
	if ((key >= k0 && key <= k9) && event.shiftKey) {
		event.returnValue = false;
	}
}


/**
 *Description:取字符串的长度，支持中文！
 *@Author 张标
 *@version 1.0.2002.1115
 *@return intStringLength
 */
function getLength(strText)
{
	var intStringLength=0;

	if (isEmpty(strText)){
		return 0;
	}
	intStringLength=strText.length;
	for (var i=0;i<strText.length;i++)
	{
		if (strText.charCodeAt(i)>255)
		{
			intStringLength++;
		}
	}
	//alert(intStringLength);
	return intStringLength;
}

/**
 *Description:判断当前字符是否为中文！
 *@Author 张标
 *@version 1.0.2002.1115
 *@return true－是；false－否；
 */
function isChineseChar(strChar)
{	
	var intDoubleByteStart=19968;	//中文字符开始的位置19968！
	if (strChar.charCodeAt(0)>=intDoubleByteStart)
	{
		return true;
	}else{
		return false;
	}
}

function isChineseString(strChar)
{	
		var intDoubleByteStart=19968;	//中文字符开始的位置19968！
		var intPlace=0;
		var count=strChar.length;
		for(var i=0;i<count;i++){
	  		intPlace=strChar.charCodeAt(i);//strChar的第i个字符的位置。
	  		if (intPlace >= intDoubleByteStart) {
	    		return true;
	      	}
	    }
	    return false;
}

function isEmpty(str) {
	if ((str==null)||(str.length==0))
	{
		return(true);
	}
	else { return(false) };
}

/**
 * 刷新表单. 控制可编辑控件是否可编辑.
 * @param obj 表单对象
 * @param editable 是否可编辑
 */
function refreshForm(obj, editable) {
  var objs = obj.tags("input");
  for (var i = 0; i < objs.length; i++) {
    if (objs[i].type == "text") objs[i].readOnly = !editable;
  }
  objs = obj.tags("textarea");
  for (var i = 0; i < objs.length; i++) {
    objs[i].readOnly = !editable;
  }
}

/**
 * 检查表单可编辑对象的数据正确性.
 * 可编辑对象指input text, input passowrd, textarea, select等控件.
 * 数据正确性包括:
 * 编号	属性名	适用控件				描述		
 * 1. 	notnull input, textarea, select	不可空
 * 2. 	clength input, textarea			允许的最大值
 * 3. 	cmax	select(multiple)		允许选择的项的个数
 * 属性 clength 与 IE默认属性 maxlength 的不同:
 * 他们的主要区别在于对中文的处理上. clength 认为中文字符长度为2, 而maxlength认为中文字符长度为1.
 * clength对中文长度的计算方式与数据库相同.
 * 比如: <input type='text' name='a' value="123一二三">. 
 * 如果设置clength = 6, 则 a.length = 9 大于 clength; validate将返回false.
 * 如果设置maxlength = 6, 则 a.length = 6 等于 maxlength; validate将返回true.
 * 一般clength的值与该控件对应的数据库字段的最大值相同.
 */
function validate(obj) {
	var texts = obj.tags("input");
	if (validate_text(texts)) {
		texts = obj.tags("textarea");
		if (validate_text(texts)) {
			texts = obj.tags("select");
			if (validate_select(texts)) {
				return true;
			}
		}
	}
	return false;
}

function validate_select(objs) {
	var control_name = "";
	var length = -1;
	var obj = null;
	var index = -1;
	for (var i = 0; i < objs.length; i++) {
		control_name = "";
		obj = objs[i];
		if (obj.cname != undefined) {
			control_name = obj.cname;
		}
		if (obj.notnull != undefined) {
			index = obj.selectedIndex;
			if (index == -1 || (index >= 0 && obj.options[index].value == "")) {
				alert("选项" + control_name + "不能为空");
				obj.focus();
				return false;
			}
		}
		if (obj.cmax != undefined && obj.multiple) {
			length = 0;
			for (var j = 0; j < obj.options.length; j++)
				if (obj.options[j].selected) length++;
			if (length > obj.cmax) {
				alert(control_name + "选择的项过多. 允许: " + obj.cmax + 
				" 目前: " + length + ".");
				obj.focus();
				return false;
			}
		}
	}
	return true;
}

function validate_text(objs) {
	
	var control_name = "";
	var length = 0;
	var obj = null;
	for (var i = 0; i < objs.length; i++) {
		control_name = "";
		length = 0;
		obj = objs[i];
		if (obj.type == "text" ||obj.type == "password" || obj.type == "textarea") {
			if (obj.cname != undefined) {
				
				control_name = obj.cname;
			}
			if (obj.clength != undefined) {
				length = getLength(obj.value);
				if ((obj.notnull != undefined) && length == 0) {
					alert("字段" + control_name + "不能为空");
					obj.focus();
					return false;
				}
				if (length > obj.clength) {
					
					alert(control_name + "值太长. 允许: " + obj.clength + 
					"字节 目前: " + length + "字节.\n中文字符占双字节.");
					obj.focus();
					return false;
				}
				//if(obj.isnumber != undefined){
					//if (obj>="0"&&theNum<="9")
					//alert(control_name+"只能为数字");
					//obj.focus();
					//return false;
				//}
			}
		}
	}
	return true;
}


/**
 * 判断键盘事件,只能接收字母、数字和退格键.
 */
function f_charAndNumber(){
	var src = event.srcElement;
	var key = event.keyCode;

	//不能输入字符
	if (key<8||(key>9&&key<46)||(key>46&&key<48)||(key>57&&key<65)||(key>90&&key<97)||(key>122)){
		event.returnValue = false;
	}
	
	//ctrl+c,ctrl+v
	if ((key==67||key==86) && event.ctrlKey) {
		event.returnValue = false;
	}
	
}


String.prototype.trim = function()
{
	return this.replace( /(^\s*)|(\s*$)/g, '' ) ;
}