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
var uuid_1 = require("./uuid");
var FILE_CLASS = "fa-file-text-o";
var FileManagerComponent = (function () {
    function FileManagerComponent() {
        //Dialog variables
        this.displayNewFolder = false;
        this.displayNewFile = false;
        this.displayRename = false;
        this.displayDelete = false;
        this.newNodeName = "";
        // file management
        this.selectedNode = null;
        //events
        this.fileSelected = new core_1.EventEmitter();
    }
    FileManagerComponent.prototype.nodeSelect = function (event) {
        if (this.selectedNode.icon === FILE_CLASS) {
            this.fileSelected.emit({ value: this.selectedNode.data });
        }
    };
    FileManagerComponent.prototype.createFolder = function () {
        var newFolder = {
            "id": this.generateId(),
            "label": this.newNodeName,
            "expandedIcon": "fa-folder-open",
            "collapsedIcon": "fa-folder",
            "children": []
        };
        if (this.selectedNode === null) {
            this.files.push(newFolder);
            return;
        }
        if (this.selectedNode.parentId === undefined) {
            if (this.selectedNode.icon === FILE_CLASS) {
                this.files.push(newFolder);
            }
            else {
                newFolder.parentId = this.selectedNode.id;
                this.selectedNode.children.push(newFolder);
            }
            return;
        }
        var parentNode = this.findNodeById(this.selectedNode.parentId, this.files);
        if (this.selectedNode.icon === FILE_CLASS) {
            newFolder.parentId = parentNode.id;
            parentNode.children.push(newFolder);
        }
        else {
            newFolder.parentId = this.selectedNode.id;
            this.selectedNode.children.push(newFolder);
        }
    };
    FileManagerComponent.prototype.createFile = function () {
        var newFile = {
            "id": this.generateId(),
            "label": this.newNodeName,
            "icon": "fa-file-text-o",
            "data": ""
        };
        if (this.selectedNode === null) {
            this.files.push(newFile);
            return;
        }
        if (this.selectedNode.parentId === undefined) {
            if (this.selectedNode.icon === FILE_CLASS) {
                this.files.push(newFile);
            }
            else {
                newFile.parentId = this.selectedNode.id;
                this.selectedNode.children.push(newFile);
            }
            return;
        }
        var parentNode = this.findNodeById(this.selectedNode.parentId, this.files);
        if (this.selectedNode.icon === FILE_CLASS) {
            newFile.parentId = parentNode.id;
            parentNode.children.push(newFile);
        }
        else {
            newFile.parentId = this.selectedNode.id;
            this.selectedNode.children.push(newFile);
        }
    };
    FileManagerComponent.prototype.findNodeById = function (id, tree) {
        for (var _i = 0, tree_1 = tree; _i < tree_1.length; _i++) {
            var node = tree_1[_i];
            var found = this.searchNode(id, node);
            if (found !== null) {
                return found;
            }
        }
    };
    FileManagerComponent.prototype.searchNode = function (id, node) {
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
    FileManagerComponent.prototype.generateId = function () {
        return uuid_1.UUID.generate();
    };
    FileManagerComponent.prototype.renameItem = function () {
        this.selectedNode.label = this.newNodeName;
    };
    FileManagerComponent.prototype.deleteItem = function () {
        var _this = this;
        if (this.selectedNode.parentId === undefined) {
            var index = this.files.indexOf(this.selectedNode, 0);
            if (index > -1) {
                this.files.splice(index, 1);
            }
        }
        else {
            var parentNode = this.findNodeById(this.selectedNode.parentId, this.files);
            var index = parentNode.children.findIndex(function (child) { return child.id === _this.selectedNode.id; });
            if (index > -1) {
                parentNode.children.splice(index, 1);
            }
        }
    };
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], FileManagerComponent.prototype, "fileSelected", void 0);
    FileManagerComponent = __decorate([
        core_1.Component({
            selector: 'filemanager',
            templateUrl: './app/filemanager/filemanager.html',
            directives: [primeng_1.Tree, primeng_1.Panel, primeng_1.Toolbar, primeng_1.Button, primeng_1.Dialog, primeng_1.InputText]
        }), 
        __metadata('design:paramtypes', [])
    ], FileManagerComponent);
    return FileManagerComponent;
}());
exports.FileManagerComponent = FileManagerComponent;
//# sourceMappingURL=filemanager.component.js.map