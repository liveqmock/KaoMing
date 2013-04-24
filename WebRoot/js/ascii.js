	function go(id) {
		if (document.getElementById(id)) return document.getElementById(id);
	}

	function convert(src, des) {
		if ('' == src.value) return false;
		des.value = toEsc(src.value);
	}

	function revert(src, des) {
		if ('' == src.value) return false;
		des.value = toChar(src.value);
	}

	function toEsc(str) {
		var esc, t;
		esc = '';
		for (var i=0; i < str.length; i ++) {
			t = str.charCodeAt(i);
			if (t < 256) esc = esc.concat(str.charAt(i));
			else esc = esc.concat('&#').concat(t).concat(';');
		}
		return esc;
	}

	function toChar(str, opt) {
		return str.replace(/&#(\d+);/g, function(word) {
		return String.fromCharCode(word.replace(/&#(\d+);/g, '$1'));
		});
	}