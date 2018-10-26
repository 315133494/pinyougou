app.service('brandService',function ($http) {


    //按条件查询分页品牌列表
    this.queryAllBrandListByConditionPage=function (current,size,searchEntity) {
        return $http.post("../brand/selectBrandListByConditionPage.do?current=" + current + "&size=" + size, searchEntity);
    };
    //根据id查询品牌
    this.selectByOne=function (id) {
        return $http.get("../brand/selectByOne.do?id="+id);
    };
    //添加品牌
    this.add=function (entity) {
        return $http.post("../brand/add.do",entity);
    };

    //修改品牌
    this.update=function (entity) {
        return $http.post("../brand/update.do",entity);
    };

    //删除功能
    this.delete=function (selectIds) {
        return $http.get("../brand/delete.do?ids="+selectIds);
    };

    //下拉 列表数据
    this.selectOptionList=function () {
        return $http.get("../brand/selectOptionList.do");
    }
});
