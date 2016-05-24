/*
* This is the main class whihc act as Critical Scetion which just have one function to print Critical Scetion.
*/
class CriticalSection
{
	public CriticalSection()
	{
		useCriticalSection();
	}
	public void useCriticalSection()
	{
		Thread t=new Thread();
		try
		{
			t.sleep(1000);
			System.out.println("Critical Scetion");
			t.sleep(1000);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}