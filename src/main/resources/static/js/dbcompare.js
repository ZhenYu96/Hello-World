var vm = new Vue({
	el:'#app',
	data:{
        info: {},
        sourceDBList: [],
        targetDBList: []
	},
	methods: {
		changeSourceEnv: function(){
			$.ajax({
				type: "GET",
			    url: "sys/db/database?env="+vm.info.sourceEnv,
                contentType: "application/json",
			    success: function(data){
			    	vm.sourceDBList = data.database;
				}
			});
		},
		changeTargetEnv: function(){
			$.ajax({
				type: "GET",
			    url: "sys/db/database?env="+vm.info.targetEnv,
                contentType: "application/json",
			    success: function(data){
			    	vm.targetDBList = data.database;
				}
			});
		},
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

