import java.util.*;
/*
* This class stores the requests of the servers in a hashmap having servers and their queues.
* Also, it stores the replies of the servers back to the parent server such that it can use the Critical Section.
*/
class RequestAndReplyStore
{
	public static Map<String,Queue<String>> hmapRequests=new HashMap<String,Queue<String>>();
	public static Map<String,Integer> hmapReplys=new HashMap<String,Integer>();
	public RequestAndReplyStore()
	{
	}
	public void addEntryHashMap(String server, String reply)
	{
		int counter;
		if(hmapReplys.containsKey(server))
		{
			counter=hmapReplys.get(server);
			counter++;
			hmapReplys.put(server,counter);
		}
		else
		{
			counter=1;
			hmapReplys.put(server,counter);
		}
	}
	public Map<String,Integer> getreply()
	{
		return hmapReplys;
	}
	public Map<String,Queue<String>> getRequests()
	{
		return hmapRequests;
	}
	public void addEntryQueue(String server, String request)
	{
		Queue<String> tempQueue;
		if(hmapRequests.containsKey(server))
		{
			tempQueue=hmapRequests.get(server);
			tempQueue.add(request);
			hmapRequests.put(server,tempQueue);
		}
		else
		{
			tempQueue=new LinkedList<String>();
			tempQueue.add(request);
			hmapRequests.put(server,tempQueue);
		}
	}
}
