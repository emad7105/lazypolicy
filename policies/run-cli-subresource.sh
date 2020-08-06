#!/usr/bin/env bash

./opa eval --input jsons/input.json --data 19-subresource.rego --unknowns data.accountStates --format pretty --partial data.vfinance.allow
