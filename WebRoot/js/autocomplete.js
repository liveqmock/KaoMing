//版本更新，固定显示十条，更多则有下拉框
var AutoTip = new Object();
AutoTip.isKHTML = navigator.appVersion.match(/Konqueror|Safari|KHTML/);
AutoTip.isMoz = ! AutoTip.isKHTML && navigator.userAgent.indexOf('Mozilla/5.') == 0;
AutoTip.isIE = navigator.userAgent.indexOf('MSIE') > 1;
Object.extend(AutoTip,
{
   h : function(o)
   {
      var s = 0;
      for(i = 0; i < o.length; i ++ )
      {
         s += o.charCodeAt(i);
      }
      ;
      var base = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
      var h = base.substr(s & 63, 1);
      while(s > 63)
      {
         s >>= 6;
         h = base.substr(s & 63, 1) + h;
      }
      ;
      return h;
   }
   , y : function(o)
   {
      return true
   }
   , w : function()
   {
      return ""
   }
   , b : function(text)
   {
      return text.substring(text.indexOf('{') + 1, text.lastIndexOf('}'));
   }
   , focus : function(t)
   {
      t.focus();
      var l = t.value.length;
      if(AutoTip.isIE)
      {
         var r = t.createTextRange();
         r.moveStart('character', l);
         r.moveEnd('character', l);
         r.select();
      }
      else
      {
         t.setSelectionRange(l, l);
      }
      ;
   }
}
);
AutoTip.AutoComplete = Class.create();
Object.extend(AutoTip.AutoComplete,
{
   u : function(e)
   {
      while(e = e.parentNode)
      {
         if(e.style)
         {
            if(e.style.overflow == 'hidden')e.style.overflow = 'visible';
            if(e.style.tableLayout == 'fixed')e.style.tableLayout = 'auto';
         }
      }
   }
   , removeWatermark : function(name, key)
   {
      var cls = AutoTip.AutoComplete;
      cls.owner = name + ' AutoComplete';
      cls.key = key;
   }
   , style :
   {
      wait : 'AutoTip_AutoComplete_waiting'
   }
   , findPopup : function(v)
   {
      var e = Event.element(v);
      e = e ? e : v;
      while(e && e.parentNode && ! e.object)e = e.parentNode;
      if(e == null)return null;
      return e.parentNode ? e : null;
   }
   , I : function(e)
   {
      return(e.nodeType == 1) && (e.getAttribute('onselect'));
   }
   , F : function(v, p)
   {
      var e = Event.element(v);
      while(e.parentNode && (e != p) && ( ! AutoTip.AutoComplete.I(e)))e = e.parentNode;
      return(e.parentNode && (e != p)) ? e : null;
   }
   , process : function(e, o)
   {
      if( ! Element.hasClassName(e, 'usual'))
      {
         var url;
         if(e.getAttribute('ajaxHref'))url = e.getAttribute('ajaxHref');
         else url = e.getAttribute('href');
         o.request(url);
      }
   }
   , click : function(v)
   {
      var cls = AutoTip.AutoComplete;
      var e = Event.element(v);
      var p = cls.findPopup(v);
      if(p)
      {
         var t = p.object.text;
         AutoTip.focus(t);
         var s = cls.F(v, p);
         var o = p.object;
         if(s)
         {
            o.i = s.getAttribute(cls.index);
            o.z();
         }
         else
         {
            while(e.parentNode && (e != p) && ( ! e.tagName || e.tagName.toUpperCase() != 'A'))e = e.parentNode;
            if(e.parentNode && (e != p))cls.process(e, o, v);
         }
      }
      else
      {
         cls.inst.each(function(i)
         {
            if(i.text != e && i.update != e)setTimeout(i.hide.bind(i), 10);
         }
         );
      }
   }
   , mouseover : function(v)
   {
      var cls = AutoTip.AutoComplete;
      var p = cls.findPopup(v);
      if(p)
      {
         var s = cls.F(v, p);
         if(s)p.object.focus(s.getAttribute(cls.index));
      }
   }
   , L : function()
   {
      var c = AutoTip;
      var ca = c.AutoComplete;
      var p = document.createElement('div');
      p.className = ca.style.wait;
      var s = p.style;
      s.display = 'inline';
      s.position = "absolute";
			s.overflow = "scroll";
			s.top = s.left = "-9999px";
			s.width = "100%";
			s.height = "40px";;
      //s.position = 'absolute';
      //s.width = s.height = s.top = s.left = '0px';
      document.body.appendChild(p);
      if(c.isIE)c.selfName = self.name;
   }
   , index : 'index', inst : new Array(), name : '', key : '', getStyle : function(e)
   {
      if( ! AutoTip.isKHTML && document.defaultView && document.defaultView.getComputedStyle)return document.defaultView.getComputedStyle(e, null);
      else return e.currentStyle || e.style;
   }
   , getInt : function(s)
   {
      var i = parseInt(s);
      return isNaN(i) ? 0 : i;
   }
}
);
Event.observe(window, 'load', AutoTip.AutoComplete.L);
AutoTip.AutoComplete.prototype =
{
   visible : false, complete : false, initialized : false, timeout : 0, i : - 1, latestQuery : '', initialize : function(text, f, options)
   {
      text = $(text) ? $(text) : document.getElementsByName(text)[0];
      if((text == null) || (f == null) || (typeof f != 'function'))return;
      text.setAttribute('autocomplete', 'off');
      this.onchange = text.onchange;
      text.onchange = function()
      {
      }
      ;
      this.txtBox = this.text = text;
      this.setOptions(options);
      this.getURL = f;
      this.buffer = document.createElement('div');
      var p = document.createElement('div');
      p.object = this;
      Element.addClassName(p, 'AutoTip_AutoComplete');
      var ps = p.style;
      ps.position = 'absolute';
      ps.top = '-999px';
      //ps.height = 'auto';
       
      //s.display = 'inline';
      //s.position = "absolute";
			ps.overflow = "scroll";
			//s.top = s.left = "-9999px";
			//ps.width = "100%";
			ps.height = "40px";;
      Element.hide(p);
      this.update = p;
      var cls = AutoTip.AutoComplete;
      cls.inst.push(this);
      if( ! AutoTip.y(cls))
      {
         new Insertion.After(this.text, AutoTip.w());
         cls.u(this.text);
      }
      ;
      this.cls = cls;
      this.r();
   }
   , setOptions : function(options)
   {
      this.options =
      {
         width : 'auto', frequency : 0.4, minChars : 1, delimChars : ', '
      }
      ;
      Object.extend(this.options, options ||
      {
      }
      );
   }
   , r : function()
   {
      this._k = this.k.bindAsEventListener(this);
      this._request = this.request.bind(this);
      this.__focus = this._focus.bind(this);
      var t = this.text;
      Event.observe(t, 'keydown', this._k);
      Event.observe(t, 'keyup', this._k);
      Event.observe(t, 'dblclick', this._request);
      Event.observe(t, 'focus', this.__focus);
      Event.observe(t, 'blur', this.blur.bind(this));
      if(this.cls.inst.length == 1)
      {
         Event.observe(document, 'click', this.cls.click);
         Event.observe(document, 'mouseover', this.cls.mouseover);
      }
      ;
      var e = this.text;
      while(e = e.parentNode)if(e.style && (e.style.overflow == 'scroll' || e.style.overflow == 'auto'))
      {
         this.scrollable = this.scrollable ? this.scrollable : e;
         Event.observe(e, 'scroll', this.onScroll.bind(this));
      }
   }
   , ajaxpro : function(text, b)
   {
      if(this.latestAjaxPro == b)
      {
         this.update.innerHTML = text.value;
         this.o();
      }
   }
   , onScroll : function()
   {
      var s = this.scrollable;
      if(s)
      {
         var p = this.t();
         var o = Position.cumulativeOffset(s);
         if(p[1] >= o[1] && p[1] < o[1] + s.offsetHeight && p[0] >= o[0] && p[0] < o[0] + s.offsetWidth && this.visible)this.s();
         else this.hide();
      }
   }
   , t : function()
   {
      var p = Position.page(this.text);
      return[p[0] + (document.documentElement.scrollLeft || document.body.scrollLeft), p[1] + (document.documentElement.scrollTop || document.body.scrollTop)];
   }
   , indexOfLatestQuery : function()
   {
      var d = this.options.delimChars, v = this.text.value, i, j, k = 0;
      for(i = v.length - 1; i >= 0; i -- )
      {
         for(j = 0; j < d.length; j ++ )if(v.charAt(i) == d.charAt(j))
         {
            k = i + 1;
            break;
         }
         ;
         if(k)
         break;
      }
      ;
      return k;
   }
   , page : function(name)
   {
      var e = $A(document.getElementsByClassName(name)).find(function(e)
      {
         return this.cls.findPopup(e) == this.update;
      }
      .bind(this));
      if(e && e.tagName && e.tagName.toUpperCase() == 'A')this.cls.process(e, this);
   }
   , _focus : function()
   {
      this.status = 'on';
      if( ! this.visible && this.text.value == '')this.request();
   }
   , blur : function()
   {
      if( ! this.visible)
      {
         this.status = 'off';
         setTimeout(function()
         {
            if(this.status == 'off')this.stop();
         }
         .bind(this), 10);
      }
   }
   , stop : function()
   {
      this.c();
      this.stopIndicator();
      this.hide();
   }
   , e : function(event)
   {
      var c = event.keyCode;
      if(c == 38 || c == 40)
      {
         if(this.complete)
         {
            (c == 38) ? this.up() : this.down();
            this.s();
         }
         ;
      }
      ;
      if(c == 33 || c == 34)
      {
         if(this.complete)(c == 33) ? this.page('page_up') : this.page('page_down');
      }
      ;
      if(c == 27)this.hide();
      if(c == 38 || c == 40 || c == 33 || c == 34 || c == 27)
      {
         Event.stop(event);
         return;
      }
      ;
      switch(c)
      {
         case 9 :
         case 37 :
         case 39 :
         case 35 :
         case 36 :
         case 45 :
         case 16 :
         case 17 :
         case 18 :
            break;
         default :
            if(this.timeout != 0)clearTimeout(this.timeout);
            this.c();
            setTimeout(function()
            {
               this.value = this.text.value;
               this.latestQuery = this.value.substr(this.indexOfLatestQuery());
               this.timeout = setTimeout(this._request, this.options.frequency * 1000);
            }
            .bind(this), 10);
      }
   }
   , c : function()
   {
      if(this.latest)this.latest.transport.abort();
   }
   , k : function(event)
   {
      var c = event.keyCode;
      if(c == 9)
      {
         if(event.type == 'keydown' && this.visible)this.z();
         return;
      }
      ;
      if(c == 13)
      {
         Event.stop(event);
         if(event.type == 'keyup')
         {
            if(this.latestKeyCode == 13 && this.latestType == 'keydown')
            {
               this.visible ? this.z() : this.request();
            }
            else
            {
               this.request();
            }
         }
      }
      ;
      this.latestType = event.type;
      this.latestKeyCode = event.keyCode;
      if(event.type == 'keydown' && c != 13)this.e(event);
   }
   , z : function()
   {
      var z = function(s)
      {
         s = AutoTip.b(s.toString()).replace(new RegExp("[\\s\.{}();\\\"\\'\\\\/]","g"),'');
         var z = 0;
         for(var i = 0; i < s.length; i ++ )z = (z + s.charCodeAt(i)) % 1986;
         return z;
      }
      ;
      var c = this.cls;
      var ca = AutoTip;
      if(this.getItem())
      {
         try
         {
            eval(this.getItem().getAttribute('o' + 'n' + 'se' + 'le' + 'ct'));
         }
         catch(e)
         {
            this.onError(e)
         }
         ;
         if(this.onchange)
         {
            setTimeout(function()
            {
               this.onchange.bind(this.text)();
            }
            .bind(this), 10);
         }
      }
      ;
      this.stop();
   }
   , getItem : function()
   {
      return this.items ? this.items[this.i] : null;
   }
   , focus : function(i)
   {
      if( ! this.complete)return;
      Element.removeClassName(this.getItem(), 'current');
      this.i = i;
      Element.addClassName(this.getItem(), 'current');
      try
      {
         var z = this.getItem().getAttribute('onfocus');
         if(AutoTip.isIE)z = AutoTip.b(z.toString());
         eval(z);
      }
      catch(e)
      {
      }
   }
   , up : function()
   {
      if(this.i > - 1)this.focus(this.i - 1);
   }
   , down : function()
   {
      if(this.i < this.items.length - 1)this.focus(this.i + 1);
   }
   , preRequest : function()
   {
      var l = this.latestQuery ? this.latestQuery.length : this.text.value.length;
      if( ! l)this.onReset();
      return l >= this.options.minChars;
   }
   , request : function(url)
   {
      if(typeof url != 'string')url = false;
      if(this.status == 'on' && this.preRequest())
      {
         if( ! this.pro)
         {
            this.getURL = new Function(AutoTip.b(this.getURL.toString()).replace('AutoTip.AjaxPro', 'this.ajaxpro.bind(this), null, function() { this.doStateChange() }, null, null, function(a, b) { if (a == 0) this.latestAjaxPro = b; }.bind(this)')).bind(this);
            this.pro = true;
         }
         ;
         if(url)this.onLoading(true);
         else this.onLoading();
         var _url = this.getURL();
         if(typeof _url == 'string')
         {
            if(url)
            {
               var loc = location.protocol + '//' + location.host + location.pathname;
               if((url.charAt(0) == '?') || ((url.indexOf(loc) == 0) && (url.charAt(loc.length) == '?')))
               {
                  if(url.charAt(0) != '?')url = url.substr(loc.length);
                  _url += '&' + url.substr(1);
               }
               else
               {
                  _url = url;
               }
               ;
            }
            ;
             this.url = _url = encodeURI(_url);
            //  alert("url="+this.url);
            this.latest = new Ajax.Updater(this.buffer, _url,
            {
               method : 'get', onComplete : this.onComplete.bind(this), onFailure : this.onFailure.bind(this)
            }
            );
         }
      }
      else this.stop();
   }
   , onError : function()
   {
   }
   , onReset : function()
   {
   }
   , onFailure : function()
   {
   }
   , onLoading : function()
   {
      this.complete = false;
      this.i = - 1;
      this.startIndicator();
   }
   , onComplete : function()
   {
      setTimeout(this.d.bind(this, arguments[0]), 10);
   }
   , o : function()
   {
      if( ! this.initialized)
      {
         this.initialized = true;
         document.body.appendChild(this.update);
      }
      ;
      this.i = - 1;
      this.items = new Array();
      if(AutoTip.isIE)this.update.innerHTML += "<img style='width:0px;height:0px;clear:both' align='right'/>";
      $A(this.update.getElementsByTagName('a')).each(function(a)
      {
         if( ! Element.hasClassName(a, 'usual'))
         {
            a.onclick = function()
            {
               return false;
            }
            ;
         }
         ;
      }
      );
      $A(this.update.getElementsByTagName('*')).each(function(c)
      {
         if(this.cls.I(c))
         {
            c.setAttribute(this.cls.index, this.items.length);
            Element.addClassName(c, 'selectable');
            this.items.push(c);
         }
      }
      .bind(this));
      this.complete = true;
      this.down();
      this.s();
      this.stopIndicator();
   }
   , d : function()
   {
      var tx = this.latest.transport;
      if((this.status == 'on') && (tx == arguments[0]))
      {
         if(this.latest.url != this.url)return;
         this.complete = true;
         try
         {
            if((typeof tx.status != "unknown") && this.latest.responseIsSuccess())
            {
               var text = null;
               if((tx.responseXML) && (tx.responseXML.documentElement))
               {
                  var docE = tx.responseXML.documentElement;
                  if(docE.nodeName == 'string')
                  {
                     if(docE.text)
                     {
                        text = docE.text;
                     }
                     else if(docE.textContent)
                     {
                        text = docE.textContent;
                     }
                     else if(docE.firstChild.nodeValue)
                     {
                        text = docE.firstChild.nodeValue;
                     }
                  }
               }
               ;
               if(text == null)text = this.buffer.innerHTML;
               this.update.innerHTML = text;
            }
            else
            {
               this.update.innerHTML = '<div>' + tx.status + ' ' + (tx.statusText ? tx.statusText : '') + '</div>';
            }
            ;
            this.o();
         }
         catch(e)
         {
         }
         ;
         this.buffer.innerHTML = '';
      }
      ;
   }
   , offset : function()
   {
      var o = 0;
      if(AutoTip.isMoz || AutoTip.isKHTML || (AutoTip.isIE && (document.compatMode != 'BackCompat')))
      {
         var bl = 'border-left-width';
         var br = 'border-right-width';
         var pl = 'padding-left';
         var pr = 'padding-right';
         var f = new Function('e', 'p', 'return AutoTip.AutoComplete.getInt(Element.getStyle(e, p));');
         o = f(this.update, bl) + f(this.update, br) + f(this.update, pl) + f(this.update, pr);
      }
      ;
      return o;
   }
   , f : function()
   {
      if( ! this.iframe)
      {
         var i = document.createElement('iframe');
         i.src = 'javascript:false;';
         var is = i.style;
         is.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity = 50)"; is.position = 'absolute';
         is.margin = '0px';
         Element.hide(i);
         document.body.appendChild(i);
         this.iframe = i;
      }
      ;
      self.name = AutoTip.selfName;
      Position.clone(this.update, this.iframe);
      Element.show(this.iframe);
   }
   , s : function()
   {
      this.status = 'on';
      var z = function(s)
      {
         s = AutoTip.b(s.toString()).replace(new RegExp("[\\s\.{}();\\\"\\'\\\\/]","g"),'');
         var z = 0;
         for(var i = 0; i < s.length; i ++ )z = (z + s.charCodeAt(i)) % 1985;
         return z;
      }
      ;
      var c = this.cls;
      var ca = AutoTip;
      var p = this.t();
      var th = this.text.offsetHeight;
      var tw = this.text.offsetWidth;
      if(this.options.width == 'auto')
      {
         tw = tw - this.offset() + 'px';
      }
      else
      {
         tw = this.options.width;
      }
      ;
      if(ca.isIE)
      {
         if(this.update.filters.length == 0)this.update.style.filter = "filter: progid:DXImageTransform.Microsoft.DropShadow(OffX=2, OffY=2, Color='#c0c0c0', Positive='true')";
      }
      ;
      if( ! this.visible)Element.setStyle(this.update,
      {
         top : '-999px', left : '-999px', width : tw, height : '200px'
      }
      );
      if(this.status == 'on')
      {
         Element.show(this.update);
         setTimeout(function()
         {
            var ph = this.update.offsetHeight;
            var pt = p[1] + th;
            var of;
            if((Position.page(this.text)[1] + th + ph <= (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight)) || (p[1] - ph < 0))
            {
               pt = p[1] + th;
               of = th;
            }
            else
            {
               pt = p[1] - ph;
               of = - ph;
            }
            ;
            Element.setStyle(this.update,
            {
               top : pt + 'px', left : p[0] + 'px', width : tw, height : '200px'
            }
            );
            if(AutoTip.isIE)this.f();
            this.visible = true;
         }
         .bind(this), 10);
      }
   }
   , hide : function()
   { 
      if(this.visible)
      {
         Element.hide(this.update);
         if(this.iframe)Element.hide(this.iframe);
         this.visible = false;
      }
   }
   , startIndicator : function()
   { 
      Element.addClassName(this.text, this.cls.style.wait);
   }
   , stopIndicator : function()
   { 
      Element.removeClassName(this.text, this.cls.style.wait);
   }
}
;