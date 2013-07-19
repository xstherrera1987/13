package net.joeherrera.Thirteen.rminet;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

// binds one TTT_Engine as "TTT" on a local RMI registry
public class Main {
    public static void main(String[] args) throws Exception {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        

    }
}
