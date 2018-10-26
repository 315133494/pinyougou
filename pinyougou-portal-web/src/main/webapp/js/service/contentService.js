app.service('contentService', function ($http) {
    //根据分类ID查询广告列表
    this.selectByCategoryId = function (categoryId) {
        return $http.get("content/selectByCategoryId.do?categoryId=" + categoryId);
    }


});