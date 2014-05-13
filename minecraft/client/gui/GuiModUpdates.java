package clashsoft.cslib.minecraft.client.gui;

import java.net.URI;
import java.util.List;

import clashsoft.cslib.minecraft.lang.I18n;
import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.update.Update;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiModUpdates extends GuiScreen
{
	public final GuiScreen		parent;
	
	public GuiModUpdatesSlot	slots;
	public GuiButton			buttonShowInvalidUpdates;
	public GuiButton			buttonInstall;
	
	public List<Update>			updates;
	public Update				update;
	public boolean				showInvalidUpdates;
	
	private String				title				= I18n.getString("update.list.title");
	private String				updates_showinvalid	= I18n.getString("update.list.showinvalid");
	private String				options_on			= I18n.getString("options.on");
	private String				options_off			= I18n.getString("options.off");
	
	private String				mod_name			= EnumChatFormatting.GRAY + I18n.getString("update.list.modname");
	private String				current_version		= EnumChatFormatting.GRAY + I18n.getString("update.list.version");
	private String				new_version			= EnumChatFormatting.GRAY + I18n.getString("update.list.newversion");
	private String				install_status		= EnumChatFormatting.GRAY + I18n.getString("update.list.install.status");
	private String				url					= EnumChatFormatting.GRAY + I18n.getString("update.list.url");
	private String				update_notes		= I18n.getString("update.list.notes");
	
	public GuiModUpdates(GuiScreen parent)
	{
		this.parent = parent;
		this.updateList();
	}
	
	@Override
	public void initGui()
	{
		this.buttonShowInvalidUpdates = new GuiButton(10, 10, this.height - 38, 140, 20, this.getShowInvalidUpdates());
		this.buttonInstall = new GuiButton(1, this.width - 90, 32, 80, 20, I18n.getString("update.list.install"));
		
		this.buttonList.add(new GuiButton(0, this.width / 2, this.height - 38, 140, 20, I18n.getString("gui.done")));
		this.buttonList.add(this.buttonInstall);
		this.buttonList.add(this.buttonShowInvalidUpdates);
		this.buttonList.add(new GuiButton(2, 10, this.height - 60, 140, 20, I18n.getString("update.list.installall")));
		this.slots = new GuiModUpdatesSlot(this);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTickTime)
	{
		if (this.slots != null)
		{
			this.slots.drawScreen(mouseX, mouseY, partialTickTime);
		}
		
		this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 14, 0xFFFFFF);
		
		Update update = this.update;
		
		if (update != null)
		{
			boolean valid = update.isValid();
			int color1 = valid ? 0xFF0000 : 0x00FF00;
			int color2 = valid ? 0x00FF00 : 0xFF0000;
			
			this.drawString(this.fontRendererObj, "\u00a7n" + update.getName(), 160, 37, 0xFFFFFF);
			this.drawString(this.fontRendererObj, this.mod_name, 160, 55, 0xFFFFFF);
			this.drawString(this.fontRendererObj, this.current_version, 160, 65, 0xFFFFFF);
			this.drawString(this.fontRendererObj, this.new_version, 160, 75, 0xFFFFFF);
			this.drawString(this.fontRendererObj, this.url, 160, 85, 0xFFFFFF);
			this.drawString(this.fontRendererObj, this.install_status, 160, 95, 0xFFFFFF);
			
			this.drawString(this.fontRendererObj, update.getModName(), 260, 55, 0xFFFFFF);
			this.drawString(this.fontRendererObj, update.getVersion(), 260, 65, color1);
			this.drawString(this.fontRendererObj, update.getNewVersion(), 260, 75, color2);
			this.drawString(this.fontRendererObj, update.getUpdateURL(), 260, 85, 0xFFFFFF);
			this.drawString(this.fontRendererObj, update.getStatus(), 260, 95, 0xFFFFFF);
			
			int i = 125;
			List<String> updateNotes = update.getUpdateNotes();
			if (!updateNotes.isEmpty())
			{
				this.drawString(this.fontRendererObj, this.update_notes, 160, 110, 0xFFFFFF);
				
				for (String line : updateNotes)
				{
					this.drawString(this.fontRendererObj, line, 160, i, this.getDiffColor(line));
					i += 10;
				}
			}
		}
		
		super.drawScreen(mouseX, mouseY, partialTickTime);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int which)
	{
		super.mouseClicked(x, y, which);
		
		if (this.update != null)
		{
			String url = this.update.getUpdateURL();
			int i = this.fontRendererObj.getStringWidth(url);
			
			if (x >= 260 && x <= 260 + i && y >= 80 && y < 90)
			{
				this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, url, 0, false));
			}
		}
	}
	
	public int getDiffColor(String line)
	{
		if (line.isEmpty())
		{
			return 0xFFFFFF;
		}
		
		switch (line.charAt(0))
		{
		case '+':
			return 0x00FF00;
		case '-':
			return 0xFF0000;
		case '*':
			return 0xFFFF00;
		case '!':
			return 0x007FFF;
		default:
			return 0xFFFFFF;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
		{
			this.mc.displayGuiScreen(this.parent);
		}
		else if (button.id == 1)
		{
			if (this.update != null)
			{
				this.update.install(this.mc.thePlayer);
			}
		}
		else if (button.id == 2)
		{
			for (Update update : this.updates)
			{
				if (update != null)
				{
					update.install(this.mc.thePlayer);
				}
			}
		}
		else if (button.id == 10)
		{
			this.showInvalidUpdates = !this.showInvalidUpdates;
			button.displayString = this.getShowInvalidUpdates();
			
			this.updateList();
		}
	}
	
	@Override
	public void confirmClicked(boolean flag, int id)
	{
		if (flag && id == 0)
		{
			URI uri = URI.create(this.update.getUpdateURL());
			try
			{
				Class oclass = Class.forName("java.awt.Desktop");
				Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
				oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { uri });
			}
			catch (Throwable throwable)
			{
				throwable.printStackTrace();
			}
		}
		else
		{
			this.mc.displayGuiScreen(this);
		}
	}
	
	protected void selectUpdate(int update)
	{
		this.update = this.updates.get(update);
		this.buttonInstall.enabled = this.update.isValid();
	}
	
	protected void updateList()
	{
		this.updates = CSUpdate.getUpdates(this.showInvalidUpdates);
		this.update = this.updates.isEmpty() ? null : this.updates.get(0);
		
		if (this.slots != null)
		{
			this.slots.selectedIndex = 0;
		}
	}
	
	private String getShowInvalidUpdates()
	{
		return this.updates_showinvalid + ": " + (this.showInvalidUpdates ? this.options_on : this.options_off);
	}
}
