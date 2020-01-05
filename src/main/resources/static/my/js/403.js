var str = $(".forbidden-tip").html();
var i = 0;
$(".forbidden-tip").html("");

setTimeout(function() {
    var se = setInterval(function() {
        i++;
        $(".forbidden-tip").html(str.slice(0, i) + "|");
        if (i == str.length) {
            clearInterval(se);
            $(".forbidden-tip").html(str);
        }
    }, 10);
},0);
