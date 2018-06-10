define(function(require) {
	var bootstrap = require('bootstrap');

	var accordion = require('class-accordionPanel');

	var Group = accordion.group;
	var Item = accordion.item;
	var Panel = accordion.view;

	var g1 = new Group('group1'); 
	g1.items.push(new Item('group1link', 'group1link'));

	var g2 = new Group('group2');
	g2.items.push(new Item('group2link', 'group2link'));

	var instance = new Panel({ groups: [g1, g2] }); 
	
	instance.render();
});
