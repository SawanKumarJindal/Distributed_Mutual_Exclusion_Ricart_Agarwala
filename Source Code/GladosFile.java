import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
/*
* This is the main class where the execution begins.
*/
public class GladosFile
{
	static List<String> listOfServers = new ListOfServers().getServersList();
	public static void sendMessageToSendRequest(String serverName)
	{
		System.out.println(serverName);
		Client cl=new Client(serverName,"Send Use CS");
	}
	// Main method where the client starts all the servers and sends them the message to start fighting for Critical Section. 
	public static void main(String[] args)
	{
		Thread t=new Thread();
		for(int i=0;i<listOfServers.size();i++)
		{
			sendMessageToSendRequest(listOfServers.get(i));
			try{
			t.sleep(1000);
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
}