package com.luopc.myrpcversion3.client;

import com.luopc.myrpcversion3.common.RPCRequest;
import com.luopc.myrpcversion3.common.RPCResponse;


public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request) throws InterruptedException;

}
