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
var description_component_1 = require("../description/description.component");
var filemanager_component_1 = require("../filemanager/filemanager.component");
var tags_component_1 = require("../tags/tags.component");
var codemirror_component_1 = require("../codemirror/codemirror.component");
var navbar_component_1 = require("../nav-bar/navbar.component");
var terminal_component_1 = require("../terminal/terminal.component");
var router_1 = require("@angular/router");
var javalab_service_1 = require("../javalab.service");
var AppComponent = (function () {
    function AppComponent(javalabService, route) {
        this.javalabService = javalabService;
        this.route = route;
    }
    AppComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.sub = this.route.params.subscribe(function (params) {
            var lang = params['lang'] == undefined ? '/java' : "/" + params['lang'];
            _this.javalabService.initialize(lang)
                .then(function (data) {
                _this.model = data;
                _this.filemanager.files = _this.model.filesTree;
                _this.navBar.options = data.config.javaClasses;
                _this.description.text = _this.model.description;
                _this.terminal.welcomeMessage = _this.model.terminal;
                _this.tagsComponent.selectedTags = _this.model.tags;
                _this.editor.config = _this.model.config;
            }, function (error) { return _this.errorMessage = error; });
        });
        setTimeout(function () {
            _this.initializeNavBar();
            _this.initializeCentralPanel();
        }, 300);
    };
    AppComponent.prototype.ngOnDestroy = function () {
        this.sub.unsubscribe();
    };
    AppComponent.prototype.showFileContent = function (event) {
        this.editor.updateCode(event.value);
    };
    AppComponent.prototype.updateFileContent = function (event) {
        if (this.filemanager.selectedNode === null) {
            return;
        }
        this.filemanager.selectedNode.data = event.value;
    };
    AppComponent.prototype.initializeCentralPanel = function () {
        this.filemanager.selectedNode = this.javalabService.findNodeById(this.model.config.initialNode, this.model.filesTree);
        this.editor.editor.setValue(this.filemanager.selectedNode.data);
        this.editor.editor.setOption("mode", this.model.config.languageMode);
    };
    AppComponent.prototype.initializeNavBar = function () {
        var optionsAsObjects = [];
        for (var _i = 0, _a = this.model.config.javaClasses; _i < _a.length; _i++) {
            var suggestionId = _a[_i];
            var found = this.javalabService.findNodeById(suggestionId, this.model.filesTree);
            optionsAsObjects.push(found);
        }
        this.navBar.options = optionsAsObjects;
        this.navBar.selected = this.navBar.options[0];
    };
    AppComponent.prototype.runCode = function () {
        var _this = this;
        this.javalabService.runCode(this.model)
            .then(function (data) {
            _this.terminal.addResponseToTerminal(data.output);
            _this.model.terminal = data.output;
            _this.navBar.displayDialog = false;
        }, function (error) { return _this.errorMessage = error; });
    };
    AppComponent.prototype.testCode = function () {
        var _this = this;
        this.javalabService.testCode(this.model)
            .then(function (data) {
            _this.terminal.addResponseToTerminal(data.output);
            _this.model.terminal = data.output;
            _this.navBar.displayDialog = false;
        }, function (error) { return _this.errorMessage = error; });
    };
    AppComponent.prototype.download = function () {
        this.javalabService.download(this.model);
    };
    __decorate([
        core_1.ViewChild(description_component_1.DescriptionComponent), 
        __metadata('design:type', description_component_1.DescriptionComponent)
    ], AppComponent.prototype, "description", void 0);
    __decorate([
        core_1.ViewChild(codemirror_component_1.CodeMirrorComponent), 
        __metadata('design:type', codemirror_component_1.CodeMirrorComponent)
    ], AppComponent.prototype, "editor", void 0);
    __decorate([
        core_1.ViewChild(filemanager_component_1.FileManagerComponent), 
        __metadata('design:type', filemanager_component_1.FileManagerComponent)
    ], AppComponent.prototype, "filemanager", void 0);
    __decorate([
        core_1.ViewChild(tags_component_1.TagsComponent), 
        __metadata('design:type', tags_component_1.TagsComponent)
    ], AppComponent.prototype, "tagsComponent", void 0);
    __decorate([
        core_1.ViewChild(navbar_component_1.NavBarComponent), 
        __metadata('design:type', navbar_component_1.NavBarComponent)
    ], AppComponent.prototype, "navBar", void 0);
    __decorate([
        core_1.ViewChild(terminal_component_1.TerminalComponent), 
        __metadata('design:type', terminal_component_1.TerminalComponent)
    ], AppComponent.prototype, "terminal", void 0);
    AppComponent = __decorate([
        core_1.Component({
            selector: 'central-panel',
            templateUrl: './app/app/app.html',
            directives: [description_component_1.DescriptionComponent, filemanager_component_1.FileManagerComponent, tags_component_1.TagsComponent, codemirror_component_1.CodeMirrorComponent, navbar_component_1.NavBarComponent, terminal_component_1.TerminalComponent]
        }), 
        __metadata('design:paramtypes', [javalab_service_1.JavalabService, router_1.ActivatedRoute])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map