app.controller('contentController', function ($scope,$controller, contentService) {

    $scope.contentList=[];//广告集合
    $scope.selectByCategoryId=function(categoryId){
        contentService.selectByCategoryId(categoryId).success(
            function(response){
                $scope.contentList[categoryId]=response;
            }
        );

    };
    //搜索跳转
    $scope.search=function () {
        location.href="http://localhost:9104/search.html#?keywords="+$scope.keywords;
    }
});