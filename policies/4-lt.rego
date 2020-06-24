package vfinance

#default allow = false
default allow_partial = false

allow_partial {
  input.action == "GET"
  input.path == ["accountStates", statementId] 
  data.accountStates[statementId].issue < input.resource.issue
}
