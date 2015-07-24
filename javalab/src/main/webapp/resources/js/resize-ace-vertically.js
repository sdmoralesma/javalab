function resizeTextAreasVertically() {

    var minWidthDesktop = 980;
    if ($(window).width() < minWidthDesktop) {
        return;
    }

    var windowHeight = $(window).height();
    var extNavHeight = $('#ext-nav').height();
    //  Define height for each element based on %
    var codeEditorHeight = (windowHeight * 75 / 100) - extNavHeight;
    var testCodeEditorHeight = (windowHeight * 75 / 100) - extNavHeight;
    var consoleHeight = (windowHeight * 25 / 100) - extNavHeight;

    // resize elements
    $('#code-editor').height(codeEditorHeight);
    $('#test-editor').height(testCodeEditorHeight);
    $('#console').height(consoleHeight);
}

resizeTextAreasVertically();