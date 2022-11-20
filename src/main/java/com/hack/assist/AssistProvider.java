package com.hack.assist;

import com.hack.agent.ProviderBase;
import com.hack.opensdk.CmdConstants;

public class AssistProvider extends ProviderBase {

    @Override
    public int getProviderCallType() {
        return CmdConstants.CMD_ASSIST_PROVIDER_CALL;
    }
}
