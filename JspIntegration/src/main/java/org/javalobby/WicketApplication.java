package org.javalobby;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.jeeweb.JEEWebResolver;
import org.wicketstuff.jeeweb.ajax.JEEWebGlobalAjaxHandler;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see org.javalobby.Start#main(String[])
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

		getPageSettings().addComponentResolver(new JEEWebResolver());
		
		//for AJAX support
		JEEWebGlobalAjaxHandler.configure(this);
	}
}
