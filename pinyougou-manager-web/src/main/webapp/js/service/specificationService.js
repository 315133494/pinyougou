app.service('specificationService',function ($http) {
    //按条件查询分页规格列表
    this.queryAllSpecificationListByConditionPage=function (current, size,searchEntity) {
        return $http.post("../specification/queryAllSpecificationListByConditionPage.do?current="+current+"&size="+size,searchEntity);
    };

    //添加规格
    this.add=function (entity) {
        return $http.post("../specification/add.do",entity);
    };
    this.update=function (entity) {
        return $http.post("../specification/update.do",entity);
    };
    //根据id查询
    this.selectByOne=function (id) {
        return $http.get("../specification/selectByOne.do?id="+id);
    }
    //删除功能
    this.delete=function (selectIds) {
        return $http.get("../specification/delete.do?ids="+selectIds);
    }

    //下拉 列表数据
    this.selectSpecificationOptionList=function () {
        return $http.get("../specification/selectSpecificationOptionList.do");
    }
});