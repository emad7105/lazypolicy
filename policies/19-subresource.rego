package vfinance

default allow = false
default allow_broker = false
default allow_account_states = false

allow {
  allow_broker
  allow_account_states
}

allow_broker {
  brokerId := input.subject.brokerIds[_]
  data.broker.id == brokerId
}

allow_account_states {
  account_state_id := input.resource.statementIds[_]
  data.account_state.id == account_state_id
}

