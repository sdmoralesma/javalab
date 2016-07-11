"use strict";
var platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
var main_component_1 = require("./main.component");
require("rxjs/Rx");
var http_1 = require("@angular/http");
var tag_service_1 = require("./tag.service");
var javalab_service_1 = require("./javalab.service");
var core_1 = require("@angular/core");
var main_routes_1 = require("./main.routes");
var common_1 = require("@angular/common");
core_1.enableProdMode();
platform_browser_dynamic_1.bootstrap(main_component_1.MainComponent, [
    main_routes_1.APP_ROUTER_PROVIDERS,
    { provide: common_1.LocationStrategy, useClass: common_1.HashLocationStrategy },
    http_1.HTTP_PROVIDERS,
    tag_service_1.TagService,
    javalab_service_1.JavalabService
]);
//# sourceMappingURL=boot.js.map