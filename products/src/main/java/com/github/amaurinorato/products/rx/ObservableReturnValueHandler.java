package com.github.amaurinorato.products.rx;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import rx.Observable;

public class ObservableReturnValueHandler implements HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		Class<?> parameterType = returnType.getParameterType();
		return parameterType == Observable.class;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		if (returnValue == null) {
			mavContainer.setRequestHandled(true);
			return ;
		}
		
		final DeferredResult<Object> dr = new DeferredResult<>();
		Observable<?> obs = (Observable<?>) returnValue;
		obs.subscribe(res -> {
			dr.setResult(res);
		}, ex -> {
			dr.setErrorResult(ex);
		});
		
		WebAsyncUtils.getAsyncManager(webRequest).startDeferredResultProcessing(dr, mavContainer);
	}
	

}
