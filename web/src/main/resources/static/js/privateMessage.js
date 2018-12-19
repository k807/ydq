layui.use(['element', 'jquery'], function () {
    var element = layui.element //Tab的切换功能，切换事件监听等，需要依赖element模块
        , admin = layui.admin
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


layui.use(['jquery', 'table', 'form'], function () {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var tableIns = table.render({
        elem: '#table',
        cols: [[
            // {type: 'checkbox', fixed: 'left'},
            // {field: 'id', hide: true},
            {field: 'title', title: "标题", sort: true},
            {field: 'type', title: "消息类型", sort: true},
            {field: 'date', title: "发送日期", sort: true},
            {field: 'content', title: "消息内容"},
            {field: 'sender', title: "发送人", sort: true},
            {field: 'receiver', title: "接收人", sort: true},
            // {field: 'remark', title: "备注"},
            {
                field: 'remark', title: '备注'
                , templet: function (row) {
                    var obj = JSON.parse(row.remark)
                    if (obj.hasOwnProperty("projectId")) {
                        var id = "项目id:" + obj.projectId
                        var a = "<a onclick=\"newTab('/project/getDetails/'+obj.projectId,obj.projectName)\">" + id + "</a>"
                        return a
                    } else
                        return row.remark
                }
            },
            {fixed: 'right', title: '操作', align: 'center', toolbar: '#bar'}
        ]],
        page: true,
        limit: 10,
        limits: [10, 20, 30],
        url: "/pm/getPMTable",
        response: {
            statusCode: 200
            , countName: 'count'
        },
        parseData: function (res) {
            return {
                "code": res.statusCode,
                "data": res.object,
                "count": res.count,
            };
        } //用于对分页请求的参数：page、limit重新设定名称
        , request: {
            pageName: 'page' //页码的参数名称，默认：page
            , limitName: 'limit' //每页数据量的参数名，默认：limit
        }
    });
    table.on('tool(table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除么', function (index) {
                layer.close(index);
                $.ajax({
                    type: "get",
                    url: "/pm/delete",
                    data: {id: data.id},
                    dataType: 'json',
                    success: function (data) {
                        if (data.statusCode === "200") {
                            obj.del();
                            layer.msg("删除成功");
                        } else {
                            layer.msg("删除失败");
                        }
                    }
                })
            });
        }
    });

    function newTab(url, tit) {
        if (top.layui.index) {
            top.layui.index.openTabsPage(url, tit)
        } else {
            window.open(url)
        }
    }
});





