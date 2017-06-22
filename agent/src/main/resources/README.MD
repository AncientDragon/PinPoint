# Pinpoint agent application guide

## Install
* download agent file and unzip it
* configure the bootstrap agent's configuration files, such as *pinpoint.config*, *lib/log4j.xml*
* configure variable or environment variable: *pinpoint_agent*, export this variable with  
 `pinpoint-boolstrap-***.jar` file's full path.
  recommendation: `export pinpoint_agent=/etc/pinpoint/agent/pinpoint-boolstrap-***.jar` in */etc/profile* as 
  a global environment variable
  
## Configure
**Pinpoint** need the configure three things to startup. `pinpoint.agentId`, `pinpoint.applicationName`,`pinpoint_agent`, the 
following:

* `pinpoint.agentId` or `pinpoint_agentId`. it's should be unique id. you can configure it with environment variable or java startup parameter
   such as `export pinpoint_agentId=XXXXXX`, `java -Dpinpoint.agentId=XXXXXX`.
   if this variable has not been set, the bootstrap agent will generate a value with host ip and name,such as `192.168.1.1_hostname`.
   the recommendation method is that it's not been set.
  
* `pinpoint.applicationName` or `pinpoint_applicationName`. it's set to the application name, you can cofigure it with environment variable or 
   java startup parameter such as `export pinpoint_applicationName=XXXX`, `java -Dpinpoint.applicationName=XXXX`.
   the recommendation method is environment variable

* `pinpoint_agent`. pinpoint use java's `-javaagent` mechanism. `-javaagent` should configure the agent jar file's full path.
  that's why the *Install* period configure this variable.
 
## Example
* **TOMCAT** 
  > **catalina.sh** </br>
    `export pinpoint_applicationName=testApp`</br>
    `JAVA_OPTS=" -javaagent:$pinpoint_agent  $JAVA_OPTS "`

## **Shell**
  > **bin.sh** </br>
  `export pinpoint_applicationName=testApp` </br>
  `agent=``[ -z "$pinpoint_agent" ] && echo "" || echo " -javaagent:$pinpoint_agent"` </br>
  `java $agent main-class` </br>
    
