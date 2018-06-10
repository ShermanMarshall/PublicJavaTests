define(function(require) {
	class Group {
		constructor(name, items) {
			this.name = name;
			this.items = items || [];
		}
	};
	class Item {
		constructor(name, link) {
			this.name = name;
			this.link = link;
		}
	};
	class View {
		constructor(obj) {
			this.el = 'template-panel-content';
			this.groups = obj.groups || [];
			this.panel = this.createPanel();
		}
		createPanel() {
			var panel = `<div class='panel panel-default'>`;
			this.groups.forEach((group) => {
				panel += `<div class='panel-heading'>
					<h4 class='panel-title'>
						<a data-toggle='collapse' data-target='#${group.name}'>${group.name}</a>
					</h4>
				</div>
				<div id='${group.name}' class='panel-collapse collapse'>`;
				group.items.forEach((item) => {
					panel += `<div class='panel-body'><a href='${item.link}'>${item.name}</a></div>`;
				});
				panel += '</div>';
			});
			return panel;
		}
		render() {
			var el = document.getElementById(this.el);
			el.innerHTML = this.panel;
		}
	};
	return {
		group: Group,
		item: Item,
		view: View
	};
});
