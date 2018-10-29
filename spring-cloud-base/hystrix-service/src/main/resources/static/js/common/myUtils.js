
var curPage = 0;

var condition = 0; //查询条件对象

function getUrlParamValue(search,key){
	
	if(search.indexOf("?")!=0){
		return "";
	}
	
	search = search.substr(1);
	
	var reg  = new RegExp("&?"+key+"=([^&]+)&?");

	var result = search.match(reg);
	
	if(result){
		return unescape(result[1]);
	}
	
	return "";
}


function forwardMain(){
	
	window.location="/test/page/main.html";
}

function forwardUpload(){
	
	window.location="/test/page/uploadAndDownload.html";
}
function forwardRole(){
	
	window.location="/test/page/role.html";
}
function forwardAddStudent(){
	
	window.location="/test/page/student.html?action=add";
}

function forwardUpdateStudent(stuId){
	window.location="/test/page/student.html?action=update&stuId="+escape(stuId);
}

function forwardFindStudent(stuId){
	window.location="/test/page/student.html?action=find&stuId="+escape(stuId);
}