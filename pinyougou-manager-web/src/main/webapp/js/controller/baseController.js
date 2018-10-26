app.controller('baseController', function ($scope) {
    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            //重新加载
            $scope.reloadList();
        }
    };

    //更新复选
    $scope.selectIds=[];//选中的ID集合
    $scope.updateSelection=function ($event, id) {
        if ($event.target.checked){//如果被选中，则添加到数组中
            $scope.selectIds.push(id);
        }else {
            var index = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index,1);//其中index表示删除的位置，1表示删除一个
        }
    };

    $scope.ifChecked=function (id) {
        //如果Id出现在数组中，则需要返回true
        var index=$scope.selectIds.indexOf(id);
        if (index==-1){
            return false;
        }else {//如果id没有出现在selectIds数组中需要返回false
            return true;
        }
    };

    $scope.ifCheckedAll=function () {
        //$scope.list中的对象的id 是否都在 $scope.selectIds中
        //如果全中，则返回true
        //如果有一个还在，就返回false
        for (var i = 0; i < $scope.list.length; i++) {
            if ($scope.selectIds.indexOf($scope.selectIds[i])==-1){
                return false;
            }
        }
        return true;
    };

    //全选与取消全选
    $scope.selectAll=function ($event) {
        if ($event.target.checked){
            for (var i = 0; i < $scope.list.length; i++) {
                //当前页面的数据的id放到数组中
                if ($scope.selectIds.indexOf($scope.list[i].id)==-1){
                    $scope.selectIds.push($scope.list[i].id);
                }
            }
        }else{
            for (var i=0;i<$scope.list.length;i++){
                var index=$scope.selectIds.indexOf($scope.list[i].id);
                $scope.selectIds.splice(index);
            }
        }
    };

    //提取json字符串数据中某个属性，返回拼接字符串 逗号分隔
    $scope.jsonToString=function (jsonString,key) {
        var json=JSON.parse(jsonString);//将字符串转换为json对象
        var value="";
        for (var i=0;i<json.length;i++){
            if (i>0){
                value+=" , ";
            }
            value+=json[i][key];
        }
        return value;
    }

});