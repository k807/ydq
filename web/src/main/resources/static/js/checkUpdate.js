layui.use('jquery', function () {

    $ = layui.$;

    $(function () {
        getNow()
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
                    layui.$("#red-dot").append("<span class=\"layui-badge-dot\"></span>")
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



