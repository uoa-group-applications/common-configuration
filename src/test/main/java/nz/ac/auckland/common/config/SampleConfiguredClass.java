package nz.ac.auckland.common.config;


import net.stickycode.stereotype.Configured;

import java.util.Map;

public class SampleConfiguredClass {
  @ConfigKey("my.armpit")
  String armpit;

  @ConfigKey
  Integer defaultValue = 5;

  @ConfigKey("no.default.value")
  Integer noDefaultValue;

  @ConfigKey
  Integer reallyNoDefaultValue;

  @ConfigKey("Manifest-Version")
  String monkey;

  @Configured
  String simpleValue;

  @Configured
  Map<String, String> simpleMap;
}
