package vfinance

#default allow = false
default allow_partial = false

allow_partial {
  input.action == "GET"
  data.accountStates.issue < input.resource.issue
}
