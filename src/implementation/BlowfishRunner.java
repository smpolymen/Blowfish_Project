package implementation;


import java.util.Arrays;
import java.util.FileInputStream;
import java.util.FileOutputStream;

import edu.rit.util.Hex;
import edu.rit.util.Packing;

/**
 * uses Electronic Codebook Mode to encrypt/decrypt a file
 * @param args	array of length 3 containing the key, plaintext file to read, and ciphertext file to write, respectively
 */
public class BlowfishRunner
{
	public static void main(String[] args)
	{
		BlockCipher cipher = new Blowfish01();

		byte[] plaintext = new byte[8];
		byte[] key = new byte[8];

		if(args.length!=3)
		{
			Arrays.fill(key, (byte)0x00);
			Arrays.fill(plaintext, (byte)0x00);
		}
		else key=Hex.toByteArray(args[0]);
		
		cipher.setKey(key);

		Scanner read=null;
		Scanner write=null;
		
		try
		{
			read=new FileInputStream(new java.io.File(args[1]));
			write=new FileOutputStream(new java.io.File(args[2]));
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println("File not Found: " + args[0]);
			System.exit(1);
		}
		
		while(read.available()>0)
		{
			int i=0;
			for(int i=0; i<8 && read.available()>0; i++)
			{
				plaintext[i]=read.read();
			}
			plaintext[i++]=1;
			if(i<8)	//fill block with 1 then 0s
				for(i=i; i<8; i++) plaintext[i]=0;
			//else	//fill another block with all 0s
			//	for(i=0; i<8; i++) plaintext[i]=0;
			
			cipher.encrypt(plaintext);		
			System.out.println(Hex.toString(plaintext));
			
			write.write(plaintext);
			
			//cipher.decrypt(plaintext);
			//System.out.println(Hex.toString(plaintext));
			
		}
	}
}
