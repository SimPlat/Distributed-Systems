package secondAssignment;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;

import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.net.URL;

import secondAssignment.RMIInterface;

public class Server extends UnicastRemoteObject implements RMIInterface {

	private int fileCounter;
	private int docCounter;
	private long fileSize;

	public Server(int port) throws RemoteException {
		super(port);
		fileCounter = 0;
		docCounter = 0;
		fileSize = 0;
	}

	public String fetchURL(String targetURL) throws RemoteException {
		FileWriter appendData = null;
		FileReader tmpData = null;
		
		long size = fileSize;
		File tmpFile = new File("src/main/resources/secondAssignment/fetchedData/tmp");
		fileCounter += (docCounter % 10 == 0) ? 1 : 0;
		File storageFile = new File("File" + fileCounter);
		

		try {
			FileUtils.copyURLToFile(new URL(targetURL),tmpFile);
		}catch (IOException ioe) {ioe.printStackTrace();}
		finally{
			docCounter++;
			fileSize += (docCounter % 10 != 0) ? FileUtils.sizeOf(tmpFile) : -fileSize;
		}

		try {
			tmpData = new FileReader(tmpFile);
			appendData = new FileWriter("src/main/resources/secondAssignment/fetchedData/" + storageFile,true);
			
			int c = tmpData.read();

			while(c != -1){
				appendData.write(c);
				c = tmpData.read();
			}
			appendData.write("\n");
		}
		catch(IOException ioe) {ioe.printStackTrace();}
		finally{
			try {tmpData.close();} catch (IOException e){}
			try {appendData.close();} catch (IOException e){}
			try {FileUtils.forceDeleteOnExit(tmpFile);} catch (IOException e) {}
		}

		return "Doc" + docCounter + ": " + storageFile + ", " + size;
	}

	public static void main(String[] args) throws RemoteException{
		try{
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Second Assignment", new Server(1099));
			System.out.println("Server Initialized!");
		}catch (RemoteException re){re.printStackTrace();}
	}
}
