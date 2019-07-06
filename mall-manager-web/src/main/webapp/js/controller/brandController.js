// 品牌控制层
app.controller('brandController', function ($scope, $controller, brandService) {
    // 继承
    $controller('baseController', {$scope: $scope})

    // 查询所有数据
    $scope.findAll = function () {
        brandService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }


    $scope.findPage = function (page, size) {
        brandService.findPage(page,size).success(
            function (response) {
                // 列表数据
                $scope.list = response.rows;
                // 更新列表总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    }

    // 重新加载数据
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

    // 保存品牌信息
    $scope.save = function () {
        // 服务层对象
        var serviceObject;
        if($scope.entity.id != null) {
            // 如果有id，则更新品牌信息
            serviceObject = brandService.updateBrand($scope.entity);
        } else {
            serviceObject = brandService.addBrand($scope.entity);
        }
        serviceObject.success(
            function (response) {
                if(response.success) {
                    // 重新加载列表数据
                    $scope.reloadList();
                } else {
                    alert(response.message)
                }
            }
        );
    }

    // 根据id查询品牌实体
    $scope.findBrandById = function(id) {
        brandService.findBrandById(id).success(
            function (response) {
                $scope.entity = response;
            });
    }

    // 删除品牌信息
    $scope.deleteBrand = function () {
        if(confirm("确认删除记录吗？")) {
            brandService.deleteBrand($scope.selectIds).success(
                function (response) {
                    if(response.success) {
                        // 刷新列表数据
                        $scope.reloadList();
                    }
                }
            )
        }
    }

    // 带条件查询
    // 每次都初始化searchEntity为空
    $scope.searchEntity={};
    $scope.search = function (page, rows) {
        brandService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                // 显示当前页数
                $scope.list = response.rows;
                // 更新总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        )
    }

});