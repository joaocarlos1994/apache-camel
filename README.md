# apache-camel

<h1>Camel’s message model</h1>
In Camel, there are two abstractions for modeling messages, both of which we’ll cover
in this section.
<ol>
    <li>
        org.apache.camel.Message — The fundamental entity containing the data
        being carried and routed in Camel
    </li>
    <li>
        org.apache.camel.Exchange — The Camel abstraction for an exchange of messages. 
        This exchange of messages has an "in" message and as a reply, an "out" message
    </li>
</ol>

<h3>Message</h3>
<p>
    Messages are the entities used by systems to communicate with each other when using messaging channels.
    Messages flow in one direction from a sender to a Sender Receiver receiver.     
</p>

<p>
    Messages have a body (a payload), headers, and optional attachments
</p>

<p>
    Messages are uniquely identified with an identifier of type
    java.lang.String . The identifier’s uniqueness is enforced
    and guaranteed by the message creator, it’s protocol dependent,
    and it doesn’t have a guaranteed format. For protocols that don’t define a 
    unique message identification scheme,  Camel uses its own UID generator.
</p>


<h3>HEADERS AND ATTACHMENTS<h3>
<p>
    Headers are values associated with the message, such as sender
    identifiers, hints about content encoding, authentication 
    information, and so on. Headers are name-value pairs; the name is a
    unique, case-insensitive string, and the value is of type java.
    lang.Object . This means that Camel imposes no constraints on
    the type of the headers. Headers are stored as a map within the
    message. A message can also have optional attachments, which
    are typically used for the web service and email components.
</p>

<h3>BODY<h3>
<p>
    The body is of type java.lang.Object . That means that a message can store any kind
    of content. It also means that it’s up to the application designer to make sure that the
    receiver can understand the content of the message. When the sender and receiver
    use different body formats, Camel provides a number of mechanisms to transform the
    data into an acceptable format, and in many cases the conversion happens automatically 
    with type converters, behind the scenes.
</p>


<h3>FAULT FLAG</h3>
<p>
    Messages also have a fault flag. Some protocols and specifications, such as WSDL and
    JBI , distinguish between output and fault messages. They’re both valid responses to
    invoking an operation, but the latter indicates an unsuccessful outcome. In general,
    faults aren’t handled by the integration infrastructure. They’re part of the contract
    between the client and the server and are handled at the application level.
    During routing, messages are contained in an exchange.
</p>

<h2>Exchange</h2>
<p>
    An exchange in Camel is the message’s container during routing. An exchange also
    provides support for the various types of interactions between systems, also known as
    message exchange patterns ( MEP s). MEP s are used to differentiate between one-way
    and request-response messaging styles. The Camel exchange holds a pattern property
    that can be either
</p>
<ol>
    <li>InOnly — A one-way message (also known as an Event message). For example, JMS messaging is often one-way messaging.</li>
    <li>
        InOut — A request-response message. For example, HTTP-based transports are often request reply, where a client requests 
        to retrieve a web page, waiting for the reply from the server.
    </li>
</ol>
<p>
    Contents of an exchange in Camel
    
    Exchange ID — A unique ID that identifies
    the exchange. Camel will generate a default
    unique ID , if you don’t explicitly set one.
    
    MEP — A pattern that denotes whether
    you’re using the InOnly or InOut messaging style. 
    When the pattern is InOnly, the
    exchange contains an in message. For
    InOut, an out message also exists that con-
    tains the reply message for the caller.
    
    Exception — If an error occurs at any time during routing, an Exception will be
    set in the exception field.
    
    Properties — Similar to message headers, but they last for the duration of the
    entire exchange. Properties are used to contain global-level information,
    whereas message headers are specific to a particular message. Camel itself will
    add various properties to the exchange during routing. You, as a developer, can
    store and retrieve properties at any point during the lifetime of an exchange.
    
    In message — This is the input message, which is mandatory. The in message contains 
    the request message.
    
    Out message — This is an optional message that only exists if the MEP is InOut.
    The out message contains the reply message.
</p>

<p>
    The routing engine uses routes as specifications for where messages are routed.
    Routes are defined using one of Camel’s domain-specific languages (DSL's). 
    Processors are used to transform and manipulate messages during routing and also to
    implement all the EIP patterns, which have corresponding keywords in the DSL 
    languages. Components are the extension points in Camel for adding connectivity to
    other systems. To expose these systems to the rest of Camel, components provide an
    endpoint interface.
</p>

<h2>CAMELCONTEXT</h2>
<p>
Components
Contains the components used. Camel is capable of loading components on the fly
either by autodiscovery on the classpath or when a new bundle is activated in an OSGi
container. In chapter 7 we’ll discuss components in more detail.

Endpoints
Contains the endpoints that have been created.

Routes
Contains the routes that have been added. We’ll cover routes in chapter 2.

Type Converters
Contains the loaded type converters. Camel has a mechanism that allows you to manually 
or automatically convert from one type to another. Type converters are covered in chapter 3

Data formats
Contains the loaded data formats. Data formats are covered in chapter 3.

Registry
Contains a registry that allows you to look up beans. By default, this will be a JNDI registry.
If you’re using Camel from Spring, this will be the Spring ApplicationContext . It can
also be an OSGi registry if you use Camel in an OSGi container. We’ll cover registries in
chapter 4.

Languages
Contains the loaded languages. Camel allows you to use many different languages to
create expressions. You’ll get a glimpse of the XPath language in action when we cover
the DSL. A complete reference to Camel’s own Simple expression language is available
in appendix A.

Each route in Camel has a unique identifier that’s used for logging, debugging, 
monitoring, and starting and stopping routes. Routes also have exactly one input
source for messages, so they’re effectively tied to an input endpoint.

</p>

<h2>PROCESSOR</h2>
<p>
The processor is a core Camel concept that represents a node capable of using, creat-
ing, or modifying an incoming exchange. During routing, exchanges flow from one
processor to another; as such, you can think of a route as a graph having specialized
processors as the nodes, and lines that connect the output of one processor to the
input of another. Many of the processors are implementations of EIP s, but one could
easily implement their own custom processor and insert it into a route.
</p>

<h2>PROCESSOR</h2>
<p>
Components are the main extension point in Camel. To date, there are over 80 com-
ponents in the Camel ecosystem that range in function from data transports, to DSL s,
data formats, and so on. You can even create your own components for Camel.

From a programming point of view, components are fairly simple: they’re associ-
ated with a name that’s used in a URI , and they act as a factory of endpoints. For exam-
ple, a FileComponent is referred to by file in a URI , and it creates FileEndpoint s.
The endpoint is perhaps an even more fundamental concept in Camel.
</p>

<h2>ENDPOINT</h2>
<p>
An endpoint is the Camel abstraction that models the end of a channel through
which a system can send or receive messages.

In a nutshell, an endpoint acts as a factory for creating consumers
and producers that are capable of receiving and sending messages to a particular end-
point.
</p>

<h2>PRODUCER</h2>
<p>
A producer is the Camel abstraction that refers to an entity capable of creating and
sending a message to an endpoint.

When a message needs to be sent to an endpoint, the producer will create an
exchange and populate it with data compatible with that particular endpoint. For
example, a FileProducer will write the message body to a file.

A JmsProducer , on the other hand, will map the Camel message to
a javax.jms.Message before sending it to a JMS destination. This is an important 
feature in Camel, because it hides the complexity of interacting with particular
transports. All you need to do is route a message to an endpoint, and the producer 
does the heavy lifting.
</p>

<h2>CONSUMER</h2>
<p>
A consumer is the service that receives messages produced by a producer, wraps them
in an exchange, and sends them to be processed. Consumers are the source of the
exchanges being routed in Camel.

The consumer create a new exchange, a consumer will use the endpoint that
wraps the payload being consumed. A processor is then used to initiate the routing of
the exchange in Camel using the routing engine.
</p>

<h2>EVENT-DRIVEN CONSUMER</h2>
<p>
This kind of consumer is mostly associated with client-server architectures and web
services. It’s also referred to as an asynchronous receiver in the EIP world. An event-driven
consumer listens on a particular messaging channel usually a TCP/IP port or a JMS queue,
and waits for a client to send messages to it. When a message arrives, the consumer wakes up 
and takes the message for processing.
</p>

<h2>POLLING CONSUMER</h2>
<p>
In contrast to the event-driven consumer, the polling consumer actively goes and fetches
messages from a particular source, such as an FTP server. The olling consumer is also known 
as a synchronous receiver in EIP lingo, because it won’t poll for more messages until it has 
finished processing the current message. A common flavor of the polling consumer is the 
scheduled polling consumer, which polls at scheduled intervals. File, FTP , and email 
transports all use scheduled polling consumers.
</p>






