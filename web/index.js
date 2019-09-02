var host = 'http://www.zhangyixin9527.cn:8081';
var global_login_url = "";
var user;
//点击购买的跳转
function save_order(videoId) {
    var token = $.cookie("token");
//    if (!token || token == ""){
        //请登录
//        window.location.href=global_login_url;
//    }
    //下单接口
    var url = host + "/api/v1/order/add?token="+token+"&video_id="+videoId+"&user_id="+user.id;
    $('#myModalLabel').text("订单有效时间10分钟");
    //document.getElementById('list1').style.display="none";
    $("#list1").hide();
    $("#pay_img").attr("src",url);
    $("#pay_img").show();
    //window.location.href=global_login_url;
}

//测试号登录
function test_login() {
    $.ajax({
        type:'get',
        url:host+'/testLogin',
        dataType:'json',
        success:function (res) {
            if (res.data != null){
                user = res.data;
                $("#testLogin").hide();
                $("#login").html(res.data.name);
                $.cookie('token',"sss3333forIverson");
                //console.log(res.data.name);
            }else{
                alert("请登录（测试账号未创建）");
            }
        }
    })
}

//我的订单列表
function my_order_list(){
	
	$('#myModalLabel').text("我的订单列表");
	$("#pay_img").hide();
	$("#orderRow").empty();
	
	$.ajax({
        type:'get',
        url:host+'/api/v1/order/myOrderList?user_id='+user.id,
        dataType:'json',
        success:function (res) {
            if (res.data != null){
                var data = res.data;
                var state = "";
                var orderDte = "";
                
                var tableHead = "<table class='table table-condensed'>"+
                				"<tr>"
       								+"<td>视频</td>"
       								+"<td>单价</td>"
       								+"<td>时间</td>"
       								+"<td>订单状态</td>"
       							+"</tr>"
                $("#orderRow").append(tableHead);
                
                for (var i = 0 ; i < data.length ;i++){
                    var order = data[i];
                    var price = order.totalFee/100;
                    var createTime = ""+order.createTime;
                    orderDte = createTime.substring(0,10);
                    //判断订单状态号
                    if (order.state) {
                    	state = "完成交易";
                    } else{
                    	state = "订单未支付";
                    }
					var template = "";
                    if (i%2==1) {//奇数行不加背景样式   第一行是data[0]
                    	template = "<tr>"+
	       								"<td width='20%'>"+orderDte+"</td>"+
	       								"<td width='40%'>流水号:"+order.outTradeNo+"</td>"+
	       								"<td width='30%'>提单时间:"+createTime+"</td>"+
	       								"<td rowspan='2' width='10%'>"+state+"</td>"+
	       							"</tr>"+
	       							"<tr>"+
	       								"<td width='20%'>"+order.videoTitle+"</td>"+
	       								"<td>￥"+price+"</td>"+
	       								"<td>确认时间:"+order.notifyTime+"</td>"+
	       							"</tr>";
                    } else{
                    	template = "<tr class='active'>"+
	       								"<td width='20%'>"+orderDte+"</td>"+
	       								"<td width='40%'>流水号:"+order.outTradeNo+"</td>"+
	       								"<td width='30%'>提单时间:"+createTime+"</td>"+
	       								"<td rowspan='2' width='10%'>"+state+"</td>"+
	       							"</tr>"+
	       							"<tr class='active'>"+
	       								"<td width='20%'>"+order.videoTitle+"</td>"+
	       								"<td>￥"+price+"</td>"+
	       								"<td>确认时间:"+order.notifyTime+"</td>"+
	       							"</tr>";
                    }

                    $("#orderRow").append(template);
                    $("#orderRow").append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                }
            }else{
                alert("该账号下无订单");
            }
            $("#orderRow").append("</table>");
        }
    })
	
	$("#list1").show();
	$("#myModal").modal();
	
}

$(function () {

    //获取视频列表
    function get_list() {

        $.ajax({
            type:'get',
            url:host+'/api/v1/video/page?size=30&page=1',
            dataType:'json',
            success:function (res){
                var data = res.data;
                for (var i = 0 ; i < data.length ;i++){
                    //console.log(data[i]);
                    var video = data[i];
                    var price = video.price/100;

                    var template = "<div class='col-sm-6 col-md-3'><div class='thumbnail'>"+
                        "<img src='"+video.coverImg+"'alt='通用的占位符缩略图'>"+
                        "<div class='caption'><h3>"+video.title+"</h3><p>价格:"+price+"元</p>"+
                        "<p><a href='' onclick='save_order("+video.id+")' data-toggle='modal' data-target='#myModal' class='btn btn-primary' role='button'>立刻购买</a></p></div></div></div>"

                    $(".row").append(template);
                }

            }
        })

    }

    //获取微信扫描地址
    function get_wechat_login() {
        //获取当前页面地址
        var current_page = window.location.href;
        $.ajax({
            type:'get',
            url:host+'/api/v1/wechat/login_url?access_page='+current_page,
            dataType:'json',
            success:function (res){
                //console.log(res.data);
                $("#login").attr("href",res.data);
                global_login_url = current_page;
            }
        })
    }

    function get_params() {
        var url = window.location.search;//获取？后面的字符串
        var obj = new Object();
        if (url.indexOf("?")!=-1){
            var str = url.substr(1);
            //console.log(str);
            strs = str.split("&");
            for (var i = 0 ; i < strs.length; i++){
                //strs[i].split("=")[1]);
                obj[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
            }
        }
        //console.log(obj);
        return obj;
    }

    //设置头像和昵称
    function set_user_info(){
        var user_info = get_params();
        if (JSON.stringify(user_info) != '{}'){
            //对象不为空
            var name = user_info['name'];
            var head_img = user_info['head_img'];
            var token = user_info['token'];
            // console.log(name);
            // console.log(head_img);
            // console.log(token);

            $("#login").html(name);
            $("#head_img").attr("src",head_img);
            $.cookie('token',token,{expires:7,path:"/"});
        }
    }




    get_list();
    get_wechat_login();
    get_params();
    set_user_info();

})
