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
package org.wicketstuff.rest.security;

import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.crypt.Base64;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.rest.resource.AbstractRestResource;

public class HttpBasicAuthRoleCheckingStrategy implements IRoleCheckingStrategy
{
	final private String username;
	final private String password;
	

	public HttpBasicAuthRoleCheckingStrategy(String username, String password)
	{
		Args.notNull(username, "username");
		Args.notNull(password, "password");
		
		this.username = username;
		this.password = password;
	}


	@Override
	public boolean hasAnyRole(Roles roles)
	{
		WebRequest currentWebRequest = AbstractRestResource.getCurrentWebRequest();
		String authorization = currentWebRequest.getHeader("Authorization");
		
		if (!Strings.isEmpty(authorization) && authorization.startsWith("Basic"))
		{
			String base64Credentials = authorization.substring("Basic".length()).trim();
			String credentials = new String(Base64.decodeBase64(base64Credentials));
			
			final String[] values = credentials.split(":",2);
			
			return username.equals(values[0]) &&
				password.equals(values[1]);
		}
		
		return false;
	}

}
