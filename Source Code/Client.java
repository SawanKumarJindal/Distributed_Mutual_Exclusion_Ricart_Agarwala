import java.net.*;
import java.io.*;
import java.util.*;
/*
* Client class which deals in both the transfering and receiving of files with the server.
*/
public class Client 
{
	Socket s=null;
	DataInputStream dataIStream;
	DataOutputStream dataOStream;
	// Client constructors dealing with multiple scenario's. Each constructor is created to handle specific case.
	public Client(String serverName,String msg)
	{
		try
		{
			int portNo = 4234;
			s = new Socket(serverName, portNo);
			dataIStream = new DataInputStream( s.getInputStream());
			dataOStream =new DataOutputStream( s.getOutputStream());
			if(msg.equals("Send Use CS"))
			{
				dataOStream.writeUTF(msg);	
				dataOStream.writeUTF(serverName);	
			}
		}
		catch (UnknownHostException e)
		{
			System.out.println("Sock:"+e.getMessage());
		}
		catch (EOFException e)
		{
			System.out.println("EOF:"+e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("IO:"+e.getMessage());
		}
		finally 
		{
			if(s!=null)
				try 
				{
					s.close();
				}
				catch (IOException e)
				{/*close failed*/}
		}
	}
	public Client(String serverName,String msg,String hostName)
	{
		try
		{
			int portNo = 4234;
			s = new Socket(serverName, portNo);
			dataIStream = new DataInputStream( s.getInputStream());
			dataOStream =new DataOutputStream( s.getOutputStream());
			if(msg.equals("Request to Use CS"))
			{
				dataOStream.writeUTF(msg);	
				dataOStream.writeUTF(hostName);	
				dataOStream.writeUTF(serverName);	
			}
			else if(msg.equals("Reply To Use CS"))
			{
				dataOStream.writeUTF(msg);	
				dataOStream.writeUTF(hostName);	
				dataOStream.writeUTF(serverName);
			}
		}
		catch (UnknownHostException e)
		{
			System.out.println("Sock:"+e.getMessage());
		}
		catch (EOFException e)
		{
			System.out.println("EOF:"+e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("IO:"+e.getMessage());
		}
		finally 
		{
			if(s!=null)
				try 
				{
					s.close();
				}
				catch (IOException e)
				{/*close failed*/}
		}
	}
}