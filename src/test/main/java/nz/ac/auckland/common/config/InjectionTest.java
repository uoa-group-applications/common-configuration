package nz.ac.auckland.common.config;

import static org.fest.assertions.Assertions.*;

import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.bootstrap.StickySystem;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InjectionTest {

	@Test
	public void onlyOne() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/test.xml");

		assertThat(ctx.getBeansOfType(StickySystem.class).size()).isEqualTo(1);
	}

  @Test
  public void test() {
    System.setProperty("my.armpit", "armpit");
    System.setProperty("no.default.value", "17");
    System.setProperty("sampleConfiguredClass.reallyNoDefaultValue", "18");
    System.setProperty("sampleConfiguredClass.simpleValue", "simpleValue");
    System.setProperty("sampleConfiguredClass.simpleMap", "one=1, two=2, three=3");

    ApplicationContext ctx = new ClassPathXmlApplicationContext("/test.xml");

    SampleConfiguredClass sample = ctx.getBean(SampleConfiguredClass.class);

    assertThat(sample.armpit).isEqualTo("armpit");
    assertThat(sample.defaultValue).isEqualTo(5);
    assertThat(sample.monkey).isNotNull();
    assertThat(sample.noDefaultValue).isEqualTo(17);
    assertThat(sample.reallyNoDefaultValue).isEqualTo(18);
    assertThat(sample.simpleValue).isEqualTo("simpleValue");
    assertThat(sample.simpleMap.size()).isEqualTo(3);
    assertThat(sample.simpleMap.get("one")).isEqualTo("1");
    assertThat(sample.simpleMap.get("two")).isEqualTo("2");
    assertThat(sample.simpleMap.get("three")).isEqualTo("3");
  }
}
