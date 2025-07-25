package com.enderzombi102.loadercomplex.fabric122.impl.utils;

/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.enderzombi102.loadercomplex.fabric122.imixin.ICreativeModeTabMixin;
import net.minecraft.item.CreativeModeTab;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.Identifier;
import net.minecraft.util.DefaultedList;

public final class ItemGroupBuilder {
	private final Identifier identifier;
	private Supplier<ItemStack> stackSupplier = () -> ItemStack.EMPTY;
	private BiConsumer<List<ItemStack>, CreativeModeTab> stacksForDisplay;

	private ItemGroupBuilder( Identifier identifier ) {
		this.identifier = identifier;
	}

	/**
	 * Create a new Item Group Builder.
	 *
	 * @param identifier the id will become the name of the CreativeModeTab and will be used for the translation key
	 * @return a FabricItemGroupBuilder
	 */
	public static ItemGroupBuilder create( Identifier identifier ) {
		return new ItemGroupBuilder( identifier );
	}

	/**
	 * This is used to add an icon to to the item group.
	 *
	 * @param stackSupplier the supplier should return the item stack that you wish to show on the tab
	 * @return a reference to the FabricItemGroupBuilder
	 */
	public ItemGroupBuilder icon( Supplier<ItemStack> stackSupplier ) {
		this.stackSupplier = stackSupplier;
		return this;
	}

	/**
	 * This allows for a custom list of items to be displayed in a tab, this enabled tabs to be created with a custom set of items.
	 *
	 * @param appender Add ItemStack's to this list to show in the CreativeModeTab
	 * @return a reference to the FabricItemGroupBuilder
	 * @deprecated use {@link ItemGroupBuilder#appendItems(Consumer)}
	 */
	@Deprecated
	public ItemGroupBuilder stacksForDisplay( Consumer<List<ItemStack>> appender ) {
		return appendItems( appender );
	}

	/**
	 * Set the item stacks of this item group, by having the consumer add them to the passed list.
	 * If you want to append stacks from your items, consider using {@linkplain #appendItems(BiConsumer) the other overload}.
	 *
	 * @param stacksForDisplay Add ItemStack's to this list to show in the CreativeModeTab
	 * @return a reference to the FabricItemGroupBuilder
	 */
	public ItemGroupBuilder appendItems( Consumer<List<ItemStack>> stacksForDisplay ) {
		return appendItems( ( itemStacks, itemGroup ) -> stacksForDisplay.accept( itemStacks ) );
	}

	/**
	 * Set the item stacks of this item group, by having the consumer add them to the passed list.
	 * Compared to the other overload, this one also passes the new CreativeModeTab.
	 *
	 * @param stacksForDisplay Add ItemStack's to this list to show in the CreativeModeTab, and check if the item is in the CreativeModeTab
	 * @return a reference to the FabricItemGroupBuilder
	 */
	public ItemGroupBuilder appendItems( BiConsumer<List<ItemStack>, CreativeModeTab> stacksForDisplay ) {
		this.stacksForDisplay = stacksForDisplay;
		return this;
	}

	/**
	 * This is a single method that makes creating an CreativeModeTab with an icon one call.
	 *
	 * @param identifier    the id will become the name of the CreativeModeTab and will be used for the translation key
	 * @param stackSupplier the supplier should return the item stack that you wish to show on the tab
	 * @return An instance of the built CreativeModeTab
	 */
	public static CreativeModeTab build( Identifier identifier, Supplier<ItemStack> stackSupplier ) {
		return new ItemGroupBuilder( identifier ).icon( stackSupplier ).build();
	}

	/**
	 * Create an instance of the CreativeModeTab.
	 *
	 * @return An instance of the built CreativeModeTab
	 */
	public CreativeModeTab build() {
		((ICreativeModeTabMixin) CreativeModeTab.BUILDING_BLOCKS).lc$expandArray();
		return new CreativeModeTab( CreativeModeTab.ALL.length - 1, String.format( "%s.%s", identifier.getNamespace(), identifier.getPath() ) ) {
			@Override
			public ItemStack getIconItem() {
				return stackSupplier.get();
			}

			@Override
			public void addItems( DefaultedList<ItemStack> inventory ) {
				if ( stacksForDisplay != null ) {
					stacksForDisplay.accept( inventory, this );
					return;
				}

				super.addItems( inventory );
			}
		};
	}
}

