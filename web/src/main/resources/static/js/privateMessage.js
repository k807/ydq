layui.use(['element', 'jquery'], function () {
    var element = layui.element //Tab的切换功能，切换事件监听等，需要依赖element模块
        ,admin = layui.admin
    element.on('tab(filter)', function (data) {
        console.log(this); //当前Tab标题所在的原始DOM元素
        console.log(data.index); //得到当前Tab的所在下标
        console.log(data.elem); //得到当前的Tab大容器
    });


});

function getList() {
    layui.$.ajax({
        url: "getPMList",
        type: 'get',
        cache: false,
        dataType: 'json',//返回的数据类型
        success: function (result) {
            var notices = result.notices;
            var messages = result.messages;
            initNoticeList(notices || []);
            initMessageList(messages || []);
        },
        error: function (result) {
            console.log(result)
        }
    })
}

function initNoticeList(list) {
    layui.use(['laytpl'], function () {
        var temp = noticemessage.innerHTML;
        layui.laytpl(temp).render({list: list}, function (html) {
            noticelist.innerHTML += html;
        });
        layui.$("#noticelist").append("  <li class=\"layui-timeline-item\">\n" +
            "                    <i class=\"layui-icon layui-timeline-axis\"></i>\n" +
            "                    <div class=\"layui-timeline-content layui-text\">\n" +
            "                        <div class=\"layui-timeline-title\">过去</div>\n" +
            "                    </div>\n" +
            "                </li>")
    });
}

function initMessageList(list) {
    layui.use(['laytpl', 'jquery'], function () {
        var temp = privatemessage.innerHTML;
        layui.laytpl(temp).render({list: list}, function (html) {
            messageList.innerHTML += html;
        });
    });
}





