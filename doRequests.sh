#!/bin/bash

#Made by kandidatnr: 2005 - 25/11/21
#In the spirit of DevOps, automating boring tasks
#Simple shell script to send N hardcoded http requests

URL="localhost:8080";
N=10; #Number of requests to send

echo "Select request: (1-3 + enter)";
echo "1. update account";
echo "2. transfer";
echo "3. get account";
read INPUT;

if [ $INPUT == 1 ]
then
	for ((i = 1; i < $N+1; i++));
	do
		echo "Sending POST request to /account"
		printf "Response: "
		curl -X POST $URL/account -H 'Content-Type: application/json' -d '{"Currency": "NOK", "id": "too", "balance": '$i'}';
		printf "\n";
	done
elif [ $INPUT == 2 ]
then 
	for ((i = 0; i < $N; i++));
	do
		echo "Sending POST request to /account/from/transfer/too"
		printf "Response: "
		curl -X POST $URL/account/from/transfer/too -H 'Content-Type: application/json' -d '{"amount": 1500}'
		printf "\n";
	done
elif [ $INPUT == 3 ]
then
	for ((i = 0; i < $N; i++));
	do
		echo "Sending GET request to /account/too"
		printf "Response: "
		curl -X GET $URL/account/too -H 'Content-Type: application/json'
		printf "\n";
	done
else
	echo "Input error"
fi
