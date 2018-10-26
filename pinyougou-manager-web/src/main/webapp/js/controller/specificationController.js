//控制层
app.controller('specificationController' ,function($scope,$controller,specificationService){
    $controller('baseController',{$scope:$scope});//继承

    $scope.searchEntity = {};//定义搜索对象

    //重新加载
    $scope.reloadList=function(){
        //切换页码
        $scope.queryAllSpecificationListByConditionPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    };

    //按条件查询分页规格列表
    $scope.queryAllSpecificationListByConditionPage=function(current,size){
        specificationService.queryAllSpecificationListByConditionPage(current,size,$scope.searchEntity).success(
            function (response) {
                $scope.paginationConf.totalItems = response.total;//总记录数
                $scope.list = response.rows;
            }
        )
    };


    //新增行
    $scope.addTableRow=function(){
        $scope.entity.specificationOptionList.push({});
    };

    //删除行
    $scope.deleteTableRow=function(index){
        $scope.entity.specificationOptionList.splice(index,1);
    };
    //根据id查询
    $scope.selectByOne=function(id){
        specificationService.selectByOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        );
    };

    //添加规格与修改规格
    $scope.save=function () {
        var object=null;
        if ($scope.entity.specification.id!=null){//如果有id
            object=specificationService.update($scope.entity);//则执行修改方法
        }else{
            object=specificationService.add($scope.entity);//添加方法
        }
        object.success(
            function (response) {
                if (response.success){
                    //重新加载数据
                    $scope.reloadList();
                }
                alert(response.message);
            }
        )
    }
    //删除功能
    $scope.delete=function () {
        specificationService.delete($scope.selectIds).success(
            function (request) {
                if (request.success){
                    //重新加载列表
                    $scope.reloadList();
                }
                alert(request.message);
            }
        )
    }

    //下拉列表数据
    this.selectOptionList=function () {
        return $http.get("../specification/selectOptionList.do");
    }

});