PrimeFaces.widget.Draggable=PrimeFaces.widget.BaseWidget.extend({init:function(a){this.cfg=a;this.id=this.cfg.id;this.jqId=PrimeFaces.escapeClientId(this.id);this.jq=$(PrimeFaces.escapeClientId(this.cfg.target));if(this.cfg.appendTo){this.cfg.appendTo=PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(this.cfg.appendTo)}this.jq.draggable(this.cfg);this.removeScriptElement(this.id)}});PrimeFaces.widget.Droppable=PrimeFaces.widget.BaseWidget.extend({init:function(a){this.cfg=a;this.id=this.cfg.id;this.jqId=PrimeFaces.escapeClientId(this.id);this.jq=$(PrimeFaces.escapeClientId(this.cfg.target));this.bindDropListener();this.jq.droppable(this.cfg);this.removeScriptElement(this.id)},bindDropListener:function(){var a=this;this.cfg.drop=function(c,d){if(a.cfg.onDrop){a.cfg.onDrop.call(a,c,d)}if(a.cfg.behaviors){var e=a.cfg.behaviors.drop;if(e){var b={params:[{name:a.id+"_dragId",value:d.draggable.attr("id")},{name:a.id+"_dropId",value:a.cfg.target}]};e.call(a,b)}}}}});