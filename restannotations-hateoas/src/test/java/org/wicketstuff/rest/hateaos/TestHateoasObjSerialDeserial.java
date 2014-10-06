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
package org.wicketstuff.rest.hateaos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.cglib.beans.BeanMap;
import org.wicketstuff.rest.domain.PersonPojo;
import org.wicketstuff.rest.hateoas.resources.ResourceUrlBuilder;
import org.wicketstuff.rest.resource.PersonsRestResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

public class TestHateoasObjSerialDeserial extends Assert
{

	WicketTester tester = new WicketTester(new WicketApplication()); 
	
    @Test
    public void test()
    {
    	PersonsRestResource mockResource = ResourceUrlBuilder.forResourceClass(PersonsRestResource.class);
	
    	mockResource.deletePerson(12);
    	mockResource.getAllPersons();
    	mockResource.getPerson(8);
    	
    	mockResource.regExpTestMethod("foo", "bar", "baz");
    }
    
    @Test
    public void testBeanGen() throws JsonProcessingException
	{
    	PersonPojo pojo = new PersonPojo(123, "John", "Ross", "1234567");
//    	BeanGenerator beanGenerator = new BeanGenerator();
//    	beanGenerator.setSuperclass(PersonPojo.class);
//    	
//    	beanGenerator.addProperty("list", List.class);
//    	
//    	Object objCreated = beanGenerator.create();

    	
//    	map.put("list", Arrays.asList("a", "b", "c"));
    	
    	Gson gson = new Gson();
//    	ObjectMapper objMapper = new ObjectMapper();
    	
    	
    	
    	Map create = new HashMap(BeanMap.create(pojo));
    	create.put("list", Arrays.asList("a", "b", "c"));
		System.out.println(gson.toJson(create));
    	    	

//    	
//    	copier.copy(new PersonPojo(123, "John", "Ross", "1234567"), objCreated, null);
	}
    
//    class PersonMethodInterceptor implements Answer
//    {
//
//		@Override
//		public Object answer(InvocationOnMock invocation) throws Throwable
//		{
//			System.out.println(invocation.getMethod().getName());
//			if (invocation.getMethod().getDeclaringClass().equals(PersonPojo.class))
//			{
//				return invocation.getMethod().invoke(pojo);
//			}
//			
//			if (invocation.getMethod().getDeclaringClass().equals(Map.class))
//			{
//				System.out.println(invocation.getMethod().getName());
//				return invocation.getMethod().invoke(map);
//			}
//			
//			
//			return null;
//		}
//    	
//    }
}
