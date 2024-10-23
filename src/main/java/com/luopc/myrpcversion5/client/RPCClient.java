package com.luopc.myrpcversion5.client;

import com.luopc.myrpcversion5.common.RPCRequest;
import com.luopc.myrpcversion5.common.RPCResponse;


public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request) throws InterruptedException;

}
