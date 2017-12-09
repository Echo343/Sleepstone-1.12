package com.blargsworkshop.sleepstone.proxy;

import com.blargsworkshop.engine.IModItems;
import com.blargsworkshop.engine.proxy.BlargsClientProxy;
import com.blargsworkshop.engine.proxy.BlargsCommonProxy;
import com.blargsworkshop.sleepstone.ModItems;

public class ClientProxy extends BlargsClientProxy {

	@Override
	protected Class<? extends BlargsCommonProxy> getCommonProxyClass() {
		return CommonProxy.class;
	}

	@Override
	protected Class<? extends IModItems> getModItemClass() {
		return ModItems.class;
	}
    
}
