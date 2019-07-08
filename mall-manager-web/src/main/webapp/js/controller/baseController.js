// 品牌控制层
app.controller('baseController', function ($scope) {
    // 重新加载数据
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

    //分页控件配置
    //currentPage:当前页
    //totalItems:总记录数
    //itemsPerPage:每页记录数
    //perPageOptions:分页选项
    //onChange:当页码变更后自动触发的方法
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();//重新加载
        }
    };

    // 定义一个数组用于保存选择的id信息
    $scope.selectIds = [];
    // 每次选择都更新数组的信息
    $scope.updateSelection = function($event, id) {
        // 如果是被选中，则增加到数组
        if($event.target.checked) {
            $scope.selectIds.push(id);
        } else {
            var index = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index, 1);
        }
    }

    // 提取json字符串数据中的某个属性，返回拼接字符串，以逗号分隔
    $scope.jsonToString = function(jsonString, key) {
        // 将json字符串转换成json对象
        var json = JSON.parse(jsonString);
        var value = "";
        for(var i = 0; i < json.length; i++) {
            if(i > 0) {
                value += ",";
            }
            value += json[i][key];
        }
        return value;
    }
});