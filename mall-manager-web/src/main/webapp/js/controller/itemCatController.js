 //控制层 
app.controller('itemCatController' ,function($scope, $controller, itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}

	// 定义一个变量记录当前页面的parentId
	$scope.parentId = 0;

	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			// 给新增实体的parentId赋值
            $scope.entity.parentId = $scope.parentId;
			serviceObject=itemCatService.add($scope.entity);//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询
                    $scope.findByParentId($scope.parentId);
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){
		if(confirm("确定删除吗？")) {
            //获取选中的复选框
            itemCatService.dele($scope.selectIds).success(
                function(response){
                    if(response.success){
                        // $scope.reloadList();//刷新列表
                        // 查询此下级列表
                        $scope.findByParentId($scope.parentId);
                        $scope.selectIds=[];
                    } else {
                    	alert(response.message);
                        $scope.selectIds=[];
					}
                }
            );
        }
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}
		);
	}
    
	// 根据父id查询商品分类列表
	$scope.findByParentId = function (parentId) {
		// 改变当前页面的parentId
        $scope.parentId = parentId;
        // 获取当前页和页大小
        page = $scope.paginationConf.currentPage;
        rows = $scope.paginationConf.itemsPerPage;
        // 给parentId赋值
        $scope.searchEntity = {parentId:parentId};
        // 发起查询
        itemCatService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    // 默认为1级
    $scope.grade = 1;
	// 设置级别
	$scope.setGrade = function(value){
		$scope.grade = value;
	}

	// 读取列表
	$scope.selectList = function(p_entity) {
		// 如果为1级，则2级和3级的列表都为空
		if($scope.grade == 1) {
			$scope.entity_1 = null
			$scope.entity_2 = null;
		}
		// 如果为2级
		if($scope.grade == 2) {
			$scope.entity_1 = p_entity;
			$scope.entity_2 = null;
		}
		// 如果为3级
		if($scope.grade == 3) {
			$scope.entity_2 = p_entity;
		}
		// 查询此下级列表
		$scope.findByParentId(p_entity.id);
	}
});	
