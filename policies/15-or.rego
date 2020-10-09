package vfinance

#default allow = false
default allow_partial = false

allow_partial {
  input.action == "GET"
  brokerId := input.subject.brokerIds[_]
  data.accountStates.broker.id == brokerId
  data.accountStates.issue > input.resource.issue
  data.accountStates.location == input.resource.location
}

allow_partial {
  input.action == "GET"
  brokerId := input.subject.brokerIds[_]
  data.accountStates.broker.id == brokerId
  data.accountStates.issue <= input.resource.issue
  data.accountStates.location != input.resource.location
}

allow_partial {
  input.action == "GET"
  brokerId := input.subject.brokerIds[_]
  data.accountStates.broker.id != brokerId
  data.accountStates.issue >= "2019i"
}
