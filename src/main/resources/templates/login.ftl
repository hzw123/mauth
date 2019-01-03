<#include "commons/macro.ftl">
<@commonHead/>
<title>${systemInfo.name}登录</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="${ctx}/css/login/iconfont/style.css" type="text/css" rel="stylesheet">
<style type="text/css">
</style>
</head>

<body style="background:url(${ctx}/css/login/images/bg.jpg) no-repeat;">
    <div class="container wrap1" style="height:450px;">
            <h2 class="mg-b20 text-center">${systemInfo.name }</h2>
            <div class="col-sm-8 col-md-5 center-auto pd-sm-50 pd-xs-20 main_content">
                <p class="text-center font16">用户登录</p>
                <form id="frm" action="${ctx}/login" name="frm" method="post">
                    <div class="form-group mg-t20">
                        <i class="icon-user icon_font"></i>
                        <input type="text" class="login_input" id="username" name="username" placeholder="请输入用户名" />
                    </div>
                    <div class="form-group mg-t20">
                        <i class="icon-lock icon_font"></i>
                        <input type="password" class="login_input"  id="password" name="password" class="password" placeholder="请输入密码" />
                    </div>
                    <div style="submit" class="login_btn" id="sbmId">登 录</div>
               </form>
        </div><!--row end-->
    </div><!--container end-->
           
</body>

<script type="text/javascript">
    $('#sbmId').keydown(function(e){
        if(e.keyCode==13){
            $('#frm')[0].submit(); //处理事件
        }
    });
    $("#sbmId").click(function () {
        var flag=true;
        var name=$("#username").val();
        var password=$("#password").val();
        if(name==null||name.length<=0){
            alert("请输入用户名！");
            flag=false;
        }
        if(password==null||password.length<=0){
            alert("请输入密码！");
            return false;
        }
        var data={
           username:name,
           password:password
        };
        if(flag){
            $.ajax({
                url:"/login",
                type:"post",
                data:data,
                dataType:"json",
                success:function(result){
                    location="/";
                },
                error:function(result){
                    alert("用户名或密码错误！");
                }
            });

        }
    });
</script>
</html>
