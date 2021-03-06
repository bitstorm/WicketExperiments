package org.javalobby;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ShowParameter extends WebPage
{

	public ShowParameter(PageParameters parameters)
	{
		super(parameters);
		add(new Label("textvaluePost", RequestCycle.get().getRequest()
		    .getPostParameters().getParameterValue("textvalue")));
		add(new Label("textvalueGet", RequestCycle.get().getRequest()
		    .getQueryParameters().getParameterValue("name")));
	}
	
}
