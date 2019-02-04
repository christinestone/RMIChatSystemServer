package com.chat.server;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.chat.apis.ChatServer;

public class ChatServerDriver {
    private static ChatServerImpl chatServer = new ChatServerImpl();

    public static void main(String[] args) {
        
        Logger logger = Logger.getLogger("MyLog");  
        FileHandler fh;

        try {
            fh = new FileHandler("C:/temp/MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info("Starting chat server");

            ChatServer stub = (ChatServer) UnicastRemoteObject.exportObject(chatServer, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("chatServer", stub);

            System.out.println("Chat Server ready ...");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
