
/***
 * ������������У�麯��.
 * ���������������ʱУ����̰���. 
 * ��ֻ�������������û���ĸ�������ַ��İ������ᱻ����.
 * һ������û������˵��,�����ĵ��ö�Ӧ����onkeydown�¼�������.
 * ��<strong>��������</strong>�����Ϊ��:
 * <p><blockquote><pre>
 * &lt;script language='javascript' src='inputValidation.js'&gt;&lt/script&gt;
 * ...
 * //�������� �����ʽΪ YYYY-MM-DD
 * &lt;input type='text' name='birthday' size='12' maxlength='10'
 * <strong>onkeydown='javascript:input_date("yyyy-mm-dd");'</strong>&gt;
 * </pre></blockquote></p>
 * ��:��׼104���̰�������ͼ(��Ӧevent.keyCode)
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
 * [�����]
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
 * [С����]
 * (num lock ��)
 * 0~9 = 96~105
 * * = 106
 * + = 107
 * - = 109
 * . = 110
 * / = 111
 * num lock = 144
 * (num lock �ر�)
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
 * [���ܼ�]
 * F1~F12 = 112~123
 * scroll lock = 145
 * [���ż�]
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
 * ����С����Ը��ݲ�ͬ��Ҫ��չ���ļ�.
 **/
//--------------------------------------------------------------------------------

/**
 * �� Backspace �˸��
 */
var kBackspace = 8;

/**
 * �� Tabspace �Ʊ��
 */
var kTabspace = 9;

/**
 * �� Return �س���
 */
var kReturn = 13;

/**
 * �� Shift �ϵ���
 */
var kShift = 16;

/**
 * �� Control ���Ƽ�
 */
var kCtrl = 17;

/**
 * �� Alternative �����ܼ�
 */
var kAlt = 18;

/**
 * �� Break ��ͣ��
 */
var kBreak = 19;

/**
 * �� Caps Lock ��д������
 */
var kCapsLock = 20;

/**
 * �� Escape �˳���
 */
var kEscape = 27;

/**
 * �� Space �ո��
 */
var kSpace = 32;

/**
 * �� Page Up ���Ϸ�ҳ��
 */
var kPageUp = 33;

/**
 * �� Page Down ���·�ҳ��
 */
var kPageDown = 34;

/**
 * �� End ��ĩ��
 */
var kEnd = 35;

/**
 * �� Home ���׼�
 */
var kHome = 36;

/**
 * �� Left �����
 */
var kLeft = 37;

/**
 * �� Up ���ϼ�
 */
var kUp = 38;

/**
 * �� Right ���Ҽ�
 */
var kRight = 39;

/**
 * �� Down ���¼�
 */
var kDown = 40;

/**
 * �� Insert �����
 */
var kInsert = 45;

/**
 * �� Delete ɾ����
 */
var kDelete = 46;

/**
 * �� 0[)]
 */
var k0 = 48;

/**
 * �� 1[!]
 */
var k1 = 49;

/**
 * �� 2[@]
 */
var k2 = 50;

/**
 * �� 3[#]
 */
var k3 = 51;

/**
 * �� 4[$]
 */
var k4 = 52;

/**
 * �� 5[%]
 */
var k5 = 53;

/**
 * �� 6[^]
 */
var k6 = 54;

/**
 * �� 7[&]
 */
var k7 = 55;

/**
 * �� 8[*]
 */
var k8 = 56;

/**
 * �� 9[(]
 */
var k9 = 57;

/**
 * �� A
 */
var kA = 65;

/**
 * �� B
 */
var kB = 66;

/**
 * �� C
 */
var kC = 67;

/**
 * �� D
 */
var kD = 68;

/**
 * �� E
 */
var kE = 69;

/**
 * �� F
 */
var kF = 70;

/**
 * �� G
 */
var kG = 71;

/**
 * �� H
 */
var kH = 72;

/**
 * �� I
 */
var kI = 73;

/**
 * �� J
 */
var kJ = 74;

/**
 * �� K
 */
var kK = 75;

/**
 * �� L
 */
var kL = 76;

/**
 * �� M
 */
var kM = 77;

/**
 * �� N
 */
var kN = 78;

/**
 * �� O
 */
var kO = 79;

/**
 * �� P
 */
var kP = 80;

/**
 * �� Q
 */
var kQ = 81;

/**
 * �� R
 */
var kR = 82;

/**
 * �� S
 */
var kS = 83;

/**
 * �� T
 */
var kT = 84;

/**
 * �� U
 */
var kU = 85;

/**
 * �� V
 */
var kV = 86;

/**
 * �� W
 */
var kW = 87;

/**
 * �� X
 */
var kX = 88;

/**
 * �� Y
 */
var kY = 89;

/**
 * �� Z
 */
var kZ = 90;

/**
 * �� Windows (��)
 */
var kWinLeft = 91;

/**
 * �� Windows (��)
 */
var kWinRight = 92;

/**
 * �� Menu
 */
var kMenu = 93;

/**
 * ���ּ� 0 С����(Num Lock ��)
 */
var kNumPad0 = 96;

/**
 * ���ּ� 1 С����(Num Lock ��)
 */
var kNumPad1 = 97;

/**
 * ���ּ� 2 С����(Num Lock ��)
 */
var kNumPad2 = 98;

/**
 * ���ּ� 3 С����(Num Lock ��)
 */
var kNumPad3 = 99;

/**
 * ���ּ� 4 С����(Num Lock ��)
 */
var kNumPad4 = 100;

/**
 * ���ּ� 5 С����(Num Lock ��)
 */
var kNumPad5 = 101;

/**
 * ���ּ� 6 С����(Num Lock ��)
 */
var kNumPad6 = 102;

/**
 * ���ּ� 7 С����(Num Lock ��)
 */
var kNumPad7 = 103;

/**
 * ���ּ� 8 С����(Num Lock ��)
 */
var kNumPad8 = 104;

/**
 * ���ּ� 9 С����(Num Lock ��)
 */
var kNumPad9 = 105;

/**
 * �� Multipy С���� *
 */
var kMultipy = 106;

/**
 * �� Plus С���� +
 */
var kPlus = 107;

/**
 * �� Minus С���� -
 */
var kMinus = 109;

/**
 * �� Dot С���� .
 */
var kDot = 110;

/**
 * �� Divide С���� /
 */
var kDivide = 111;

/**
 * �� Num Lock С����
 */
var kNumLock = 144;

/**
 * �� 5 С���� (Num Lock �ر�)
 */
var kNumPad5 = 12;

/**
 * ���ܼ� F1
 */
var kF1 = 112; 

/**
 * ���ܼ� F2
 */
var kF2 = 113; 

/**
 * ���ܼ� F3
 */
var kF3 = 114; 

/**
 * ���ܼ� F4
 */
var kF4 = 115; 

/**
 * ���ܼ� F5
 */
var kF5 = 116; 

/**
 * ���ܼ� F6
 */
var kF6 = 117; 

/**
 * ���ܼ� F7
 */
var kF7 = 118; 

/**
 * ���ܼ� F8
 */
var kF8 = 119; 

/**
 * ���ܼ� F9
 */
var kF9 = 120; 

/**
 * ���ܼ� F10
 */
var kF10 = 121; 

/**
 * ���ܼ� F11
 */
var kF11 = 122; 

/**
 * ���ܼ� F12
 */
var kF12 = 123; 

/**
 * �� Scroll Lock
 */
var kScrollLock = 145;

/**
 * �� ;[:]
 */
var kSemicolon = 186;

/**
 * �� =[+]
 */
var kEqual = 187;

/**
 * �� ,[<]
 */
var kComma = 188;

/**
 * �� -[_]
 */
var kDash= 189;

/**
 * �� .[>]
 */
var kFullStop = 190;

/**
 * �� /[?]
 */
var kSlash = 191;

/**
 * �� `[~]
 */
var kBackQuote = 192;

/**
 * �� [[{]
 */
var kSquareBraketLeft = 219;

/**
 * �� \[|]
 */
var kBacklash = 220;

/**
 * �� ][}]
 */
var kSquareBraketRight = 221;

/**
 * �� '["]
 */
var kQuote = 222;

/**
 * ���Ƽ��̰������������ַ�. ֻ�ܰ�û���ַ��ļ�.
 * �� �˸��, �س���, �������.
 * @author 
 * @version 1.0.2003.0815
 */
function f_nochar() {
	var key = event.keyCode;
	//����������
	if (key >= k0 && key <= k9) {
		event.returnValue = false;
	}
	//�������ַ���С��������
	if (key == kSpace || key >= kA && key <=kZ || key >= 96 && key <= kDivide || 
		key >=kSemicolon && key <=kQuote) {
		event.returnValue = false;
	}	
}

/**
 * �жϼ����¼�,ֻ�ܽ������ְ����ĺ���.
 * ��������ּ�����İ������ᱻ����.
 * ��input �ؼ�onkeydown�¼�ʱ����.
 * @author 
 * @version 1.0.2003.0815
 */
function f_onlynumber() {
	var src = event.srcElement;
	var key = event.keyCode;

	//���������ַ�
	if (key == kSpace || key >= kA && key <=kZ || 
		key >= kMultipy && key <= kDivide || key >=kSemicolon && key <=kQuote) {
		//alert(false);
		event.returnValue = false;
	}
	//�����ڰ�סshift��ͬʱ�����ּ�
	if ((key >= k0 && key <= k9) && event.shiftKey) {
	//alert(false);
		event.returnValue = false;
	}
}

/**
 * �жϼ����¼�,ֻ�ܽ������ְ�����С����ĺ���.
 * ��������ּ�����İ������ᱻ����.
 * ��input �ؼ�onkeydown�¼�ʱ����.
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
 * �жϼ����¼�,ֻ�ܽ�����ĸ�����֡��»��ߺ��˸��.
 */
function f_pwck(){
	var src = event.srcElement;
	var key = event.keyCode;

	//���������ַ�
	if (key<8||(key>9&&key<46)||(key>46&&key<48)||(key>57&&key<65)||(key>90&&key<96)||(key>122)){
		event.returnValue = false;
	}
	//�»���
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
 * �����������������뷽ʽ.
 * ���ݸ�ʽ�������ڵ�����. 
 * ���÷���: onkeydown='javascript:input_date([format]);'
 * ��Ч�ĸ�ʽ��: yyyy-mm-dd, yyyy:mm:dd, yyyy/mm/dd, yyyy.mm.dd, yyyymmdd
 * ��ʽ�����ִ�Сд.
 * @param format ���ڸ�ʽ(��ѡ)
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
		//����":"��, �������������ַ�
		if (key == kSpace || key >= kA && key <=kZ || 
			key >= kMultipy && key <= kDivide || 
			key > kSemicolon && key <=kQuote || 
			key == kSemicolon && !event.shiftKey) {
			event.returnValue = false;
		}
		//�����ڰ�סshift��ͬʱ�����ּ�
		if ((key >= k0 && key <= k9) && event.shiftKey) {
			event.returnValue = false;
		}
		if (length > 9) {
			f_nochar();
		}
		break;
	case "yyyy/mm/dd":
		//����"/"��, �������������ַ�
		if (key == kSpace || key >= kA && key <=kZ || 
			key >= kMultipy && key < kDivide || key >=kSemicolon && key < kSlash || 
			key > kSlash && key <=kQuote || key == kSlash && event.shiftKey || 
			key == kDivide && event.shiftKey) {
			event.returnValue = false;
		}
		//�����ڰ�סshift��ͬʱ�����ּ�
		if ((key >= k0 && key <= k9) && event.shiftKey) {
			event.returnValue = false;
		}
		if (length > 9) {
			f_nochar();
		}
		break;
	case "yyyy.mm.dd":
		//����"."��, �������������ַ�
		if (key == kSpace || key >= kA && key <=kZ || 
			key >= kMultipy && key < kDot || key == kDivide || 
			key >=kSemicolon && key <kFullStop || 
			key > kFullStop && key <=kQuote || 
			key == kFullStop && event.shiftKey || key == kDot && event.shiftKey) {
			event.returnValue = false;
		}
		//�����ڰ�סshift��ͬʱ�����ּ�
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
		//����"-"��, �������������ַ�
		if (key == kSpace || key >= kA && key <=kZ || 
			key >= kMultipy && key < kMinus || key > kMinus && key <= kDivide || 
			key >=kSemicolon && key <kDash || key > kDash && key <=kQuote || 
			key == kDash && event.shiftKey || key == kMinus && event.shiftKey) {
			event.returnValue = false;
		}
		//�����ڰ�סshift��ͬʱ�����ּ�
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
 * ������֤�������յļ��̰����Ƿ�����ȷ�İ�����.
 * ���ֻ֤����15λ(ֻ������)��18λ(���һλ����Ϊ�ַ�).
 * �ڿؼ�onkeydown�¼�ʱ����.
 * ע��Ҫ��������������ַ�����Ϊ18(maxlength=18).
 * @author 
 * @version 1.0.2003.0815
 */
function input_idno() {
	var length = event.srcElement.value.length;
	if (length < 17) {//��17λʱ���������ַ�
		f_onlynumber();
	}
	if (length == 18) {//���ӶԳ���18λ�ַ����ж�, �൱��maxlength=18
		f_nochar();
	}
}

/**
 * ����������������.
 * �й���������ֻ��6λ(Ĭ��).
 * ���������������(�뻥�������Һ�׺��ͬ). ��ͬ�����в�ͬ�������������.
 * ʹ�÷���: onkeydown='javascript:input_postcode([country_code]);'
 * <p><blockquote><pre>
 * ����ʹ�õĹ��ұ�����:
 * CN = �й�
 * US = ����
 * MY = ��������
 * </pre></blockquote></p>
 * @param country ���ұ���(��ѡ)
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
		if (length = 5) {//�����������5λ
			f_nochar();
		}
	break;
	default://"CN"
		if (length < 6) {
			f_onlynumber();
		}
		if (length = 6) {//�����������6λ
			f_nochar();
		}
	break;
	}
}

/**
 * У�����������. �� 10, 123, 65535��.
 * @author 
 * @version 1.0.2003.0819
 */
function input_int() {
	f_onlynumber();
}

/**
 * У��ʵ�������. �� 10, 13.5, 0.228��.
 * @author 
 * @version 1.0.2003.0819
 */
function input_real() {
	//����"."��, �������������ַ�
	if (key == kSpace || key >= kA && key <=kZ || key >= kMultipy && key < kDot || 
		key == kDivide || key >=kSemicolon && key <kFullStop || 
		key > kFullStop && key <=kQuote || key == kFullStop && event.shiftKey || 
		key == kDot && event.shiftKey) {
		event.returnValue = false;
	}
	//�����ڰ�סshift��ͬʱ�����ּ�
	if ((key >= k0 && key <= k9) && event.shiftKey) {
		event.returnValue = false;
	}
}

/**
 * У���ѧ�����������. �� 10, 3e+2, 45E-12��.
 * @author 
 * @version 1.0.2003.0819
 */
function input_scientific() {
	//����"e, +, -"��, �������������ַ�
	if (key == kSpace || key >= kA && key < kE || key > kE && key <=kZ || 
		key == kMultipy || key == 108 || key > kMinus && key <= kDivide || 
		key == kSemicolon || key == kComma || k > kDash && k <=kQuote || 
		key == kDash && event.shiftKey || !(key == kDash && event.shiftKey)) {
		event.returnValue = false;
	}
	//�����ڰ�סshift��ͬʱ�����ּ�
	if ((key >= k0 && key <= k9) && event.shiftKey) {
		event.returnValue = false;
	}
}


/**
 *Description:ȡ�ַ����ĳ��ȣ�֧�����ģ�
 *@Author �ű�
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
 *Description:�жϵ�ǰ�ַ��Ƿ�Ϊ���ģ�
 *@Author �ű�
 *@version 1.0.2002.1115
 *@return true���ǣ�false����
 */
function isChineseChar(strChar)
{	
	var intDoubleByteStart=19968;	//�����ַ���ʼ��λ��19968��
	if (strChar.charCodeAt(0)>=intDoubleByteStart)
	{
		return true;
	}else{
		return false;
	}
}

function isChineseString(strChar)
{	
		var intDoubleByteStart=19968;	//�����ַ���ʼ��λ��19968��
		var intPlace=0;
		var count=strChar.length;
		for(var i=0;i<count;i++){
	  		intPlace=strChar.charCodeAt(i);//strChar�ĵ�i���ַ���λ�á�
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
 * ˢ�±�. ���ƿɱ༭�ؼ��Ƿ�ɱ༭.
 * @param obj ������
 * @param editable �Ƿ�ɱ༭
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
 * �����ɱ༭�����������ȷ��.
 * �ɱ༭����ָinput text, input passowrd, textarea, select�ȿؼ�.
 * ������ȷ�԰���:
 * ���	������	���ÿؼ�				����		
 * 1. 	notnull input, textarea, select	���ɿ�
 * 2. 	clength input, textarea			��������ֵ
 * 3. 	cmax	select(multiple)		����ѡ�����ĸ���
 * ���� clength �� IEĬ������ maxlength �Ĳ�ͬ:
 * ���ǵ���Ҫ�������ڶ����ĵĴ�����. clength ��Ϊ�����ַ�����Ϊ2, ��maxlength��Ϊ�����ַ�����Ϊ1.
 * clength�����ĳ��ȵļ��㷽ʽ�����ݿ���ͬ.
 * ����: <input type='text' name='a' value="123һ����">. 
 * �������clength = 6, �� a.length = 9 ���� clength; validate������false.
 * �������maxlength = 6, �� a.length = 6 ���� maxlength; validate������true.
 * һ��clength��ֵ��ÿؼ���Ӧ�����ݿ��ֶε����ֵ��ͬ.
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
				alert("ѡ��" + control_name + "����Ϊ��");
				obj.focus();
				return false;
			}
		}
		if (obj.cmax != undefined && obj.multiple) {
			length = 0;
			for (var j = 0; j < obj.options.length; j++)
				if (obj.options[j].selected) length++;
			if (length > obj.cmax) {
				alert(control_name + "ѡ��������. ����: " + obj.cmax + 
				" Ŀǰ: " + length + ".");
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
					alert("�ֶ�" + control_name + "����Ϊ��");
					obj.focus();
					return false;
				}
				if (length > obj.clength) {
					
					alert(control_name + "ֵ̫��. ����: " + obj.clength + 
					"�ֽ� Ŀǰ: " + length + "�ֽ�.\n�����ַ�ռ˫�ֽ�.");
					obj.focus();
					return false;
				}
				//if(obj.isnumber != undefined){
					//if (obj>="0"&&theNum<="9")
					//alert(control_name+"ֻ��Ϊ����");
					//obj.focus();
					//return false;
				//}
			}
		}
	}
	return true;
}


/**
 * �жϼ����¼�,ֻ�ܽ�����ĸ�����ֺ��˸��.
 */
function f_charAndNumber(){
	var src = event.srcElement;
	var key = event.keyCode;

	//���������ַ�
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