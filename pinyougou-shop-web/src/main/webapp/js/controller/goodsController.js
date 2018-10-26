app.controller('goodsController', function ($scope, $controller,$location, goodsService,uploadService,itemCatService,typeTemplateService) {
    $controller('baseController', {$scope: $scope});//继承

    $scope.searchEntity = {};//定义搜索对象
    $scope.itemCatList=[];//商品分类列表
    //加载商品分类列表
    $scope.selectItemCatList=function(){
      itemCatService.selectAll().success(
          function (response) {
              for (var i=0;i<response.length;i++){
                  $scope.itemCatList[response[i].id]=response[i].name;
              }
          }
      )
    };

    $scope.reloadList = function () {
        //切换页码
        $scope.queryAllGoodsListByConditionPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    $scope.searchEntity = {};//定义搜索对象
    //商品状态
    $scope.status=['未审核','审核中','审核通过','已驳回'];

    //上架状态
    $scope.isMarketables=['否','是'];
    //按条件查询分页品牌列表
    $scope.queryAllGoodsListByConditionPage = function (current, size) {
        goodsService.queryAllGoodsListByConditionPage(current,size,$scope.searchEntity).success(
            function (response) {
                $scope.paginationConf.totalItems = response.total;//总记录数
                $scope.list = response.rows;
            }
        )
    };

    //根据id查询实体
    $scope.selectByOne=function(){
        var id=$location.search()['id'];//获取参数值
        if (id == null){
            return;
        }

        goodsService.selectByOne(id).success(
            function (response) {
                $scope.entity=response;
                //向富文本编辑器添加商品介绍
                editor.html($scope.entity.goodsDesc.introduction);
                //显示图片x列表
                $scope.entity.goodsDesc.itemImages=JSON.parse($scope.entity.goodsDesc.itemImages);
                //显示扩展属性
                $scope.entity.goodsDesc.customAttributeItems= JSON.parse($scope.entity.goodsDesc.customAttributeItems);
                //规格
                $scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);
                //sku列表规格列转换
                for (var i=0;i<$scope.entity.itemList.length;i++){
                    $scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec);
                }
            }
        )
    };

    //根据规格名称和选项名称返回是否被勾选
    $scope.checkAttributeValue=function(specName,optionName){
        var items = $scope.entity.goodsDesc.specificationItems;
        var object = $scope.searchObjectByKey(items,'attributeName',specName);
        if (object == null) {
            return false;
        }else {
            if (object.attributeValue.indexOf(optionName)>=0){
                return true;
            } else {
                return false;
            }
        }
    };


    $scope.save = function () {
        //获得富文本中的值
        $scope.entity.goodsDesc.introduction=editor.html();
        var serviceObject;//服务层对象
        if ($scope.entity.goods.id != null) {//如果有id
            serviceObject=goodsService.update($scope.entity);//修改
        }else {
            serviceObject=goodsService.add($scope.entity);//增加
        }

        serviceObject.success(
            function (response) {
                if (response.success) {
                    //跳转到商品列表页
                    location.href="goods.html";
                    //$scope.reloadList();
                }
                alert(response.message);
            }
        )
    };

    /**
     * 上传图片
     */
    $scope.uploadFile=function () {
        uploadService.uploadFile().success(
            function (response) {
                if (response.success){//如果上传成功
                    $scope.image_entity.url=response.message;//设置文件地址
                }else{
                    alert(response.message);
                }
            }
        ).error(function () {
            alert("上传发生错误！");
        })
    };
    $scope.entity={goods:{},goodsDesc:{itemImages:[]}};//定义页面实体结构
    //添加图片列表
    $scope.add_image_entity=function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    };
    //移除
    $scope.remove_image_entity=function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    };

    //读取一级分类
    $scope.selectByParentId=function () {
        itemCatService.selectByParentId(0).success(
            function (response) {
                $scope.itemCat1List=response;
            }
        )
    };
    //读取二级分类
    $scope.$watch('entity.goods.category1Id',function (newValue, oldValue) {
        //根据选择的值，查询二级分类
        itemCatService.selectByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List=response;
            }
        )
    });
    //读取三级分类
    $scope.$watch('entity.goods.category2Id',function (newValue, oldValue) {
        //根据选择的值，查询二级分类
        itemCatService.selectByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List=response;
            }
        )
    });

    //三级分类选择后  读取模板ID
    $scope.$watch('entity.goods.category3Id',function (newValue, oldValue) {
        itemCatService.selectByOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId=response.typeId;//更新模板id
            }
        );
    });

    //模板id改变后，读取品牌列表
    $scope.$watch('entity.goods.typeTemplateId',function (newValue, oldValue) {

        typeTemplateService.selectByOne(newValue).success(
            function (response) {
                $scope.typeTemplate=response;//获取类型模板
                //将一个 JSON 字符串转换为对象
                //品牌列表
                $scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);
                //扩展属性
                //如果没有ID，则加载模板中的扩展数据
                if($location.search()['id']==null) {
                    $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
                }
            }
        );

        //查询规格列表
        typeTemplateService.selectSpecList(newValue).success(
            function (response) {
                $scope.specList=response;
            }
        );
    });
    $scope.entity={ goodsDesc:{itemImages:[],specificationItems:[]}  };

    $scope.updateSpecAttribute=function ($event, name, value) {
        var object=$scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',name);
        if (object != null) {
            if ($event.target.checked) {
                object.attributeValue.push(value);
            }else {//取消勾选
                object.attributeValue.splice(object.attributeValue.indexOf(value),1);//移除选项
                //如果选项都取消了，将此条记录移除
                if (object.attributeValue.length==0){
                    $scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object),1);
                }
            }
        }else {
            $scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]})
        }
    };

    //创建sku列表
    $scope.createItemList=function () {
        //初始化
        $scope.entity.itemList=[{spec:{},price:0,num:9999,status:'0',isDefault:'0'}];
        var items = $scope.entity.goodsDesc.specificationItems;
        for (var i=0;i<items.length;i++){
            $scope.entity.itemList= addColumn($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
        }
    };

    //添加列值
    addColumn=function (list, columnName, columnValues) {
        //新的集合
        var newList=[];
        for (var i = 0; i < list.length; i++) {
            var oldRow=list[i];
            for (var j = 0; j < columnValues.length; j++) {
                //深克隆
                var newRow=JSON.parse(JSON.stringify(oldRow));
                newRow.spec[columnName]=columnValues[j];
                newList.push(newRow);
            }
        }

        return newList;
    };

    //删除
    $scope.delete=function () {
      goodsService.delete($scope.selectIds).success(
          function (response) {
              if (response.success) {
                  //重新加载列表
                  $scope.reloadList();
              }
              alert(response.message);
          }
      )
    };
    //提交审核
    $scope.audit=function (status) {
        goodsService.audit($scope.selectIds,status).success(
            function (response) {
                if (response.success) {
                    //重新加载列表
                    $scope.reloadList();
                }
                alert(response.message);
            }
        )
    };

    //上架,下架
    $scope.isMarketable=function (status) {
        goodsService.isMarketable($scope.selectIds,status).success(
            function (response) {
                if (response.success) {
                    //重新加载列表
                    $scope.reloadList();
                }
                alert(response.message);
            }
        )
    }
});