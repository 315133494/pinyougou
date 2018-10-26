app.controller('brandController', function ($scope,$controller, brandService) {
    $controller('baseController',{$scope:$scope});//继承
    $scope.reloadList = function () {
        //切换页码
        $scope.queryAllBrandListByConditionPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    $scope.searchEntity = {};//定义搜索对象

    //按条件查询分页品牌列表
    $scope.queryAllBrandListByConditionPage = function (current, size) {
        brandService.queryAllBrandListByConditionPage(current,size,$scope.searchEntity).success(
            function (response) {
                $scope.paginationConf.totalItems = response.total;//总记录数
                $scope.list = response.rows;
            }
        )
    };

    //添加品牌与修改品牌
    $scope.save = function () {
        var object=null;
        if ($scope.entity.id!=null){//如果有id
            object=brandService.update($scope.entity);//则执行修改方法
        }else{
            object=brandService.add($scope.entity);//添加方法
        }
        object.success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();
                }
                alert(response.message);
            })
    };

    //根据id查询品牌
    $scope.selectByOne=function (id) {
        brandService.selectByOne(id).success(
            function (request) {
                $scope.entity=request;
            }
        )
    };

    //删除功能
    $scope.delete=function () {
        brandService.delete($scope.selectIds).success(
            function (response) {
                if (response.success){

                    //重新加载列表
                    $scope.reloadList();
                }
                alert(response.message);
            }
        )
    }

});