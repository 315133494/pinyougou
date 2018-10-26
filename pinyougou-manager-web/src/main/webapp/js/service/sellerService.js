app.service('sellerService',function ($http) {


    //按条件查询分页列表
    this.queryAllSellerListByConditionPage=function (current,size,searchEntity) {
        return $http.post("../seller/queryAllSellerListByConditionPage.do?current=" + current + "&size=" + size, searchEntity);
    };
    //根据id查询
    this.selectByOne=function (id) {
        return $http.get("../seller/selectByOne.do?id="+id);
    };
    //添加
    this.add=function (entity) {
        return $http.post("../seller/add.do",entity);
    };

    //修改
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

    //更新状态
    this.updateStatus=function (id, status) {
        return $http.get("../seller/updateStatus.do?id="+id+"&status="+status);
    }
});
