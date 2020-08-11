#!/bin/bash

# read -p "Policy (defult: policy): " policy
# policy=${policy:-policy}

# read -p "Input (default: input): " input
# input=${input:-input}

# read -p "Unknown (default: data.accountStates): " unknown
# unknown=${unknown:-data.accountStates}

# read -p "Query (default: data.vfinance.allow_partial): " query
# query=${query:-data.vfinance.allow_partial}

# echo "-----------"
# echo "input => "  $input.json
# echo "policy => " $policy.rego
# echo "unknown => " $unknown
# echo "query => " $query
# echo "-----------"

# ./opa eval --input jsons/$input.json --data $policy.rego --unknowns $unknown --format pretty --partial $query

input="input"
unknown="data.accountStates"
query="data.vfinance.allow_partial"

policies=( 
"1-eq"
"1-eq-specific-row"
"2-gt"
"2-gt-specific-row"
"3-gte"
"3-gte-specific-row"
"4-lt"
"4-lt-specific-row"
"5-lte"
"5-lte-specific-row"
"6-neq"
#"7-all"
"8-and"

#"9-any"
#"10-between"
#"11-exist"
#"12-exists"
#"13-like"
#"14-not"

#"15-or"
#"16-eq-some-rows"
#"17-eq-all-rows"
#"18-eq-relations"
#"paul-eq"
)

#for policy in `ls *.rego`
for policy in "${policies[@]}"
do
   printf "\n\n$policy\n"
   ./opa eval --input jsons/input.json --data $policy.rego --unknowns $unknown --format pretty --partial $query
done

bash run-cli-subresource.sh