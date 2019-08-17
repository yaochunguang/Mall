//控制层
app.controller('goodsController', function ($scope, $controller, $location, goodsService, uploadService, itemCatService, typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        var id = $location.search()['id'];
        if(null == id) {
            return ;
        }
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                // 向富文本编辑器添加商品介绍
                editor.html(response.goodsDesc.introduction);
                // 显示图片列表
                $scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages);
                // 商品扩展属性
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.entity.goodsDesc.customAttributeItems);
                // 规格列表
                $scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);
            }
        );
    }

    // 根据规格名称和选项名称返回是否被勾选
    $scope.checkAttributeValue = function(specName, optionName) {
        var items = $scope.entity.goodsDesc.specificationItems;
        var object =  $scope.searchObjectByKey(items,'attributeName',specName);
        if(null == object) {
            return false;
        } else {
            if(object.attributeValue.indexOf(optionName)>=0){
                return true;
            }else{
                return false;
            }
        }
    }

    //保存
    $scope.save = function () {
        // 获取富文本编辑器中的商品描述
        $scope.entity.goodsDesc.introduction = editor.html();
        var serviceObject;//服务层对象
        if ($scope.entity.goods.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    // 新增成功之后，清空实体
                    $scope.entity = {};
                    // 清空富文本编辑器
                    editor.html('');
                    location.href="goods.html";//跳转到商品列表页
                } else {
                    alert(response.message);
                }
            }
        );
    }

    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    // 上传文件
    $scope.uploadFile = function () {
        uploadService.uploadFile().success(function (response) {
            if (response.success) {//如果上传成功，取出url
                $scope.image_entity.url = response.message;//设置文件地址
            } else {
                alert(response.message);
            }
        }).error(function () {
            alert("上传发生错误");
        });
    };

    $scope.entity = {goods: {}, goodsDesc: {itemImages: []}};//定义页面实体结构
    //添加图片列表
    $scope.add_image_entity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }

    //列表中移除图片
    $scope.remove_image_entity = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    }

    // 一级分类下拉框
    $scope.selectItemCat1List = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1List = response;
            }
        );
    }

    // 二级分类下拉框
    // 一级分类下拉框改变的时候，触发二级下拉框改变
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        // 根据选择的值查询二级分类
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List = response;
            }
        );
    });


    // 三级分类下拉框
    // 二级分类下拉框改变的时候，触发三级下拉框改变
    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        // 根据选择的值查询二级分类
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List = response;
            }
        );
    });

    // 读取模板id
    // 当三级分类改变的时候，触发模板id的改变
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId = response.typeId;
            })
    });

    $scope.entity = {goodsDesc: {itemImages: [], specificationItems: []}};

    // 当模板id发生变化的时候，品牌下拉框发现改变
    $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        // 查询品牌列表及扩展属性
        typeTemplateService.findOne(newValue).success(
            function (response) {
                // 获取类型模板
                $scope.typeTemplate = response;
                // 品牌列表
                $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
                if($location.search()['id'] == null) {
                    // 扩展属性
                    $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
                }
                // SKU列表，规格列转换
                for(var i=0; i<$scope.entity.itemList.length; i++) {
                    $scope.entity.itemList[i].spec = JSON.parse( $scope.entity.itemList[i].spec);
                }
            }
        );
        // 查询规格列表
        typeTemplateService.findSpecList(newValue).success(
            function (response) {
                $scope.specList = response;
            }
        )
    });

    $scope.updateSpecAttribute = function ($event, name, value) {
        var object = $scope.searchObjectByKey(
            $scope.entity.goodsDesc.specificationItems, 'attributeName', name);
        if (object != null) {
            if ($event.target.checked) {
                object.attributeValue.push(value);
            } else {
                //取消勾选
                // object.attributeValue.splice( object.attributeValue.indexOf(value ) ,1);
                // 移除选项
                //如果选项都取消了，将此条记录移除
                if (object.attributeValue.length == 0) {
                    $scope.entity.goodsDesc.specificationItems.splice(
                        $scope.entity.goodsDesc.specificationItems.indexOf(object), 1);
                }
            }
        } else {
            $scope.entity.goodsDesc.specificationItems.push(
                {"attributeName": name, "attributeValue": [value]});
        }
    }

    //创建SKU列表
    $scope.createItemList = function () {
        $scope.entity.itemList = [{spec: {}, price: 0, num: 99999, status: '0', isDefault: '0'}];//初始
        var items = $scope.entity.goodsDesc.specificationItems;
        for (var i = 0; i < items.length; i++) {
            $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);
        }
    }

    //添加列值
    addColumn = function (list, columnName, conlumnValues) {
        var newList = [];//新的集合
        for (var i = 0; i < list.length; i++) {
            var oldRow = list[i];
            for (var j = 0; j < conlumnValues.length; j++) {
                var newRow = JSON.parse(JSON.stringify(oldRow));//深克隆
                newRow.spec[columnName] = conlumnValues[j];
                newList.push(newRow);
            }
        }
        return newList;
    }

    // 定义商品状态
    $scope.status = ['未审核','审核通过', '审核未通过', '关闭' ];

    // 定义商品分类列表
    $scope.itemCatList = [];
    // 查询商品分类列表信息
    $scope.findItemCatList = function() {
        itemCatService.findAll().success(
            function (response) {
                for(var i=0; i<response.length; i++) {
                    // 根据对应的id设置上对应的商品名称
                    $scope.itemCatList[response[i].id]=response[i].name;
                }
            }
        );
    }
});	
