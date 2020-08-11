package vfinance

#default allow = false
default allow_partial = false

allow_partial {
  input.action == "GET"
  statementId := input.path[1]
  brokerId := input.subject.brokerIds[_]
  data.accountStates.id = statementId
  data.accountStates.broker.Id == brokerId
  data.accountStates.location == input.resource.location
}
