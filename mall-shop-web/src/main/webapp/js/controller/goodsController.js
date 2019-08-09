 //控制层 
app.controller('goodsController' ,function($scope, $controller, goodsService, uploadService, itemCatService, typeTemplateService){
	
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
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){
		var serviceObject;//服务层对象
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update($scope.entity); //修改
		}else{
			serviceObject=goodsService.add($scope.entity);//增加
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

    // 新增商品
    $scope.add=function(){
		// 获取富文本编辑器中的商品描述
		$scope.entity.goodsDesc.introduction = editor.html();
        goodsService.add($scope.entity).success(
            function(response){
                if(response.success){
                    //重新查询
                    alert(response.message);
                    // 新增成功之后，清空实体
                    $scope.entity = {};
                    // 清空富文本编辑器
                    editor.html('');
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

	// 上传文件
    $scope.uploadFile=function(){
        uploadService.uploadFile().success(function(response) {
            if(response.success){//如果上传成功，取出url
                $scope.image_entity.url=response.message;//设置文件地址
            }else{
                alert(response.message);
            }
        }).error(function() {
            alert("上传发生错误");
        });
    };

    $scope.entity={goods:{},goodsDesc:{itemImages:[]}};//定义页面实体结构
    //添加图片列表
    $scope.add_image_entity=function(){
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }

    //列表中移除图片
    $scope.remove_image_entity=function(index){
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    }

    // 一级分类下拉框
	$scope.selectItemCat1List = function(){
        itemCatService.findByParentId(0).success(
        	function(response) {
        		$scope.itemCat1List = response;
			}
		);
	}

    // 二级分类下拉框
	// 一级分类下拉框改变的时候，触发二级下拉框改变
	$scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
		// 根据选择的值查询二级分类
        itemCatService.findByParentId(newValue).success(
            function(response) {
                $scope.itemCat2List = response;
            }
        );
    });


    // 三级分类下拉框
	// 二级分类下拉框改变的时候，触发三级下拉框改变
    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        // 根据选择的值查询二级分类
        itemCatService.findByParentId(newValue).success(
            function(response) {
                $scope.itemCat3List = response;
            }
        );
    });

    // 读取模板id
	// 当三级分类改变的时候，触发模板id的改变
	$scope.$watch('entity.goods.category3Id' , function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
        	function (response) {
			$scope.entity.goods.typeTemplateId = response.typeId;
        })
    });

	// 当模板id发生变化的时候，品牌下拉框发现改变
	$scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(
        	function (response) {
				// 获取类型模板
				$scope.typeTemplate = response;
				// 品牌列表
                $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
            }
		)
    })
	
});	
