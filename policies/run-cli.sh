#!/bin/bash

read -p "Policy (defult: policy): " policy
policy=${policy:-policy}

read -p "Input (default: input): " input
input=${input:-input}

read -p "Unknown (default: data.accountStates): " unknown
unknown=${unknown:-data.accountStates}

read -p "Query (default: data.vfinance.allow_partial): " query
query=${query:-data.vfinance.allow_partial}

echo "-----------"
echo "input => "  $input.json
echo "policy => " $policy.rego
echo "unknown => " $unknown
echo "query => " $query
echo "-----------"

./opa eval --input jsons/$input.json --data $policy.rego --unknowns $unknown --format pretty --partial $query