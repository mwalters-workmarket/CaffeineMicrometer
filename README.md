
This app will export caffeine cache metrics through the actuator prometheus endpoint.


The simple way (which allows global cache config only) is to specify application properties
```
#recordStats must be set in the spec to allow micrometer to record metrics
spring.cache.caffeine.spec=recordStats,maximumSize=100
spring.cache.cache-names=cache1,cache2

```


The list of options for the spec is here: https://static.javadoc.io/com.github.ben-manes.caffeine/caffeine/2.6.2/com/github/benmanes/caffeine/cache/CaffeineSpec.html




If you want to configure each cache separately, you'll need to programmatically create the Cache and CacheManager objects (see `CacheConfiguration.java`)
NOTE: once you do this, the above application.properties won't be used, even if specified


See https://github.com/micrometer-metrics/micrometer/blob/master/micrometer-core/src/main/java/io/micrometer/core/instrument/binder/cache/CacheMeterBinder.java
and https://github.com/micrometer-metrics/micrometer/blob/master/micrometer-core/src/main/java/io/micrometer/core/instrument/binder/cache/CaffeineCacheMetrics.java

for more info about the cache metrics collected by micrometer.

Execute `mvn clean package` to build and then
`mvn spring-boot:run -Dspring.profiles.active=programmatic_cache` to run with the programmatically defined caches
or
`mvn spring-boot:run -Dspring.profiles.active=simple_cache` to run with the application property defined caches


Once the app launches, to see the cache metrics, execute `curl localhost:8080/actuator/prometheus | grep 'cacheManager="cacheManager"'` 