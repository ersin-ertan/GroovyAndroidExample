/*
--------------------------------------------------

Left shift operator << has be overloaded on actors, agents and dataflow expressions (both variables and streams) to mean
send a message or assign a value.

myActor << 'message'
myAgent << {account -> account.add('5 USD')}

myDataflowVariable << 120332

--------------------------------------------------

On actors and agents the default call() method has been also overloaded to mean send.
So sending a message to an actor or agent may look like a regular method call.

myActor "message"
myAgent {house -> house.repair()}

--------------------------------------------------

The rightShift operator >> in GPars has the when bound meaning. So...

myDataflowVariable >> {value -> doSomethingWith(value)}

will schedule the closure to run only after myDataflowVariable is bound to a value, with the value as a parameter.

--------------------------------------------------

Samples we tend to statically import frequently used factory methods:

GParsPool.withPool()
GParsPool.withExistingPool()
GParsExecutorsPool.withPool()
GParsExecutorsPool.withExistingPool()
Actors.actor()
Actors.reactor()
Actors.fairReactor()
Actors.messageHandler()
Actors.fairMessageHandler()
Agent.agent()
Agent.fairAgent()
Dataflow.task()
Dataflow.operator()

--------------------------------------------------

*/
