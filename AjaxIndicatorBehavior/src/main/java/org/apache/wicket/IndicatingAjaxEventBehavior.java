/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.wicket;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.ResourceReference;

public class IndicatingAjaxEventBehavior extends AjaxEventBehavior
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Resource reference for the indicator element */
	private final ResourceReference indicatorPicture;
	
	public IndicatingAjaxEventBehavior(String event)
	{
		this(event, AbstractDefaultAjaxBehavior.INDICATOR);
	}
	
	public IndicatingAjaxEventBehavior(String event, ResourceReference indicator)
	{
		super(event);
		this.indicatorPicture = indicator;		
	}

	@Override
	protected String findIndicatorId()
	{
		return getMarkupId();
	}
	
	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);
		AjaxCallListener listener = new AjaxCallListener();
		String componentId = getComponent().getMarkupId();
		String indicatorId = getMarkupId();
		
		listener.onBefore(generateIndicatorScript(componentId, indicatorId, 
			generateIndicatorMarkup()));
		attributes.getAjaxCallListeners().add(listener);
	}
	
	/**
	 * 
	 * 
	 * @param componentId
	 * @param indicatorId
	 * @param indicatorMarkup
	 * @return
	 */
	protected String generateIndicatorScript(String componentId, 
		String indicatorId, StringBuilder indicatorMarkup)
	{
		String appendScript = "if($('#%s').length === 0) { \n" +
								  "$('#%s').append('%s'); \n" +
							   "}";
		
		return String.format(appendScript, 
			indicatorId, componentId, indicatorMarkup);
	}

	/**
	 * 
	 * @param stringBuilder
	 */
	protected StringBuilder generateIndicatorMarkup()
	{
		StringBuilder stringBuilder = new StringBuilder(256);
		
		stringBuilder.append("<span class=\"")
					.append(getSpanClass())
					.append("\" style=\"display:inline;position:absolute;\">")
					.append("<img src=\"")
				    .append(getIndicatorUrl())
					.append("\" alt=\"\" ")
					.append("id=\"")
					.append(getMarkupId())
					.append( "\" /></span>");
		
		return stringBuilder;
	}
	
	/**
	 * @return url of the animated indicator image
	 */
	protected CharSequence getIndicatorUrl()
	{
		IRequestHandler handler = new ResourceReferenceRequestHandler(
			indicatorPicture);
		return RequestCycle.get().urlFor(handler);
	}

	/**
	 * @return css class name of the generated outer span
	 */
	protected String getSpanClass()
	{
		return "wicket-ajax-indicator";
	}

	/**
	 * Returns the markup id attribute of the outer most span of this indicator. This is the id of
	 * the span that should be hidden or show to hide or show the indicator.
	 * 
	 * @return markup id of outer most span
	 */
	public String getMarkupId()
	{
		return getComponent().getMarkupId() + "--ajax-indicator";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target)
	{
	
	}
}
