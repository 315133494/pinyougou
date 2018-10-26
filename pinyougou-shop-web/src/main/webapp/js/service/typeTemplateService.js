app.service('typeTemplateService',function ($http) {
    //按条件查询分页模板列表
    this.queryAllTypeTemplateListByConditionPage=function (current, size,searchEntity) {
        return $http.post("../typeTemplate/queryAllTypeTemplateListByConditionPage.do?current="+current+"&size="+size,searchEntity);
    };
    //添加
    this.add=function (entity) {
        return $http.post("../typeTemplate/add.do",entity);
    };
    this.update=function (entity) {
        return $http.post("../typeTemplate/update.do",entity);
    };
    //根据id查询
    this.selectByOne=function (id) {
        return $http.get("../typeTemplate/selectByOne.do?id="+id);
    };
    //删除功能
    this.delete=function (selectIds) {
        return $http.get("../typeTemplate/delete.do?ids="+selectIds);
    };
    //查询规格列表
    this.selectSpecList=function (id) {
        return $http.get("../typeTemplate/selectSpecList.do?id="+id);
    }

});