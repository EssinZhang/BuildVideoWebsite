var host = 'http://127.0.0.1:8081';
var global_login_url = "";
var user;
//点击购买的跳转
function save_order(videoId) {
    var token = $.cookie("token");
    if (!token || token == ""){
        //请登录
        window.location.href=global_login_url;
    }
    //下单接口
    var url = host + "/api/v1/order/add?token="+token+"&video_id="+videoId+"&user_id="+user.id;
    $("#pay_img").attr("src",url);
    window.location.href=global_login_url;
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