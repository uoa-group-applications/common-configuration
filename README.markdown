commons-configuration
=====================

This provides a Spring scanned configuration file to support the @ConfigKey annotation from Sticky Code that injects System properties into your
Spring objects.

e.g.

    @UniversityComponent
    class MyClass {
      @ConfigKey("person.name") String name
      @ConfigKey("person.age") Integer age
      @ConfigKey Boolean alive   // myclass.alive
      @ConfigKey List<String> aliases // myclass.aliases
      @ConfigKey Map<String, Integer> childrenAges  // myclass.childrenAges
    }

in system properties you would have

    person.name=fred
    person.age=35
    myClass.alive=true
    myClass.aliases=The Hammer, Sooty, Sweep
    myClass.childrenAges=Barbara:7, William:2

You can include in your class a @PostConfigured annotation to then do post processing on the configuration.

    @UniversityComponent
    class AlsoMyClass {
       @ConfigKey String parts = "gibberish"
       @ConfigKey String password


       @PostConfigured
       public void configure() {
         // do something here
       }
    }

Configuration Sources
---------------------

There are two - the Jar Manifest file is first, the System Properties are second. Jar Manifest's can't use keys with a "." in their name, so those are immediately skipped.

Alternatives
------------

We can use the @Value annotation from Spring, but this has a number of "issues":

  * @Value annotated fields in base classes will not pull their configuration settings when the bean is configured
  * @Value requires you to specify where your properties are coming from, which generally is undesirable as the source of configuration is extensible.
  * @Value's default mechanism is clumsy, requiring error prone stringization of defaults

Web Usage
---------
The artifact includes a web fragment, if included in a Servlet 3.x container, it will automatically be added to the global web.xml

Other Spring usage
------------------
Anyone using the Artifact outside of a web container will need to get the StickyBootstrap object and call "start" on it (see the StickyBootStrapServlet for an example).

Notes
-----

+ if you don't give a default value and the user doesnt provide a value, the initialization of your application will immediately fail (FAST FAIL)

Applications using:  pretty much everything


