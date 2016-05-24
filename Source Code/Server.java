import java.net.*;
import java.io.*;
import java.util.*;
/*
* Server class which deals in both the transfering and receiving of files with the client.
*/
public class Server
{
	// main method whihc will start the server.
	public static void main (String args[]) 
	{
		try
		{
			int portNo = 4234;
			ServerSocket serverSocket = new ServerSocket(portNo);
			while(true)
			{
				Socket clientSocket = serverSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		}
		catch(IOException e)
		{
			System.out.println("Listen :"+e.getMessage());
		}
	}
}
// This class will act as a thread for each client request.
class Connection extends Thread 
{
	DataInputStream inputStream;
	DataOutputStream outputStream;
	Socket clientSocket;
	File f;
	static Map<String,Integer> hmap=new LamportTime().getHashMap();	
	static String status="Want";
	public Connection (Socket clientSocket)
	{
		try 
		{
			this.clientSocket = clientSocket;
			inputStream = new DataInputStream( clientSocket.getInputStream());
			outputStream =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		}
		catch(IOException e)
		{
			System.out.println("Connection:"+e.getMessage());
		}
	}
	// This run method handles the dofferent cases thrown by the client.
	public void run()
	{
		try
		{ 
			String dataReceived = inputStream.readUTF();
			String yourName,hostName;
			if(dataReceived.equals("Send Use CS"))
			{
				hostName=inputStream.readUTF();
				sendUseCSRequests(hostName);
				if(status.equals("Held"))
				{
					new CriticalSection();
					status="Released";
					RequestAndReplyStore r=new RequestAndReplyStore();
					while(r.hmapRequests.get(hostName).size()>0)
					{
						Client c3 = new Client(r.hmapRequests.get(hostName).poll(),"Reply To Use CS",hostName);
					}
				}
			}
			else if(dataReceived.equals("Request to Use CS"))
			{
				hostName=inputStream.readUTF();
				yourName=inputStream.readUTF();
				if( status.equals("Held") || (status.equals("Want") && (hmap.get(yourName) < hmap.get(hostName))))// Lamport (yourName) < Lamport (hostName)))
				{
					RequestAndReplyStore t=new RequestAndReplyStore();
					t.addEntryQueue(yourName,hostName);
				}
				else
				{
					Client c2 = new Client(hostName,"Reply To Use CS",yourName);
				}
			}
			else if(dataReceived.equals("Reply To Use CS"))
			{
				hostName=inputStream.readUTF();
				yourName=inputStream.readUTF();
				new RequestAndReplyStore().addEntryHashMap(yourName,hostName);
			}
		}
		catch(EOFException e) 
		{
			System.out.println("EOF:"+e.getMessage());
		}
		catch(IOException e) 
		{
			System.out.println("IO:"+e.getMessage());
		}
		finally
		{ 
			try
			{
				clientSocket.close();
			}
			catch (IOException e)
			{/*close failed*/}
		}
	}	
	// This method will deal with sending the requests to the fellow servers and after doing it, it waits for the requests to arrive
	// and in the end changes the status to Held from Want.
	public void sendUseCSRequests(String hostName)
	{
		List<String> listOfServers = new ListOfServers().getServersList();
		for(int i=0;i<listOfServers.size();i++)
		{
			if(!listOfServers.get(i).equals(hostName))
			{
				Client cy=new Client(listOfServers.get(i),"Request to Use CS",hostName);
			}
				
		}
		int setSize;
		if(new RequestAndReplyStore().getreply().isEmpty())
		{
			setSize=0;
		}
		else
			setSize=new RequestAndReplyStore().getreply().get(hostName);
		while(setSize != listOfServers.size()-1 )
		{
			if(new RequestAndReplyStore().getreply().isEmpty())
			{
				setSize=0;
			}
			else
				setSize=new RequestAndReplyStore().getreply().get(hostName);
		}
		status = "Held";
	}
}