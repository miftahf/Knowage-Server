/*
 * Knowage, Open Source Business Intelligence suite
 * Copyright (C) 2016 Engineering Ingegneria Informatica S.p.A.
 * 
 * Knowage is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Knowage is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.eng.spagobi.security.hmacfilter;

import it.eng.spagobi.commons.utilities.StringUtilities;
import it.eng.spagobi.utilities.Helper;
import it.eng.spagobi.utilities.assertion.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.client.ClientRequest;

/**
 * Provide client HMAC authentication for {@link HMACFilter}.
 * 
 * @author fabrizio
 * 
 */
public class HMACFilterAuthenticationProvider {

	private final String key;
	private final HMACTokenValidator validator;

	public HMACFilterAuthenticationProvider(String key) {
		this(key, new SystemTimeHMACTokenValidator(HMACFilter.MAX_TIME_DELTA_DEFAULT_MS));
	}

	public HMACFilterAuthenticationProvider(String key, HMACTokenValidator validator) {
		Helper.checkNotNullNotTrimNotEmpty(key, "key");
		Helper.checkNotNull(validator, "validator");

		this.key = key;
		this.validator = validator;
	}

	/**
	 * For REST Easy {@link ClientRequest}
	 * 
	 * @param req
	 * @throws HMACSecurityException
	 */
	public void provideAuthentication(ClientRequest req) throws HMACSecurityException {
		Helper.checkNotNull(req, "req");

		String token = validator.generateToken();
		Assert.assertNotNull(token, "token");
		String signature;
		try {
			signature = getSignature(req, token);
		} catch (Exception e) {
			throw new HMACSecurityException("Problems while signing the request", e);
		}

		req.header(HMACUtils.HMAC_TOKEN_HEADER, token);
		req.header(HMACUtils.HMAC_SIGNATURE_HEADER, signature);
	}

	private String getSignature(ClientRequest req, String token) throws IOException, Exception {
		String res = HMACUtils.sign(getBody(req),getQueryPath(req),  getParamsString(req), getHeaders(req), token, key);
		return res;
	}

	private static String getHeaders(ClientRequest req) {
		MultivaluedMap<String, String> headers = req.getHeaders();
		StringBuilder res = new StringBuilder();
		for (String name : HMACUtils.HEADERS_SIGNED) {
			List<String> values = headers.get(name); // only 1 value admitted
			if (values == null) {
				// header not present
				continue;
			}
			Assert.assertTrue(values.size() == 1, "only one value admitted for each header");
			res.append(name);
			res.append(values.get(0));
		}
		return res.toString();
	}

	private static String getBody(ClientRequest req) throws IOException {
		Object body = req.getBody();
		if (body == null) {
			return "";
		}
		if (body instanceof String) {
			String bodyS = (String) body;
			return bodyS;
		}
		if (body instanceof InputStream) {
			InputStream stream = (InputStream) body;
			String s = StringUtilities.readStream(stream);
			// replace the already read stream
			InputStream replace = new ByteArrayInputStream(s.getBytes(StringUtilities.DEFAULT_CHARSET));
			req.body(req.getBodyContentType(), replace);
			return s;
		}
		Assert.assertUnreachable("body object not supported");
		return null;
	}

	private static String getParamsString(ClientRequest req) throws Exception {
		String uri = req.getUri();
		URL url = new URL(uri);
		return url.getQuery();
	}

	private static String getQueryPath(ClientRequest req) throws Exception {
		String uri = req.getUri();
		URL url = new URL(uri);
		return url.getPath();
	}
}
