package org.ethelred.heatcontrol3;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import org.ethelred.heatcontrol3.template.DynamicTemplates;
import org.ethelred.heatcontrol3.template.StaticTemplates;
import org.ethelred.heatcontrol3.template.Templates;

import java.nio.file.Paths;

@Factory
public class TemplatesFactory {
    @Singleton
    @Requires(property = "micronaut.views.jte.dynamic", value = "false", defaultValue = "false")
    public Templates staticTemplates() {
        return new StaticTemplates();
    }

    @Singleton
    @Requires(property = "micronaut.views.jte.dynamic", value = "true")
    public Templates dynamicTemplates(TemplateEngine engine) {
        return new DynamicTemplates(engine);
    }

    @Singleton
    @Requires(property = "micronaut.views.jte.dynamic", value = "true")
    public TemplateEngine newDynamicTemplateEngine(
            @Property(name = "micronaut.views.jte.dynamic-source-path") String templatePath) {
        return TemplateEngine.create(new DirectoryCodeResolver(Paths.get(templatePath)), ContentType.Html);
    }
}
