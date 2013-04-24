//	*****************************************************************************
//	*	文件名称：client_ini.js      											*														*
//	*	模块功能：统一定义JS里面的提示常量和公共的配置信息等，类似于property文	*
//  *             件，二次开发中不要修改该文件，若要覆盖这里的属性，可采用base	*
//  *             标签的js属性，设置为二次开发中自己的JS文件，然后在其中重新赋	*
//  *             值。					*
//	*************************************************************

var Ces_Library_path="";

//---- General Setting ----
var processEnterAsTab = true; // 设置当焦点在输入框时，按回车键焦点是否转移到下一个输入框，即相当于按了tag键
var showSubmitCommand = false;
var disableSystemContextMenu = false;

//---- String Table ----
var constErrType = "错误类型";
var constErrDescription = "错误描述";
var constErrUnknown = "未知错误！";
var constErrDataType = "数据超长或类型不匹配！";
var constErrKeyViolence = "主键或外键约束错误！";
var constErrUnsupportBrowser = "由于您使用的不是 Microsoft Internet Explorer 5.0 或更高版本的浏览器，您将有可能无法获得本页面正确的显示结果！/n请升级您的浏览器。";
var constErrDownLoadFailed = "下载数据失败！";
var constErrUpdateFailed = "保存数据失败！";
var constErrAddDataField = "您不能对已完成初始化的记录集添加字段！";
var constErrEmptyFieldName = "字段名不能为空！";
var constErrCantFindField = "找不到指定的字段[%s]！";
var constErrCantFindMasterField = "主表字段[%s]不存在！";
var constErrCantFindDetailField = "从表字段[%s]不存在！";
var constErrLoadPageOnDetailDataset = "已建立主从绑定的从表记录集不能执行分批下载！";
var constErrLoadPageAfterSort = "已进行客户端排序的记录集不能执行分批下载！";
var constErrFieldValueRequired = "字段[%s]的内容不能为空！";
var constErrKeyFieldRequired = "没有定义主键字段！";
var constErrUpdateFieldRequired = "没有可更新的字段！";
var constErrTypeInt = "您输入的值[%s]不是一个有效的整数！";
var constErrTypeNumber = "您输入的值[%s]不是一个有效的数字！";
var constErrTypeDate = "您输入的值[%s]不是一个有效的日期型值！\n请重新输入或清空后重新选择！";
var constErrTypeDateTime = "您输入的值[%s]不是一个有效的日期+时间型值！\n请重新输入或清空后重新选择！";
var constErrTypeTime = "您输入的值[%s]不是一个有效的时间型值！";
var constErrOutOfDropDownList = "您输入了无效的值！";
var constErrNoCurrentRecord = "由于记录集没有当前记录而无法修改字段值！";

var constErrDateOutOfLowerRange = "您选择的日期超出限定的日期范围！\n日期应大于[%minDate]，请重新选择";
var constErrDateOutOfUpperRange = "您选择的日期超出限定的日期范围！\n日期应小于[%maxDate]，请重新选择";

var constErrNoEmpty = "该输入框不能为空！";
var constErrPasswordNotMatchable = "密码确认不匹配！";
var constErrInvalidChar = "以下字符不合法:";
var constErrInvalidFloatFormat = "不合法的小数格式！";


var constErrFloatType = "输入不正确，应为合法的数值！";
var constErrFloatFormat = "输入不正确，数值格式应为：\n整数位[%intCount]个，小数位[%decimalCount]个";
var constErrMinlength = "输入字符数小于最小长度[%minLength]！";

var constErrMaxlength = "输入字符数大于最大长度[%maxLength]！";

var constErrInt = "输入不正确，应为合法的整数！";
var constErrFloat = "输入不正确，应为合法的小数！";
var constErrEmail = "输入不正确，应为合法的电子邮件地址！";
var constErrRectify = "\n请修改不正确的输入后再提交！";

var constDatasetConfirmCancel = "您确定要撤消对当前记录的修改吗？";
var constDatasetConfirmDelete = "您确定要删除当前记录吗？";
var constDatasetMoveFirst = "移动到第一条记录";
var constDatasetPrevPage = "向前翻页";
var constDatasetMovePrev = "移动到上一条记录";
var constDatasetMoveNext = "移动到下一条记录";
var constDatasetNextPage ="向后翻页";
var constDatasetMoveLast = "移动到最后一条记录";
var constDatasetInsertRecord = "插入一条新记录";
var constDatasetAppendRecord = "添加一条新记录";
var constDatasetDeleteRecord = "删除当前记录";
var constDatasetEditRecord = "修改当前记录";
var constDatasetCancelRecord = "撤销对当前记录的修改";
var constDatasetUpdateRecord = "确认对当前记录的修改";

var constBtnInsertRecord = "插入";
var constBtnAppendRecord = "添加";
var constBtnDeleteRecord = "删除";
var constBtnEditRecord = "修改";
var constBtnCancelRecord = "撤销";
var constBtnUpdateRecord = "确认";

var constJanuary = "一月";
var constFebrary = "二月";
var constMarch = "三月";
var constApril = "四月";
var constMay = "五月";
var constJune = "六月";
var constJuly = "七月";
var constAugust = "八月";
var constSeptember = "九月";
var constOctober= "十月";
var constNovember = "十一月";
var constDecember = "十二月";


var constMonday = "一";
var constTuesday = "二";
var constWednesday = "三";
var constThursday = "四";
var constFriday = "五";
var constSaturday = "六";
var constSunday = "日";

var constLastYear = "上一年";
var constNextYear = "下一年";
var constLastMonth = "上个月";
var constNextMonth = "下个月";
var constToday = "今天";

var constDownLoadingData = "正在加载数据，请稍候...";
var constCancelSort = "不排序";

//var constIsDisplayErrorPage=true;
var constIsCheckDisabledRow=false;



// added by steve_gu at 2003-12-25
var constTime = "时间：";

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

/**  增加一个对图标路径的控制和是否使用common目录下的loadtree.jsp  **/
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
	// 若一节点没有action， 则使它不为超链接
	noActionNoAnchor: false,
	//  loadtree specific
	loadingText     : "正在加载数据，请稍候...",
	loadErrorTextTemplate : "Error loading \"%1%\"",
	emptyErrorTextTemplate : "Error \"%1%\" does not contain any tree items",
	idFieldName     : "id",
	labelFieldName  : "name",
	parentIdField   : "parentid",
	valueFieldName  : "code"
	// end add
};

/* 有效性校验配置 */
var validateConfig = {
	// 是否一次弹出所有不正确的输入框错误信息，在调用cescom_js_global_validate(htmlControl, displayAllErrorMessage)
	// 函数时，如果没有第二个参数，使用这儿统一的配置；若有第二个参数，覆盖这个配置
	displayAllErrorMessage : true,
	// 控制对单个输入框失去焦点时是否进行验证,为false的情况通常用在只采用提交时一次校验的方式
	validateOnBlur : false,
	// 过滤掉的字符串，数组中的元素可以为字符串常量和正则表达式
	filterChar:      []
};
/* end add */

/** 全局配置 */
var cescomGlobalConfig = {
	// 是否需要向下兼容, 若不设置该属性或为true，保留id为_ces这个input的对象,以便已经用了_ces这个id的程序不受影响
	needCompatibility: false
};
/* end add */

