var power='';
var  cookie;
var uid='';
var perlogNum = 10;
var $doc=$(document);
var t_pageType;;
//各种加载信息
function loadInfo(type,data,count){
    var type = type;
    switch (type){
        case "Gym":
        	$("#tBody").empty();
            for (i in data) {
                var placeinfo = "<tr><td>" + data[i].ID + "</td><td>" + data[i].GymArea + "</td><td>" + data[i].Place + "</td><td>" + data[i].SonPlace + "</td><td>" + data[i].SonArea
                    + "</td><td>" + data[i].Admin + "</td><td>" + data[i].status + "</td><td>" +
                    "<button class='btn btn-primary' data-toggle='modal' data-target='#edit_place' onclick='editplaceinfo(event)'>编辑</button>" +
                    "<button class='btn btn-danger' onclick='delplace(event)'>移除</button></td></tr>";
                $("#tBody").append(placeinfo);
            }
            break;
        case "indexGymType":
            var j=0;
            $("#tBody").empty();
            for (i in data) {
                j++;
                var gyminfo = "<tr><td>"+data[i].Id+"</td><td>"+data[i].Type+"</td><td>"+
                    data[i].Address+"</td><td>"+data[i].Price+"元/小时</td><td>" +
                    "<button class='btn btn-primary edit' data-toggle='modal' data-target='#edit_gym' onclick='editgyminfo(event)'>编辑</button>"+
                    "<button class='btn btn-danger remove' onclick='delgym(event)'>移除</button></td></tr>";
                $("#tBody").append(gyminfo);
                $("#imgid").attr("src",data[i].Picture);
                $("#imgid").attr("id",j);
            }
            break;
        case "user":
        	$("#tBody").empty();
            for(i in data){
                var user="<tr><td>"+data[i].Id+"</td><td>"+data[i].Name+"</td><td>"+data[i].Tel+"</td><td>"+data[i].Isfsd
                    +"</td><td>"+data[i].College+"</td><td>"+data[i].Grade+"</td><td>"+data[i].StuID
                    +"</td><td><button class='btn btn-danger' data-toggle='modal' data-target='#myModal5' onclick='addblack(event)'>加入黑名单</button></td> </tr>"
                $("#tBody").append(user);
                var queryGrade = new Array();
                if($.inArray(data[i].Grade, queryGrade)){
                    continue;
                }
                else{
                    queryGrade[i] = data[i].Grade;
                }
            }
            var qGrade;
            for(i in queryGrade){
               qGrade = "<option>" + queryGrade[i] + "</option>";
                $("#Grade").append(qGrade);
            }
            break;
        case "department":
        	$("#tBody").empty();
            for (i in data) {
                var adminmsg= "<tr><td>"+data[i].Id+"</td><td>"+ data[i].Name+" </td><td>"+data[i].Tel+ "</td><td>"+data[i].Email+"</td>" +
                    "<td><button class='btn btn-primary'  data-toggle='modal' data-target='#editAdminModal' onclick='resetadmin(event)'>重置密码</button>" +
                    "<button class='btn btn-danger' onclick='deladmin(event)'> 移除 </button></td> </tr>";
                $("#tBody").append(adminmsg);
            }
            break;
        case "order":
        	$("#tBody").empty();
            for (i in data) {
                if (data[i].State == "unfinished") {
                    var orderinfo = "<tr><td colspan='3'>订单编号:" +
                        data[i].Id +
                        "<div style='background-color: red;color:#ffffff;float:left;'>未付款</div></td> </tr><tr id='order_id'><td>" +
                        data[i].Name +
                        "</td><td>"+
                        data[i].Tel +
                        "</td><td>" +
                        data[i].GymArea +
                        "</td><td>" +
                        data[i].Place +
                        "</td><td>" +
                        data[i].SonPlace +
                        "</td><td>" +
                        data[i].SonArea +
                        "</td><td>" +
                        data[i].Date+"&nbsp;&nbsp;"+
                        data[i].StartTime +
                        "至" +
                        data[i].OverTime + "</td> <td>时间:" +
                        data[i].OrderTime + "</td><td>" +
                        data[i].Money + "元</td><td><button class='btn'>付款</button><button class='btn btn-danger' onclick='cancel(event)'>取消</button></td></tr>";
                    $("#tBody").append(orderinfo);
                    $("#order_id").attr("id",data[i].Id);
                }
                else if(data[i].State == "finished"){
                    var orderinf = "<tr><td colspan='3'>订单编号:" +
                        data[i].Id +
                        "<div style='background-color: green;color:#ffffff;float:left;'>已完成</div></td> </tr><tr><td>" +
                        data[i].Name +
                        "</td><td>"+
                        data[i].Tel +
                        "</td><td>" +
                        data[i].GymArea +
                        "</td><td>" +
                        data[i].Place +
                        "</td><td>" +
                        data[i].SonPlace +
                        "</td><td>" +
                        data[i].SonArea +
                        "</td><td>" +
                        data[i].Date+"&nbsp;&nbsp;"+
                        data[i].StartTime +
                        "至" +
                        data[i].OverTime + "</td> <td>时间:" +
                        data[i].OrderTime + "</td><td>" +
                        data[i].Money + "元</td>";
                    $("#tBody").append(orderinf);
                }
            }
            break;
        case "perCenter":
            for (i in data) {
                uid = data[i].Id;
                $("#text1").attr("value",data[i].Name);
                $("#email").attr("value",data[i].Email);
                $("#text2").attr("value",data[i].Tel);
                $("#password1").attr("value",data[i].Password);
                $("#repassword1").attr("value",data[i].Password);
            }
            break;
    }    $
    $("#count").empty();
    $("#count").append("共"+ count + "条");
    if(perlogNum>=count){
    	$("#upPage").parent().attr("class","disabled");
    	$("#downPage").parent().attr("class","disabled");
    }
};
$(document).ready(function(){
	var pn = $("#hidePage").val();
	logByPage(pn,perlogNum);
})

function logByPage(pn,ps){
	var pageType = $("#pageType").attr("pageType");
    t_pageType = pageType;
    cookie= document.cookie.split(';');
     power=getCookie("power");
    if(power=='A'){
        $(".new-item").css("display","block");
    }
    else if(power=='B'){
        $("#department").css("display","none");
        $(".edit").css("display","none");
        $(".remove").css("display","none");
    }
    var admin=new Object();
    admin.Username=decodeURIComponent(cookie[1]);
    admin.Power=power;
    admin.pn=pn;
    admin.ps=ps;
    var admininfo=JSON.stringify(admin);
    $("#login").load("adminlogin.jsp");
    switch (pageType) {
        case "Gym":
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: 'ShowArea?admininfo='+admininfo,
                success: function (data) {
                	if(data.msg=="failure")
                		alert("不存在该页");
                	else{
                		var count = data[data.length-1].count;
                    	data.splice(data.length-1,1);
                        loadInfo("Gym",data,count);
                        $("#hidePage").val(pn);
                	}
                }
            });break;
        case "indexGymType":
            //加载首页场地信息
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: 'LoadType?admininfo='+admininfo,
                data: "admininfo",
                success: function (data) {
                	if(data.msg=="failure")
                		alert("不存在该页");
                	else{
                		var count = data[data.length-1].count;
                    	data.splice(data.length-1,1);
                        loadInfo("indexGymType",data,count);
                        $("#hidePage").val(pn);
                	}
                }
            });break;
        case "department":
            //加载管理员信息
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: 'ShowAdminServlet?admininfo='+admininfo,
                success: function (data) {
                	if(data.msg=="failure")
                		alert("不存在该页");
                	else{
                		var count = data[data.length-1].count;
                    	data.splice(data.length-1,1);
                        loadInfo("department",data,count);
                        $("#hidePage").val(pn);
                	}
                }
            });break;
        case "user":
            //加载用户信息
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: 'LoadUsers?admininfo='+admininfo,
                success: function (data) {
                	if(data.msg=="failure")
                		alert("不存在该页");
                	else{
                		var count = data[data.length-1].count;
                    	data.splice(data.length-1,1);
                        loadInfo("user",data,count);
                        $("#hidePage").val(pn);
                	}
                }
            });break;
        case "order":
            //加载订单信息
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: 'LoadOrders?admininfo='+admininfo,
                success: function (data) {
                	if(data.msg=="failure")
                		alert("不存在该页");
                	else{
                		var count = data[data.length-1].count;
                    	data.splice(data.length-1,1);
                        loadInfo("order",data,count);
                        $("#hidePage").val(pn);
                	}
                }
            });break;

        case "perCenter":
            //个人中心加载信息
            var admintel = new Object();
            admintel.Tel = decodeURIComponent(cookie[0]);
            admintel.Power = power;
            var admininfotel = JSON.stringify(admintel);
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: 'LoadPers?admininfo=' + admininfotel,
                data: "admininfotel",
                success: function (data) {
                    loadInfo("perCenter",data);
                }
            });break;
    }
}
//管理员登录
function adminlogin(e){
    var jsonuserinfo = $('#adminlogin').serializeObject();
    txt = JSON.stringify(jsonuserinfo);
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: '',
        data: "txt",
        success: function (data) {
            if(data.msg=='success')
            {
                power=data.power;
                window.location.reload();
            }
            else alert("用户名或密码错误"+data.reason);
        },
        error: function (err) { //如果执行不成功，那么执行此方法
            alert("请求失败" + err);
        }
    } );
}

//注销
function exit(e){
	var r=confirm("确认退出帐号？")
	  if (r==true)
	{
        document.cookie = "power=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
        event.preventDefault();
        var obj=new Object();
       obj.msg = "exit";
       var exit=JSON.stringify(obj);
       $.ajax({
           type: "POST",
           contentType: "application/json; charset=utf-8",
           dataType: "json",
           url: 'ExitServlet',
           data: "",
           success: function (data) {
            window.location.href="index.html";
           }
        }) ;
    }
}

//移除场地
function delplace(e){
	var r=confirm("确认移除场地？")
	  if (r==true)
	{
    var e = window.event;
    var targ = e.target;
    var place = new Object();
    $(targ).parent().siblings("td").each(function(i){
        if(i==0) place.ID=$(this).text();
        if(i==1) place.GymArea=$(this).text();
        if(i==2) place.Place=$(this).text();
        if(i==3) place.SonPlace=$(this).text();
        if(i==4) place.SonArea=$(this).text();
    });
    var placeinfo =JSON.stringify(place);
       /* alert(placeinfo);*/
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'RemoveArea?placeinfo='+placeinfo,
        data: "placeinfo",
        success: function (data) {
            if(data.msg=='success'){
                alert("移除场地成功");
            }
            window.location.href="Gym.html";
        },
        error: function (err) { //如果执行不成功，那么执行此方法
            alert("移除场地失败" + err);
        }
    } );
	}
}

//新增场地
function addplace(event){
/*	var r=confirm("确认新增场地？")
	  if (r==true)
	{*/
    var jsonplaceinfo = $('#addplace').serializeObject();
    var placeinfo = JSON.stringify(jsonplaceinfo);
    /*alert(placeinfo);*/
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: 'AddArea?placeinfo='+placeinfo,
            data: "placeinfo",
            success: function (data) {
                if(data.msg=='exsit'){
                    alert("场地已存在！检查后重新填写");
                }
                else {      
                	window.location.href="Gym.html";
                	window.location.reload();
                }
            },
            error: function (err) { //如果执行不成功，那么执行此方法

            	alert("添加场地失败" + err);
            }
        } );
	
}

//编辑场馆信息
function editplace(e){
    $("#idedit").removeAttr('disabled');
	var r=confirm("确认修改？")
	  if (r==true)
	{
    var jsonplaceinfo = $('#editplace').serializeObject();
    var placeinfo = JSON.stringify(jsonplaceinfo);
    /*alert(placeinfo);*/
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: 'EditArea?placeinfo='+placeinfo,
            data: "placeinfo",
            success: function (data) {
//                if(data.msg='exsit'){
//                    alert("场地已存在！检查后重新填写");
//                }
            	if(data.msg=='success'){
                    alert("修改场地信息成功");
                    window.location.href="Gym.html";
                    window.location.reload();
                }
            },
            error: function (err) { //如果执行不成功，那么执行此方法
                alert("修改场地信息失败" + err);
            }
        } );
	}
}
// 编辑场地信息加载
function editplaceinfo(e){
    var e = window.event;
    var targ = e.target;
    $(targ).parent().siblings("td").each(function(i){
        if(i==0) $("#idedit").attr('value',$(this).text());
        if(i==1) $("#gymarea").attr('value',$(this).text());
        if(i==2) $("#eplace").attr('value',$(this).text());
        if(i==3) $("#sonplace").attr('value',$(this).text());
        if(i==4) $("#sonarea").attr('value',$(this).text());
        if(i==5) $("#myadmin").attr('value',$(this).text());
        if(i==6 && $(this).text()=='可用') $("#able").attr("checked","");
        if(i==6 && $(this).text()=='不可用') $("#unable").attr("checked","");
    })
}

//新增首页场地
function addgym(e){
	var r=confirm("确认新增首页场地类型？")
	  if (r==true)
	{
    var jsongyminfo = $('#addgym').serializeObject();
    var gyminfo = JSON.stringify(jsongyminfo);
    /*alert(gyminfo);*/
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'AddType?gyminfo='+gyminfo,
        data: "gyminfo",
        success: function (data) {
            if(data.msg=='exsit'){//如果type已存在返回{'msg'==‘exsit'}给前端
                alert("首页场地类型已存在！检查后重新填写");
            }
            else{
                alert("添加首页成功");
                window.location.href="indexGymType.html";
                window.location.reload();
            }

           /* window.location.reload();*/
        
        },
        error: function (err) { //如果执行不成功，那么执行此方法
            alert("添加场地失败" + err);
        }
    });
	}
}
//编辑首页场地信息加载
function editgyminfo(e){
    var e = window.event;
    var targ = e.target;
    $(targ).parent().siblings("td").each(function(i){
        if(i==0) $("#gym_id").attr('value',$(this).text());
        if(i==1) $("#gym_type").attr('value',$(this).text());
        if(i==2) $("#gym_address").attr('value',$(this).text());
        if(i==3) $("#gym_price").attr('value',$(this).text().replace("元/小时",""));
    })
}
//编辑首页场地
function editgym(e){
	var r=confirm("确认修改？")
	  if (r==true)
	{
    $("#gym_id").removeAttr('disabled')
    var jsongyminfo = $('#editgym').serializeObject();
    var gyminfo = JSON.stringify(jsongyminfo);
    /*alert(gyminfo);*/
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'EditType?gyminfo='+gyminfo,
        data: "gyminfo",
        success: function (data) {
//            if(data.msg=='exist'){
//                alert("首页场地类型已存在！检查后重新填写");
//            }
            if(data.msg=='success'){
                alert("修改首页场地信息成功");
                window.location.href="indexGymType.html";
                window.location.reload();
            }

        }
    } );
	}
}
//删除首页场地
function delgym(e){
	var r=confirm("确认删除首页场地类型？")
	  if (r==true)
	{
    var e = window.event;
    var targ = e.target;
    var gym = new Object();
    $(targ).parent().siblings("td").each(function(i){
        if(i==0) gym.ID=$(this).text();
    })
    var gyminfo =JSON.stringify(gym);
    /*alert(gyminfo);*/
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'DelType?gyminfo='+gyminfo,
        data: "gyminfo",
        success: function (data) {
                $(targ).closest("tr").remove();
        }
    } );
	}
}
//按条件查询用户信息
function query(e){
    var e = window.event;
    var targ = e.target;
    var userin = $('#queryuser').serializeObject();
    var userinfo = JSON.stringify(userin);
    $("#tBody").empty();
//   alert("传给后台数据"+userinfo);
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'LookIntoUsers?userinfo='+userinfo,
        data: "userinfo",
        success: function (data) {
            for(i in data){
                var user="<tr><td>"+data[i].Id+"</td><td>"+data[i].Name+"</td><td>"+data[i].Tel+"</td><td>"+data[i].Isfsd
                    +"</td><td>"+data[i].College+"</td><td>"+data[i].Grade+"</td><td>"+data[i].StuID
                    +"</td><td><button class='btn btn-danger' data-toggle='modal' data-target='#myModal5' onclick='addblack(event)'>加入黑名单</button></td> </tr>"
                $("#tBody").append(user);
            }
        }
    })
}

//加入黑名单，暂时是删除该用户
function addblack(e){
	var r=confirm("确认加入黑名单？")
	  if (r==true)
	{
    var e = window.event;
    var targ = e.target;
    var user = new Object();
    $(targ).parent().siblings("td").each(function(i){
        if(i==0) user.Id=$(this).text();
    })
    var userinfo = JSON.stringify(user);
   /* alert(userinfo);*/
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'DelUser?userinfo='+userinfo,
        data: "userinfo",
        success: function (data) {
            $(targ).closest("tr").remove();
        }
    });
	}
}

function outschool(e){
    var e = window.event;
    var targ = e.target;
    if(targ.value=="校外人员") $("#Grade").attr("disabled","");
    else if(targ.value=="校内人员") $("#Grade").removeAttr("disabled","");
}


//新增管理员
function addadmin(e){
	var r=confirm("确认新增管理员？")
	  if (r==true)
	{
    var admin = $('#addadmin').serializeObject();
    var admininfo=JSON.stringify(admin);
  /*  alert(admininfo);*/
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url:'AddAdmin?admininfo='+admininfo,
        data: "admininfo",
        success: function (data) {
            if(data.msg=='exsit'){
                alert("管理员已存在！检查后重新填写");
            }
            else window.location.href="department.html";
        }
    })
	}
}
//确认重置密码
function confirmEditPsw(){
    var admin = new Object();
    admin.msg="reset";
    admin.Id = $("#thisId").val();
    admin.newPsw = $("#newPsw").val();
    var admininfo=JSON.stringify(admin);
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url:'ResetPass?admininfo='+admininfo,
        success: function () {
            alert("重置密码成功");
        }
    })
}
//按下重置密码按钮
function resetadmin(e){
    var e = window.event;
    var targ = e.target;
    $(targ).parent().siblings("td").each(function(i){
        if(i==0) 
        	$("#thisId").val($(this).text());
    })
}
//删除管理员
function deladmin(e){
	var r=confirm("确认删除管理员？")
	  if (r==true)
	{
    var e = window.event;
    var targ = e.target;
    var admin = new Object();
    admin.msg="delete";
    $(targ).parent().siblings("td").each(function(i){
        if(i==0) admin.Id=$(this).text();
    })
    var admininfo=JSON.stringify(admin);
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url:'DelAdmin?admininfo='+admininfo,
        data: "admininfo",
        success: function (data) {
            $(targ).closest("tr").remove();
        }
    })
	}
}
//查询订单
function queryorder(e){
    if($("#beginTime").val()!="" && $("#endTime").val()!="" && $("#beginTime").val() > $("#endTime").val() )
    {
     alert("结束时间需 大于 起始时间，请重新填写！");
        return;
    }

    var e = window.event;
    var targ = e.target;
    var order = $('#queryorder').serializeObject();

    var orderInfo = JSON.stringify(order);

    $("#tBody").empty();
//    alert("传给后台数据"+orderInfo);
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'LookIntoOrders?orderInfo='+orderInfo,
        data: "orderInfo",
        success: function (data) {
            for (i in data) {
                if (data[i].State == "unfinished") {
                    var orderinfo = "<tr><td colspan='3'>订单编号:" +
                        data[i].Id +
                        "<div style='background-color: red;color:#ffffff;float:left;'>未付款</div></td> </tr><tr id='order_id'><td>" +
                        data[i].Name +
                        "</td><td>"+
                        data[i].Tel +
                        "</td><td>" +
                        data[i].GymArea +
                        "</td><td>" +
                        data[i].Place +
                        "</td><td>" +
                        data[i].SonPlace +
                        "</td><td>" +
                        data[i].SonArea +
                        "</td><td>" +
                        data[i].Date+"&nbsp;&nbsp;"+
                        data[i].StartTime +
                        "至" +
                        data[i].OverTime + "</td> <td>时间:" +
                        data[i].OrderTime + "</td><td>" +
                        data[i].Money + "元</td><td><button class='btn'>付款</button><button class='btn btn-danger' onclick='cancel(event)'>取消</button></td></tr>";
                    $("#tBody").append(orderinfo);
                    $("#order_id").attr("id",data[i].Id);
                }
                else if(data[i].State == "finished"){
                    var orderinf = "<tr><td colspan='3'>订单编号:" +
                        data[i].Id +
                        "<div style='background-color: green;color:#ffffff;float:left;'>已完成</div></td> </tr><tr><td>" +
                        data[i].Name +
                        "</td><td>"+
                        data[i].Tel +
                        "</td><td>" +
                        data[i].GymArea +
                        "</td><td>" +
                        data[i].Place +
                        "</td><td>" +
                        data[i].SonPlace +
                        "</td><td>" +
                        data[i].SonArea +
                        "</td><td>" +
                        data[i].Date+"&nbsp;&nbsp;"+
                        data[i].StartTime +
                        "至" +
                        data[i].OverTime + "</td> <td>时间:" +
                        data[i].OrderTime + "</td><td>" +
                        data[i].Money + "元</td>";
                    $("#tBody").append(orderinf);
                }
            }

        }
    })
}
// 取消订单
function cancel(e){
	var r=confirm("确认取消订单？")
	  if (r==true)
	{
    var e = window.event;
    var targ = e.target;
    var order=new Object();
    order.orderid= $(targ).closest('tr').attr("id");
    var orderinfo=JSON.stringify(order);
    //alert(orderinfo);
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'CancelOrders?orderinfo='+orderinfo,
        data: "orderinfo",
        success: function (data) {
            $(targ).closest("tr").prev().remove();
            $(targ).closest("tr").remove();
        }
    })
	}
}
//修改个人信息
function personal(e){
	var r=confirm("确认修改？")
	  if (r==true)
	{
   var admin=$("#personal").serializeObject();
    admin.Username=decodeURIComponent(cookie[1]);
    admin.Id=uid;
    var admininfo=JSON.stringify(admin);
   /* alert(admininfo);*/
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'EditPers?admininfo='+admininfo,
        data: "admininfo",
        success: function (data) {
            loadInfo("perCenter",data)
        }
    })
	}
}
//直接转到某页
function directPage(e){
	var pn = $("#ps").val();
	logByPage(pn,perlogNum);
}
//翻页相关
function turnPage(e){
	var e = window.event;
	var targ = e.target;
	
	if($(targ).parent().attr("class")=="disabled"){
        return;
    }
	
	var totalCount = $("#count").text().substring(1,2);
	
	var ptype =  $(targ).attr("id");  
    var pn = parseInt($("#hidePage").val());
    
    console.log("处理前  "+pn+" "+perlogNum)

    //用于判断是点上一页还是下一页
    if(ptype=="upPage"){
    	pn-=1;
    }
    else if(ptype=="downPage"){
    	pn+=1;
    }
    console.log("处理后"+" "+pn+" "+ps);
    
    if(pn==1){
		$("#upPage").parent().attr("class","disabled");
    }
    if(perlogNum>=totalCount){
    	$("#upPage").parent().attr("class","disabled");
    	$("#downPage").parent().attr("class","disabled");
    }
    if(perlogNum*pn>=totalCount){
    	$("#upPage").parent().removeClass("disabled");
    	$("#downPage").parent().attr("class","disabled");
    }
    if(1<pn&&perlogNum*pn<totalCount){
    	$("#upPage").parent().removeClass("disabled");
    	$("#downPage").parent().removeClass("disabled");
    }
    
    logByPage(pn,perlogNum);
}

function getCookie(cname)
{
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++)
    {
        var c = ca[i].trim();
        if (c.indexOf(name)==0) return c.substring(name.length,c.length);
    }
    return "";
}
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
