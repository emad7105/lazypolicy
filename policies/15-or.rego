package vfinance

#default allow = false
default allow_partial = false

allow_partial {
  input.action == "GET"
  input.path == ["accountStates", statementId] 
  brokerId := input.subject.brokerIds[_]
  data.accountStates[statementId].brokerId == brokerId
}

allow_partial {
  input.action == "GET"
  input.path == ["accountStates", statementId]
  data.accountStates[statementId].location == input.resource.location
}
