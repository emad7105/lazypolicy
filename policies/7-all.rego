package vfinance

default allow_partial = false

# Rule: There must be be no account statement from this location (e.g., Belgium)
# Universal quantification is a logical constant,,which clarify as “given any” 
# or “for all. In other words, it is described as “any among a set” or “all in 
# the set”. It conveys that universal quantification can be satisfied by every 
# member of the set. In short, its result depends on each and every member of the set.


allow_partial {
  input.action == "GET"
  input.path[0] == "accountStates"
  not any_located_in_this_location
}

any_located_in_this_location {
  statementId := input.path[1]
  data.accountStates[statementId].location == input.resource.location
}

# TODO: the above rules do not really represent Universal quantification. This rule says
# the statement ID, e.g. 25, is not from a prticular location, e.g., Belgium. However, 
# what we actually want is: There must be be no account statement from this location (e.g., Belgium)
# Note: not really sure what it means in SQL though.


# Search here https://www.openpolicyagent.org/docs/latest/policy-language/#universal-quantification-for-all
# for 'Universal Quantification'