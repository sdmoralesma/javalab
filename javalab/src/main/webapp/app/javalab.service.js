"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var http_1 = require("@angular/http");
require("rxjs/add/operator/toPromise");
var JavalabService = (function () {
    function JavalabService(http) {
        this.http = http;
    }
    JavalabService.prototype.initialize = function () {
        var _this = this;
        // let url = "assets/json/mock-response.json";
        var url = "http://localhost:48080/rest/process/init/java";
        return this.http.get(url)
            .toPromise()
            .then(function (res) {
            var json = res.json();
            _this.model = json;
            return json;
        })
            .catch(this.handleError);
    };
    JavalabService.prototype.handleError = function (error) {
        console.error("An error occurred:", error);
        return Promise.reject(error.message || error);
    };
    JavalabService.prototype.runCode = function (model) {
        var _this = this;
        var runCodeURL = "http://localhost:48080/rest/process/run";
        var body = JSON.stringify({ model: model });
        var headers = new http_1.Headers({ 'Content-Type': 'application/json' });
        var options = new http_1.RequestOptions({ headers: headers });
        return this.http.post(runCodeURL, body, options)
            .toPromise()
            .then(function (res) { return res.json(); })
            .catch(function (error) { return _this.handleError; });
    };
    JavalabService.prototype.testCode = function (model) {
        var _this = this;
        var runCodeURL = "http://localhost:48080/rest/process/run";
        var body = JSON.stringify({ model: model });
        var headers = new http_1.Headers({ 'Content-Type': 'application/json' });
        var options = new http_1.RequestOptions({ headers: headers });
        return this.http.post(runCodeURL, body, options)
            .toPromise()
            .then(function (res) { return res.json(); })
            .catch(function (error) { return _this.handleError; });
    };
    JavalabService.prototype.findNodeById = function (id, tree) {
        for (var _i = 0, tree_1 = tree; _i < tree_1.length; _i++) {
            var node = tree_1[_i];
            var found = this.searchNode(id, node);
            if (found !== null) {
                return found;
            }
        }
    };
    JavalabService.prototype.searchNode = function (id, node) {
        if (node.id === id) {
            return node;
        }
        else if (node.children !== null && node.children !== undefined) {
            var result = null;
            for (var i = 0; result === null && i < node.children.length; i++) {
                result = this.searchNode(id, node.children[i]);
            }
            return result;
        }
        return null;
    };
    JavalabService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], JavalabService);
    return JavalabService;
}());
exports.JavalabService = JavalabService;
//# sourceMappingURL=javalab.service.js.map