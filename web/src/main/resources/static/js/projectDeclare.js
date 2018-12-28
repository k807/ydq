layui.use(['jquery', 'form', 'upload','layer'], function () {
    var $ = layui.$;
    var form = layui.form;
    var layer=layui.layer;
    var upload = layui.upload;

    var pics=[];
    var file={};

    upload.render({
        elem: '#upload-img'
        , url: '/file/upload'
        ,accept: 'images'
        ,multiple:true
        ,exts:'jpg|png|gif|bmp|jpeg'
        , choose: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#img-box').append('<div class="imgDiv">\n' +
                    '                     <img src="'+result+'" alt="'+file.name+'" class="layui-upload-img">\n' +
                    '                     <img src="/static/assets/layui/images/delete.png" class="delete" />\n' +
                    '                 </div>');
                deleteBtn();
            });
        }
        ,done:function(res){
            if (res.statusCode==="200"){
                var pic={};
                pic.id=res.object.id;
                pics.push(pic)
            }
        }
        , error: function () {
            //演示失败状态，并实现重传
            $('div > .imgDiv:last').remove();
        }
    });
    upload.render({
        elem:'#upload-file',
        url: '/file/upload',
        accept:'file',
        exts:'doc|docx|pdf|txt|ppt|pptx',
        choose:function(obj){
            obj.preview(function(index,file,result){
                $('#file').val(file.name);
            })
        },
        done:function(res){
            if (res.statusCode==="200") {
                file = res.object;
                $('#upload-file').attr('type','hidden');
                $('#delete-file').attr('type','button');
            }
        }
    });

    $('#delete-file').click(function(){
        $.get('/file/delete?id='+file.id,function(res){
            if (res.statusCode==='200'){
                $('#file').val('');
                file={};
            }
            if (JSON.stringify(file)==='{}'){
                $('#delete-file').attr('type','hidden');
                $('#upload-file').attr('type','button');
            }
        },'json')
    });

    function saveOrSubmit(action, data) {
        var json = {};
        json.name = data.field.name;
        json.level = data.field.level;
        json.manager={'id':window.location.search.split('&')[1].split('=')[1]};
        json.phone = data.field.phone;
        json.major = data.field.major;
        json.email = data.field.email;
        json.commitmentPics = pics;
        json.declaration = {"id":file.id};
        json.entrance={"id":window.location.search.split('&')[0].split('=')[1]};
        var members = [];
        $('input[name^=members]').each(function (index, element) {
            members.push(element.value);
        });
        json.members = JSON.stringify(members);
        $.ajax({
            type: "post",
            url: "/project/new/" + action,
            contentType: 'application/json',
            data: JSON.stringify(json),
            dataType: "json",
            success: function (res) {
                openNewTab("/project/getDetails/"+res.object,data.field.name);
            }
        });
    }

    $.get('/authority/getSelfInfo',function (res) {
        form.val('form',{
            'leader':res.object.nick
        })
    });

    form.on('submit(submit)',function(data){
        if (pics.length===0)
            layer.tips('请上传立项承诺书！！！','#upload-img',{
                tipsMore:true
            });
        if (JSON.stringify(file)==='{}')
            layer.tips('请上传申报书！！！','#upload-file',{
                tipsMore:true
            });
        if (pics.length!==0&&JSON.stringify(file)!=='{}')
            saveOrSubmit('submit',data);
        return false;
    });
    form.on("submit(save)",function(data){
        if (pics.length===0)
            layer.tips('请上传立项承诺书！！！','#upload-img',{
                tipsMore:true
            });
        if (JSON.stringify(file)==='{}')
            layer.tips('请上传申报书！！！','#upload-file',{
                tipsMore:true
            });
        if (pics.length!==0&&JSON.stringify(file)!=='{}')
            saveOrSubmit('save',data);
        return false;
    });

    function deleteBtn(){
        var imgDiv=$('.imgDiv');
        $(imgDiv).mouseenter(function () {
            $(this).find(".delete").show();
        });
        $(imgDiv).mouseleave(function () {
            $(this).find(".delete").hide();
        });
    }

    var body=$('body');

    $(body).on('click','.delete',function(e){
        var index=$('.img-list .imgDiv').index(this.parent);
        $(this).parent('div').remove();
        $.ajax({
            type:'get',
            url:'/file/delete',
            data:'id='+pics[index].id,
            dataType:'json',
            success:function(data){
                pics.splice(index,1);
            }
        })
    });

    var maxMembers=5;
    var memberBox=$("#member-box");
    var addButton=$("#add-member");
    var x=memberBox.length;
    var memberCount=1;
    $(addButton).on('click', function () {
        if (x<=maxMembers){
            memberCount++;
            $(memberBox).append('<div class="layui-row"  style="margin-top: 8px">\n' +
                '                            <div class="layui-col-xs10">\n' +
                '                                <label class="layui-form-label" style="border-width: 0; background-color: #ffffff"></label>\n' +
                '                                <div class="layui-input-block">\n' +
                '                                    <input type="text" name="members[]" lay-required placeholder="请输入成员名字" autocomplete="off" class="layui-input" id="member_'+memberCount+'">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="layui-col-xs2">\n' +
                '                                <div style="margin-left: 10px; float: right;">\n' +
                '                                    <input type="button" value="删除" id="del-member" class="layui-btn layui-btn-danger remove-class">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                        </div>');
            x++;
        }
        return false;
    });
    $(body).on('click','.remove-class',function(e){
        if (x>1){
            $(this).parent('div').parent('div').parent('div').remove();
            x--;
        }
        return false;
    });

    function openNewTab(url,title) {
        if(top.layui.index){
            top.layui.index.openInThisTab(url,title)
        }else{
            window.open(url)
        }
    }
});