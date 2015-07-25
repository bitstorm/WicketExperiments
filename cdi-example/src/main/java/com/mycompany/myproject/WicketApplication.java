package com.mycompany.myproject;

import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without
 * deploying, run the Start class.
 * 
 * @see com.mycompany.myproject.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
		
		CdiConfiguration cdiConfiguration = new CdiConfiguration();
		cdiConfiguration.configure(this);
	}

//	@Override
//	public Session newSession(Request request, Response response)
//	{
//		return new MySession(request);
//	}
}
