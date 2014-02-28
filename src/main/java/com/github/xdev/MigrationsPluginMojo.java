package com.github.xdev;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;

@Mojo( name = "new")
public class MigrationsPluginMojo extends AbstractMojo {

    private static final String VERSION_PATTERN = "yyyyMMddHHmmssSSS";

    private static final String FLYWAY_DB_MIGRATION_PATTERN = "V{0}__{1}.sql";

    @Parameter( defaultValue = "${project.basedir}/src/main/resources/db/migration", property = "migrationsDir", required = true )
    private File migrationsDirectory;

    @Parameter( property = "name", defaultValue = "new_migration" )
    private String name;

    public void execute() throws MojoExecutionException {
        getLog().info("Migrations location is " + migrationsDirectory.getAbsolutePath());
        if (!migrationsDirectory.exists()) {
            getLog().info("Migrations location not found. Creating migrations directory");
            migrationsDirectory.mkdirs();
        }

        String timeStamp = DateTime.now().toString(VERSION_PATTERN);
        String migrationName = MessageFormat.format(FLYWAY_DB_MIGRATION_PATTERN, timeStamp, name);
        getLog().info("Creating new migration: " + migrationName);

        FileWriter writer = null;
        try {
            File migration = new File(migrationsDirectory, migrationName);
            writer = new FileWriter(migration);
            //Write name comment
            writer.write("# " + name);

            getLog().info("Migration created " + migration.getAbsolutePath());
        } catch (IOException ex) {
            throw new MojoExecutionException("Error creating new migration file", ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    //Ignore
                }
            }
        }
    }
}
