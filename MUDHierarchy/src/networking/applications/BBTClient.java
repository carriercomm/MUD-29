package networking.applications;

import networking.testio.*;

import java.nio.*;

public class BBTClient {

	public static void main(String[] args)
	{
		
		ClientIO socketmanager = new ClientIO();
		int[] nums = {1,2,3,4,5,6,7,8,9,0};
		
		ByteBuffer bbuf = ByteBuffer.allocate(nums.length);
		for(int i=0;i<nums.length;i++)
		{
			bbuf.put((byte)nums[i]);
		}
		
		socketmanager.write(bbuf.array());
		
		byte[] barr = null;
		while(barr == null)
		{
			barr = socketmanager.getNext();
		}
		
		bbuf = ByteBuffer.wrap(barr);
		
		while(bbuf.hasRemaining())
		{
			System.out.print(bbuf.get() + ",");
		}
		
		IntBuffer iarr = ((ByteBuffer) bbuf.rewind()).asIntBuffer();
		
		System.out.print("\n");
		
		while(iarr.hasRemaining())
		{
			System.out.print(iarr.get() + ",");
		}
		
		System.out.print("\n");
			
		socketmanager.cleanUp();
	}

}
