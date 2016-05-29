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
    function AppComponent(javalabService, changeDetectionRef) {
        this.javalabService = javalabService;
        this.changeDetectionRef = changeDetectionRef;
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
    AppComponent.prototype.initializeApplication = function () {
        this.initialData = this.javalabService.getMockResponse();
    };
    AppComponent.prototype.ngOnInit = function () {
        this.initializeApplication();
    };
    AppComponent.prototype.ngAfterViewInit = function () {
        this.filemanager.files = this.initialData.filesTree;
        this.changeDetectionRef.detectChanges();
    };
    AppComponent.prototype.onResize = function (event) {
        var minWidthDesktop = 980;
        if (window.innerWidth < minWidthDesktop) {
            return;
        }
        var windowHeight = window.innerHeight;
        var extNavHeight = this.navBar.height;
        //  Define height for each element based on %
        var codeEditorHeight = (windowHeight * 0.75) - extNavHeight;
        var consoleHeight = (windowHeight * 0.25) - extNavHeight;
        // resize elements
        this.editor.updateHeight(codeEditorHeight);
        this.terminal.updateHeight(consoleHeight);
    };
    AppComponent.prototype.showFileContent = function (event) {
        this.editor.updateCode(event.value);
    };
    AppComponent.prototype.updateFileContent = function (event) {
        this.filemanager.selectedNode.data = event.value;
    };
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
    AppComponent = __decorate([
        core_1.Component({
            selector: 'javalab-app',
            templateUrl: './app/app.html',
            directives: [description_component_1.DescriptionComponent, filemanager_component_1.FileManagerComponent, navbar_component_1.NavBarComponent, tags_component_1.TagsComponent, terminal_component_1.TerminalComponent, codemirror_component_1.CodeMirrorComponent, router_1.ROUTER_DIRECTIVES],
            providers: [javalab_service_1.JavalabService]
        }),
        router_1.Routes([
            { path: '/heroes', component: hero_list_component_1.HeroListComponent }
        ]), 
        __metadata('design:paramtypes', [javalab_service_1.JavalabService, core_1.ChangeDetectorRef])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map