---
    layout: null
---

/**
 * é¡µé�¢readyæ–¹æ³•
 */
$(document).ready(function() {

    backToTop();
    search();
});

/**
 * å›žåˆ°é¡¶éƒ¨
 */
function backToTop() {
    $("[data-toggle='tooltip']").tooltip();
    var st = $(".page-scrollTop");
    var $window = $(window);
    var topOffset;
    //æ»šé¡µé�¢æ‰�æ˜¾ç¤ºè¿”å›žé¡¶éƒ¨
    $window.scroll(function() {
        var currnetTopOffset = $window.scrollTop();
        if (currnetTopOffset > 0 && topOffset > currnetTopOffset) {
            st.fadeIn(500);
        } else {
            st.fadeOut(500);
        }
        topOffset = currnetTopOffset;
    });

    //ç‚¹å‡»å›žåˆ°é¡¶éƒ¨
    st.click(function() {
        $("body").animate({
            scrollTop: "0"
        }, 500);
    });


}

function search(){
    (function(w,d,t,u,n,s,e){w['SwiftypeObject']=n;w[n]=w[n]||function(){
        (w[n].q=w[n].q||[]).push(arguments);};s=d.createElement(t);
        e=d.getElementsByTagName(t)[0];s.async=1;s.src=u;e.parentNode.appendChild(s);
    })(window,document,'script','//s.swiftypecdn.com/install/v2/st.js','_st');

    _st('install','{{site.swiftype_searchId}}','2.0.0');
}





