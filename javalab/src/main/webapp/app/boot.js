"use strict";
var platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
var app_component_1 = require("./app.component");
require("rxjs/Rx");
var router_1 = require("@angular/router");
var core_1 = require("@angular/core");
var common_1 = require("@angular/common");
var http_1 = require("@angular/http");
var tag_service_1 = require("./tag.service");
var javalab_service_1 = require("./javalab.service");
platform_browser_dynamic_1.bootstrap(app_component_1.AppComponent, [router_1.ROUTER_PROVIDERS, http_1.HTTP_PROVIDERS, tag_service_1.TagService, javalab_service_1.JavalabService,
    core_1.provide(common_1.APP_BASE_HREF, { useValue: '/' })
]);
//# sourceMappingURL=boot.js.map