app.controller('sellerController', function ($scope,$controller, sellerService) {
    $controller('baseController',{$scope:$scope});//继承
    $scope.reloadList = function () {
        //切换页码
        $scope.queryAllSellerListByConditionPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    $scope.searchEntity = {};//定义搜索对象

    //按条件查询分页列表
    $scope.queryAllSellerListByConditionPage = function (current, size) {
        sellerService.queryAllSellerListByConditionPage(current,size,$scope.searchEntity).success(
            function (response) {
                $scope.paginationConf.totalItems = response.total;//总记录数
                $scope.list = response.rows;
            }
        )
    };

    //添加
    $scope.add = function () {
        sellerService.add($scope.entity).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    location.href='shoplogin.html';
                }else{
                    alert(response.message);
                }

            })
    };

    //根据id查询
    $scope.selectByOne=function (id) {
        sellerService.selectByOne(id).success(
            function (request) {
                $scope.entity=request;
            }
        )
    };

    //删除功能
    $scope.delete=function () {
        sellerService.delete($scope.selectIds).success(
            function (request) {
                if (request.success){
                    //重新加载列表
                    $scope.reloadList();
                }
                alert(request.message);
            }
        )
    }

});