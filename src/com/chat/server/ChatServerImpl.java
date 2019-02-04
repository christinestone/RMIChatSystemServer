package com.chat.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.chat.apis.ChatClient;
import com.chat.apis.ChatServer;
import com.server.exceptions.UserAlreadyExistsException;

public class ChatServerImpl implements ChatServer {
	private Map<String, ChatClient> clientsMap = new HashMap<>();

	@Override
	public void registerUser(ChatClient client, String clientName) throws RemoteException {
		clientsMap.put(clientName, client);        
		System.out.println(clientName + " has joined.");
	}

	@Override
	public ArrayList<String> listRegisteredUsers() throws RemoteException {
		return new ArrayList<String>(clientsMap.keySet());
	}

	@Override
	public void sendPrivateMessage(String message, String user) throws RemoteException {
		clientsMap.get(user).display(message);
	}

	@Override
	public void broadcastMessages(String message) throws RemoteException {
		for (Map.Entry<String, ChatClient> client : clientsMap.entrySet()) {
			String clientName = client.getKey();
			if (!message.startsWith(clientName)) {
				client.getValue().display(message);
			}
		}
	}
}
