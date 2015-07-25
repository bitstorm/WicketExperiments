package com.mycompany.myproject;

import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class MySession extends WebSession
{
	@Inject
	private MyBean message;
	
	public MySession(Request request)
	{
		super(request);
		NonContextual.of(MySession.class).inject(this);
	}

}
