package com.blargsworkshop.sleepstone.proxy;

import com.blargsworkshop.sleepstone.IModItems;
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
