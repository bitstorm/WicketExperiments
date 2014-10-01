package org.javalobby;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.jeeweb.ajax.JEEWebGlobalAjaxEvent;


public class ShowParameterAjax extends WebPage
{
	
	private final Label paramLabel;

	public ShowParameterAjax()
	{
		 setStatelessHint(false);
		 
		 add(paramLabel = new Label("paramLabel", ""));
		 
		 paramLabel.setOutputMarkupPlaceholderTag(true);		
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		JEEWebGlobalAjaxEvent castedEvent = JEEWebGlobalAjaxEvent.getCastedEvent(event);
		if (castedEvent != null)
		{
			AjaxRequestTarget ajaxRequestTarget = castedEvent.getAjaxRequestTarget();
			
			// Get-Request
			StringValue paramVal = castedEvent.getPostParameters().getParameterValue("textvalue");
			
			paramLabel.setDefaultModelObject("Last submitted value: " + paramVal);
			
			ajaxRequestTarget.add(paramLabel);
		}
	}
}
