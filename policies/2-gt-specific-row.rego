package vfinance

#default allow = false
default allow_partial = false

allow_partial {
  input.action == "GET"
  statementId := input.path[1]
  data.accountStates.id == statementId
  data.accountStates.issue > input.resource.issue
}
