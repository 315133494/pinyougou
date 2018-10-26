app.controller('contentCategoryController', function ($scope,$controller, contentCategoryService) {
    $controller('baseController', {$scope: $scope});//继承
    $scope.reloadList = function () {
        //切换页码
        $scope.queryAllContentCategoryListByConditionPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    $scope.searchEntity = {};//定义搜索对象

    //按条件查询分页品牌列表
    $scope.queryAllContentCategoryListByConditionPage = function (current, size) {
        contentCategoryService.queryAllContentCategoryListByConditionPage(current, size, $scope.searchEntity).success(
            function (response) {
                $scope.paginationConf.totalItems = response.total;//总记录数
                $scope.list = response.rows;
            }
        )
    };
    //根据id查询
    $scope.selectByOne=function(id){
        contentCategoryService.selectByOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    };


    //保存
    $scope.save=function () {
        var object=null;
        if ($scope.entity.id!=null){//更新
            object=contentCategoryService.update($scope.entity);
        } else {//新增
            object=contentCategoryService.add($scope.entity)
        }
        object.success(
            function (response) {
                if (response.success){
                    $scope.reloadList();
                }
                alert(response.message);
            }
        );
    };

    //删除
    $scope.delete=function () {
      contentCategoryService.delete($scope.selectIds).success(
          function (response) {
              if (response.success) {
                  $scope.reloadList();
              }
              alert(response.message);
          }
      );

    }
});