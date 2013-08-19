package nz.ac.auckland.common.config;


import net.stickycode.configuration.ConfigurationSource;
import net.stickycode.configuration.ConfigurationValue;
import net.stickycode.configuration.ResolvedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemPropertiesConfigurationSource implements ConfigurationSource {
	private Logger log = LoggerFactory.getLogger("nz.ac.auckland.configuration");

	@Override
	public void apply(net.stickycode.configuration.ConfigurationKey configurationKey, ResolvedConfiguration resolvedConfiguration) {
		String key = configurationKey.join(".");

		final String value = System.getProperty(key);

		if (value != null) {
			resolvedConfiguration.add(new ConfigurationValue() {
				@Override
				public String get() {
					return value;
				}

				@Override
				public boolean hasPrecedence(ConfigurationValue v) {
					return false;
				}
			});
		}
	}
}
