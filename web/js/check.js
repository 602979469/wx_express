var checkPhone = function (phone) {

    var myreg = /^[1][3,4,5,7,8,9][0-9]{9}$/;
    if (!myreg.test(phone)) {
        layer.msg("手机号格式不正确");
        return false;
    }
    return true;
}

var checkExpressNumber = function (number) {
    var regNumber = /^\d{6,32}$/;
    if (!regNumber.test(number)) {
        layer.msg("快递单号格式不对");
        return false;
    }
    return true;
}

var checkLoginUsername=function (username) {
    var patrn = /^(\w){6,20}$/;
    if (!patrn.test(username)){
        layer.msg("用户名格式不对!");
        return false;
    }
    return true;
}

var checkerUsername = function (username) {
    var regName = /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9_]){2,20}$/
    if (!regName.test(username)) {
        layer.msg("收件人姓名格式不对");
        return false;
    }
    return true;
}

var checkIdCardNumber = function (card) {
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (reg.test(card) === false) {
        layer.msg("身份证输入不合法");
        return false;
    }
    return true;
}

var checkPassword = function (password) {
    var patrn = /^(\w){6,20}$/;
    if (!patrn.test(password)){
        layer.msg("密码格式不对!");
        return false;
    }
    return true;
}

var checkName=function(name){
    var patrn=/^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/;
    if (!patrn.test(name)){
        layer.msg("姓名格式不合法!");
        return false;
    }
    return true;
}
