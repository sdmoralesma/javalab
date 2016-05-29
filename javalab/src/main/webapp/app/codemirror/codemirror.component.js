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
    function CodeMirrorComponent(elRef) {
        //events
        this.fileContentChanged = new core_1.EventEmitter();
        this.editorNativeElement = elRef.nativeElement;
    }
    CodeMirrorComponent.prototype.ngOnInit = function () {
    };
    CodeMirrorComponent.prototype.ngAfterViewInit = function () {
        var _this = this;
        var config = {
            mode: "text/x-java",
            lineNumbers: true,
            value: "import com.demo.util.MyType;\r\nimport com.demo.util.MyInterface;\r\n\r\npublic enum Enum {\r\n  VAL1, VAL2, VAL3\r\n}\r\n\r\npublic class Class<T, V> implements MyInterface {\r\n  public static final MyType<T, V> member;\r\n  \r\n  private class InnerClass {\r\n    public int zero() {\r\n      return 0;\r\n    }\r\n  }\r\n\r\n  @Override\r\n  public MyType method() {\r\n    return member;\r\n  }\r\n\r\n  public void method2(MyType<T, V> value) {\r\n    method();\r\n    value.method3();\r\n    member = value;\r\n  }\r\n}\r\n"
        };
        this.editor = CodeMirror(this.editorNativeElement, config);
        this.editor.setSize("75%", "595px");
        this.editor.setOption("matchbrackets", true);
        this.editor.on('change', function (editor) {
            var content = _this.editor.getDoc().getValue();
            _this.fileContentChanged.emit({ value: content });
        });
    };
    CodeMirrorComponent.prototype.ngOnChanges = function (changes) {
        console.log("on changes");
    };
    CodeMirrorComponent.prototype.updateHeight = function (height) {
        this.height = height;
    };
    CodeMirrorComponent.prototype.updateCode = function (newCode) {
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