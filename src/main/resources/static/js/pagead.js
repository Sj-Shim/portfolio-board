"use strict";

function _createForOfIteratorHelper(t, e) {
    var a = "undefined" != typeof Symbol && t[Symbol.iterator] || t["@@iterator"];
    if (!a) {
        if (Array.isArray(t) || (a = _unsupportedIterableToArray(t)) || e && t && "number" == typeof t.length) {
            a && (t = a);
            var n = 0,
                e = function() {};
            return {
                s: e,
                n: function() {
                    return n >= t.length ? {
                        done: !0
                    } : {
                        done: !1,
                        value: t[n++]
                    }
                },
                e: function(t) {
                    throw t
                },
                f: e
            }
        }
        throw new TypeError("Invalid attempt to iterate non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")
    }
    var o, i = !0,
        r = !1;
    return {
        s: function() {
            a = a.call(t)
        },
        n: function() {
            var t = a.next();
            return i = t.done, t
        },
        e: function(t) {
            r = !0, o = t
        },
        f: function() {
            try {
                i || null == a.return || a.return()
            } finally {
                if (r) throw o
            }
        }
    }
}

function _unsupportedIterableToArray(t, e) {
    if (t) {
        if ("string" == typeof t) return _arrayLikeToArray(t, e);
        var a = Object.prototype.toString.call(t).slice(8, -1);
        return "Map" === (a = "Object" === a && t.constructor ? t.constructor.name : a) || "Set" === a ? Array.from(t) : "Arguments" === a || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(a) ? _arrayLikeToArray(t, e) : void 0
    }
}

function _arrayLikeToArray(t, e) {
    (null == e || e > t.length) && (e = t.length);
    for (var a = 0, n = new Array(e); a < e; a++) n[a] = t[a];
    return n
}
window.LivePageAd = function() {
    window.LiveConfig || (window.LiveConfig = {}), void 0 === window.LiveConfig.adTags && (window.LiveConfig.adTags = 0);
    var i, r, s = [],
        d = !1,
        c = !1,
        l = !1,
        f = $("[data-ad-t]").filter(function(t, e) {
            return !!e.offsetParent
        }).map(function(t, e) {
            return {
                t: $(e).attr("data-ad-t"),
                w: e.offsetWidth,
                h: e.offsetHeight,
                b: $(e).attr("data-ad-b")
            }
        }).toArray();

    function g(t, e) {
        return '<iframe style="display:inline-block;width:100%;height:100%;border:none;" frameborder="0" scrolling="no" src="//safefra.me/' + t + "/#" + e.t + '"></iframe>'
    }

    function u(t) {
        if (d = !0, i = 300, t)(adsbygoogle = window.adsbygoogle || []).push({});
        else
            for (var e = 0; e < s.length; e++)(adsbygoogle = window.adsbygoogle || []).push({});
        if (l) window.MutationObserver && r.observe(document.getElementById(t.t).getElementsByTagName("ins")[0], {
            attributes: !0
        });
        else if (window.MutationObserver) {
            r = new MutationObserver(p);
            var a, n = _createForOfIteratorHelper(s);
            try {
                for (n.s(); !(a = n.n()).done;) {
                    var o = a.value;
                    r.observe(document.getElementById(o.t).getElementsByTagName("ins")[0], {
                        attributes: !0
                    })
                }
            } catch (t) {
                n.e(t)
            } finally {
                n.f()
            }
        } else h()
    }

    function p(t, i) {
        l = !0, t.forEach(function(t) {
            if ("attributes" === t.type && "data-ad-status" === t.attributeName && "unfilled" === t.target.dataset.adStatus) {
                for (var e = 0; e < s.length; e++) {
                    var a, n, o = s[e];
                    if (o.t === (null === (a = t.target) || void 0 === a || null === (n = a.parentNode) || void 0 === n ? void 0 : n.dataset.adT)) {
                        delete s[e], b(o);
                        break
                    }
                }
                0 === (s = s.filter(Boolean)).length && (i.disconnect(), l = !1)
            }
        })
    }

    function h() {
        if (l = !0, --i < 0) l = !1;
        else {
            var t, e = !1,
                a = _createForOfIteratorHelper(s);
            try {
                for (a.s(); !(t = a.n()).done;) {
                    var n, o = t.value;
                    o.loaded || (void 0 !== (n = $("#".concat(o.t)).children(":first").data("ad-status")) ? (o.loaded = !0, "unfilled" === n && b(o)) : e = !0)
                }
            } catch (t) {
                a.e(t)
            } finally {
                a.f()
            }
            e && setTimeout(h, 100)
        }
    }

    function y(u) {
        0 !== u.length && $.getJSON("/api/pagead?i=".concat(encodeURIComponent(JSON.stringify(u)), "&r=").concat(encodeURIComponent(location.toString()), "&t=").concat(window.LiveConfig.adTags, "&s=").concat(~~$('meta[name="has_sensitive_media"]').prop("content"))).then(function(t) {
            var e, a = /\?(https(?:.*?)&adurl=)/g.exec(location.href),
                n = a && a[1] ? "".concat(a[1]).concat(location.protocol, "//").concat(location.host) : "",
                o = _createForOfIteratorHelper(u);
            try {
                for (o.s(); !(e = o.n()).done;) {
                    var i, r, s, d, c, l, f, g = e.value;
                    t[g.t] ? (r = (i = t[g.t]).i, s = i.v, d = i.t, c = i.s, l = i.w, void 0 !== (f = i.n) ? (g.fallback_networks = f, b(g)) : $("#".concat(g.t)).append(c ? $("<a>").addClass("a-badge sponsored").text("SPONSORED") : $("<a>").attr("href", "/u/advertisements").addClass("a-badge adservice").text(i18n("PAGEAD_DO_AD")), $("<a>").attr("href", "".concat(n, "/api/ads/").concat(d, "?t=").concat(g.t, "&s=").concat(l)).attr("target", "_blank").append(s ? $("<video autoplay loop muted playsinline>").attr("src", s) : $("<img>").attr("src", r))).css("height")) : b(g)
                }
            } catch (t) {
                o.e(t)
            } finally {
                o.f()
            }
        })
    }

    function b(t) {
        var e, a, n = null === (e = t.fallback_networks) || void 0 === e ? void 0 : e.shift(),
            o = $("#".concat(t.t));
        o.children().each(function() {
            "defaultImage" !== $(this).attr("id") && $(this).remove()
        }), o.removeAttr("style"), void 0 !== n ? ("ga" === n ? (a = '<ins class="adsbygoogle" style="display:inline-block;width:100%;height:100%;" data-ad-client="ca-pub-7162146779303471" data-ad-slot="3916101267"></ins>', "zLcnqmwwq6UCKGT7" === (e = t).t || "Lfnq5YV6zrq3QrwD" === e.t ? a = '<ins class="adsbygoogle" style="display:inline-block;width:300px;height:600px" data-ad-client="ca-pub-7162146779303471" data-ad-slot="1289937924"></ins>' : "DB9VA6tC2dGCEb3V" === e.t ? a = '<ins class="adsbygoogle" style="display:inline-block;width:300px;height:250px" data-ad-client="ca-pub-7162146779303471" data-ad-slot="5467922246"></ins>' : "Kt6gbCBrHQpObQFW" !== e.t && "zYa8sDuyBUyBIPa0" !== e.t || (a = '<ins class="adsbygoogle" style="display:inline-block;width:160px;height:600px" data-ad-client="ca-pub-7162146779303471" data-ad-slot="6893421618"></ins>'), s.push(e), a = a) : "gu" === n ? a = g("gougoi", t) : "bm" === n ? a = g("bidmob", t) : "dm" === n ? a = g("dm", t) : "au" === n ? y([{
            t: t.t,
            w: t.w,
            h: t.h,
            b: "a"
        }]) : b(t), a && o.append(a), "ga" === n && (!1 === c ? function() {
            c = !0;
            var t = document.createElement("script");
            t.setAttribute("async", "async"), t.setAttribute("src", "https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7162146779303471"), t.setAttribute("crossorigin", "anonymous"), t.onload = u, document.head.appendChild(t)
        }() : d && u(t))) : "zLcnqmwwq6UCKGT7" === t.t || "Lfnq5YV6zrq3QrwD" === t.t || "Kt6gbCBrHQpObQFW" === t.t || "zYa8sDuyBUyBIPa0" === t.t ? 299 <= t.w ? o.append('<img src="/static/assets/images/empty_ad_sidebar.png'.concat(window.LiveConfig.CACHE_KEY, '" width="100%" height="100%">')) : o.append('<img src="/static/assets/images/empty_ad_sidebar_small.png'.concat(window.LiveConfig.CACHE_KEY, '" width="100%" height="100%">')) : "svQazR5NHC3xCQr3" === t.t || "uSgrU972mJzDppaC" === t.t ? 727 <= t.w ? o.append('<img src="/static/assets/images/empty_ad_list-728.png'.concat(window.LiveConfig.CACHE_KEY, '" width="100%" height="100%">')) : o.append('<img src="/static/assets/images/empty_ad_list-320.png'.concat(window.LiveConfig.CACHE_KEY, '" width="100%" height="100%">')) : o.css("height", "0")
    }
    y(f), window.addEventListener("message", function(t) {
        if ("https://safefra.me" === t.origin) {
            var e = t.data;
            if (16 === e.length) {
                var a, n = _createForOfIteratorHelper(f);
                try {
                    for (n.s(); !(a = n.n()).done;) {
                        var o = a.value;
                        if (o.t === e) {
                            b(o);
                            break
                        }
                    }
                } catch (t) {
                    n.e(t)
                } finally {
                    n.f()
                }
            }
        }
    })
};