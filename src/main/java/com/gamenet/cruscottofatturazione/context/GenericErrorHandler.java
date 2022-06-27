package com.gamenet.cruscottofatturazione.context;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Component("genericErrorHandler")
public class GenericErrorHandler extends DefaultResponseErrorHandler {

	public GenericErrorHandler() {
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {

		switch (response.getStatusCode()) {
		case BAD_REQUEST:
			throw new IOException(response.getStatusText());
		case UNAUTHORIZED:
			throw new IOException(response.getStatusText());
		case NOT_FOUND:
			throw new IOException(response.getStatusText());
		case METHOD_NOT_ALLOWED:
			throw new IOException(response.getStatusText());
		case NOT_ACCEPTABLE:
			throw new IOException(response.getStatusText());
		case UNSUPPORTED_MEDIA_TYPE:
			throw new IOException(response.getStatusText());
		default:
			throw new IOException("Internal Server Error");
		}
	}
}
