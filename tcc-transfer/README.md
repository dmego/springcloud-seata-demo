cURL Request Example
```shell
curl --location --request POST 'http://127.0.0.1:8082/tcc' \
--header 'Content-Type: application/json' \
--data-raw '{
    "inId": "1",
    "outId": "1",
    "amount": "1"
}'
```