layui.use(['jquery', 'table', 'laydate', 'form','layer'], function () {
    var $ = layui.$;
    var table = layui.table;
    var laydate = layui.laydate;
    var form = layui.form;
    laydate.render({
        elem: '#start',
        type: 'datetime'
    });
    laydate.render({
        elem: '#end',
        type: 'datetime'
    });
    var tableIns = table.render({
        id:'id,sex',
        pid:'pid,sex',
        elem: '#table',
        toolbar: '#toolbar',
        height: 'full-200',
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'name', title: "项目名称"},
            {field: 'leader', title: "负责人"},
            {
                field: 'stage', title: '所处阶段', templet: function (d) {
                    if (d.stage === 1)
                        return '中期检查';
                    else
                        return '结题验收';
                }
            },
            {
                field: 'createTime', title: "开始时间", templet: function (d) {
                    var date = new Date(d.createTime);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() ;
                }
            },
            {
                field: 'updateTime', title: "更新时间", templet: function (d) {
                    var date = new Date(d.updateTime);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() ;
                }
            },
            {
                field: 'status', title: "审核状态", templet: function (d) {
                    if (d.status === 0)
                        return '待审核';
                    else if (d.status === 1)
                        return '已通过';
                    else if (d.status === 2)
                        return '待整改';
                }
            },
            {field: 'verifier', title: "审核人"},
            {fixed: 'right', title: '操作', align: 'center', toolbar: '#bar'}
        ]],
        page: true,
        limit: 10,
        limits: [10, 20, 30],
        url: "/stage/getAll",
        response: {
            statusCode: 200,
        },
        parseData: function (res) {
            return {
                "code": res.statusCode,
                "data": res.object.data,
                "count": res.object.count
            };
        }
    });

    table.on('toolbar(table)', function (obj) {
        var stage = table.checkStatus(obj.config.id);
        if(obj.event === 'setRule'){
            if (stage.data.length !== 0) {
                var stageId = [];
                for (var i = 0; i < stage.data.length; i++) {
                    stageId.push(stage.data[i].id);
                }
                var id = stageId.join(',');
                var s = parseInt($("#stage").val());
                if(s === 1){
                    var t = '设置中期检查规则说明';
                }
                else if(s === 2){
                    var t = '设置结题验收规则说明';
                }
                layer.open({
                    type: 2,
                    title: t,
                    content: ['checkRule.html?id=' + id + '&stage=' + s , 'no'],
                    area: ['700px', '550px'],
                });
            }
            else {
                layer.msg('您还未选择任何项目');
            }
        }
    });

    table.on('tool(table)', function (obj) {
        var data = obj.data;
        var option = {};
        option.id = data.id;
        option.stage = data.stage;
        if (obj.event === 'check') {
            layer.prompt({
                formType: 2,    // 弹出文本层类型
                title: '项目阶段检查意见',    // 标题
                value:'',    // 可以设置文本默认值
                area: ['400px', '80px'],     // 设置弹出层大小
                btn: ['通过', '待整改','取消'],    // 自定义设置多个按钮
                yes: function(index, layero){
                    var value = layero.find(".layui-layer-input").val();
                    if(value===''){
                        if(option.stage===1){
                            value = '您的项目中期检查已通过！请准备开展后续工作。';
                        }
                        else if(option.stage===2){
                            value = '您的项目结题验收已通过！请准备开展后续工作。';
                        }
                    }
                    option.msg = value;
                    option.status = 1;
                    changeStatus(option);
                    layer.close(index);
                },
                btn2: function(index, elem){
                    var value = layui.jquery('#layui-layer'+index + " .layui-layer-input").val();
                    if(value===''){
                        if(option.stage===1){
                            value = '您的项目中期阶段检查未通过，进入待整改状态，请及时处理！';
                        }
                        else if(option.stage===2){
                            value = '您的项目结题验收未通过，进入待整改状态，请及时处理！';
                        }
                    }
                    option.msg = value;
                    option.status = 2;
                    changeStatus(option);
                    layer.close(index);
                },
                btnAlign: 'c',    // 设置按钮位置
            });
        }
        else if (obj.event==='view'){
            newTab('/project/getDetails/'+obj.data.pid,obj.data.name);
        }
    });
    function newTab(url,tit){
        if(top.layui.index){
            top.layui.index.openTabsPage(url,tit)
        }else{
            window.open(url)
        }
    }

    function changeStatus(option) {
        $.ajax({
            type: 'POST',
            url: '/stage/changeState',
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(option),
            dataType: 'json',
            success: function (data) {
                if (data.state === 400) {
                    layer.msg("审核失败");
                } else {
                    layer.msg("审核成功");
                    tableIns.reload();
                }
            }
        });
    }

    function reload(data){
        tableIns.reload({
            url: '/stage/getByConditions',
            where: {
                name: $('#name').val(),
                leader: $('#leader').val(),
                status: $('#status').val(),
                stage: $('#stage').val(),
                createTime:$('#start').val(),
                endTime: $('#end').val(),
            },
            page: {
                curr: 1
            }
        });
    }

    form.on('submit(search)', function (data) {
        reload(data);
        return false;
    });
    form.on('select(select)', function (data) {
        reload(data);
    });
});
