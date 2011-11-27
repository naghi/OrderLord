package orderlord.website

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

class IpInfoService { //*** to be modified

    static transactional = true

	public static void main(String[] argv)
	{
		Enumeration<NetworkInterface> ifaces;
		try
		{
			ifaces = NetworkInterface.getNetworkInterfaces();
		   
			for (NetworkInterface iface : Collections.list(ifaces))
			{
				Enumeration<NetworkInterface> virtualIfaces = iface.getSubInterfaces();
			   
				for (NetworkInterface viface : Collections.list(virtualIfaces))
				{
					System.out.println(iface.getDisplayName() + " VIRT " + viface.getDisplayName());
					Enumeration<InetAddress> vaddrs = viface.getInetAddresses();
				   
					for (InetAddress vaddr : Collections.list(vaddrs))
					{
						System.out.println("\t" + vaddr.toString());
					}
				}
				System.out.println("Real iface addresses: " + iface.getDisplayName());
				Enumeration<InetAddress> raddrs = iface.getInetAddresses();
			   
				for (InetAddress raddr : Collections.list(raddrs))
				{
					System.out.println("\t" + raddr.toString());
				}
			}
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		println "the end"
	}
	
}//end of class
