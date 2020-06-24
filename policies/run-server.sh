#!/bin/bash

read -p "Policy (defult: policy): " policy
policy=${policy:-policy}

echo "-----------"
echo "policy => " $policy.rego
echo "query => " $policy-query.json
echo "-----------"


# start the server
#rm -f server.log && touch server.log
./opa run -s > server.log 2>&1 &

# Fetch policies:  
# curl 127.0.0.1:8181/v1/policies?pretty

# Create a policy:  
curl -X PUT --data-binary @$policy.rego 127.0.0.1:8181/v1/policies/$policy
echo $policy".rego is created."
#curl 127.0.0.1:8181/v1/policies/$policy

# Partial evaluation of a policy:  
# curl -X POST --data-binary @$policy-query.json 127.0.0.1:8181/v1/compile?pretty
curl -X POST --data-binary @jsons/query.json 127.0.0.1:8181/v1/compile  | python -m json.tool | pygmentize -l json


# stop the server
pgrep opa | xargs kill