package xyz.semoteo.ready.mod.impl.player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.TickEvent;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.setting.Setting;
import xyz.semoteo.ready.utils.TeoTimer;

public class AutoArmor extends Mod{

	public AutoArmor() {
		super("AutoAmor", new String[]{"autoarmor,aa,autoarmour"});
		category = Category.Player;
		 this.delay = new Setting("Delay", Float.valueOf(500.0F));
	}
	 private Setting<Float> delay;
	  private int teotime;
	private final int[] boots = { 313, 309, 317, 305, 301 };
	private final int[] chestplate = { 311, 307, 315, 303, 299 };
	private final int[] helmet = { 310, 306, 314, 302, 298 };
	private final int[] leggings = { 312, 308, 316, 304, 300 };
	private TeoTimer timer;
	@EventListener
	public void onEvent(TickEvent event)
	{	
		if (mc.thePlayer == null) {
			return;
		}
		
		if ((mc.thePlayer.openContainer != null) && (mc.thePlayer.openContainer.windowId != 0)) {
			return;
		}
		int item = -1;
		int[] arrayOfInt;
		int j;
		int i;
		if (mc.thePlayer.inventory.armorInventory[0] == null) {
			j = (arrayOfInt = this.boots).length;
			for (i = 0; i < j; i++) {
				int id = arrayOfInt[i];
				if (findItem(id) != -1) {
					item = findItem(id);
					break;
				}
			}
		}
		if (armourIsBetter(0, this.boots)) {
			item = 8;
		}
		if (mc.thePlayer.inventory.armorInventory[3] == null) {
			j = (arrayOfInt = this.helmet).length;
			for (i = 0; i < j; i++) {
				int id = arrayOfInt[i];
				if (findItem(id) != -1) {
					item = findItem(id);
					break;
				}
			}
		}
		if (armourIsBetter(3, this.helmet)) {
			item = 5;
		}
		if (mc.thePlayer.inventory.armorInventory[1] == null) {
			j = (arrayOfInt = this.leggings).length;
			for (i = 0; i < j; i++) {
				int id = arrayOfInt[i];
				if (findItem(id) != -1) {
					item = findItem(id);
					break;
				}
			}
		}
		if (armourIsBetter(1, this.leggings)) {
			item = 7;
		}
		if (mc.thePlayer.inventory.armorInventory[2] == null) {
			j = (arrayOfInt = this.chestplate).length;
			for (i = 0; i < j; i++) {
				int id = arrayOfInt[i];
				if (findItem(id) != -1) {
					item = findItem(id);
					break;
				}
			}
		}
		if (armourIsBetter(2, this.chestplate)) {
			item = 6;
		}
		if (item != -1) {
			mc.playerController.windowClick(0, item, 0, 1, mc.thePlayer);
			
			return;
		}
	}

	public boolean armourIsBetter(int slot, int[] armourtype) {
		if (mc.thePlayer.inventory.armorInventory[slot] != null) {
			int currentIndex = 0;
			int finalCurrentIndex = -1;
			int invIndex = 0;
			int finalInvIndex = -1;
			int[] arrayOfInt;
			int j = (arrayOfInt = armourtype).length;
			for (int i = 0; i < j; i++) {
				int armour = arrayOfInt[i];
				if (Item.getIdFromItem(mc.thePlayer.inventory.armorInventory[slot].getItem()) == armour) {
					finalCurrentIndex = currentIndex;
					break;
				}
				currentIndex++;
			}
			j = (arrayOfInt = armourtype).length;
			for (int i = 0; i < j; i++) {
				int armour = arrayOfInt[i];
				if (findItem(armour) != -1) {
					finalInvIndex = invIndex;
					break;
				}
				invIndex++;
			}
			if (finalInvIndex > -1) {
				return finalInvIndex < finalCurrentIndex;
			}
		}
		return false;
	}

	private int findItem(int id) {
		for (int index = 9; index < 45; index++) {
			ItemStack item = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
			if ((item != null) && (Item.getIdFromItem(item.getItem()) == id)) {
				return index;
			}
		}
		return -1;
	}
  }

