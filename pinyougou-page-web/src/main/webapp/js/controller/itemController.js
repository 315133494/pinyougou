//��Ʒ��ϸҳ�����Ʋ㣩
app.controller('itemController',function($scope,$http){
	//��������
	$scope.addNum=function(x){
		$scope.num=$scope.num+x;
		if($scope.num<1){
			$scope.num=1;
		}
	};



	//��¼�û�ѡ������� 
	$scope.specilication={};
	//�û�ѡ����
	$scope.selectSpecification=function(name,value){
		alert(name);
		$scope.specitionItems[name]=value;
		searchSku();//��ȡsku
	};

$scope.specificationItems={};//�û�ѡ�����Ʒ���

	//�ж�ĳ���ѡ���Ƿ��û�ѡ��
	$scope.isSelected=function(name,value){
		if($scope.specificationItems[name]==value){
			return true;
		}else{
			return false; 
		}
	};



	$scope.sku={};//��ǰѡ���sku
	//����Ĭ��sku
	$scope.loadSku=function(){
		$scope.sku=skuList[0];
		$scope.specificationItems=JOSN.parse(JSON.stringify($scope.sku.spec));
	};

	//ƥ����������
	matchObject=function(map1,map2){
		for (var k in map1){
			if(map1[k]!=map2[k]){
				return false;
			}
		}
		for (var k in map1){
			if(map2[k]!=map1[k]){
				return false;
			}
		}

		return true;
	};

	//��ѯsku
	searchSku=function(){
		for(var i=0;i<skuList.length;i++ ){
			if( matchObject(skuList[i].spec ,$scope.specificationItems ) ){
				$scope.sku=skuList[i];
				return ;
			}			
		}	
		$scope.sku={id:0,title:'--------',price:0};//���û��ƥ���	

	};

	$scope.addToCart=function(){
		alert('skuid'+$scope.sku.id);
		
	};

    //������Ʒ�����ﳵ
    $scope.addToCart=function(){
        $http.get('http://localhost:9107/cart/addGoodsToCartList.do?itemId='
            + $scope.sku.id +'&num='+$scope.num ,{'withCredentials':true}).success(
            function(response){
                if(response.success){
                    location.href='http://localhost:9107/cart.html';//��ת�����ﳵҳ��
                }else{
                    alert(response.message);
                }
            }
        );
    }


});