#!/bin/bash

opa eval --input input.json --data policy.rego --unknowns data.accountStates --format pretty --partial "data.vfinance.allow"
