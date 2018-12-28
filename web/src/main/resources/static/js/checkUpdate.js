layui.use('jquery', function () {

    var $ = layui.jquery;
    $(function () {
        // getNow()
        checkNewPM()
    })

    function checkNewPM() {
        $.ajax({
            type: "GET",
            url: "pm/checkNewPM",
            dataType: "json",
            success: function (result) {
                console.log(result)
                var num = result.object.count
                if (num > 0) {
                    $("#red-dot").append("<span class=\"layui-badge-dot\">" + num + "</span>")
                }
            },
            error: function (result) {
                console.log(result)
            }
        })
    }

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

    // setInterval(checkUpdate, 300000);
});


function getNow() {
    nowTime = new Date().getTime()
}



