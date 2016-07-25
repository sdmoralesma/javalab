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
var CodeMirrorComponent = (function () {
    function CodeMirrorComponent(elRef, changeDetector) {
        this.elRef = elRef;
        this.changeDetector = changeDetector;
        this.fileContentChanged = new core_1.EventEmitter();
    }
    CodeMirrorComponent.prototype.ngAfterViewInit = function () {
        var _this = this;
        var config = {
            mode: "text/x-java",
            lineNumbers: true,
            value: "default text; // if you are reading this there was a problem :("
        };
        this.editor = CodeMirror(this.elRef.nativeElement, config);
        this.editor.setSize(this.calculateWidht(), this.calculateHeight());
        this.editor.setOption("matchbrackets", true);
        this.editor.on('change', function (editor) {
            var content = _this.editor.getDoc().getValue();
            _this.fileContentChanged.emit({ value: content });
        });
        this.changeDetector.detectChanges();
    };
    CodeMirrorComponent.prototype.calculateHeight = function () {
        var innerHeight = window.innerHeight;
        if (this.isSingleColumnMode()) {
            return (innerHeight <= 640) ? 350 : innerHeight * 60 / 100;
        }
        else {
            return innerHeight * 66 / 100;
        }
    };
    CodeMirrorComponent.prototype.calculateWidht = function () {
        var width = window.innerWidth;
        if (width <= 600) {
            return width * 0.77;
        }
        else {
            return width * 0.74;
        }
    };
    CodeMirrorComponent.prototype.isSingleColumnMode = function () {
        return window.innerWidth <= 1024;
    };
    CodeMirrorComponent.prototype.updateCode = function (newCode) {
        this.editor.setValue(newCode);
    };
    CodeMirrorComponent.prototype.onResize = function (event) {
        this.editor.setSize(this.calculateWidht(), this.calculateHeight());
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
        __metadata('design:paramtypes', [core_1.ElementRef, core_1.ChangeDetectorRef])
    ], CodeMirrorComponent);
    return CodeMirrorComponent;
}());
exports.CodeMirrorComponent = CodeMirrorComponent;
//# sourceMappingURL=codemirror.component.js.map