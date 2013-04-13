package clashsoft.clashsoftapi;

//import clashsoft.mods.combinationcraft.EnumToolMaterial2;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraftforge.common.EnumHelper;

public class ClashsoftItems
{
	/**
	 * Adds an Item without recipe.
	 * @param par1Item
	 * @param par2
	 * @param par3
	 */
	public static void addItem(Item par1Item, String par2)
	{
		ModLoader.addName(par1Item, par2);
	}
	
	/**
	 * Adds an Item with recipe.
	 * @param par1Item
	 * @param par2
	 * @param par3
	 * @param par4
	 * @param par5ArrayOfObj
	 */
	public static void addItemWithRecipe(Item par1Item, int par2, String par3, int par4, Object[] par5ArrayOfObj)
	{
		addItem(par1Item, par3);
		ModLoader.addRecipe(new ItemStack(par1Item, par4), par5ArrayOfObj);
	}
	
	/**
	 * Adds an Item with shapeless recipe.
	 * @param par1Item
	 * @param par2
	 * @param par3
	 * @param par4
	 * @param par5ArrayOfObj
	 */
	public static void addItemWithShapelessRecipe(Item par1Item, int par2, String par3, int par4, Object[] par5ArrayOfObj)
	{
		addItem(par1Item, par3);
		ModLoader.addShapelessRecipe(new ItemStack(par1Item, par4), par5ArrayOfObj);
	}
	
	public static EnumToolMaterial addToolMaterial(String name, int harvestLevel, int maxUses, float efficiency, int damage, int enchantability, int color, ItemStack recipe)
	{
		try
		{
			//EnumToolMaterial2 material2 = new EnumToolMaterial2(name, harvestLevel, maxUses, efficiency, damage, enchantability, color, recipe).register();
		}
		catch (Exception ex)
		{
			System.out.println("CombinationCraft not installed");
		}
		return EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, damage, enchantability);
	}
}
