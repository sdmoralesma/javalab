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
var EDITOR_WIDTH = "75%";
var EDITOR_HEIGHT = "595px";
var CodeMirrorComponent = (function () {
    function CodeMirrorComponent(elRef) {
        this.fileContentChanged = new core_1.EventEmitter();
        this.editorNativeElement = elRef.nativeElement;
    }
    CodeMirrorComponent.prototype.ngAfterViewInit = function () {
        var _this = this;
        var config = {
            mode: "text/x-java",
            lineNumbers: true,
            value: ""
        };
        this.editor = CodeMirror(this.editorNativeElement, config);
        this.editor.setSize(EDITOR_WIDTH, EDITOR_HEIGHT);
        this.editor.setOption("matchbrackets", true);
        this.editor.on('change', function (editor) {
            var content = _this.editor.getDoc().getValue();
            _this.fileContentChanged.emit({ value: content });
        });
    };
    CodeMirrorComponent.prototype.updateHeight = function (height) {
        this.height = height;
    };
    CodeMirrorComponent.prototype.updateCode = function (newCode) {
        this.editor.setValue(this.config.value);
        this.editor.setOption("mode", this.config.languageMode);
        this.editor.setValue(newCode);
    };
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], CodeMirrorComponent.prototype, "fileContentChanged", void 0);
    CodeMirrorComponent = __decorate([
        core_1.Component({
            selector: 'codemirror',
            templateUrl: './app/codemirror/codemirror.html'
        }), 
        __metadata('design:paramtypes', [core_1.ElementRef])
    ], CodeMirrorComponent);
    return CodeMirrorComponent;
}());
exports.CodeMirrorComponent = CodeMirrorComponent;
//# sourceMappingURL=codemirror.component.js.map