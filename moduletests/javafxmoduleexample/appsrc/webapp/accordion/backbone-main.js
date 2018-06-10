define(function(require) {
	var Backbone = require('backbone');
	var bootstrap = require('bootstrap');

	var accordion = require('accordionPanel');

	var Group = accordion.model;
	var AccordionCollection = accordion.collection;
	var Panel = accordion.view;

	var Item = Backbone.Model.extend();

	var g1 = new Group({}); 
	g1.set('name', "group1");
	g1.get('items').push(new Item({ link: 'group1link', name: 'group1link' }));

	var g2 = new Group({name: 'group2'});
	g2.get('items').push(new Item({link: 'group2link', name: 'group2link'}));

	var set = new AccordionCollection();
	set.add(g1); 
	set.add(g2);

	console.log(set);

	var instance = new Panel({ collection: set }); 
	instance.$el.html = instance.template(instance.collection);
	$('#template-panel-content').html(instance.$el.html);
});
