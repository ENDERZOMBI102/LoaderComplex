package com.enderzombi102.loadercomplex.bukkit.impl.item;

import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BukkitItemMeta implements ItemMeta {

	public ResourceIdentifier itemType;


	public BukkitItemMeta(Item item, ResourceIdentifier identifier) {
		this.itemType = identifier;
	}

	public BukkitItemMeta(Map<String, Object> data) {

	}


	/**
	 * Checks for existence of a display name.
	 *
	 * @return true if this has a display name
	 */
	@Override
	public boolean hasDisplayName() {
		return false;
	}

	/**
	 * Gets the display name that is set.
	 * <p>
	 * Plugins should check that hasDisplayName() returns <code>true</code>
	 * before calling this method.
	 *
	 * @return the display name that is set
	 */
	@Override
	public String getDisplayName() {
		return null;
	}

	/**
	 * Sets the display name.
	 *
	 * @param name the name to set
	 */
	@Override
	public void setDisplayName(String name) {

	}

	/**
	 * Checks for existence of a localized name.
	 *
	 * @return true if this has a localized name
	 */
	@Override
	public boolean hasLocalizedName() {
		return false;
	}

	/**
	 * Gets the localized display name that is set.
	 * <p>
	 * Plugins should check that hasLocalizedName() returns <code>true</code>
	 * before calling this method.
	 *
	 * @return the localized name that is set
	 */
	@Override
	public String getLocalizedName() {
		return null;
	}

	/**
	 * Sets the localized name.
	 *
	 * @param name the name to set
	 */
	@Override
	public void setLocalizedName(String name) {

	}

	/**
	 * Checks for existence of lore.
	 *
	 * @return true if this has lore
	 */
	@Override
	public boolean hasLore() {
		return false;
	}

	/**
	 * Gets the lore that is set.
	 * <p>
	 * Plugins should check if hasLore() returns <code>true</code> before
	 * calling this method.
	 *
	 * @return a list of lore that is set
	 */
	@Override
	public List<String> getLore() {
		return null;
	}

	/**
	 * Sets the lore for this item.
	 * Removes lore when given null.
	 *
	 * @param lore the lore that will be set
	 */
	@Override
	public void setLore(List<String> lore) {

	}

	/**
	 * Checks for the existence of any enchantments.
	 *
	 * @return true if an enchantment exists on this meta
	 */
	@Override
	public boolean hasEnchants() {
		return false;
	}

	/**
	 * Checks for existence of the specified enchantment.
	 *
	 * @param ench enchantment to check
	 * @return true if this enchantment exists for this meta
	 */
	@Override
	public boolean hasEnchant(Enchantment ench) {
		return false;
	}

	/**
	 * Checks for the level of the specified enchantment.
	 *
	 * @param ench enchantment to check
	 * @return The level that the specified enchantment has, or 0 if none
	 */
	@Override
	public int getEnchantLevel(Enchantment ench) {
		return 0;
	}

	/**
	 * Returns a copy the enchantments in this ItemMeta. <br>
	 * Returns an empty map if none.
	 *
	 * @return An immutable copy of the enchantments
	 */
	@Override
	public Map<Enchantment, Integer> getEnchants() {
		return null;
	}

	/**
	 * Adds the specified enchantment to this item meta.
	 *
	 * @param ench                   Enchantment to add
	 * @param level                  Level for the enchantment
	 * @param ignoreLevelRestriction this indicates the enchantment should be
	 *                               applied, ignoring the level limit
	 * @return true if the item meta changed as a result of this call, false
	 * otherwise
	 */
	@Override
	public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
		return false;
	}

	/**
	 * Removes the specified enchantment from this item meta.
	 *
	 * @param ench Enchantment to remove
	 * @return true if the item meta changed as a result of this call, false
	 * otherwise
	 */
	@Override
	public boolean removeEnchant(Enchantment ench) {
		return false;
	}

	/**
	 * Checks if the specified enchantment conflicts with any enchantments in
	 * this ItemMeta.
	 *
	 * @param ench enchantment to test
	 * @return true if the enchantment conflicts, false otherwise
	 */
	@Override
	public boolean hasConflictingEnchant(Enchantment ench) {
		return false;
	}

	/**
	 * Set itemflags which should be ignored when rendering a ItemStack in the Client. This Method does silently ignore double set itemFlags.
	 *
	 * @param itemFlags The hideflags which shouldn't be rendered
	 */
	@Override
	public void addItemFlags(ItemFlag... itemFlags) {

	}

	/**
	 * Remove specific set of itemFlags. This tells the Client it should render it again. This Method does silently ignore double removed itemFlags.
	 *
	 * @param itemFlags Hideflags which should be removed
	 */
	@Override
	public void removeItemFlags(ItemFlag... itemFlags) {

	}

	/**
	 * Get current set itemFlags. The collection returned is unmodifiable.
	 *
	 * @return A set of all itemFlags set
	 */
	@Override
	public Set<ItemFlag> getItemFlags() {
		return null;
	}

	/**
	 * Check if the specified flag is present on this item.
	 *
	 * @param flag the flag to check
	 * @return if it is present
	 */
	@Override
	public boolean hasItemFlag(ItemFlag flag) {
		return false;
	}

	/**
	 * Return if the unbreakable tag is true. An unbreakable item will not lose
	 * durability.
	 *
	 * @return true if the unbreakable tag is true
	 */
	@Override
	public boolean isUnbreakable() {
		return false;
	}

	/**
	 * Sets the unbreakable tag. An unbreakable item will not lose durability.
	 *
	 * @param unbreakable true if set unbreakable
	 */
	@Override
	public void setUnbreakable(boolean unbreakable) {

	}

	@Override
	public ItemMeta clone() {
		return null;
	}

	/**
	 * Creates a Map representation of this class.
	 * <p>
	 * This class must provide a method to restore this class, as defined in
	 * the {@link ConfigurationSerializable} interface javadocs.
	 *
	 * @return Map containing the current state of this class
	 */
	@Override
	public Map<String, Object> serialize() {
		return null;
	}
}
