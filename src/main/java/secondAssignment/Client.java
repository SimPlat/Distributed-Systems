package secondAssignment;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

import secondAssignment.RMIInterface;

public class Client implements RMIInterface {

	private RMIInterface lookUp;

	public String fetchURL(String targetURL) throws RemoteException {
		return lookUp.fetchURL(targetURL);
	}

	private void connect() {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);
			lookUp = (RMIInterface) registry.lookup("Second Assignment");
			System.out.println("Connected to localhost:1099");
		} catch (RemoteException | NotBoundException re) {re.printStackTrace();}
	}

	private ArrayList<String> urlFileToList(){
		ArrayList<String> listOfUrls = new ArrayList<>();
		File urlFiles = new File("src/main/resources/secondAssignment/urls.txt");
		FileReader reader = null;

		try{
			reader = new FileReader(urlFiles);
			StringBuffer sb = new StringBuffer();
			while (reader.ready()){
			  char c = (char) reader.read();
			  if (c == '\n'){
					listOfUrls.add(sb.toString());
					sb = new StringBuffer();
				} 
				else sb.append(c);
			}
			if (sb.length() > 0) listOfUrls.add(sb.toString());
		}catch(IOException ioe) {ioe.printStackTrace();}
		finally{
			try {reader.close();}catch (IOException e) {}
		}
		
		return listOfUrls;
	}

	public static void main(String args[]){
		Client client = new Client();
		client.connect();
		
		ArrayList<String> listOfUrls = client.urlFileToList();

		listOfUrls.forEach(url -> {
			try {
				System.out.println("Requesting download of " + url + " from the server.");
				System.out.println(client.fetchURL(url));
			}catch(RemoteException re) {re.printStackTrace();}
		});
	}
}
