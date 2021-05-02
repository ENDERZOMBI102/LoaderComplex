package com.enderzombi102.loadercomplex.bukkit.impl.item;

import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.Hand;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.bukkit.Utilities;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import static com.enderzombi102.loadercomplex.bukkit.LoaderComplexBukkit.LOGGER;

public class BukkitItem implements Listener {

	private final Item itemImpl;
	private final ResourceIdentifier identifier;

	public BukkitItem(Item item, ResourceIdentifier identifier) {
		this.identifier = identifier;
		this.itemImpl = item;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		// check if this event involves an item
		if (! evt.hasItem() ) return;
		// check if the item is the one pointed by this object
		if (! this.checkItem( evt.getItem() ) ) return;

		// may be used on a block or in air
		if ( evt.hasBlock() ) {
			// useOnBlock
			this.itemImpl.useOnBlock();
		} else {
			// use
			this.itemImpl.use();
		}
	}

	@EventHandler
	public void onPlayerInteractWithEntity(PlayerInteractEntityEvent evt) {
		ItemStack stack = evt.getPlayer().getInventory().getItemInMainHand();
		// check if this event involves an item
		if ( stack == null ) return;
		// check if the item is the one pointed by this object
		if (! this.checkItem( stack ) ) return;

		// everything's good, call event
		this.itemImpl.useOnEntity(
				new BukkitItemStack(stack),
				evt.getPlayer(),
				evt.getRightClicked(),
				Hand.valueOf( evt.getHand().name() )
		);
	}

	@EventHandler
	public void onItemCraft(CraftItemEvent evt) {
		ItemStack stack = evt.getRecipe().getResult();
		if (! this.checkItem(stack) ) return;

		// everything's alright
		this.itemImpl.onCraft(
				new BukkitItemStack( stack ),
				evt.getWhoClicked()
		);
	}




	public ItemStack create(Item item, ResourceIdentifier identifier) {
		Material mat = Utilities.getMaterialFor( item.placeholderItem );
		if ( mat == null ) {
			LOGGER.error("Item \"" + identifier + "\" doesn't have a valid placeholder item: " + item.placeholderItem);
			return null;
		}
		ItemStack stack = new ItemStack( mat );
		return stack;
	}

	protected boolean checkItem(ItemStack stack) {
		// check if the event has something to do with LC
		// TODO: use NBT to [serialize/deserialize/check if] an item (is an LC item)
		if (! (stack.getItemMeta().) ) return false;

		// check if the event is for this item type
//		return meta.itemType.equals(this.identifier);
	}

}
