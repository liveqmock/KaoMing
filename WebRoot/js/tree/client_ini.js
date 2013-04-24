//	*****************************************************************************
//	*	�ļ����ƣ�client_ini.js      											*														*
//	*	ģ�鹦�ܣ�ͳһ����JS�������ʾ�����͹�����������Ϣ�ȣ�������property��	*
//  *             �������ο����в�Ҫ�޸ĸ��ļ�����Ҫ������������ԣ��ɲ���base	*
//  *             ��ǩ��js���ԣ�����Ϊ���ο������Լ���JS�ļ���Ȼ�����������¸�	*
//  *             ֵ��					*
//	*************************************************************

var Ces_Library_path="";

//---- General Setting ----
var processEnterAsTab = true; // ���õ������������ʱ�����س��������Ƿ�ת�Ƶ���һ������򣬼��൱�ڰ���tag��
var showSubmitCommand = false;
var disableSystemContextMenu = false;

//---- String Table ----
var constErrType = "��������";
var constErrDescription = "��������";
var constErrUnknown = "δ֪����";
var constErrDataType = "���ݳ��������Ͳ�ƥ�䣡";
var constErrKeyViolence = "���������Լ������";
var constErrUnsupportBrowser = "������ʹ�õĲ��� Microsoft Internet Explorer 5.0 ����߰汾��������������п����޷���ñ�ҳ����ȷ����ʾ�����/n�����������������";
var constErrDownLoadFailed = "��������ʧ�ܣ�";
var constErrUpdateFailed = "��������ʧ�ܣ�";
var constErrAddDataField = "�����ܶ�����ɳ�ʼ���ļ�¼������ֶΣ�";
var constErrEmptyFieldName = "�ֶ�������Ϊ�գ�";
var constErrCantFindField = "�Ҳ���ָ�����ֶ�[%s]��";
var constErrCantFindMasterField = "�����ֶ�[%s]�����ڣ�";
var constErrCantFindDetailField = "�ӱ��ֶ�[%s]�����ڣ�";
var constErrLoadPageOnDetailDataset = "�ѽ������Ӱ󶨵Ĵӱ��¼������ִ�з������أ�";
var constErrLoadPageAfterSort = "�ѽ��пͻ�������ļ�¼������ִ�з������أ�";
var constErrFieldValueRequired = "�ֶ�[%s]�����ݲ���Ϊ�գ�";
var constErrKeyFieldRequired = "û�ж��������ֶΣ�";
var constErrUpdateFieldRequired = "û�пɸ��µ��ֶΣ�";
var constErrTypeInt = "�������ֵ[%s]����һ����Ч��������";
var constErrTypeNumber = "�������ֵ[%s]����һ����Ч�����֣�";
var constErrTypeDate = "�������ֵ[%s]����һ����Ч��������ֵ��\n�������������պ�����ѡ��";
var constErrTypeDateTime = "�������ֵ[%s]����һ����Ч������+ʱ����ֵ��\n�������������պ�����ѡ��";
var constErrTypeTime = "�������ֵ[%s]����һ����Ч��ʱ����ֵ��";
var constErrOutOfDropDownList = "����������Ч��ֵ��";
var constErrNoCurrentRecord = "���ڼ�¼��û�е�ǰ��¼���޷��޸��ֶ�ֵ��";

var constErrDateOutOfLowerRange = "��ѡ������ڳ����޶������ڷ�Χ��\n����Ӧ����[%minDate]��������ѡ��";
var constErrDateOutOfUpperRange = "��ѡ������ڳ����޶������ڷ�Χ��\n����ӦС��[%maxDate]��������ѡ��";

var constErrNoEmpty = "���������Ϊ�գ�";
var constErrPasswordNotMatchable = "����ȷ�ϲ�ƥ�䣡";
var constErrInvalidChar = "�����ַ����Ϸ�:";
var constErrInvalidFloatFormat = "���Ϸ���С����ʽ��";


var constErrFloatType = "���벻��ȷ��ӦΪ�Ϸ�����ֵ��";
var constErrFloatFormat = "���벻��ȷ����ֵ��ʽӦΪ��\n����λ[%intCount]����С��λ[%decimalCount]��";
var constErrMinlength = "�����ַ���С����С����[%minLength]��";

var constErrMaxlength = "�����ַ���������󳤶�[%maxLength]��";

var constErrInt = "���벻��ȷ��ӦΪ�Ϸ���������";
var constErrFloat = "���벻��ȷ��ӦΪ�Ϸ���С����";
var constErrEmail = "���벻��ȷ��ӦΪ�Ϸ��ĵ����ʼ���ַ��";
var constErrRectify = "\n���޸Ĳ���ȷ����������ύ��";

var constDatasetConfirmCancel = "��ȷ��Ҫ�����Ե�ǰ��¼���޸���";
var constDatasetConfirmDelete = "��ȷ��Ҫɾ����ǰ��¼��";
var constDatasetMoveFirst = "�ƶ�����һ����¼";
var constDatasetPrevPage = "��ǰ��ҳ";
var constDatasetMovePrev = "�ƶ�����һ����¼";
var constDatasetMoveNext = "�ƶ�����һ����¼";
var constDatasetNextPage ="���ҳ";
var constDatasetMoveLast = "�ƶ������һ����¼";
var constDatasetInsertRecord = "����һ���¼�¼";
var constDatasetAppendRecord = "���һ���¼�¼";
var constDatasetDeleteRecord = "ɾ����ǰ��¼";
var constDatasetEditRecord = "�޸ĵ�ǰ��¼";
var constDatasetCancelRecord = "�����Ե�ǰ��¼���޸�";
var constDatasetUpdateRecord = "ȷ�϶Ե�ǰ��¼���޸�";

var constBtnInsertRecord = "����";
var constBtnAppendRecord = "���";
var constBtnDeleteRecord = "ɾ��";
var constBtnEditRecord = "�޸�";
var constBtnCancelRecord = "����";
var constBtnUpdateRecord = "ȷ��";

var constJanuary = "һ��";
var constFebrary = "����";
var constMarch = "����";
var constApril = "����";
var constMay = "����";
var constJune = "����";
var constJuly = "����";
var constAugust = "����";
var constSeptember = "����";
var constOctober= "ʮ��";
var constNovember = "ʮһ��";
var constDecember = "ʮ����";


var constMonday = "һ";
var constTuesday = "��";
var constWednesday = "��";
var constThursday = "��";
var constFriday = "��";
var constSaturday = "��";
var constSunday = "��";

var constLastYear = "��һ��";
var constNextYear = "��һ��";
var constLastMonth = "�ϸ���";
var constNextMonth = "�¸���";
var constToday = "����";

var constDownLoadingData = "���ڼ������ݣ����Ժ�...";
var constCancelSort = "������";

//var constIsDisplayErrorPage=true;
var constIsCheckDisabledRow=false;



// added by steve_gu at 2003-12-25
var constTime = "ʱ�䣺";

var constMonthMapping = new Object();
constMonthMapping.pre01 = "January";
constMonthMapping.pre02 = "February";
constMonthMapping.pre03 = "March";
constMonthMapping.pre04 = "April";
constMonthMapping.pre05 = "May";
constMonthMapping.pre06 = "June";
constMonthMapping.pre07 = "July";
constMonthMapping.pre08 = "Auguest";
constMonthMapping.pre09 = "September";
constMonthMapping.pre10 = "October";
constMonthMapping.pre11 = "November";
constMonthMapping.pre12 = "December";

/**  ����һ����ͼ��·���Ŀ��ƺ��Ƿ�ʹ��commonĿ¼�µ�loadtree.jsp  **/
var gConfig = {
	imgPath     : Ces_Library_path+'images/xp/',
	hasLoadTreePath:true 
};
//end added 

//---- Tree Setting ----
var treeConfig = {
	rootIcon        : Ces_Library_path+'images/xp/rootIcon.png',
	openRootIcon    : Ces_Library_path+'images/xp/openfoldericon.png',
	folderIcon      : Ces_Library_path+'images/xp/foldericon.png',
	openFolderIcon  : Ces_Library_path+'images/xp/openfoldericon.png',
	fileIcon        : Ces_Library_path+'images/xp/file.png',
	iIcon           : Ces_Library_path+'images/xp/I.png',
	lIcon           : Ces_Library_path+'images/xp/L.png',
	lMinusIcon      : Ces_Library_path+'images/xp/Lminus.png',
	lPlusIcon       : Ces_Library_path+'images/xp/Lplus.png',
	tIcon           : Ces_Library_path+'images/xp/T.png',
	tMinusIcon      : Ces_Library_path+'images/xp/Tminus.png',
	tPlusIcon       : Ces_Library_path+'images/xp/Tplus.png',
	blankIcon       : Ces_Library_path+'images/xp/blank.png',
	defaultText     : 'Tree Item',
	defaultAction   : 'javascript:void(0);',
	defaultBehavior : 'explorer',
	defaultDisplayRoot: true,
	usePersistence	: true,
	// ��һ�ڵ�û��action�� ��ʹ����Ϊ������
	noActionNoAnchor: false,
	//  loadtree specific
	loadingText     : "���ڼ������ݣ����Ժ�...",
	loadErrorTextTemplate : "Error loading \"%1%\"",
	emptyErrorTextTemplate : "Error \"%1%\" does not contain any tree items",
	idFieldName     : "id",
	labelFieldName  : "name",
	parentIdField   : "parentid",
	valueFieldName  : "code"
	// end add
};

/* ��Ч��У������ */
var validateConfig = {
	// �Ƿ�һ�ε������в���ȷ������������Ϣ���ڵ���cescom_js_global_validate(htmlControl, displayAllErrorMessage)
	// ����ʱ�����û�еڶ���������ʹ�����ͳһ�����ã����еڶ��������������������
	displayAllErrorMessage : true,
	// ���ƶԵ��������ʧȥ����ʱ�Ƿ������֤,Ϊfalse�����ͨ������ֻ�����ύʱһ��У��ķ�ʽ
	validateOnBlur : false,
	// ���˵����ַ����������е�Ԫ�ؿ���Ϊ�ַ���������������ʽ
	filterChar:      []
};
/* end add */

/** ȫ������ */
var cescomGlobalConfig = {
	// �Ƿ���Ҫ���¼���, �������ø����Ի�Ϊtrue������idΪ_ces���input�Ķ���,�Ա��Ѿ�����_ces���id�ĳ�����Ӱ��
	needCompatibility: false
};
/* end add */

