/**
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.util.string.Strings;
import org.wicketstuff.rest.annotations.parameters.PathParam;
import org.wicketstuff.rest.resource.MethodMappingInfo;
import org.wicketstuff.rest.resource.urlsegments.AbstractURLSegment;
import org.wicketstuff.rest.resource.urlsegments.FixedURLSegment;
import org.wicketstuff.rest.resource.urlsegments.MultiParamSegment;
import org.wicketstuff.rest.resource.urlsegments.ParamSegment;
import org.wicketstuff.rest.resource.urlsegments.visitor.ISegmentVisitor;
import org.wicketstuff.rest.utils.reflection.MethodParameter;

public class SegmentGeneratorVisitor implements ISegmentVisitor
{
	private final List<MethodParameter<?>> namedParams;
	private final List<AbstractURLSegment> segments;
	private final List<String> actualSegments;
	private final Iterator<Object> valuesIterator;
	private final Object[] paramsValues;

	public SegmentGeneratorVisitor(MethodMappingInfo methodInfo, Object[] paramsValues)
	{
		this(methodInfo, paramsValues, Arrays.asList(paramsValues).iterator());
	}
	
	public SegmentGeneratorVisitor(MethodMappingInfo methodInfo, Object[] paramsValues, Iterator<Object> valuesIterator)
	{
		this(methodInfo.getAnnotatedMethodParameters().get(PathParam.class), 
			 methodInfo.getSegments(), valuesIterator, paramsValues);
	}
	
	public SegmentGeneratorVisitor(List<MethodParameter<?>> namedParams,
		List<AbstractURLSegment> segments, Iterator<Object> valuesIterator, 
		Object[] paramsValues)
	{
		this.namedParams = namedParams;
		this.segments = segments;
		this.actualSegments = new ArrayList<String>();
		this.valuesIterator = valuesIterator;
		this.paramsValues = paramsValues;
		
		generateSegments();
	}

	private List<String> generateSegments()
	{
		for (AbstractURLSegment abstractURLSegment : segments)
		{
			abstractURLSegment.accept(this);
		}
		
		return actualSegments;
	}
	
	@Override
	public void visit(FixedURLSegment segment)
	{
		actualSegments.add(segment.toString());
	}

	@Override
	public void visit(MultiParamSegment segment)
	{
		SegmentGeneratorVisitor subVisitor = new SegmentGeneratorVisitor(namedParams, 
			segment.getSubSegments(), valuesIterator, paramsValues);
		
		String segmentValue = Strings.join("", subVisitor.getActualSegments());
		actualSegments.add(segmentValue);
	}

	@Override
	public void visit(ParamSegment segment)
	{
		String paramValue = findParamValue(segment.getParamName());
		
		if(paramValue != null)
		{
			actualSegments.add(paramValue);
		} else 
		{
			actualSegments.add(valuesIterator.next().toString());			
		}
	}

	private String findParamValue(String paramName)
	{
		if(namedParams == null)
		{
			return null;			
		}
		
		for (MethodParameter<?> namedParam : namedParams)
		{
			PathParam pathParam = (PathParam)namedParam.getAnnotationParam();
			
			if(pathParam.value().equals(paramName))
			{
				return paramsValues[namedParam.getParamIndex()].toString();
			}
		}
		
		return null;
	}

	public List<String> getActualSegments()
	{
		return actualSegments;
	}
}