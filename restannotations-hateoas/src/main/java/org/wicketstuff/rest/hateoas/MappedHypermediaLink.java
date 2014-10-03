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
package org.wicketstuff.rest.hateoas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.wicketstuff.rest.hateaos.annotations.HypermediaParameter;
import org.wicketstuff.rest.resource.MethodMappingInfo;

public class MappedHypermediaLink {
	private final MethodMappingInfo mappingInfo;
	private final String rel;
	private final String type;
	private final Class<?> entityClass;
	private final HypermediaParameter[] linkParams;
	
	public MappedHypermediaLink(MethodMappingInfo mappingInfo, String rel, String type, 
		          HypermediaParameter[] linkParams, Class<?> entityClass) {
		this.mappingInfo = mappingInfo;
		this.rel = rel;
		this.type = type;
		this.entityClass = entityClass;
		this.linkParams = linkParams;
		
		Arrays.sort(this.linkParams, new HypermediaParameterComparator());
	}

	public String getRel() {
		return rel;
	}

	public String getType() {
		return type;
	}

	public Class<?> getResourceClass()
	{
	    return entityClass;
	}

	public MethodMappingInfo getMappingInfo()
	{
	    return mappingInfo;
	}

	public Iterator<String> getPropertiesIterator()
	{
	    List<String> properties = new ArrayList<String>();
	    
	    for (int i = 0; i < linkParams.length; i++)
	    {
		properties.add(linkParams[i].propertyExpression());
	    }
	    
	    return properties.iterator();
	}
	
	static class HypermediaParameterComparator implements Comparator<HypermediaParameter> {

	    @Override
	    public int compare(HypermediaParameter hypermediaParameter, HypermediaParameter otherHypermediaParameter)
	    {
		return Integer.compare(hypermediaParameter.parameterIndex(), 
				       otherHypermediaParameter.parameterIndex());
	    }
	    
	}
}
