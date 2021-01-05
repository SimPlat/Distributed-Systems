package firstAssignment;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

public class Client implements RMIInterface {
	
	private RMIInterface lookUp;

	@Override
	public int[][] serverSideOperation(int[][] firstMatrix, int[][] secondMatrix, String operation) throws RemoteException{
		return lookUp.serverSideOperation(firstMatrix, secondMatrix, operation);
	}

	private void connect(){
		try{
			Registry reg = LocateRegistry.getRegistry("192.168.1.2", 1099);
			lookUp = (RMIInterface) reg.lookup("First Assignment");
			System.out.println("Connected to 192.168.1.2:1099");
		}catch (RemoteException | NotBoundException re) {re.printStackTrace();}
	}

	// Output needs improvements on bracket size scaling depending on the max size of the rows. 
	public String matrixToString(int[][] matrix) {
		int pos = 1;
		boolean newLine = true;
		String output = "┌   ┐\n";
		for (int[] row : matrix){
			for (int value : row){
				output += newLine ? "│" + value + " " : (pos == row.length) ? value : value + " ";
				newLine = false;
				pos++;
			}
			pos = 1;
			newLine = true;
			output += "│\n";
		}
		output += "└   ┘\n";
		return output;
   }

	public static void main(String args[]){
		Client client = new Client();
		client.connect();

		int[][] mtrxOne = {
			{1,1},
			{2,2},
			{3,3}
		};

		int[][] mtrxTwo = {
			{2,2},
			{3,3},
			{4,4}
		};

		int[][] mtrxThree = {
			{1,2,3},
			{2,3,4}
		};

		int[][] mtrxRslt = new int[0][0];

		// <editor-fold defaultstate="collapsed" desc="Summation Test">
		try{
			mtrxRslt = client.serverSideOperation(mtrxOne, mtrxTwo, "summation");
		}
		catch(RemoteException re){
			re.printStackTrace();
		}
		finally{
			System.out.print(client.matrixToString(mtrxOne));
			System.out.println("+");
			System.out.print(client.matrixToString(mtrxTwo));
			System.out.println("-----");
			System.out.print(client.matrixToString(mtrxRslt));
			System.out.println("=================");
			
		}// </editor-fold>
		
		// <editor-fold defaultstate="collapsed" desc="Subtraction Test">
		try{
			mtrxRslt = client.serverSideOperation(mtrxOne, mtrxTwo, "subtraction");
		}
		catch(RemoteException re){
			re.printStackTrace();
		}
		finally{
			System.out.print(client.matrixToString(mtrxOne));
			System.out.println("-");
			System.out.print(client.matrixToString(mtrxTwo));
			System.out.println("-----");
			System.out.print(client.matrixToString(mtrxRslt));
			System.out.println("=================");
		}// </editor-fold>

		// <editor-fold defaultstate="collapsed" desc="Multiplication Test">
		try{
			mtrxRslt = client.serverSideOperation(mtrxOne, mtrxTwo, "multiplication");
		}
		catch(RemoteException re){
			re.printStackTrace();
		}
		finally{
			System.out.print(client.matrixToString(mtrxOne));
			System.out.println("*");
			System.out.print(client.matrixToString(mtrxTwo));
			System.out.println("-----");
			System.out.print(client.matrixToString(mtrxRslt));
			System.out.println("=================");
		}// </editor-fold>

		// <editor-fold defaultstate="collapsed" desc="Matrix Multiplication Test">
		try{
			mtrxRslt = client.serverSideOperation(mtrxOne, mtrxThree, "matrix multiplication");
		}
		catch(RemoteException re){
			re.printStackTrace();
		}
		finally{
			System.out.print(client.matrixToString(mtrxOne));
			System.out.println("·");
			System.out.print(client.matrixToString(mtrxThree));
			System.out.println("---------");
			System.out.print(client.matrixToString(mtrxRslt));
			System.out.println("=================");
		}// </editor-fold>

	}
}

