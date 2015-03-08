package org.apache.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		Label link = new Label("link", "ciao");
		
		add(link.add(new IndicatingAjaxEventBehavior("click") {
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				try {
				    Thread.sleep(5000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
		}));
		
		PackageResourceReference reference = new PackageResourceReference(getPageClass(), "bigIndicator.gif");
		Label linkBig = new Label("linkBig", "big ciao");
		
		add(linkBig.add(new IndicatingAjaxEventBehavior("click", reference) {
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				try {
				    Thread.sleep(5000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
		}));
    }
}
