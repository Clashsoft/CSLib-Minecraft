package clashsoft.cslib.util;

import net.minecraft.client.Minecraft;

/**
 * The class CSString
 * <p>
 * Extension for {@link CSString} adding String utils only usable with
 * Minecraft.
 * 
 * @author Clashsoft
 */
public class CSStringMC
{
	/**
	 * Trims a string to the given render width.
	 * <p>
	 * If the renderer string length is longer than the maximum render width
	 * given, the last chars will be replaced with "...".
	 * 
	 * @param string
	 *            the string
	 * @param maxRenderWidth
	 *            the max render width
	 * @return the string
	 */
	public static String trimStringToRenderWidth(String string, int maxRenderWidth)
	{
		Minecraft mc = Minecraft.getMinecraft();
		int i = mc.fontRendererObj.getStringWidth("...");
		boolean flag = false;
		while (string.length() > 0 && mc.fontRendererObj.getStringWidth(string) + i > maxRenderWidth - 6)
		{
			string = string.substring(0, string.length() - 1);
			flag = true;
		}
		if (flag)
		{
			string += "...";
		}
		
		return string;
	}
}