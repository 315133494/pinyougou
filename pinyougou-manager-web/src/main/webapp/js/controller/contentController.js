app.controller('contentController', function ($scope,$controller, contentService,contentCategoryService,uploadService) {
    $controller('baseController', {$scope: $scope});//继承
    $scope.reloadList = function () {
        //切换页码
        $scope.queryAllContentListByConditionPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    $scope.searchEntity = {};//定义搜索对象
    //状态
    $scope.statusArray=['无效','有效'];
    //广告分类数组，用来显示分类名称
    $scope.contentCategoryList=[];
    //按条件查询分页品牌列表
    $scope.queryAllContentListByConditionPage = function (current, size) {
        contentService.queryAllContentListByConditionPage(current, size).success(
            function (response) {
                $scope.paginationConf.totalItems = response.total;//总记录数
                $scope.list = response.rows;
            }
        )
    };

    //查询全部分类名称
    $scope.queryAllContentCategoryList=function(){
      contentCategoryService.queryAllList().success(
          function (response) {
              $scope.contentcategory=response;
              for (var i=0;i<response.length;i++){
                  $scope.contentCategoryList[response[i].id]=response[i].name;
              }
          }
      )
    };


    //根据id查询
    $scope.selectByOne=function(id){
        contentService.selectByOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    };


    //保存
    $scope.save=function () {
        var object=null;
        if ($scope.entity.id!=null){//更新
            object=contentService.update($scope.entity);
        } else {//新增
            object=contentService.add($scope.entity)
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
        contentService.delete($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.selectIds=[];
                    $scope.reloadList();
                }
                alert(response.message);
            }
        );

    };
    //上传图片
    $scope.uploadFile=function () {
        uploadService.uploadFile().success(
            function (response) {
                if (response.success){//如果上传成功
                    $scope.entity.pic=response.message;//设置文件地址
                }else{
                    alert(response.message);
                }
            }
        ).error(function () {
            alert("上传发生错误！");
        })
    };

    //开启有效与屏蔽
    $scope.status=function (status) {
        contentService.status($scope.selectIds,status).success(
            function (response) {
                if (response.success) {
                    $scope.selectIds=[];
                    $scope.reloadList();
                }
                alert(response.message);
            }
        )
    }
});