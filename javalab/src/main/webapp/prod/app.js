webpackJsonp([0],{

/***/ 0:
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	var platform_browser_dynamic_1 = __webpack_require__(1);
	var main_module_1 = __webpack_require__(337);
	var core_1 = __webpack_require__(11);
	core_1.enableProdMode();
	platform_browser_dynamic_1.platformBrowserDynamic().bootstrapModule(main_module_1.MainModule);
	//# sourceMappingURL=main.js.map

/***/ },

/***/ 337:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var platform_browser_1 = __webpack_require__(203);
	var primeng_1 = __webpack_require__(338);
	var http_1 = __webpack_require__(498);
	var forms_1 = __webpack_require__(345);
	var main_component_1 = __webpack_require__(520);
	var app_component_1 = __webpack_require__(521);
	var javalab_service_1 = __webpack_require__(530);
	var tag_service_1 = __webpack_require__(526);
	var main_routes_1 = __webpack_require__(531);
	var description_component_1 = __webpack_require__(522);
	var filemanager_component_1 = __webpack_require__(523);
	var codemirror_component_1 = __webpack_require__(527);
	var tags_component_1 = __webpack_require__(525);
	var navbar_component_1 = __webpack_require__(528);
	var terminal_component_1 = __webpack_require__(529);
	var MainModule = (function () {
	    function MainModule() {
	    }
	    MainModule = __decorate([
	        core_1.NgModule({
	            imports: [
	                main_routes_1.routing,
	                platform_browser_1.BrowserModule,
	                primeng_1.InputTextModule,
	                platform_browser_1.BrowserModule,
	                forms_1.FormsModule,
	                forms_1.ReactiveFormsModule,
	                http_1.HttpModule,
	                primeng_1.AccordionModule,
	                primeng_1.AutoCompleteModule,
	                primeng_1.BreadcrumbModule,
	                primeng_1.ButtonModule,
	                primeng_1.CalendarModule,
	                primeng_1.CarouselModule,
	                primeng_1.ChartModule,
	                primeng_1.CheckboxModule,
	                primeng_1.CodeHighlighterModule,
	                primeng_1.SharedModule,
	                primeng_1.ContextMenuModule,
	                primeng_1.DataGridModule,
	                primeng_1.DataListModule,
	                primeng_1.DataScrollerModule,
	                primeng_1.DataTableModule,
	                primeng_1.DialogModule,
	                primeng_1.DragDropModule,
	                primeng_1.DropdownModule,
	                primeng_1.EditorModule,
	                primeng_1.FieldsetModule,
	                primeng_1.GalleriaModule,
	                primeng_1.GMapModule,
	                primeng_1.GrowlModule,
	                primeng_1.InputMaskModule,
	                primeng_1.InputSwitchModule,
	                primeng_1.InputTextModule,
	                primeng_1.InputTextareaModule,
	                primeng_1.LightboxModule,
	                primeng_1.ListboxModule,
	                primeng_1.MegaMenuModule,
	                primeng_1.MenuModule,
	                primeng_1.MenubarModule,
	                primeng_1.MessagesModule,
	                primeng_1.MultiSelectModule,
	                primeng_1.OrderListModule,
	                primeng_1.OverlayPanelModule,
	                primeng_1.PaginatorModule,
	                primeng_1.PanelModule,
	                primeng_1.PanelMenuModule,
	                primeng_1.PasswordModule,
	                primeng_1.PickListModule,
	                primeng_1.ProgressBarModule,
	                primeng_1.RadioButtonModule,
	                primeng_1.RatingModule,
	                primeng_1.ScheduleModule,
	                primeng_1.SelectButtonModule,
	                primeng_1.SlideMenuModule,
	                primeng_1.SliderModule,
	                primeng_1.SpinnerModule,
	                primeng_1.SplitButtonModule,
	                primeng_1.TabMenuModule,
	                primeng_1.TabViewModule,
	                primeng_1.TerminalModule,
	                primeng_1.TieredMenuModule,
	                primeng_1.ToggleButtonModule,
	                primeng_1.ToolbarModule,
	                primeng_1.TooltipModule,
	                primeng_1.TreeModule,
	                primeng_1.TreeTableModule
	            ],
	            declarations: [
	                main_component_1.MainComponent,
	                app_component_1.AppComponent,
	                codemirror_component_1.CodeMirrorComponent,
	                filemanager_component_1.FileManagerComponent,
	                description_component_1.DescriptionComponent,
	                tags_component_1.TagsComponent,
	                navbar_component_1.NavBarComponent,
	                terminal_component_1.TerminalComponent
	            ],
	            providers: [
	                main_routes_1.routingProviders,
	                tag_service_1.TagService,
	                javalab_service_1.JavalabService
	            ],
	            bootstrap: [main_component_1.MainComponent]
	        }), 
	        __metadata('design:paramtypes', [])
	    ], MainModule);
	    return MainModule;
	}());
	exports.MainModule = MainModule;
	//# sourceMappingURL=main.module.js.map

/***/ },

/***/ 520:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var MainComponent = (function () {
	    function MainComponent() {
	    }
	    MainComponent = __decorate([
	        core_1.Component({
	            selector: 'javalab-app',
	            templateUrl: './app/main.html'
	        }), 
	        __metadata('design:paramtypes', [])
	    ], MainComponent);
	    return MainComponent;
	}());
	exports.MainComponent = MainComponent;
	//# sourceMappingURL=main.component.js.map

/***/ },

/***/ 521:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var description_component_1 = __webpack_require__(522);
	var filemanager_component_1 = __webpack_require__(523);
	var tags_component_1 = __webpack_require__(525);
	var codemirror_component_1 = __webpack_require__(527);
	var navbar_component_1 = __webpack_require__(528);
	var terminal_component_1 = __webpack_require__(529);
	var router_1 = __webpack_require__(384);
	var javalab_service_1 = __webpack_require__(530);
	var AppComponent = (function () {
	    function AppComponent(javalabService, route) {
	        this.javalabService = javalabService;
	        this.route = route;
	    }
	    AppComponent.prototype.ngOnInit = function () {
	        var _this = this;
	        this.routerSubscriber = this.route.params.subscribe(function (params) {
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
	                _this.initializeNavBar();
	                _this.initializeCentralPanel();
	            }, function (error) { return _this.errorMessage = error; });
	        });
	    };
	    AppComponent.prototype.ngOnDestroy = function () {
	        this.routerSubscriber.unsubscribe();
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
	        this.terminal.replace("running ...");
	        this.javalabService.runCode(this.model)
	            .then(function (data) {
	            _this.terminal.append(data.output);
	            _this.model.terminal = data.output;
	            _this.navBar.displayDialog = false;
	        }, function (error) { return _this.errorMessage = error; });
	    };
	    AppComponent.prototype.testCode = function () {
	        var _this = this;
	        this.javalabService.testCode(this.model)
	            .then(function (data) {
	            _this.terminal.append(data.output);
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
	            templateUrl: './app/app/app.component.html'
	        }), 
	        __metadata('design:paramtypes', [javalab_service_1.JavalabService, router_1.ActivatedRoute])
	    ], AppComponent);
	    return AppComponent;
	}());
	exports.AppComponent = AppComponent;
	//# sourceMappingURL=app.component.js.map

/***/ },

/***/ 522:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var DescriptionComponent = (function () {
	    function DescriptionComponent() {
	    }
	    DescriptionComponent = __decorate([
	        core_1.Component({
	            selector: 'description',
	            templateUrl: './app/description/description.html'
	        }), 
	        __metadata('design:paramtypes', [])
	    ], DescriptionComponent);
	    return DescriptionComponent;
	}());
	exports.DescriptionComponent = DescriptionComponent;
	//# sourceMappingURL=description.component.js.map

/***/ },

/***/ 523:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var uuid_1 = __webpack_require__(524);
	var FILE_CLASS = "fa-file-text-o";
	var FileManagerComponent = (function () {
	    function FileManagerComponent(renderer) {
	        this.renderer = renderer;
	        // dialog variables
	        this.displayNewFolder = false;
	        this.displayNewFile = false;
	        this.displayRename = false;
	        this.displayDelete = false;
	        this.newNodeName = "";
	        // file management
	        this.selectedNode = null;
	        this.fileSelected = new core_1.EventEmitter();
	    }
	    FileManagerComponent.prototype.ngAfterViewInit = function () {
	        var clazz = this;
	        setTimeout(function () {
	            var tree = document.getElementsByTagName("p-tree")[0];
	            var srcMainJava = tree.getElementsByClassName("ui-tree-toggler fa fa-fw fa-caret-right")[0];
	            var srcTestJava = tree.getElementsByClassName("ui-tree-toggler fa fa-fw fa-caret-right")[1];
	            var event = new MouseEvent('click', { bubbles: true });
	            clazz.renderer.invokeElementMethod(srcMainJava, 'dispatchEvent', [event]);
	            clazz.renderer.invokeElementMethod(srcTestJava, 'dispatchEvent', [event]);
	            setTimeout(function () {
	                var mainComCompanyProject = tree.getElementsByClassName("ui-tree-toggler fa fa-fw fa-caret-right")[0];
	                var testComCompanyProject = tree.getElementsByClassName("ui-tree-toggler fa fa-fw fa-caret-right")[1];
	                clazz.renderer.invokeElementMethod(mainComCompanyProject, 'dispatchEvent', [event]);
	                clazz.renderer.invokeElementMethod(testComCompanyProject, 'dispatchEvent', [event]);
	            }, 200);
	        }, 200);
	    };
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
	            templateUrl: './app/filemanager/filemanager.html'
	        }), 
	        __metadata('design:paramtypes', [core_1.Renderer])
	    ], FileManagerComponent);
	    return FileManagerComponent;
	}());
	exports.FileManagerComponent = FileManagerComponent;
	//# sourceMappingURL=filemanager.component.js.map

/***/ },

/***/ 524:
/***/ function(module, exports) {

	"use strict";
	var UUID = (function () {
	    function UUID() {
	    }
	    UUID.generate = function () {
	        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
	            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
	            return v.toString(16);
	        });
	    };
	    return UUID;
	}());
	exports.UUID = UUID;
	//# sourceMappingURL=uuid.js.map

/***/ },

/***/ 525:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var tag_service_1 = __webpack_require__(526);
	var TagsComponent = (function () {
	    function TagsComponent(tagService) {
	        this.tagService = tagService;
	    }
	    TagsComponent.prototype.filterTagsMultiple = function (event) {
	        var _this = this;
	        var query = event.query;
	        this.tagService.getTags()
	            .subscribe(function (tags) { return _this.tagsSuggested = _this.filterTags(query, tags); }, function (error) { return _this.errorMessage = error; });
	    };
	    TagsComponent.prototype.filterTags = function (query, tagList) {
	        var filtered = [];
	        for (var i = 0; i < tagList.length; i++) {
	            var tag = tagList[i];
	            if (tag.toLowerCase().indexOf(query.toLowerCase()) == 0) {
	                filtered.push(tag);
	            }
	        }
	        return filtered;
	    };
	    TagsComponent = __decorate([
	        core_1.Component({
	            selector: 'tags',
	            templateUrl: './app/tags/tags.html'
	        }), 
	        __metadata('design:paramtypes', [tag_service_1.TagService])
	    ], TagsComponent);
	    return TagsComponent;
	}());
	exports.TagsComponent = TagsComponent;
	//# sourceMappingURL=tags.component.js.map

/***/ },

/***/ 526:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var http_1 = __webpack_require__(498);
	var Observable_1 = __webpack_require__(70);
	var TagService = (function () {
	    function TagService(http) {
	        this.http = http;
	        this.tagsURL = "assets/json/taglist.json";
	    }
	    TagService.prototype.getTags = function () {
	        return this.http.get(this.tagsURL)
	            .map(function (res) { return res.json().data; })
	            .catch(this.handleError);
	    };
	    TagService.prototype.handleError = function (error) {
	        console.error(error);
	        return Observable_1.Observable.throw(error.json().error || 'Server error');
	    };
	    TagService = __decorate([
	        core_1.Injectable(), 
	        __metadata('design:paramtypes', [http_1.Http])
	    ], TagService);
	    return TagService;
	}());
	exports.TagService = TagService;
	//# sourceMappingURL=tag.service.js.map

/***/ },

/***/ 527:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
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

/***/ },

/***/ 528:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var router_1 = __webpack_require__(384);
	var NavBarComponent = (function () {
	    function NavBarComponent(router, route) {
	        this.router = router;
	        this.route = route;
	        this.displayDialog = false;
	        this.runCodeClicked = new core_1.EventEmitter();
	        this.testCodeClicked = new core_1.EventEmitter();
	        this.downloadClicked = new core_1.EventEmitter();
	    }
	    NavBarComponent.prototype.ngOnInit = function () {
	        this.items = [
	            { label: 'Java', icon: 'fa-file-code-o', routerLink: ['/java'] },
	            { label: 'Scala', icon: 'fa-file-code-o', routerLink: ['/scala'] },
	            { label: 'Groovy', icon: 'fa-file-code-o', routerLink: ['/groovy'] }
	        ];
	    };
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
	            styleUrls: ['./app/nav-bar/nav-bar.css']
	        }), 
	        __metadata('design:paramtypes', [router_1.Router, router_1.ActivatedRoute])
	    ], NavBarComponent);
	    return NavBarComponent;
	}());
	exports.NavBarComponent = NavBarComponent;
	//# sourceMappingURL=navbar.component.js.map

/***/ },

/***/ 529:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var TerminalComponent = (function () {
	    function TerminalComponent(changeDetector) {
	        this.changeDetector = changeDetector;
	    }
	    TerminalComponent.prototype.ngAfterViewInit = function () {
	        this.width = this.calculateWidht();
	        this.height = this.calculateHeight();
	        this.changeDetector.detectChanges();
	    };
	    TerminalComponent.prototype.append = function (response) {
	        this.welcomeMessage = "";
	        this.response = "javalab $ \n" + response;
	    };
	    TerminalComponent.prototype.replace = function (msg) {
	        this.welcomeMessage = "";
	        this.response = "javalab $ \n" + msg;
	    };
	    TerminalComponent.prototype.onResize = function (event) {
	        this.width = this.calculateWidht();
	        this.height = this.calculateHeight();
	    };
	    TerminalComponent.prototype.calculateHeight = function () {
	        var innerHeight = window.innerHeight;
	        if (this.isSingleColumnMode()) {
	            return (innerHeight <= 640) ? 250 : innerHeight * 25 / 100;
	        }
	        else {
	            return innerHeight * 25 / 100;
	        }
	    };
	    TerminalComponent.prototype.calculateWidht = function () {
	        var width = window.innerWidth;
	        if (width <= 600) {
	            return width * 0.97;
	        }
	        else if (width > 600 && width <= 1350) {
	            return width * 0.98;
	        }
	        else {
	            return width * 0.985;
	        }
	    };
	    TerminalComponent.prototype.isSingleColumnMode = function () {
	        return window.innerWidth <= 1024;
	    };
	    TerminalComponent = __decorate([
	        core_1.Component({
	            selector: 'terminal',
	            templateUrl: './app/terminal/terminal.html',
	        }), 
	        __metadata('design:paramtypes', [core_1.ChangeDetectorRef])
	    ], TerminalComponent);
	    return TerminalComponent;
	}());
	exports.TerminalComponent = TerminalComponent;
	//# sourceMappingURL=terminal.component.js.map

/***/ },

/***/ 530:
/***/ function(module, exports, __webpack_require__) {

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
	var core_1 = __webpack_require__(11);
	var http_1 = __webpack_require__(498);
	__webpack_require__(436);
	var BASE = "rest/process";
	var JavalabService = (function () {
	    function JavalabService(http) {
	        this.http = http;
	    }
	    JavalabService.prototype.initialize = function (lang) {
	        var url = BASE + "/init" + lang;
	        return this.http.get(url)
	            .toPromise()
	            .then(function (res) { return res.json(); })
	            .catch(this.handleError);
	    };
	    JavalabService.prototype.handleError = function (error) {
	        console.error("An error occurred:", error);
	        return Promise.reject(error.message || error);
	    };
	    JavalabService.prototype.runCode = function (model) {
	        var _this = this;
	        var runCodeURL = BASE + "/run";
	        var body = JSON.stringify(model);
	        var headers = new http_1.Headers({ 'Content-Type': 'application/json' });
	        var options = new http_1.RequestOptions({ headers: headers });
	        return this.http.post(runCodeURL, body, options)
	            .toPromise()
	            .then(function (res) { return res.json(); })
	            .catch(function (error) { return _this.handleError; });
	    };
	    JavalabService.prototype.testCode = function (model) {
	        var _this = this;
	        var testCodeURL = BASE + "/test";
	        var body = JSON.stringify(model);
	        var headers = new http_1.Headers({ 'Content-Type': 'application/json' });
	        var options = new http_1.RequestOptions({ headers: headers });
	        return this.http.post(testCodeURL, body, options)
	            .toPromise()
	            .then(function (res) { return res.json(); })
	            .catch(function (error) { return _this.handleError; });
	    };
	    JavalabService.prototype.findNodeById = function (id, tree) {
	        for (var _i = 0, tree_1 = tree; _i < tree_1.length; _i++) {
	            var node = tree_1[_i];
	            var found = this.searchNode(id, node);
	            if (found !== null) {
	                return found;
	            }
	        }
	    };
	    JavalabService.prototype.searchNode = function (id, node) {
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
	    JavalabService.prototype.download = function (model) {
	        // Xhr creates new context so we need to create reference to this
	        var self = this;
	        var pending = true;
	        // Create the Xhr request object
	        var xhr = new XMLHttpRequest();
	        var url = BASE + "/download";
	        xhr.open('POST', url, true);
	        xhr.setRequestHeader("Content-type", "application/json");
	        xhr.responseType = 'blob';
	        // Xhr callback when we get a result back
	        // We are not using arrow function because we need the 'this' context
	        xhr.onreadystatechange = function () {
	            // We use setTimeout to trigger change detection in Zones
	            setTimeout(function () {
	                pending = false;
	            }, 0);
	            // If we get an HTTP status OK (200), save the file using fileSaver
	            if (xhr.readyState === 4 && xhr.status === 200) {
	                var blob = new Blob([this.response], { type: 'application/zip' });
	                saveAs(blob, 'project.zip');
	            }
	        };
	        // Start the Ajax request
	        xhr.send(JSON.stringify(model));
	    };
	    JavalabService = __decorate([
	        core_1.Injectable(), 
	        __metadata('design:paramtypes', [http_1.Http])
	    ], JavalabService);
	    return JavalabService;
	}());
	exports.JavalabService = JavalabService;
	//# sourceMappingURL=javalab.service.js.map

/***/ },

/***/ 531:
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	var router_1 = __webpack_require__(384);
	var app_component_1 = __webpack_require__(521);
	var common_1 = __webpack_require__(205);
	exports.routes = [
	    { path: ':lang', component: app_component_1.AppComponent },
	    { path: '', component: app_component_1.AppComponent }
	];
	exports.routingProviders = [
	    { provide: common_1.LocationStrategy, useClass: common_1.HashLocationStrategy }
	];
	exports.routing = router_1.RouterModule.forRoot(exports.routes);
	//# sourceMappingURL=main.routes.js.map

/***/ }

});