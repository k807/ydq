
function getTime() {
    nowTime = new Date().getTime()
}

function checkUpdate() {
    layui.$.ajax({
        type: "GET",
        url: "pm/findAll",
        dataType: "json",
        success: function (result) {
            json = result
            var updateTime = new Date(json[json.length-1].date).getTime();
            if (updateTime < nowTime) {
                // alert("updateTime is older nowTime");
                // layui.$("#red-dot").append("<span class=\"layui-badge-dot\"></span>")
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

setInterval(checkUpdate, 5000);
