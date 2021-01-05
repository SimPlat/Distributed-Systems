package firstAssignment;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {

	/**
	*  Sends 2 matrices and specify the required operation to the server.
	*  @param firstMatrix int[][]
	*  @param secondMatrix int[][]
	*  @param operation Enum(summation,subtraction,multiplication,matrix multiplication)
	*  @return resultMatrix int[][]
	*/
	public int[][] serverSideOperation(int[][] firstMatrix, int[][] secondMatrix, String operation) throws RemoteException;

}
