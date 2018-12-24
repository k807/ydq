layui.use('jquery', function () {

    var $ = layui.jquery;
    manager = "pm/messageTable"
    user = "pm/messageList"
    $(function () {
        getNow()
        userIn()
    })

    function checkUpdate() {
        $.ajax({
            type: "GET",
            url: "pm/checkUpdate",
            dataType: "json",
            success: function (result) {
                var updateTime = new Date(result.date).getTime();
                // console.log(updateTime)
                // console.log(nowTime)
                if (updateTime < nowTime) {
                    // alert("updateTime is older nowTime");
                } else {
                    // alert("updateTime is newer nowTime");
                    $("#red-dot").append("<span class=\"layui-badge-dot\"></span>")
                }
            },
            error: function (result) {
                console.log(result)
            }
        })
    }

    function userIn() {

        $.ajax({
            type: "POST",
            url: "/authority/getSelfInfo",
            dataType: "json",
            success: function (result) {
                var role = result.object.role.name
                switch (role) {
                    case "manager":
                        $("#red-dot").attr("lay-href", manager)
                        break
                    case "teacher":
                        $("#red-dot").attr("lay-href", user)
                        break
                    case "expert":
                        $("#red-dot").attr("lay-href", user)
                        break
                    default:
                        $("#red-dot").attr("lay-href", user)
                }
            },
            error: function (result) {
                console.log(result)
            }
        })

    }

    setInterval(checkUpdate, 60000);
});


function getNow() {
    nowTime = new Date().getTime()
}



