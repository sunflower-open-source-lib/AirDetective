package com.example.administrator.airdetective.util.tool;

import java.util.HashMap;
import java.util.Map;

public class MsgManager implements MyHandlerMsg{

    private Map<String, Integer> msgMap;

    public MsgManager() {
        msgMap = new HashMap<> ();
        initValue ();
    }

    private void initValue() {
        msgMap.put ( "login", LOGIN_SUCCESS );
        msgMap.put ( "register", REGISTER_SUCCESS );
        msgMap.put ( "getFamilyInfo", GETFAMILYINFO_SUCCESS );
        msgMap.put ( "createFamily", CREATEFAMILY_SUCCESS );
        msgMap.put ( "invitation", INVITATION_SUCCESS );
        msgMap.put ( "joinFamily", JOINFAMILY_SUCCESS );
        msgMap.put ( "addRoom", ADDROOM_SUCCESS );
        msgMap.put ( "getAirInfo", GETAIRINFO_SUCCESS );
        msgMap.put ( "getDailyAirInfo", GETDAYILAIRINFO_SUCCESS );
        msgMap.put ( "getTimingAirInfo", GETTIMINGAIRINFO_SUCCESS );
        msgMap.put ( "getDeviceDataAirInfo", GETDEVICE_SUCCESS );
    }

    public int getMsg(String key) {
        return msgMap.get ( key );
    }
}


