package secondAssignment;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote{
   
   /**
	*  Sends 2 matrices and specify the required operation to the server.
	*  @param targetURL String
	*  @return documentLocation String
	*/
   public String fetchURL(String targetURL) throws RemoteException;
}
