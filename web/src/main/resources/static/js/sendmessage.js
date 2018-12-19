function sendCheckRule(start, end, verifier, ruleContent) {
    var msg = '<ul>\n' +
        '        <li style="font-size:16px;color:#393D49;">开始时间：' + start + ' 截止时间：' + end + '</li>\n' +
        '        <li style="font-size:14px;color:#01AAED;">审核人：' + verifier + '</li>\n' +
        '        <li style="font-size:14px">规则：' + ruleContent + '</li>\n' +
        '      </ul>'

    return msg
}

function sendProjectRemark() {
    var remark

    return remark

}