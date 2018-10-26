app.service('sellerService',function ($http) {


    //按条件查询分页品牌列表
    this.selectSellerListByConditionPage=function (current,size,searchEntity) {
        return $http.post("../seller/selectSellerListByConditionPage.do?current=" + current + "&size=" + size, searchEntity);
    };
    //根据id查询品牌
    this.selectByOne=function (id) {
        return $http.get("../seller/selectByOne.do?id="+id);
    };
    //添加品牌
    this.add=function (entity) {
        return $http.post("../seller/add.do",entity);
    };

    //修改品牌
    this.update=function (entity) {
        return $http.post("../seller/update.do",entity);
    };

    //删除功能
    this.delete=function (selectIds) {
        return $http.get("../seller/delete.do?ids="+selectIds);
    }

    //下拉 列表数据
    this.selectOptionList=function () {
        return $http.get("../seller/selectOptionList.do");
    }
});
