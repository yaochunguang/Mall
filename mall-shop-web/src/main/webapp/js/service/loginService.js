// 前端：登录服务层
app.service('loginService', function ($http) {

    // 获取登录人名称
    this.getLoginName = function () {
        return $http.get('../login/getLoginName.do');
    }
});