app.service('goodsService',function ($http) {

    //按条件查询分页列表
    this.queryAllGoodsListByConditionPage=function (current,size,searchEntity) {
        return $http.post("../goods/queryAllGoodsListByConditionPage.do?current=" + current + "&size=" + size, searchEntity);
    };

    this.selectByOne=function (id) {
        return $http.get("../goods/selectByOne.do?id="+id);
    };

   this.add=function (entity) {
       return $http.post("../goods/add.do",entity);
   };
    this.update=function (entity) {
        return $http.post("../goods/update.do",entity);
    };
    this.delete=function (ids) {
        return $http.post("../goods/delete.do?ids="+ids);
    };
    //提交审核
    this.audit=function (ids,status) {
        return $http.get("../goods/audit.do?ids="+ids+"&status="+status);
    }
    this.isMarketable=function (ids,status) {
        return $http.get("../goods/isMarketable.do?ids="+ids+"&status="+status);
    }
});