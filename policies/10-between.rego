package vfinance

#default allow = false
default allow_partial = false

allow_partial {
  input.action == "GET"
  2010 < data.accountStates.issue < input.resource.issue
}
