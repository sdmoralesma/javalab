"use strict";
var platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
var app_component_1 = require("./app.component");
require("rxjs/Rx");
var http_1 = require("@angular/http");
var tag_service_1 = require("./tag.service");
var javalab_service_1 = require("./javalab.service");
var core_1 = require("@angular/core");
core_1.enableProdMode();
platform_browser_dynamic_1.bootstrap(app_component_1.AppComponent, [
    http_1.HTTP_PROVIDERS,
    tag_service_1.TagService,
    javalab_service_1.JavalabService
]);
//# sourceMappingURL=boot.js.map