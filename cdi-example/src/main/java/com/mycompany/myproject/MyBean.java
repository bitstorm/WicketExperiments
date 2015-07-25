package com.mycompany.myproject;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyBean
{
	private String name = "ciao";

	public String getName()
	{
		return name;
	}
}
