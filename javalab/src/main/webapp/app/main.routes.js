"use strict";
var router_1 = require("@angular/router");
var app_component_1 = require("./app/app.component");
exports.routes = [
    { path: '', component: app_component_1.AppComponent },
    { path: ':lang', component: app_component_1.AppComponent }
];
exports.APP_ROUTER_PROVIDERS = [
    router_1.provideRouter(exports.routes)
];
//# sourceMappingURL=main.routes.js.map