var fourSelectData={
	"校区":{val:"0",items:{
		"场馆":{val:"00",items:{
			"场地":{val:"000",items:{
				"场地号":"0000"
			}}
		}}
	}},
	"旗山校区":{val:"旗山校区",items:{
		"东区田径场":{val:"东区田径场",items:{
			"足球场":{val:"足球场",items:{
				"足球场1号场":"足球场1号场",
				"足球场2号场":"足球场2号场"
			}},
		}},
		"体训馆":{val:"体训馆",items:{
			"篮球馆":{val:"篮球馆",items:{
				"篮球馆1号场":"篮球馆1号场",
				"篮球场2号场":"篮球场2号场"
			}},
			"羽毛球场":{val:"羽毛球场",items:{
				"羽毛球场1号场":"羽毛球场1号场",
				"羽毛球场2号场":"羽毛球场2号场"
			}}
		}}
	}},
	"仓山校区":{val:"仓山校区",items:{
		"仓山体训馆":{val:"仓山体训馆",items:{
			"排球馆":{val:"排球馆",items:{
				"篮球馆1号场":"篮球馆1号场",
				"篮球场2号场":"篮球场2号场",
				"篮球场3号场":"篮球场3号场"
			}},
		}},
	}},

};
var defaults = {
	s1:'Select1',
	s2:'Select2',
	s3:'Select3',
	s4:'Select4',
	v1:"0",
	v2:"00",
	v3:"000",
	v4:"0000"
};
$(function(){
	fourSelect(defaults);
});
function fourSelect(config){
	var $s1=$("#"+config.s1);
	var $s2=$("#"+config.s2);
	var $s3=$("#"+config.s3);
	var $s4=$("#"+config.s4);
	var v1=config.v1?config.v1:null;
	var v2=config.v2?config.v2:null;
	var v3=config.v3?config.v3:null;
	var v4=config.v4?config.v4:null;
	$.each(fourSelectData,function(k,v){
		appendOptionTo($s1,k,v.val,v1);
	});
	$s1.change(function(){
		$s2.html("");
		if(this.selectedIndex==-1) return;
		var s1_curr_val = this.options[this.selectedIndex].value;
		$.each(fourSelectData,function(k,v){
			if(s1_curr_val==v.val){
				if(v.items){
					$.each(v.items,function(k,v){
						appendOptionTo($s2,k,v.val,v2);
					});
				}
			}
		});
		if($s2[0].options.length==0){appendOptionTo($s2,"...","",v2);}
		$s2.change();
	}).change();
	$s2.change(function(){
		$s3.html("");
		if(this.selectedIndex==-1) return;
		var s1_curr_val = $s1[0].options[$s1[0].selectedIndex].value;
		var s2_curr_val = this.options[this.selectedIndex].value;
		$.each(fourSelectData,function(k,v){
			if(s1_curr_val==v.val){
				if(v.items){
					$.each(v.items,function(k,v){
						if(s2_curr_val==v.val){
							if(v.items){
								$.each(v.items,function(k,v){
									appendOptionTo($s3,k,v.val,v3);
								});
							}
						}
					});
				}
			}
		});
		if($s3[0].options.length==0){appendOptionTo($s3,"...","",v3);}
		$s3.change();
	}).change();
	$s3.change(function(){
		$s4.html("");
		if(this.selectedIndex==-1) return;
		var s1_curr_val = $s1[0].options[$s1[0].selectedIndex].value;
		var s2_curr_val = $s2[0].options[$s2[0].selectedIndex].value;
		var s3_curr_val = this.options[this.selectedIndex].value;
		$.each(fourSelectData,function(k,v){
			if(s1_curr_val==v.val){
				if(v.items){
					$.each(v.items,function(k,v){
						if(s2_curr_val==v.val){
							if(v.items){
								$.each(v.items,function(k,v){
									if(s3_curr_val==v.val){
										if(v.items){
											$.each(v.items,function(k,v){
												appendOptionTo($s4,k,v,v4);
											});
										}
									}
								});
							}
						}
					});
				}
			}
		});
		if($s4[0].options.length==0){appendOptionTo($s4,"...","",v4);}
	}).change();
	function appendOptionTo($o,k,v,d){
		var $opt=$("<option>").text(k).val(v);
		if(v==d){$opt.attr("selected", "selected")}
		$opt.appendTo($o);
	}
}