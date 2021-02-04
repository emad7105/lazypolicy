package vfinance

default allow_partial = false

allow_partial {
  input.action == "GET"
  statementId := input.path[1]
  brokerId := input.subject.brokerIds[_]
  data.accountStates.id == statementId
  data.accountStates.broker.id == brokerId
}