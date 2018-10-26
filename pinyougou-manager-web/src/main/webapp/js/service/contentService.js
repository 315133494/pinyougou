app.service('contentService',function ($http) {
    //查询
    this.queryAllContentListByConditionPage=function (current, size) {
        return $http.post("../content/queryAllContentListByConditionPage.do?current=" + current + "&size=" + size);
    };

    //根据id查询
    this.selectByOne=function (id) {
        return $http.get("../content/selectByOne.do?id="+id);
    };

    //新增
    this.add=function (entity) {
        return $http.post("../content/add.do",entity);
    };

    this.update=function (entity) {
        return $http.post("../content/update.do?",entity);
    };

    //删除
    this.delete=function (ids) {
        return $http.get("../content/delete.do?ids="+ids);
    };

    //开启与屏蔽
    this.status=function (ids, status) {
        return $http.get("../content/status.do?ids="+ids+"&status="+status);
    };

});