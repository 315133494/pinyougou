app.service('itemCatService',function ($http) {
    /**
     * 根据id查询上一级分类
     * @param parentId
     */
    this.selectByParentId=function (parentId) {
       return $http.get("../itemCat/selectByParentId.do?parentId="+parentId);
    };
    //查询全部
    this.selectAll=function () {
        return $http.get("../itemCat/selectAll.do");
    };
    //新增
    this.add=function (entity) {
        return $http.post("../itemCat/add.do",entity);
    };

    //修改
    this.update=function (entity) {
        return $http.post("../itemCat/update.do",entity);
    };
    //根据id查询
    this.selectByOne=function (id) {
        return $http.get("../itemCat/selectByOne.do?id="+id);
    };
    //删除功能
    this.delete=function (selectIds) {
        return $http.get("../itemCat/delete.do?ids="+selectIds);
    };
});