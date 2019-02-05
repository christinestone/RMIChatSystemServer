package com.chat.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.chat.apis.ChatClient;
import com.chat.apis.ChatServer;

public class ChatServerImpl implements ChatServer {
	private Map<String, ChatClient> clientsMap = new HashMap<>();

	@Override
	public void register(ChatClient client, String clientName) throws RemoteException {
		if(clientName.length() < 1) {
			System.out.println("Username cannot be null");
		} else {
			clientsMap.put(clientName, client);
			broadcast(clientName + " has joined");
		}
	}

	@Override
	public ArrayList<String> listUsers() throws RemoteException {
		return new ArrayList<String>(clientsMap.keySet());
	}

	@Override
	public void privateMessage(String message, String user) throws RemoteException {
		clientsMap.get(user).receive(message);
	}

	@Override
	public void broadcast(String message) throws RemoteException {
		for (Map.Entry<String, ChatClient> client : clientsMap.entrySet()) {
			client.getValue().receive(message);
		}
	}
}
