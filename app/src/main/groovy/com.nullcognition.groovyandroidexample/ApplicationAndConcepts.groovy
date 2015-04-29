/*
--------------------------------------------------

Application of GPars Concepts:

parallel collections - iterrated or processed with each(), collect(), find() where each element is independent of the other
asynchronous invocation support - background long lasting computation, async can be composed for quick parallization
dataflow variables and channels - parallize internally sequential tasks to run concurrently with others to express their dependencies and to exchange data safely
agents - wrap data and serialize access, where multiple threads are using shared mutable state

message passing architectures -
   groovy csp - highly deterministic and composable model for concurrent processes, calculations or processes run concurrently and communicate through synchronous channels
   dataflow operations - complex data processing to build a network, event-driven transformations wired into pipelines using asynchronous channels
   actors and active objects - general-purpose, highly concurrent and scalable architecture following the object-oriented paradigm

--------------------------------------------------

Data Decomposition -
   geometric decomposition - parallel collections
   recursive - fork/join

Task Decomposition -
   independent tasks - parallel collections, dataflow tasks
   recursively dependent tasks - fork/join
   tasks with mutual dependencies - composable asynchronous functions, dataflow tasks, groovy csp
   tasks cooperating on the same data - software transactional memory, agents

Streamed Data Decomposition -
   pipline - dataflow channels and operators
   event based - actors, active objects

--------------------------------------------------

see link for detailed comparison
http://gpars.codehaus.org/Concepts+compared

 */
