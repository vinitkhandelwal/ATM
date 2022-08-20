ATM

This is Spring Boot Application.
Assumption : Authorized user will access this API so here I haven't implemented any security.
Created two dummy customer with account and card once application bootstrap.

Get Account Detail
GET http://localhost:8081/account/100

Deposit Amount
PUT http://localhost:8081/account/deposit/100
{"accountId" : 100,
"amount" : 2000
}

Transfer Amount
POST http://localhost:8081/account/withdraw/100
{"fromAccount" : 100,
"toAccount":200,
"amount" : 20
}




			