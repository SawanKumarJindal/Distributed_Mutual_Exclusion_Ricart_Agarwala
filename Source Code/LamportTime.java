import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
class LamportTime
{
	private	Map<String, Integer> hmap;
	private static List<String> list;
	//int randomNumber;
	public LamportTime()
	{
		hmap=new HashMap<String,Integer>();
		list=new ListOfServers().getServersList();
		//Random r=new Random();
		for(int i=0;i<list.size();i++)
		{
		//	randomNumber =r.nextInt(100-2 +1) + 2;
			hmap.put(list.get(i),list.size()-i);
		}
	}
	public Map<String,Integer> getHashMap()
	{
		return hmap;
	}
	public void setHashMap(Map<String,Integer> hmap)
	{
		hmap=hmap;
	}
}