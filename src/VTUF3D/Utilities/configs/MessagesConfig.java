package VTUF3D.Utilities.configs;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessagesConfig {
	private static final String BUNDLE_NAME = "au.edu.monash.ges.tuf3d.messagesConfig"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private MessagesConfig() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) 
		{
			System.err.println("Missing config key " + key);
			e.printStackTrace();
			return '!' + key + '!';
			
		}
	}
	public static String getStringNoException(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) 
		{
			return null;			
		}
	}
	public static int getInteger(String key) 
	{
		try 
		{
			String keyValue = RESOURCE_BUNDLE.getString(key);
			int keyValueInteger = new Integer(keyValue).intValue();
			return keyValueInteger;
		} catch (MissingResourceException e) 
		{
			//return '!' + key + '!';
			System.err.println("Missing config key " + key);
			e.printStackTrace();
		}
		return -1;
	}
	public static boolean getBoolean(String key) 
	{
		boolean returnValue = false;
		try 
		{
			String keyValue = RESOURCE_BUNDLE.getString(key);
			if (keyValue.equalsIgnoreCase("true"))
			{
				returnValue = true;
			}
			
		} catch (MissingResourceException e) 
		{
			//return '!' + key + '!';
			System.err.println("Missing config key " + key);
			e.printStackTrace();
		}
		return returnValue;
	}
	public static double getDouble(String key) 
	{
		try 
		{
			String keyValue = RESOURCE_BUNDLE.getString(key);
			double keyValueInteger = new Double(keyValue).doubleValue();
			return keyValueInteger;
		} catch (MissingResourceException e) 
		{
			System.err.println("Missing config key " + key);
			e.printStackTrace();
			//return '!' + key + '!';
		}
		return -1;
	}
}
