/*自动刷新iframe的高度*/
var home = {
    iFrameHeight: function () {
        var ifm = $("#mainFrame");
        ifm.height(300);
        try {
            var windowHeight = $(window).height();
            var ifmHeight = ifm.contents().height();
            var heightW = ifmHeight > windowHeight ? ifmHeight : windowHeight;
            ifm.height(heightW);
        } catch (e) {
            ;
        }
    }
};