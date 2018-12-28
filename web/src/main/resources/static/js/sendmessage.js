function sendCheckRule(start, end, verifier, ruleContent) {
    return '<ul>' +
        '<li style="font-size:16px;color:#393D49;">开始时间：' + start + '</li>' +
        '<li style="font-size:16px;color:#393D49;">截止时间：' + end + '</li>' +
        '<li style="font-size:15px;">审核人：' + verifier + '</li>' +
        '<li style="font-size:15px">审核规则：' + ruleContent + '</li></ul>'
}
function addNewRule(start, end,major, ruleContent ){
    return '<ul>' +
        '<li style="font-size:16px;color:#393D49;">开始时间：' + start + '</li>' +
        '<li style="font-size:16px;color:#393D49;">截止时间：' + end + '</li>' +
        '<li style="font-size:15px;">所属专业：' + major + '</li>' +
        '<li style="font-size:15px">申报规则：' + ruleContent + '</li></ul>'
}

function sendProjectRemark() {
    var remark

    return remark

}