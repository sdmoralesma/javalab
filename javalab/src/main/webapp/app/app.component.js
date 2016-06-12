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
var navbar_component_1 = require("./nav-bar/navbar.component");
var tags_component_1 = require("./tags/tags.component");
var javalab_service_1 = require("./javalab.service");
var description_component_1 = require("./description/description.component");
var filemanager_component_1 = require("./filemanager/filemanager.component");
var terminal_component_1 = require("./terminal/terminal.component");
var codemirror_component_1 = require("./codemirror/codemirror.component");
var router_1 = require("@angular/router");
var hero_list_component_1 = require("./hero-list/hero-list.component");
var AppComponent = (function () {
    function AppComponent(javalabService) {
        this.javalabService = javalabService;
        //this.attachWindowEvents(); //TODO: activate on prod
    }
    AppComponent.prototype.attachWindowEvents = function () {
        window.onbeforeunload = function (e) {
            e = e || window.event;
            e.preventDefault();
            e.cancelBubble = true;
            e.returnValue = 'Code not saved!';
        };
    };
    AppComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.javalabService.getMockResponse()
            .subscribe(function (data) {
            _this.filemanager.files = data.filesTree;
            _this.navBar.options = data.config.javaClasses;
            _this.description.text = data.description;
            _this.terminal.welcomeMessage = data.terminal;
            _this.tagsComponent.selectedTags = data.tags;
            _this.editor.config = data.config;
        }, function (error) { return _this.errorMessage = error; });
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
    __decorate([
        core_1.ViewChild(description_component_1.DescriptionComponent), 
        __metadata('design:type', description_component_1.DescriptionComponent)
    ], AppComponent.prototype, "description", void 0);
    __decorate([
        core_1.ViewChild(terminal_component_1.TerminalComponent), 
        __metadata('design:type', terminal_component_1.TerminalComponent)
    ], AppComponent.prototype, "terminal", void 0);
    __decorate([
        core_1.ViewChild(codemirror_component_1.CodeMirrorComponent), 
        __metadata('design:type', codemirror_component_1.CodeMirrorComponent)
    ], AppComponent.prototype, "editor", void 0);
    __decorate([
        core_1.ViewChild(navbar_component_1.NavBarComponent), 
        __metadata('design:type', navbar_component_1.NavBarComponent)
    ], AppComponent.prototype, "navBar", void 0);
    __decorate([
        core_1.ViewChild(filemanager_component_1.FileManagerComponent), 
        __metadata('design:type', filemanager_component_1.FileManagerComponent)
    ], AppComponent.prototype, "filemanager", void 0);
    __decorate([
        core_1.ViewChild(tags_component_1.TagsComponent), 
        __metadata('design:type', tags_component_1.TagsComponent)
    ], AppComponent.prototype, "tagsComponent", void 0);
    AppComponent = __decorate([
        core_1.Component({
            selector: 'javalab-app',
            templateUrl: './app/app.html',
            directives: [description_component_1.DescriptionComponent, filemanager_component_1.FileManagerComponent, navbar_component_1.NavBarComponent, tags_component_1.TagsComponent, terminal_component_1.TerminalComponent, codemirror_component_1.CodeMirrorComponent, router_1.ROUTER_DIRECTIVES]
        }),
        router_1.Routes([
            { path: '/heroes', component: hero_list_component_1.HeroListComponent }
        ]), 
        __metadata('design:paramtypes', [javalab_service_1.JavalabService])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map