app.controller('itemCatController',function ($scope,$controller, itemCatService) {
    $controller('baseController',{$scope:$scope});//继承

    //面包屑设置
    $scope.grade=1;//默认为1级
    //设置级别
    $scope.setGrade=function(value){
        $scope.grade=value;
    };

    //读取列表
    $scope.selectList=function(p_entity){
      if ($scope.grade==1){//如果为1级
          $scope.entity_1=null;
          $scope.entity_2=null;
      }
      if ($scope.grade==2){//如果为2级
          $scope.entity_1=p_entity;
          $scope.entity_2=null;
      }
      if ($scope.grade==3){//如果为3级
          $scope.entity_2=p_entity;
      }

      $scope.selectByParentId(p_entity.id);	//查询此级下级列表

    };

    $scope.parentId=0;//上级ID

   $scope.selectByParentId=function (parentId) {
       $scope.parentId=parentId;//记住上级ID
       itemCatService.selectByParentId(parentId).success(
           function (response){
               $scope.list=response;
           }
       )
   };

    //新增 与 修改
    $scope.save=function () {
        var obj=null;
        if ($scope.entity.id!=null){
            obj=itemCatService.update($scope.entity);//修改
        }else {
            $scope.entity.parentId=$scope.parentId;//赋予上级ID
            obj=itemCatService.add($scope.entity);//新增
        }
        obj.success(
            function (response) {
                //重新查询
                if(response.success) {
                    $scope.selectByParentId($scope.parentId);//重新加载
                }
                alert(response.message);
            }
        )
    };

    //根据id查询
    $scope.selectByOne=function (id) {
        itemCatService.selectByOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    };
    //删除功能
    $scope.delete=function () {
        alert("delete");
        itemCatService.delete($scope.selectIds).success(
            function (request) {
                if (request.success){
                    //读取列表
                    $scope.selectByParentId($scope.parentId);//重新加载
                }
                alert(request.message);
            }
        )
    }
});