define(function(require) {
	var Backbone 	= require('backbone');
	var _		= require('underscore');

	var Item = Backbone.Model.extend({
		defaults: {
			link: '',
			name: ''
		}
	});
	var Group = Backbone.Model.extend({
		initialize: function() {
			this.set('items', []);
		},
		defaults: {
			name: '',
		},
		addItem: function(params) {
			this.get('items').push(new Item(params));
		}
	});
	var AccordionCollection = Backbone.Collection.extend({
		model: Group,
	});
	var Panel = Backbone.View.extend({
		el: '#template-panel-content',
		initialize: function(object) {
			this.createTemplate();
		},
		createTemplate: function() {
			var panelTemplate = "<div class='panel panel-default'>";
			//Iterate over the content to display each individual item
			this.collection.models.forEach(function(item) {
				//If the item in the collection is a group, handle all elements
				if (item.get('items') && item.get('items') instanceof Array) {
					panelTemplate += "<div class='panel-heading'><h4 class='panel-title'><a data-toggle='collapse' " +
							"data-target='#" + item.get('name') + "'>" + item.get('name') + "</a></h4></div><div id='" +
							item.get('name') + "' class='panel-collapse collapse'>";
					item.get('items').forEach(function(model) {
						panelTemplate += "<div class='panel-body'><a href='" + model.get('link') +"'>" + model.get('name') + "</a></div>";
					});
					panelTemplate += "</div>";
					console.log(item);
				}
			});
			panelTemplate += "</div>";
			this.template = _.template(panelTemplate);
			console.log(panelTemplate);
		}
	});
	return {
		view: Panel,
		collection: AccordionCollection,
		model: Group
	};
});
