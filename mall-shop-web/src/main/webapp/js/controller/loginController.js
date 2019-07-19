app.controller('loginController', function ($scope, $controller, loginService) {

    // 获取登录人信息
    $scope.showLoginName = function () {
        loginService.getLoginName().success(
            function (response) {
            $scope.loginName = response.loginName;
        })
    }
});