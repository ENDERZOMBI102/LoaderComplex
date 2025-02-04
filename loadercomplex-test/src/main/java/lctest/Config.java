package lctest;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;


public class Config {
	private static final Properties DEFAULT_CONFIG = new Properties();
	private static final ConfigData DATA = new ConfigData();

	public static void init( Path configDir ) throws IOException {
		Path path = configDir.resolve( "testaddon.properties" );
		Properties properties = new Properties( DEFAULT_CONFIG );
		if ( path.toFile().exists() ) {
			try ( Reader reader = Files.newBufferedReader(path) ) {
				properties.load( reader );
				DATA.item = Boolean.parseBoolean( (String) properties.get( "item" ) );
				DATA.itemGroup = Boolean.parseBoolean( (String) properties.get( "itemGroup" ) );
				DATA.block = Boolean.parseBoolean( (String) properties.get( "block" ) );
				DATA.command = Boolean.parseBoolean( (String) properties.get( "command" ) );
				DATA.keybind = Boolean.parseBoolean( (String) properties.get( "keybind" ) );
				DATA.network = Boolean.parseBoolean( (String) properties.get( "network" ) );
				DATA.entity = Boolean.parseBoolean( (String) properties.get( "entity" ) );
			}
		} else {
			try ( Writer writer = Files.newBufferedWriter(path) ) {
				properties.store( writer, "Config for LC test addon" );
			}
		}
	}

	public static ConfigData get() {
		return DATA;
	}

	static {
		DEFAULT_CONFIG.put( "item", false );
		DEFAULT_CONFIG.put( "itemGroup", false );
		DEFAULT_CONFIG.put( "block", false );
		DEFAULT_CONFIG.put( "entity", false );
		DEFAULT_CONFIG.put( "command", false );
		DEFAULT_CONFIG.put( "keybind", false );
		DEFAULT_CONFIG.put( "network", false );
	}

	public static class ConfigData {
		private ConfigData() {}

		public boolean item = false;
		public boolean itemGroup = false;
		public boolean block = false;
		public boolean entity = false;
		public boolean command = false;
		public boolean keybind = false;
		public boolean network = false;
	}
}
