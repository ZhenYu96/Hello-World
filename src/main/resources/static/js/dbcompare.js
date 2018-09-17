var vm = new Vue({
	el:'#app',
	data:{
		q:{
			tableName: null
		},
        info: {}
	},
	methods: {
		compare: function() {
			if(vm.validator()){
                return ;
            }
			location.href = "sys/db/diff?sourceEnv="+vm.info.sourceEnv+"&sourceDB="+vm.info.sourceDB+"&targetEnv="+vm.info.targetEnv+"&targetDB="+vm.info.targetDB;
			/*
			$.ajax({
				type: "POST",
			    url: "sys/generator/code",
                contentType: "application/json",
			    data: JSON.stringify(vm.info),
			    success: function(response, status, request){
			    	
				}
			});
			*/
		},
		validator: function () {
            if(isBlank(vm.info.sourceEnv)){
                alert("来源环境不能为空");
                return true;
            }
            if(isBlank(vm.info.sourceDB)){
                alert("来源数据库不能为空");
                return true;
            }
            if(isBlank(vm.info.targetEnv)){
                alert("目标环境不能为空");
                return true;
            }
            if(isBlank(vm.info.targetDB)){
                alert("目标数据库不能为空");
                return true;
            }
        }
	}
});

