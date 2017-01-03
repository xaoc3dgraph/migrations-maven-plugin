migrations-maven-plugin
=======================

Simple plugin for prefix [flywaydb](http://flywaydb.org/) migrations with timestamp

#Configuration

#Repository settings
```xml
<pluginRepositories>
  <pluginRepository>
    <id>xdev.developer.repo</id>
    <name>xdev developer repository</name>
    <url>http://xdev-developer.github.io/maven/</url>
  </pluginRepository>
</pluginRepositories>
```

#Plugin configuration
```xml
    <plugins>
      <plugin>
        <groupId>com.github.xdev</groupId>
        <artifactId>migrations-maven-plugin</artifactId>
        <version>1.0</version>
        <configuration>
          <migrationsDirectory>${project.basedir}/src/main/resources/db/migration</migrationsDirectory>
        </configuration>        
      </plugin>
    </plugins>
```

Running
```
 mvn migrations:new -Dname=create_new_table
```

This command create new migration file like: ${project.basedir}src/main/resources/db1/migration/**V20140228225148416__create_new_table.sql**

