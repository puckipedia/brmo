/*
 * Copyright (C) 2022 B3Partners B.V.
 *
 * SPDX-License-Identifier: MIT
 *
 */

package nl.b3p.brmo.nhr.loader.cli;

import java.io.BufferedReader;
import java.io.FileReader;
import nl.b3p.brmo.loader.BrmoFramework;
import nl.b3p.brmo.nhr.loader.NHRCertificateOptions;
import nl.b3p.brmo.nhr.loader.NHRDatabaseOptions;
import nl.b3p.brmo.nhr.loader.NHRLoader;
import nl.b3p.brmo.nhr.loader.NHRLoaderUtils;
import nl.kvk.schemas.schemas.hrip.dataservice._2015._02.Dataservice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.geotools.util.logging.Logging;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "nhr-loader", mixinStandardHelpOptions = true, versionProvider = NHRLoaderMain.class,
    resourceBundle = NHRLoaderUtils.BUNDLE_NAME, subcommands = {})
public class NHRLoaderMain implements IVersionProvider {
    private static Log log;

     /**
     * init logging.
     *
     * @param standAlone set to {@code false} when using in a preconfigured environment, eg. calling methods from a servlet,
     *                   use {@code true} for commandline usage.
     */
    public static void configureLogging(boolean standAlone) {
        if (standAlone) {
            PropertyConfigurator.configure(NHRLoaderMain.class.getResourceAsStream("/nhr-loader-cli-log4j.properties"));
            log = LogFactory.getLog(NHRLoaderMain.class);
            try {
                Logging.ALL.setLoggerFactory("org.geotools.util.logging.Log4JLoggerFactory");
            } catch (ClassNotFoundException ignored) {
            }
        } else {
            log = LogFactory.getLog(NHRLoaderMain.class);
        }
    }

    public static void main(String... args) {
        configureLogging(true);

        CommandLine cmd = new CommandLine(new NHRLoaderMain())
                .setUsageHelpAutoWidth(true);
        System.exit(cmd.execute(args));
    }

    @Override
    public String[] getVersion() {
        return new String[] {
                NHRLoaderUtils.getLoaderVersion(),
                NHRLoaderUtils.getUserAgent()
        };
    }

    @Command(name = "request", sortOptions = false)
    public int process(
            @Mixin NHRLoadOptions loadOptions,
            @Mixin NHRCertificateOptions certificateOptions,
            @Mixin NHRDatabaseOptions databaseOptions,
            @Parameters(paramLabel = "<nummers>") String[] nummers,
            @Option(names={"-h","--help"}, usageHelp = true) boolean showHelp) throws Exception {

        log.info(NHRLoaderUtils.getUserAgent());

        Dataservice dataservice = NHRLoadUtils.getDataservice(loadOptions.getLocation(), loadOptions.getPreprod(), certificateOptions);
        BrmoFramework brmoFramework = NHRLoadUtils.getFramework(databaseOptions);

        for (String nummer : nummers) {
            NHRLoader.sendSingleRequest(dataservice, brmoFramework, nummer, null);
        }

        brmoFramework.closeBrmoFramework();

        return ExitCode.OK;
    }

    @Command(name = "file", sortOptions = false)
    public int file(
            @Mixin NHRLoadOptions loadOptions,
            @Mixin NHRCertificateOptions certificateOptions,
            @Mixin NHRDatabaseOptions databaseOptions,
            @Parameters(paramLabel = "<bestanden>") String[] files,
            @Option(names={"-h","--help"}, usageHelp = true) boolean showHelp) throws Exception {

        log.info(NHRLoaderUtils.getUserAgent());

        Dataservice dataservice = NHRLoadUtils.getDataservice(loadOptions.getLocation(), loadOptions.getPreprod(), certificateOptions);
        BrmoFramework brmoFramework = NHRLoadUtils.getFramework(databaseOptions);

        for (String file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while (reader.ready()) {
                    String nummer = reader.readLine();
                    try {
                    NHRLoader.sendSingleRequest(dataservice, brmoFramework, nummer, null);
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        }

        brmoFramework.closeBrmoFramework();

        return ExitCode.OK;
    }
}
