package net.minecraftforge.fml.common.network;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandler;
import io.netty.util.AttributeKey;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetHandler;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import net.minecraftforge.fml.common.network.internal.NetworkModHolder;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public enum NetworkRegistry {
	INSTANCE;

	private EnumMap<Side, Map<String, FMLEmbeddedChannel>> channels = Maps.newEnumMap(Side.class);

	private Map<ModContainer, NetworkModHolder> registry = Maps.newHashMap();

	private Map<ModContainer, IGuiHandler> serverGuiHandlers = Maps.newHashMap();

	private Map<ModContainer, IGuiHandler> clientGuiHandlers = Maps.newHashMap();

	public static final AttributeKey<String> FML_CHANNEL;

	public static final AttributeKey<Side> CHANNEL_SOURCE;

	public static final AttributeKey<ModContainer> MOD_CONTAINER;

	public static final AttributeKey<INetHandler> NET_HANDLER;

	public static final AttributeKey<Boolean> FML_MARKER;

	public static final byte FML_PROTOCOL = 2;

	static {
		FML_CHANNEL = AttributeKey.valueOf("fml:channelName");
		CHANNEL_SOURCE = AttributeKey.valueOf("fml:channelSource");
		MOD_CONTAINER = AttributeKey.valueOf("fml:modContainer");
		NET_HANDLER = AttributeKey.valueOf("fml:netHandler");
		FML_MARKER = AttributeKey.valueOf("fml:hasMarker");
	}

	NetworkRegistry() {
		this.channels.put( Side.CLIENT, Maps.newConcurrentMap() );
		this.channels.put( Side.SERVER, Maps.newConcurrentMap() );
		this.channels.put( Side.BUKKIT, Maps.newConcurrentMap() );
	}

	public static class TargetPoint {
		public final double x;

		public final double y;

		public final double z;

		public final double range;

		public final int dimension;

		public TargetPoint(int dimension, double x, double y, double z, double range) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.range = range;
			this.dimension = dimension;
		}
	}

	public EnumMap<Side, FMLEmbeddedChannel> newChannel(String name, ChannelHandler... handlers) {
		if (this.channels.get(Side.CLIENT).containsKey(name) || this.channels.get(Side.SERVER).containsKey(name) || name.startsWith("MC|") || name.startsWith("\001") || name.startsWith("FML"))
			throw new RuntimeException("That channel is already registered");
		EnumMap<Side, FMLEmbeddedChannel> result = Maps.newEnumMap(Side.class);
		for (Side side : Side.values()) {
			FMLEmbeddedChannel channel = new FMLEmbeddedChannel(name, side, handlers);
			this.channels.get(side).put(name, channel);
			result.put(side, channel);
		}
		return result;
	}

	public SimpleNetworkWrapper newSimpleChannel(String name) {
		return new SimpleNetworkWrapper(name);
	}

	public FMLEventChannel newEventDrivenChannel(String name) {
		return new FMLEventChannel(name);
	}

	public EnumMap<Side, FMLEmbeddedChannel> newChannel(ModContainer container, String name, ChannelHandler... handlers) {
		if (this.channels.get(Side.CLIENT).containsKey(name) || this.channels.get(Side.SERVER).containsKey(name) || name.startsWith("MC|") || name.startsWith("\001") || (name.startsWith("FML") && !"FML".equals(container.getModId())))
			throw new RuntimeException("That channel is already registered");
		EnumMap<Side, FMLEmbeddedChannel> result = Maps.newEnumMap(Side.class);
		for (Side side : Side.values()) {
			FMLEmbeddedChannel channel = new FMLEmbeddedChannel(container, name, side, handlers);
			this.channels.get(side).put(name, channel);
			result.put(side, channel);
		}
		return result;
	}

	public FMLEmbeddedChannel getChannel(String name, Side source) {
		return this.channels.get(source).get(name);
	}

	public void registerGuiHandler(Object mod, IGuiHandler handler) {
		ModContainer mc = FMLCommonHandler.instance().findContainerFor(mod);
		if (mc == null) {
			FMLLog.log.error("Mod of type {} attempted to register a gui network handler during a construction phase", mod.getClass().getName());
			throw new RuntimeException("Invalid attempt to create a GUI during mod construction. Use an EventHandler instead");
		}
		this.serverGuiHandlers.put(mc, handler);
		this.clientGuiHandlers.put(mc, handler);
	}

	@Nullable
	public Container getRemoteGuiContainer(ModContainer mc, EntityPlayerMP player, int modGuiId, World world, int x, int y, int z) {
		IGuiHandler handler = this.serverGuiHandlers.get(mc);
		if (handler != null)
			return (Container)handler.getServerGuiElement(modGuiId, (EntityPlayer)player, world, x, y, z);
		return null;
	}

	@Nullable
	public Object getLocalGuiContainer(ModContainer mc, EntityPlayer player, int modGuiId, World world, int x, int y, int z) {
		IGuiHandler handler = this.clientGuiHandlers.get(mc);
		return handler.getClientGuiElement(modGuiId, player, world, x, y, z);
	}

	public boolean hasChannel(String channelName, Side source) {
		return this.channels.get(source).containsKey(channelName);
	}

	public void register(ModContainer fmlModContainer, Class<?> clazz, @Nullable String remoteVersionRange, ASMDataTable asmHarvestedData) {
		NetworkModHolder networkModHolder = new NetworkModHolder(fmlModContainer, clazz, remoteVersionRange, asmHarvestedData);
		this.registry.put(fmlModContainer, networkModHolder);
		networkModHolder.testVanillaAcceptance();
	}

	public boolean isVanillaAccepted(Side from) {
		return this.registry.values().stream()
				.allMatch(mod -> mod.acceptsVanilla(from));
	}

	public Collection<String> getRequiredMods(Side from) {
		return this.registry.values().stream()
				.filter(mod -> !mod.acceptsVanilla(from))
				.map(mod -> mod.getContainer().getName())
				.sorted()
				.collect(Collectors.toList());
	}

	public Map<ModContainer, NetworkModHolder> registry() {
		return ImmutableMap.copyOf(this.registry);
	}

	public Set<String> channelNamesFor(Side side) {
		return this.channels.get(side).keySet();
	}

	public void fireNetworkHandshake(NetworkDispatcher networkDispatcher, Side origin) {
		NetworkHandshakeEstablished handshake = new NetworkHandshakeEstablished(networkDispatcher, networkDispatcher.getNetHandler(), origin);
		for (FMLEmbeddedChannel channel : this.channels.get(origin).values() ) {
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DISPATCHER);
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(networkDispatcher);
			channel.pipeline().fireUserEventTriggered(handshake);
		}
	}

	public void cleanAttributes() {
		this.channels.values().forEach( map -> map.values().forEach(FMLEmbeddedChannel::cleanAttributes) );
	}
}
