package firstAssignment;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements RMIInterface {
	
	public Server(int port) throws RemoteException{
		super(port);
	}
	
	@Override
	public int[][] serverSideOperation(int[][] firstMatrix, int[][] secondMatrix, String operation){
		int firstY = firstMatrix.length;
		int firstX = firstMatrix[0].length;
		int secondY = secondMatrix.length;
		int secondX = secondMatrix[0].length;
		int[][] resultMatrix = new int[0][0];

		switch(operation){
			case "summation":{
				if(firstY == secondY && firstX == secondX){
					resultMatrix = new int [firstY][firstX];
					for (int i = 0; i < firstY; i++){
						for (int j = 0; j < firstX; j++){
							resultMatrix[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
						}
				  }
				}
				else System.out.println("Incompatable Matrices for this Operation.");
				break;
			}
			case "subtraction":{
				if(firstY == secondY && firstX == secondX){
					resultMatrix = new int [firstY][firstX];
					for (int i = 0; i < firstY; i++){
						for (int j = 0; j < firstX; j++){
							resultMatrix[i][j] = firstMatrix[i][j] - secondMatrix[i][j];
						}
				   }
				}
				else System.out.println("Incompatable Matrices for this Operation.");
				break;
			}
			case "multiplication":{
				if(firstY == secondY && firstX == secondX){
					resultMatrix = new int [firstY][firstX];
					for (int i = 0; i < firstY; i++){
						for (int j = 0; j < firstX; j++){
							resultMatrix[i][j] = firstMatrix[i][j] * secondMatrix[i][j];
						}
				   }
				}
				else System.out.println("Incompatable Matrices for this Operation.");
				break;
			}
			case "matrix multiplication":{
				if(firstX == secondY){
					resultMatrix = new int [firstY][secondX];
					for (int i = 0; i < firstY; i++){
						for (int j = 0; j < secondX; j++){
							for (int k = 0; k < firstX; k++){
								resultMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
							}
						}
				   }
				}
				else System.out.println("Incompatable Matrices for this Operation.");
				break;
			}
			default:{
				System.out.println("Unkown Operation!");
			}
		}
		return resultMatrix;
	}

	public static void main(String[] args) throws RemoteException{
		try{
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("First Assignment", new Server(1099));
			System.out.println("Server initialized!");
		}catch (RemoteException re){re.printStackTrace();}
   }

}

