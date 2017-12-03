package com.blargsworkshop.sleepstone.proxy;

public class ClientProxy extends BlargsClientProxy {

	@Override
	protected Class<? extends BlargsCommonProxy> getCommonProxyClass() {
		return CommonProxy.class;
	}
    
}
