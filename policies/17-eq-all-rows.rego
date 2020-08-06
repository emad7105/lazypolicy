package vfinance

#default allow = false
default allow_partial = false

allow_partial {
  input.action == "GET"
  brokerId := input.subject.brokerIds[_]
  data.accountStates.brokerId == brokerId
}