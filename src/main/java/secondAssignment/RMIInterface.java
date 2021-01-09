package secondAssignment;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote{
   
   /**
	*  Request 1 URL to be downloaded on the server and the server will return its location(ex. 'Doc#: File#, Distance_from_start_in_bytes).
	*  @param targetURL String
	*  @return documentLocation String
	*/
   public String fetchURL(String targetURL) throws RemoteException;
}
