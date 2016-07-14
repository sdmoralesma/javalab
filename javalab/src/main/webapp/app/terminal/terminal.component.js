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
var TerminalComponent = (function () {
    function TerminalComponent() {
    }
    TerminalComponent.prototype.addResponseToTerminal = function (response) {
        this.welcomeMessage = "";
        this.response = "javalab $ " + response;
    };
    TerminalComponent = __decorate([
        core_1.Component({
            selector: 'terminal',
            templateUrl: './app/terminal/terminal.html',
        }), 
        __metadata('design:paramtypes', [])
    ], TerminalComponent);
    return TerminalComponent;
}());
exports.TerminalComponent = TerminalComponent;
//# sourceMappingURL=terminal.component.js.map