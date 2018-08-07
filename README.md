# Thunderbolt
A Gatling Abstraction Layer using Scala. Named after the A10 Thunderbolt (famous for its Gatling cannons). This abstraction layer is best used when orchestrated calls to a service are required, which is a weakness in the otherwise exceptional tool. Gatling is immutable at run time, so once a Scenario has been defined it cannot be amended on the fly. Because most uses of Gatling involve hard coded Scenario information, this means that the dynamic creation of calls in not immediately available.

By enabling the dynamic creation of Scenarios, Thunderbolt enables a performance tester to orchestrate Scenarios, create dynamic feeder files from the results of previous scenarios and to dynamically create load profiles.
