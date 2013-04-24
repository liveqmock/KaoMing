package com.dne.sie.util.init;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.*;

public class ContextMgr {
    private static Context ctx;
    static {
        try {
            ctx = new InitialContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Context getContext(){
        if(ctx==null){
            try {
                ctx = new InitialContext();
            } catch (NamingException ex) {
                ex.printStackTrace();
            }
        }
        return ctx;
    }
}
