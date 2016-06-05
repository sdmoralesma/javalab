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
var tag_service_1 = require("../tag.service");
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
            templateUrl: './app/tags/tags.html',
            directives: [primeng_1.AutoComplete, primeng_1.Panel]
        }), 
        __metadata('design:paramtypes', [tag_service_1.TagService])
    ], TagsComponent);
    return TagsComponent;
}());
exports.TagsComponent = TagsComponent;
//# sourceMappingURL=tags.component.js.map