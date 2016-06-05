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
var core_1 = require('angular2/core');
var Tree = (function () {
    function Tree() {
    }
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], Tree.prototype, "value", void 0);
    Tree = __decorate([
        core_1.Component({
            selector: 'p-tree',
            template: "\n        <div class=\"ui-tree ui-widget ui-widget-content ui-corner-all\">\n            <ul class=\"ui-tree-container\">\n                <li class=\"ui-treenode\" *ngFor=\"#node of value\">\n                    <span class=\"ui-treenode-content ui-treenode-selectable\">\n                    <span class=\"ui-tree-toggler fa fa-fw fa-caret-right\"></span>\n                    <span class=\"ui-treenode-icon fa fa-fw fa-folder\"></span>\n                    <span class=\"ui-treenode-label ui-corner-all\">{{node.label}}</span></span>\n                    <ul class=\"ui-treenode-children\" style=\"display: none;\">\n                        \n                    </ul>\n                </li>\n            </ul>\n        </div>\n    "
        }), 
        __metadata('design:paramtypes', [])
    ], Tree);
    return Tree;
}());
exports.Tree = Tree;
//# sourceMappingURL=treenode.js.map