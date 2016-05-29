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
var country_service_1 = require("../country.service");
var http_1 = require("@angular/http");
var TagsComponent = (function () {
    function TagsComponent(countryService) {
        this.countryService = countryService;
    }
    TagsComponent.prototype.filterCountryMultiple = function (event) {
        var _this = this;
        var query = event.query;
        this.countryService.getCountries()
            .subscribe(function (countries) { return _this.filteredCountriesMultiple = _this.filterCountry(query, countries); }, function (error) { return _this.errorMessage = error; });
    };
    TagsComponent.prototype.filterCountry = function (query, countries) {
        //in a real application, make a request to a remote url with the query and return filtered results, for demo we filter at client side
        var filtered = [];
        for (var i = 0; i < countries.length; i++) {
            var country = countries[i];
            if (country.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
                filtered.push(country);
            }
        }
        return filtered;
    };
    TagsComponent = __decorate([
        core_1.Component({
            selector: 'tags',
            templateUrl: './app/tags/tags.html',
            directives: [primeng_1.AutoComplete, primeng_1.Panel],
            providers: [http_1.HTTP_PROVIDERS, country_service_1.CountryService]
        }), 
        __metadata('design:paramtypes', [country_service_1.CountryService])
    ], TagsComponent);
    return TagsComponent;
}());
exports.TagsComponent = TagsComponent;
//# sourceMappingURL=tags.component.js.map