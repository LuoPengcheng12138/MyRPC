package com.luopc.myrpcversion4.client;

import com.luopc.myrpcversion4.common.RPCRequest;
import com.luopc.myrpcversion4.common.RPCResponse;


public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request) throws InterruptedException;

}
