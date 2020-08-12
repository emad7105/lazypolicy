package vfinance

#default allow = false
default allow_partial = false

allow_partial {
  input.action == "GET"
  statementId := input.resource.statementIds[_]
  brokerId := input.subject.brokerIds[_]
  data.accountStates[statementId].brokerId == brokerId
}