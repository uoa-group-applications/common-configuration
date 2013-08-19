package nz.ac.auckland.common.config;

import net.stickycode.configuration.ConfigurationNotFoundException;
import net.stickycode.configuration.ConfigurationSource;
import net.stickycode.configuration.ConfigurationValue;
import net.stickycode.configuration.ResolvedConfiguration;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Provides access to the current Jar file's manifest data.
 * <p>Author: <a href="http://gplus.to/tzrlk">Peter Cummuskey</a></p>
 */
public class JarManifestConfigurationSource implements ConfigurationSource {
	private static final Logger logger = LoggerFactory.getLogger(JarManifestConfigurationSource.class);

	public static final String KEY_IMPLEMENTATION_VERSION = Attributes.Name.IMPLEMENTATION_VERSION.toString();
	// Add whatever keys are required as needed. Adding them all is a waste of time.

	private Manifest manifest;

	/**
	 * Loads the manifest file from the current thread's context, then caches it to avoid IO issues.
	 *
	 * @return The state of the manifest after loading (<code>manifest != null</code>).
	 */
	@PostConstruct
	public boolean loadManifest() {
		if (manifest != null) {
			return true;
		}

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = null;

		try {
			stream = loader.getResourceAsStream(JarFile.MANIFEST_NAME);
			manifest = new Manifest(stream);

			return true;
		} catch (IOException e) {
			logger.warn("An error occurred while loading the manifest: {}", e.getMessage());
			manifest = null;

			return false;
		} finally {
			IOUtils.closeQuietly(stream);
		}
	}

	/**
	 * {@inheritDoc} Triggers lazy-loading of the manifest.
	 */
	public boolean hasValue(String key) {
		return loadManifest() && manifest.getMainAttributes().containsKey(key);
	}

	/**
	 * {@inheritDoc}
	 * Will throw the ConfigurationNotFoundException if either the manifest, or the value returned from the attribute
	 * map is null.
	 */
	public String getValue(String key) throws ConfigurationNotFoundException {
		if (manifest == null) {
			throw new ConfigurationNotFoundException(key);
		}

		String value = manifest.getMainAttributes().getValue(key);

		if (value == null) {
			throw new ConfigurationNotFoundException(key);
		}

		return value;
	}

	@Override
	public void apply(net.stickycode.configuration.ConfigurationKey key, ResolvedConfiguration values) {
		String configKey = key.join(".");

		if (configKey.contains(".")) return;

		if (manifest == null) return;

		final String value = manifest.getMainAttributes().getValue(configKey);

		if (value != null) {
			values.add(new ConfigurationValue() {
				@Override
				public String get() {
					return value;
				}

				@Override
				public boolean hasPrecedence(ConfigurationValue v) {
					return true;
				}
			});
		}
	}
}
