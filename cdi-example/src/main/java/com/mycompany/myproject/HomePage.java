package com.mycompany.myproject;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MyBean message;
	
	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

		// TODO Add your page's components here

    }
	
	@Override
	protected void onConfigure()
	{
		super.onConfigure();
		
		System.out.println(message.getName());
	}
}
