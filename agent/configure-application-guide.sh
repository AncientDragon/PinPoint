#!/bin/bash
set -e
set -x

#check pinpoint_agentId variable or environment variable
# export pinpoint_agentId=XXXXXXXX
echo "pinpoint_agentId"
echo $pinpoint_agentId

# check pinpoint_agent variable or environment variable，if not，set this variable with  "pinpoint-boolstrap-***.jar" full path, global environment variable suggested.
# export pinpoint_agent=XXXXXXXX
echo "pinpoint_agent:"
echo $pinpoint_agent

#check pinpoint_applicationName variable or environment variable
# export pinpoint_applicationName=XXXXXXXX
echo "pinpoint_applicationName:"
echo $pinpoint_applicationName

# configure the script which starting java progress, eg:
# agent=`[ -z "$pinpoint_agent" ] && echo "" || echo " -javaagent:$pinpoint_agent"`
# echo $agent
# java $agent main-class
#