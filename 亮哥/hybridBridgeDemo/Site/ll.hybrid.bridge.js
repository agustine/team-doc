(function(){
	var isIOS = /iP(ad|hone|od)/.test(navigator.userAgent);

	//同步调用
	function exec(service, action, args) {
		if(isIOS){
			return LlWebBridge.callNative(false, service, action, args);
		}else{
			var cmd = {service: service, action: action};
			args = args || {};

			var result_str = prompt(JSON.stringify(cmd), JSON.stringify(args));
			var result = JSON.parse(result_str);
			if (result.status == LlWebBridge.CALL_STATUS.SUCCESS) {
				return result.message;
			} else {
				console.error("service:" + service + " action:" + action + " error:"
					+ result.message);
			}
		}
	}

	//异步调用
	function exec_asyn(service, action, args, callbacks) {
		LlWebBridge.callNative(true, service, action, args, callbacks);		
	}

	var LlWebBridge = {
		idCounter : 0,
		REQUEST_MESSAGE: [],
		OUTPUT_RESULTS : {},

		CALL_STATUS:{
			SUCCESS: 0,
			FAIL: 1,
			CANCEL: 2
		},

		CALLBACKS: {},

		getRequestMessage: function(){
			var message = this.REQUEST_MESSAGE.shift();
			if (!message) {
				return "";
			}
			return JSON.stringify(message);
		},

		callNative : function(isAsync, service, action, args, callbacks) {
			var key = "ID_" + (++this.idCounter);

			var request_message = {
				'requestId': key,
				'service' : service,
				'action' : action,
				'args': args || {},
				'async': isAsync
			}
			callbacks = callbacks || {};
			
			this.CALLBACKS[key] = {}
	    	if (typeof callbacks.success === 'function') this.CALLBACKS[key][this.CALL_STATUS.SUCCESS] = callbacks.success;
	    	if (typeof callbacks.fail === 'function') this.CALLBACKS[key][this.CALL_STATUS.FAIL] = callbacks.fail;
	    	if (typeof callbacks.cancel === 'function') this.CALLBACKS[key][this.CALL_STATUS.CANCEL] = callbacks.cancel;

	    	this.REQUEST_MESSAGE.push(request_message);
			if(!isIOS){
				window.LyWebProxy.setRequestMessage(this.getRequestMessage());
			}

			var iframe = document.createElement("iframe");
			iframe.setAttribute("src", "llyhybrid://ready?id=" + key);
			console.log('iframe appendChild');
			document.documentElement.appendChild(iframe);
			iframe.parentNode.removeChild(iframe);
			iframe = null;

			console.log('output:' + this.OUTPUT_RESULTS[key])
			return this.OUTPUT_RESULTS[key]; //同步调用时返回值

		},

		//native执行完毕后将调用此函数处理回调
		callBackJs : function(result, key, isAsync) {
	        console.log('callbackjs')
	        var obj = JSON.parse(result);

	        var message = obj.message;
	        if(typeof message === "object"){
	        	message = JSON.stringify(message)
	        }else{
	        	message = "'" + message + "'"
	        }
	        
	        var status = obj.status;
	        if(isAsync){ 	//异步回调
	        	if(this.CALLBACKS[key] && this.CALLBACKS[key][status]){
		        	setTimeout(function(){
		        		llWebBridge.CALLBACKS[key][status](message);
		        		delete llWebBridge.CALLBACKS[key];
		        	}, 0);
		        } 
	        }else{	//供同步调用使用
	        	this.OUTPUT_RESULTS[key] = obj.message;	
	        }
		}
	};


	//business interface
	var LlNative = {};
	LlNative.getNetType = function(){
		return exec('Device', 'getNetType');
	}

	LlNative.pay = function(args, callbacks){
		exec_asyn('Payment', 'pay', args, callbacks);
	}

	window.llWebBridge = LlWebBridge;
	window.llNative = LlNative;

})();