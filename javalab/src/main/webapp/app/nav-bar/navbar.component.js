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
var primeng_1 = require("primeng/primeng");
var router_1 = require("@angular/router");
var NavBarComponent = (function () {
    function NavBarComponent(router, route) {
        this.router = router;
        this.route = route;
        this.displayDialog = false;
        this.runCodeClicked = new core_1.EventEmitter();
        this.testCodeClicked = new core_1.EventEmitter();
        this.downloadClicked = new core_1.EventEmitter();
    }
    NavBarComponent.prototype.search = function (event) {
        var query = event.query;
        this.suggestions = this.filterOptions(query, this.options);
    };
    NavBarComponent.prototype.filterOptions = function (query, options) {
        var filtered = [];
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            if (option.label.toLowerCase().indexOf(query.toLowerCase()) == 0) {
                filtered.push(option);
            }
        }
        return filtered;
    };
    NavBarComponent.prototype.handleDropdownClick = function ($event) {
        var _this = this;
        this.suggestions = [];
        //won't work without this timeout
        setTimeout(function () {
            _this.suggestions = _this.options;
        }, 100);
    };
    NavBarComponent.prototype.runCode = function () {
        this.runCodeClicked.emit(this.selected);
    };
    NavBarComponent.prototype.testCode = function () {
        this.testCodeClicked.emit(this.selected);
    };
    NavBarComponent.prototype.download = function () {
        this.downloadClicked.emit("");
    };
    NavBarComponent.prototype.newWorkspace = function (lang) {
        var _this = this;
        this.route.params.subscribe(function (params) {
            var goTolang = params['lang'] == undefined ? 'java' : params['lang'];
            console.log(goTolang);
            _this.router.navigateByUrl('/' + goTolang);
        });
    };
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], NavBarComponent.prototype, "runCodeClicked", void 0);
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], NavBarComponent.prototype, "testCodeClicked", void 0);
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], NavBarComponent.prototype, "downloadClicked", void 0);
    NavBarComponent = __decorate([
        core_1.Component({
            selector: 'nav-bar',
            templateUrl: './app/nav-bar/nav-bar.html',
            styleUrls: ['./app/nav-bar/nav-bar.css'],
            directives: [primeng_1.AutoComplete, primeng_1.Toolbar, primeng_1.Button, primeng_1.Dialog, primeng_1.SplitButton, primeng_1.SplitButtonItem, router_1.ROUTER_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [router_1.Router, router_1.ActivatedRoute])
    ], NavBarComponent);
    return NavBarComponent;
}());
exports.NavBarComponent = NavBarComponent;
//# sourceMappingURL=navbar.component.js.map