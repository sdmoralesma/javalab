// trigger dialog before leave page
window.onbeforeunload = function (e) {
    e = e || window.event;

    const CODE_NOT_SAVED = 'Code not saved!';

    // For IE and Firefox prior to version 4
    if (e) {
        e.returnValue = CODE_NOT_SAVED;
    }

    // For Safari
    return CODE_NOT_SAVED;
};

// resize areas if window is resized
$(window).resize(resizeTextAreasVertically);
