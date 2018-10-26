//控制层
app.controller('typeTemplateContrller' ,function($scope,$controller,typeTemplateService,brandService,specificationService){
    $controller('baseController',{$scope:$scope});//继承

    $scope.searchEntity = {};//定义搜索对象

    //重新加载
    $scope.reloadList=function(){
        //切换页码
        $scope.queryAllTypeTemplateListByConditionPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    };

    //按条件查询分页规格列表
    $scope.queryAllTypeTemplateListByConditionPage=function(current,size){
        typeTemplateService.queryAllTypeTemplateListByConditionPage(current,size,$scope.searchEntity).success(
            function (response) {
                $scope.paginationConf.totalItems = response.total;//总记录数
                $scope.list = response.rows;
            }
        )
    };

    //根据id查询
    $scope.selectByOne=function(id){
        typeTemplateService.selectByOne(id).success(
            function (response) {
                $scope.entity=response;
                //转换品牌列表
                $scope.entity.brandIds=JSON.parse($scope.entity.brandIds);
                //转换规格列表
                $scope.entity.specIds=JSON.parse($scope.entity.specIds);
                //转换扩展属性
                $scope.entity.customAttributeItems= JSON.parse($scope.entity.customAttributeItems);
            }
        );
    };

    //添加与修改
    $scope.save=function () {
        var object=null;
       if ($scope.entity.id!=null){//如果有id
            object=typeTemplateService.update($scope.entity);//则执行修改方法
        }else{
           object=typeTemplateService.add($scope.entity);//添加方法
        }
        //
        object.success(
            function (response) {
                if (response.success){
                    //重新加载数据
                    $scope.reloadList();
                }
                alert(response.message);
            }
        )
    };

    //删除功能
    $scope.delete=function () {
        alert("删除");
        typeTemplateService.delete($scope.selectIds).success(
            function (request) {
                if (request.success){
                    //重新加载列表
                    $scope.reloadList();
                }
                alert(request.message);
            }
        )
    };




    //品牌列表
    $scope.brandList={data:[]};
    //规格列表
    $scope.specList={data:[]};

    //品牌下拉列表数据
    $scope.selectOptionList=function(){
      brandService.selectOptionList().success(
          function (response) {
              $scope.brandList={data:response};
          }
      );
    };
    //规格下拉列表数据
    $scope.selectSpecificationOptionList=function(){
        specificationService.selectSpecificationOptionList().success(
            function (response) {
                $scope.specList={data:response};
            }
        );
    };

    //添加行
    $scope.addTableRow=function () {
        $scope.entity.customAttributeItems.push({});
    };

    //删除行
    $scope.deleteTableRow=function (index) {
        $scope.entity.customAttributeItems.splice(index,1);//删除
    }


});