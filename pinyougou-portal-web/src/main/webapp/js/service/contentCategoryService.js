app.service('contentCategoryService',function ($http) {
   //查询
    this.queryAllContentCategoryListByConditionPage=function (current, size, entity) {
       return $http.post("../contentCategory/queryAllContentCategoryListByConditionPage.do?current=" + current + "&size=" + size, entity);
   };
    //查询全部列表
    this.queryAllList=function () {
        return $http.get("../contentCategory/queryAllList.do");
    };

    //根据id查询
    this.selectByOne=function (id) {
        return $http.get("../contentCategory/selectByOne.do?id="+id);
    };

    //新增
    this.add=function (entity) {
        return $http.post("../contentCategory/add.do",entity);
    };

    this.update=function (entity) {
        return $http.post("../contentCategory/update.do?",entity);
    };

    //删除
    this.delete=function (ids) {
        return $http.get("../contentCategory/delete.do?ids="+ids);
    };


});