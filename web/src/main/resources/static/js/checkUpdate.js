layui.use('jquery', function () {

    $ = layui.jquery;
    $(function () {
        // getNow()
        checkNewPM()
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

    // setInterval(checkUpdate, 300000);
});

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
                $("#pmList").append("<span class=\"layui-badge\">" + num + "</span>")
            }
        },
        error: function (result) {
            console.log(result)
        }
    })
}

function cleanDot() {
    $("#red-dot").find(".layui-badge-dot").remove()
    $("#pmList").find(".layui-badge").remove()
}

function getNow() {
    nowTime = new Date().getTime()
}



