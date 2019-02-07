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
			clientsMap.put(clientName, client);
			broadcast(clientName + " joined the conversation.");
	}

	@Override
	public ArrayList<String> listUsers() throws RemoteException {
		return new ArrayList<String>(clientsMap.keySet());
	}
	
	@Override
	public void updateUsersList(String clientName) throws RemoteException {
		clientsMap.remove(clientName);
	}

	@Override
	public void privateMessage(String user, String message) throws RemoteException {
		clientsMap.get(user).receive(message);
	}

	@Override
	public void broadcast(String message) throws RemoteException {
		for (Map.Entry<String, ChatClient> client : clientsMap.entrySet()) {
			client.getValue().receive(message);
		}
	}
}
