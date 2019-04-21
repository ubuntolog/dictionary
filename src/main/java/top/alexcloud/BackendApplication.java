package top.alexcloud;

import top.alexcloud.health.AppHealthCheck;
import top.alexcloud.resources.MiscResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.LoggerFactory;

public class BackendApplication extends Application<BackendConfiguration> {
    private static final ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MiscResource.class);

    public static void main(String[] args) throws Exception{
        new BackendApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<BackendConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/frontend/webui/app/", "/", "index.html", "static"));
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
    }

    @Override
    public void run(BackendConfiguration configuration, Environment environment) {
        MiscResource miscResource = new MiscResource(configuration);

        environment.getApplicationContext().setErrorHandler(new HttpErrorHandler());
        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(miscResource);
        environment.jersey().setUrlPattern("/api/*");
        environment.healthChecks().register("dictionary", new AppHealthCheck());
    }
}
