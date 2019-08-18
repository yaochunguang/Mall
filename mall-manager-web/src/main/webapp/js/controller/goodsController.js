 //控制层 
app.controller('goodsController' ,function($scope, $controller, $location, goodsService, itemCatService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	/*//查询实体
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}*/
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

	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	// 定义状态
	$scope.status = ['未审核', '审核通过', '审核未通过', '关闭'];

	// 定义商品分类列表
	$scope.itemCatList = [];
	// 查询商品分类列表
	$scope.findItemCatList = function() {
        itemCatService.findAll().success(
        	function (response) {
				for(var i = 0; i < response.length; i++) {
                    $scope.itemCatList[response[i].id] = response[i].name;
				}
            }
		);
	}
	
	// 商品审核
	$scope.updateStatus = function (status) {
        goodsService.updateStatus($scope.selectIds, status).success(
        	function (response) {
				// 操作成功
        		if(response.success){
                    alert(response.message);
        			// 刷新列表
					$scope.reloadList();
					// 清空ID集合
					$scope.selectIds = [];
				} else {
        			alert(response.message);
				}
            }
		)
    }
    
});	
