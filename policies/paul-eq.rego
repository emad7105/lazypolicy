package vfinance

default allow_partial = false

allow_partial {
  input.action == "GET"
  brokerId := input.subject.brokerIds[_]
  data.accountStates.broker.id == brokerId
}