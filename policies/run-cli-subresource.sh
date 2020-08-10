#!/usr/bin/env bash

printf "\n\n19-subresource\n"
./opa eval --input jsons/input.json --data 19-subresource.rego --unknowns data.account_state,data.broker --format pretty --partial data.vfinance.allow
