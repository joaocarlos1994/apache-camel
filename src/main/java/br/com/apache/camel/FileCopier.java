package br.com.apache.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileCopier {

    // Routes in Camel are defined in such a way that they flow when read
    public static void main(final String... args) throws Exception {
        final CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("file:data/inbox?noop=true")
                        .to("file:data/outbox");
            }
        });

        context.start();
        Thread.sleep(10000);
        context.stop();

    }
}
