/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.rest.hateoas.resources;

import static org.mockito.Mockito.mock;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.ResourceReference.Key;
import org.apache.wicket.util.string.Strings;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.wicketstuff.rest.resource.AbstractRestResource;
import org.wicketstuff.rest.resource.MethodMappingInfo;
import org.wicketstuff.rest.resource.urlsegments.AbstractURLSegment;
import org.wicketstuff.rest.resource.urlsegments.ParamSegment;
import org.wicketstuff.rest.utils.reflection.MethodParameter;

public class ResourceUrlBuilder
{

	public static <T extends AbstractRestResource<?>> T forResourceClass(Class<T> resourceClass)
	{
		T proxyResource = mock(resourceClass, new MappedMethodInterceptor());
		return proxyResource;
	}
}


class MappedMethodInterceptor implements Answer<String>
{

	@Override
	public String answer(InvocationOnMock invocation) throws Throwable
	{
		Object obj = invocation.getMock();
		Method method = invocation.getMethod();
		Object[] args = invocation.getArguments();
		String appName = Application.class.getName();
		String mockedClassName = obj.getClass().getSuperclass().getName();

		Key key = new Key(appName, mockedClassName, null, null, null);

		ResourceReference ref = Application.get().getResourceReferenceRegistry()
			.getResourceReference(key, false, false);

		if (ref == null || !(ref instanceof HateoasResourceReference))
		{
			return "";
		}

		HateoasResourceReference hateoasReference = (HateoasResourceReference)ref;
		AbstractRestResource<?> resource = hateoasReference.getResource();
		MethodMappingInfo methodInfo = resource.getMethodInfo(method);

		if (methodInfo == null)
		{
			return "";
		}

		String resultString = RequestCycle.get().mapUrlFor(hateoasReference, null) + "/"
			+ buildMethodUrl(methodInfo, args);
		System.out.println(resultString);

		return null;
	}

	private String buildMethodUrl(MethodMappingInfo methodInfo, Object[] args)
	{
		List<AbstractURLSegment> segments = methodInfo.getSegments();
		List<String> actualSegments = new ArrayList<String>();
		Iterator<MethodParameter<?>> paramsIterator = methodInfo.getMethodParameters().iterator();
		Map<Class<? extends Annotation>, List<MethodParameter<?>>> annotatedParams = methodInfo
			.getAnnotatedMethodParameters();

		for (AbstractURLSegment abstractURLSegment : segments)
		{
			if(abstractURLSegment instanceof ParamSegment)
			{
				MethodParameter<?> param = paramsIterator.next();
				actualSegments.add(args[param.getParamIndex()].toString());
			}
		}

		return Strings.join("/", actualSegments);
	}

}
