#!/bin/bash


# response folder
rm -f responses
mkdir responses

# start the server
#rm -f server.log && touch server.log
./opa run -s > responses/server.log 2>&1 &

input="input"
unknown="data.accountStates"
query="data.vfinance.allow_partial"

policies=( 
"1-eq" 
"2-gt" 
"3-gte"
"4-lt"
"5-lte"
"6-neq"
"7-all"
"8-and"
#"9-any"
#"10-between"
#"11-exist"
#"12-exists"
#"13-like"
#"14-not"
"15-or"
)

#for policy in `ls *.rego`
for policy in "${policies[@]}"
do
   printf "\n\n$policy\n"
   
   # ---> Create a policy:  
   curl -X PUT --data-binary @$policy.rego 127.0.0.1:8181/v1/policies/$policy
   echo $policy".rego is created."
   #curl 127.0.0.1:8181/v1/policies/$policy

   # ----> Partial evaluation of a policy:  
   # curl -X POST --data-binary @$policy-query.json 127.0.0.1:8181/v1/compile?pretty
   curl -X POST --data-binary @jsons/query.json 127.0.0.1:8181/v1/compile?pretty -o responses/$policy.json

   # ----> Delete the policy
   curl -X DELETE 127.0.0.1:8181/v1/policies/$policy
done



# stop the server
pgrep opa | xargs kill