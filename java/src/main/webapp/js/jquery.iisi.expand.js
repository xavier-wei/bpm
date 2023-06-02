(function($) {
	"use strict";
	const pad = (str, len, inchr, direction) => {
		const decrease = Math.min(len, 30) - str.length;
		let temp = '';
		for (let i = 0 ; i < decrease ; i++) {
			temp += inchr
		};
	    if (direction === 'R') {
	    	return str + temp;
	    } else if(direction === 'L') {
	    	return temp + str;
	    }
	    return '';
	};
	const eventHandler = {
		//純數字輸入檢核
		num_only: ($src) => {
			$src.val($src.val().replace(/\D/g, ''));
		},
		//純數字輸入檢核(可輸入小數點)
		float_only: ($src) => {
			$src.val($src.val().match(/\d*\.?\d*/g).find(m => !!m));
		},
		//英數輸入檢核
		num_eng_only: ($src) => {
			$src.val($src.val().replace(/[^0-9a-zA-Z]/g, ''));
		},
		//身分證號
	    idn: ($src) => {
			$src.val($src.val().replace(/[^0-9a-zA-Z]/g, '').toUpperCase());
		},
		//只接受登打數字或()-#空格
	    tel: ($src) => {
			$src.val($src.val().replace(/[^0-9\-#\(\)\s]*/g, ''));
		},
		//只接受登打英數或()-
	    account: ($src) => {
			$src.val($src.val().replace(/[^0-9\-\(\)]*/g, ''));
		},
		//特殊符號只可輸入-~'":;.(),空白
	    addr: ($src) => {
			$src.val($src.val().replace(/[^a-zA-Z0-9\u4E00-\u9FFF~-\s'"]*/g, ''));
		},
		//地址英數檢查
		num_eng_only_addr: ($src) => {
			$src.val($src.val().replace(/[^a-zA-Z0-9\s\.\,;-]*/g, ''));
		},
		//可輸入英數及空白
		num_eng_blank_only: ($src) => {
			$src.val($src.val().replace(/[^a-zA-Z0-9\s]*/g, ''));
		},
		//地址數字檢查
		num_only_addr: ($src) => {
			$src.val($src.val().replace(/[^a-zA-Z0-9\u4E00-\u9FFF#()-]*/g, ''));
		},
		//只接受登打英數或.@-_
		mail: ($src) => {
			$src.val($src.val().replace(/[^a-zA-Z0-9\-@._]*/g, ''));
		},
		//可接受民前日期
	    birth: ($src) => {
			$src.val($src.val().match(/^-?\d{0,8}/g).find(m => !!m));
			$src.off('blur.iisi.common.birth').one('blur.iisi.common.birth', () => {
				const len = $src.prop('maxlength');
				const val = $src.val();
				if (val) {
		        	if (val.charAt(0) === '-') {
		            	if (len && !isNaN(len) && len > val.length) {
		            	    $src.val('-'.concat(pad(val.substr(1), len - 1, '0', 'L')));
		            	}
		        	} else {
			            $src.val(pad(val, len, '0', 'L'));
		        	}
		    	}
			});
		},
		//日期
	    date: ($src, e) => {
			$src.val($src.val().replace(/\D/g, ''));
			$src.off('blur.iisi.common.date').one('blur.iisi.common.date', () => {
				const len = $src.prop('maxlength');
		        const val = $src.val();
		        if (len && !isNaN(len) && val != '' && len > val.length) {
		        	$src.val(pad(val, len, '0', 'L'));
		    	}
			});
		},
		//格式化小數點兩位
		rateForm: ($src) => {
			const val = $src.val().match(/\d*\.?\d*/g).find(m => !!m);
		    if (isNaN(val) || val === '') {
				$src.val('');
		    } else {
				$src.val((parseFloat(val) + 0.001).toFixed(2));
			}
		},
		//格式化小數點一位
		rateForm1: ($src) => {
			const val = $src.val().match(/\d*\.?\d*/g).find(m => !!m);
		    if (isNaN(val) || val === '') {
				$src.val('');
		    } else {
				$src.val((parseFloat(val) + 0.001).toFixed(1));
			}
		},
		//無條件捨去至小數點第一位
		rateForm2: ($src) => {
			const val = $src.val().match(/\d*\.?\d*/g).find(m => !!m);
		    if (isNaN(val) || val === '') {
				$src.val('');
		    } else {
				$src.val(Math.floor(val * 10) / 10);
			}
		},
		//轉大寫
		upCase: ($src) => {
			$src.val($src.val().toUpperCase());
		},
		//轉全形
		fullCase: ($src) => {
			const asciiTable = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
			const big5Table = "%u3000%uFF01%u201D%uFF03%uFF04%uFF05%uFF06%u2019%uFF08%uFF09%uFF0A%uFF0B%uFF0C%uFF0D%uFF0E%uFF0F%uFF10%uFF11%uFF12%uFF13%uFF14%uFF15%uFF16%uFF17%uFF18%uFF19%uFF1A%uFF1B%uFF1C%uFF1D%uFF1E%uFF1F%uFF20%uFF21%uFF22%uFF23%uFF24%uFF25%uFF26%uFF27%uFF28%uFF29%uFF2A%uFF2B%uFF2C%uFF2D%uFF2E%uFF2F%uFF30%uFF31%uFF32%uFF33%uFF34%uFF35%uFF36%uFF37%uFF38%uFF39%uFF3A%uFF3B%uFF3C%uFF3D%uFF3E%uFF3F%u2018%uFF41%uFF42%uFF43%uFF44%uFF45%uFF46%uFF47%uFF48%uFF49%uFF4A%uFF4B%uFF4C%uFF4D%uFF4E%uFF4F%uFF50%uFF51%uFF52%uFF53%uFF54%uFF55%uFF56%uFF57%uFF58%uFF59%uFF5A%uFF5B%uFF5C%uFF5D%uFF5E";
			const val = $src.val();
			if (val) {
				let result = "";
				for (let i = 0; i < val.length; i++) {
					const v = val.charAt(i);
					const j = asciiTable.indexOf(v) * 6;
					result += (j > -1 ? unescape(big5Table.substring(j, j + 6)) : v);
				}
				$src.val(result);
			} else {
				$src.val('');
			}
		},
		//轉小寫
		lowCase: ($src) => {
			$src.val($src.val().toLowerCase());
		},
		//左方補0到限制長度
		padL: ($src) => {
			$src.off('blur.iisi.common.padL').one('blur.iisi.common.padL', () => {
				const len = $src.prop('maxlength');
		        const val = $src.val();
		        if (len && !isNaN(len) && val != '' && len > val.length) {
		        	$src.val(pad(val, len, '0', 'L'));
		    	}
			});
		},
		//右方補0到限制長度
		padR: ($src) => {
			$src.off('blur.iisi.common.padR').one('blur.iisi.common.padR', () => {
				const len = $src.prop('maxlength');
		        const val = $src.val();
		        if (len && !isNaN(len) && val != '' && len > val.length) {
		        	$src.val(pad(val, len, '0', 'R'));
		    	}
			});
		},
		//移除前後的空白字元
		trimS: ($src) => {
			$src.val($src.val().trim());
		}
	};
	
	const autotab = ($src, e) => {
		if ($src.is(":not(:disabled,[readonly])")) {
			
			if($src.data('apno12autotab')){
				apno12autotab(e);
			}
			
			if (($src.is(":text") || $src.is("textarea")) && ($src.val().length != $src.attr("maxlength") && !e.forceJump) 
			    || $src.attr("type") == "search"
				|| e.key === "Tab"
				|| e.key === "Shift"
				|| e.key === "Alt"
				|| e.key === "Ctrl") {
        		return;
        	} else if ($src.is("select") && !$src.val()) {
                return;
            } else if ($src.is(":radio,:checkbox") && !$src.is('input:checked')) {
                return;
            }
			const $els = $src.closest('form').find('input,select,textarea').not(':hidden');
	        for (let next = $els.index($src.get(0)) + 1; next < $els.length ; next++) {
				const $t = $els.eq(next).not(':disabled,[readonly]');
	            if ($t.length && $t.prop('name') != $src.prop('name')) {
				    $t.focus();
					return;
				}
	        }
			$('.formButtonDiv').find('button').first().focus();
		}
	}
	
	const commonHandler = () => {
		let ime = false;
		return (e) => {
			if (e.type === 'compositionstart') {
				ime = true;
			} else if (e.type === 'compositionend') {
				ime = false;
			}
			e.ime = ime;
			const $src = $(e.target);
			if (!ime) {

				[...e.target.classList].forEach(cls => {
					if (eventHandler.hasOwnProperty(cls)) {
						eventHandler[cls]($src, e);
					}
				});
				if (e.type === 'keyup') {
					autotab($src, e);
				}
			}
		};
	};
	
	/**
	 * Keyboard一般通用事件回調函數綁定
	 */
	$.fn.iisiCommonHandler  = function() {
		return this.on('input keyup compositionstart compositionend', 'input,select,textarea', commonHandler());
	};
	
})(jQuery);