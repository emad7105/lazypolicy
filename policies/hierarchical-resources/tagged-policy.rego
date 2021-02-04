package vfinance

default allow = false
default allow_brokers = false
default allow_accountstates = false

allow {
  allow_brokers
  allow_accountstates
}

allow_brokers {
  input.path[0] == token.payload.bid
  data.broker.id == token.payload.bid
  data.broker.vat == token.payload.vat
}

allow_accountstates {
  input.path[1] == token.payload.asid
  data.accountStates.id == token.payload.asid
  data.accountStates.creationDate == token.payload.cdate
}




# Helper to get the token payload.
token = {"payload": payload} {
  [header, payload, signature] := io.jwt.decode(input.token)
}

#{
# "bid": "2",
# "vat": "124454",
# "asid": "33",
# "cdate": 1420070400,
# "jti": "5d1f944d-73c2-45af-923f-ed8dabc8eda6",
# "iat": 1605222579,
# "exp": 1605226179
#}

#  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJiaWQiOiIyIiwidmF0IjoiMTI0NDU0IiwiYXNpZCI6IjMzIiwiY2RhdGUiOjE0MjAwNzA0MDAsImp0aSI6IjVkMWY5NDRkLTczYzItNDVhZi05MjNmLWVkOGRhYmM4ZWRhNiIsImlhdCI6MTYwNTIyMjU3OSwiZXhwIjoxNjA1MjI3NjcyfQ.N-BqMtVMrV1mGsR2aOFRHcE_kJHZZCVrVRwayvlm3dI"
