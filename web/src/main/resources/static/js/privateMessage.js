layui.use(['element', 'jquery'], function () {
    var element = layui.element //Tab的切换功能，切换事件监听等，需要依赖element模块
        , admin = layui.admin
    element.on('tab(filter)', function (data) {
        console.log(this); //当前Tab标题所在的原始DOM元素
        console.log(data.index); //得到当前Tab的所在下标
        console.log(data.elem); //得到当前的Tab大容器
    });
    $ = layui.jquery
    role = null
    $(function () {
        userIn()
    })
});

function userIn() {
    $.ajax({
        type: "POST",
        url: "/authority/getSelfInfo",
        dataType: "json",
        success: function (result) {
            var roleName = result.object.role.name
            switch (roleName) {
                case "manager":
                    role = 1
                    break
                case "teacher":
                    role = 2
                    break
                case "expert":
                    role = 3
                    break
                default:
            }
        },
        error: function (result) {
            console.log(result)
        }
    })
}

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
        layui.laytpl(temp).render({list: list, role: role}, function (html) {
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
        layui.laytpl(temp).render({list: list, role: role}, function (html) {
            messageList.innerHTML += html;
        });
    });
}


layui.use(['jquery', 'layer', 'table', 'form'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var table = layui.table;
    var form = layui.form;
    var tableIns = table.render({
        elem: '#table',
        toolbar: '#toolbar',
        height: 'full-200',
        cols: [[
            {field: 'title', title: "标题", sort: true},
            {field: 'type', title: "消息类型", sort: true},
            {field: 'date', title: "发送日期", sort: true},
            {field: 'read', title: "是否已读", sort: true},
            {field: 'content', title: "消息内容", event: "msgContent"},
            {field: 'sender', title: "发送人", sort: true},
            {
                field: 'receiver', title: "接收人", sort: true
                , templet: function (row) {
                    return row.reveiver == null ? "所有用户" : row.reveiver
                }
            },
            {
                field: 'remark', title: '备注', event: "remarkDetail"
                , templet: function (row) {
                    var obj
                    if (row.remark != "") {
                        obj = JSON.parse(row.remark)
                        if (obj.hasOwnProperty("projectId")) {
                            var id = "项目id:" + obj.projectId
                            var a = "<a >" + id + "</a>"
                            return a
                        } else if (obj.hasOwnProperty("ruleId")) {
                            var id = "入口id:" + obj.ruleId
                            var a = "<a >" + id + "</a>"
                            return a
                        }
                    } else
                        return row.remark
                }
            },
            {fixed: 'right', title: '操作', align: 'center', toolbar: '#bar'}
        ]],
        text: {
            none: '暂无相关数据'
        },
        page: true,
        limit: 10,
        limits: [10, 20, 30],
        url: "/pm/getPMTable",
        initSort: {field: 'date', type: 'desc'},
        response: {
            statusCode: 200
        },
        parseData: function (res) {
            return {
                "code": res.statusCode,
                "data": res.object,
                "count": res.count,
            };
        }
    });
    table.on('tool(table)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'del':
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
                break;
            case 'remarkDetail':
                var obj = JSON.parse(data.remark)
                if (obj.hasOwnProperty("projectId")) {
                    newTab('/project/getDetails/' + obj.projectId, obj.projectName)
                } else if (obj.hasOwnProperty("ruleId")) {
                    newTab('/project/declare.htm?rule=' + obj.ruleId + '&publisher=' + obj.publisher, obj.ruleTitle)
                }
                break;
            case 'msgContent':
                var title = data.title
                var content = data.content
                layer.open({
                    type: 1,
                    title: title,
                    content: '\<\div style="padding:20px;">' + content + '\<\/div>',
                    area: ['420px', '240px'], //宽高
                });

                break;
            default:
                break
        }
    });
    form.on('submit(search)', function (data) {
        tableIns.reload({
            url: '/pm/queryPM',
            where: {
                type: data.field.type,
                title: data.field.title,
                receiver: data.field.receiver
            },

            page: {
                curr: 1
            }
        });
        return false;
    });
    form.on('select(type)', function (data) {
        tableIns.reload({
            url: '/pm/queryPM',
            where: {
                type: data.value,
                title: $("#title").val(),
                receiver: $("#receiver").val()
            },

            page: {
                curr: 1
            }
        });
    });


    function newTab(url, tit) {
        if (top.layui.index) {
            top.layui.index.openTabsPage(url, tit)
        } else {
            window.open(url)
        }
    }
});





