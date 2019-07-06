// 品牌服务层
app.service('brandService', function ($http) {
    // 读取所有数据
    this.findAll = function() {
        return $http.get('../brand/findAll.do');
    }

    // 分页查询数据
    this.findPage = function(page, size) {
        return $http.get('../brand/findPage.do?page=' + page + '&rows=' + size);
    }

    // 增加品牌
    this.addBrand = function(entity) {
        return $http.post('../brand/addBrand.do', entity);
    }

    // 更新品牌
    this.updateBrand = function(entity) {
        return $http.post('../brand/updateBrand.do', entity);
    }

    // 根据id查询品牌实体
    this.findBrandById = function(id) {
        return $http.get('../brand/findBrandById.do?id=' + id);
    }

    // 删除品牌信息
    this.deleteBrand = function(ids) {
        return $http.get('../brand/deleteBrandById.do?ids=' + ids);
    }

    // 搜索
    this.search = function(page, rows, searchEntity) {
        return $http.post('../brand/search.do?page=' + page + '&rows=' + rows, searchEntity);
    }

});
